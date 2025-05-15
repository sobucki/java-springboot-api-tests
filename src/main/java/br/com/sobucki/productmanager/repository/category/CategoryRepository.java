package br.com.sobucki.productmanager.repository.category;

import java.util.UUID;

import br.com.sobucki.productmanager.model.Category;
import br.com.sobucki.productmanager.repository.base.BaseRepository;

public interface CategoryRepository extends BaseRepository<Category, UUID> {
  // This interface extends BaseRepository with Category as the entity type and
  // String as the ID type.
  // It inherits all the methods from BaseRepository.
  // You can add any additional methods specific to Category if needed.

}
