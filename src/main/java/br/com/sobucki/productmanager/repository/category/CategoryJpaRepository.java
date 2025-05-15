package br.com.sobucki.productmanager.repository.category;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sobucki.productmanager.model.Category;

@Repository
public interface CategoryJpaRepository extends JpaRepository<Category, UUID> {

}
