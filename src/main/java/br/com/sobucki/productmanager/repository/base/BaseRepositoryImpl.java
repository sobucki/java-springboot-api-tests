package br.com.sobucki.productmanager.repository.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseRepositoryImpl<T, ID> implements BaseRepository<T, ID> {

  protected abstract JpaRepository<T, ID> getRepository();

  @Override
  public boolean delete(ID id) {
    if (getRepository().existsById(id)) {
      getRepository().deleteById(id);
      return true;
    }
    return false;
  }

  @Override
  public void deleteAll() {
    getRepository().deleteAll();
  }

  @Override
  public List<T> findAll() {

    return getRepository().findAll();
  }

  @Override
  public T findById(ID id) {
    return getRepository().findById(id).orElse(null);
  }

  @Override
  public T save(T entity) {
    return getRepository().save(entity);
  }

  @Override
  public List<T> saveAll(List<T> entities) {
    return getRepository().saveAll(entities);
  }

}
