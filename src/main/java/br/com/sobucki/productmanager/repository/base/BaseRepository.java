package br.com.sobucki.productmanager.repository.base;

import java.util.List;

public interface BaseRepository<T, ID> {
  List<T> findAll();

  T findById(ID id);

  T save(T entity);

  boolean delete(ID id);

  List<T> saveAll(List<T> entities);

  void deleteAll();
}