package br.com.sobucki.productmanager.repository.category;

import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import br.com.sobucki.productmanager.model.Category;

@Repository
@Primary
public class CategoryPostgresRepository implements CategoryRepository {

  private final CategoryJpaRepository jpaRepository;

  public CategoryPostgresRepository(CategoryJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public List<Category> findAll() {
    return jpaRepository.findAll();
  }

  @Override
  public Category findById(UUID id) {
    return jpaRepository.findById(id).orElse(null);
  }

  @Override
  public Category save(Category product) {
    return jpaRepository.save(product);
  }

  @Override
  public boolean delete(UUID id) {
    if (jpaRepository.existsById(id)) {
      jpaRepository.deleteById(id);
      return true;
    }
    return false;
  }

  @Override
  public List<Category> saveAll(List<Category> products) {
    return jpaRepository.saveAll(products);
  }

  @Override
  public void deleteAll() {
    jpaRepository.deleteAll();
  }

}
