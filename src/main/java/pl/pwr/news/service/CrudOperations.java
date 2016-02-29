package pl.pwr.news.service;

import java.util.List;

/**
 * Created by jakub on 2/29/16.
 */
public interface CrudOperations<T> {

    void save(T entity);

    void delete(T entity);

    List<T> findAll();

    T findById(Long id);

}
