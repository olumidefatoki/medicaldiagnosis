package com.thrive.agric.medicaldiagnosis.repository;


import com.thrive.agric.medicaldiagnosis.domain.PaginationRequest;
import com.thrive.agric.medicaldiagnosis.model.Issue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;


@Transactional
@Repository
@Slf4j
public class BaseRepository {

    @Autowired
    protected EntityManager em;

    public <T> T save(T entity) {
        em.persist(entity);
        em.flush();
        return entity;
    }

    public <T> T update(T entity) {
        em.merge(entity);
        return entity;

    }


    public <T> Optional<T> findOneOptional(Class<T> type, Long id) {
        T obj = em.find(type, id);
        if (!StringUtils.isEmpty(obj)) {
            em.detach(obj);
            return Optional.of(obj);
        }
        return Optional.empty();
    }


    //The filter map object takes care of both OR, AND & LIKE;
    //If the value is of type List means it represents OR; 
    //If the value is of type Map means it represents LIKE;
    //By default all value are chained with AND
    @SuppressWarnings("unchecked")
    public <T> Page<T> findAllBy(Class<T> classz, Map<String, Object> filterTemp, PaginationRequest page) {

        Map<String, Object> filter = new HashMap<>(filterTemp);
        AtomicReference<String> sqlQuery = new AtomicReference<>();
        sqlQuery.set("select e from  " + classz.getSimpleName() + " e " + (filter.isEmpty() ? "" : "where "));

        filter.entrySet().stream().filter(o -> o.getValue() == null).forEach(i -> {
            sqlQuery.set(sqlQuery.get() + " " + i.getKey() + " IS NULL AND");
        });

        filter.entrySet().stream().filter(o -> (o.getValue() != null && !(o.getValue() instanceof List<?>) && !(o.getValue() instanceof Map)))
                .forEach(i -> {
                    sqlQuery.set(sqlQuery.get() + " " + i.getKey() + " = :" + i.getKey() + " AND");
                });

        filter.keySet().stream().filter(o -> (filter.get(o) instanceof Map))
                .forEach(i -> ((Map<String, String>) filter.get(i)).keySet().stream().forEach(k -> {
                    String value = ((Map<String, String>) filter.get(i)).get(k);
                    sqlQuery.set(sqlQuery.get() + " " + k + " LIKE :" + k + " AND");
                }));


        for (String s : filter.keySet()) {
            if ((filter.get(s) != null && filter.get(s) instanceof List<?>)) {
                sqlQuery.set(sqlQuery.get() + "(");
                IntStream.range(0, ((List<?>) filter.get(s)).size()).forEach(index -> {
                    sqlQuery.set(sqlQuery.get() + " " + s + " = :" + s + "" + index + " OR");
                });
                sqlQuery.set(sqlQuery.get().substring(0, sqlQuery.get().length() - 3) + ") AND");
            }
        }

        sqlQuery.set(filter.isEmpty() ? sqlQuery.get() : sqlQuery.get().substring(0, sqlQuery.get().length() - 4));

        TypedQuery<Long> countQuery = em.createQuery(sqlQuery.get().replace("select e from", "select count(e) from"),
                Long.class);
        TypedQuery<T> typeQuery = em.createQuery(sqlQuery.get(), classz);

        filter.keySet().stream().filter(i -> filter.get(i) != null && !(filter.get(i) instanceof List<?>) && !(filter.get(i) instanceof Map)).forEach(i -> {
            typeQuery.setParameter(i, filter.get(i));
            countQuery.setParameter(i, filter.get(i));
        });

        filter.keySet().stream().filter(o -> filter.get(o) != null && (filter.get(o) instanceof List<?>)).forEach(i -> {
            List<?> values = (List<?>) filter.get(i);

            IntStream.range(0, values.size()).forEach(index -> {
                typeQuery.setParameter(i + "" + index, values.get(index));
                countQuery.setParameter(i + "" + index, values.get(index));
            });
        });

        filter.keySet().stream().filter(o -> filter.get(o) != null && (filter.get(o) instanceof Map))
                .forEach(i -> ((Map<String, String>) filter.get(i)).keySet().stream().forEach(k -> {
                    String value = ((Map<String, String>) filter.get(i)).get(k);

                    typeQuery.setParameter(k, value);
                    countQuery.setParameter(k, value);

                }));

        Long contentSize = countQuery.getSingleResult();
        page.setSize(page.getSize() == 0 ? (contentSize.intValue() == 0 ? 1 : contentSize.intValue()) : page.getSize());

        typeQuery.setFirstResult((page.getPage() - 1) * page.getSize()).setMaxResults(page.getSize());

        return new PageImpl<>(typeQuery.getResultList(), PageRequest.of(page.getPage() - 1, page.getSize()),
                contentSize);
    }



}