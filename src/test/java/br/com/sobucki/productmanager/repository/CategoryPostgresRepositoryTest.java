package br.com.sobucki.productmanager.repository;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.sobucki.productmanager.model.Category;
import br.com.sobucki.productmanager.repository.category.CategoryPostgresRepository;

@SpringBootTest
@ActiveProfiles("test")
class CategoryPostgresRepositoryTest {

    @Autowired
    private CategoryPostgresRepository repository;

    @AfterEach
    void cleanup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("should save category")
    void shouldSaveProduct() {
        // given
        Category category = new Category(null, "Categoria Teste", "Descrição da Categoria");

        // when
        Category saved = repository.save(category);

        // then
        assertNotNull(saved.getId());
        assertEquals("Categoria Teste", saved.getName());
        assertEquals("Descrição da Categoria", saved.getDescription());
        assertNotNull(saved.getCreatedDate());
        assertNotNull(saved.getLastUpdatedDate());
        assertNotNull(saved.getCreatedBy());
        assertNotNull(saved.getUpdatedBy());
        assertEquals(saved.getCreatedDate(), saved.getLastUpdatedDate());
    }

    @Test
    @DisplayName("should list all categories")
    void shouldListAllCategories() {
        // given
        Category category1 = new Category(null, "Categoria Teste 1", "Descrição da Categoria 1");
        Category category2 = new Category(null, "Categoria Teste 2", "Descrição da Categoria 2");
        repository.save(category1);
        repository.save(category2);

        // when
        var categories = repository.findAll();

        // then
        assertNotNull(categories);
        assertEquals(2, categories.size());
    }

    @Test
    @DisplayName("should find category by id")
    void shouldFindProductById() {
        // given
        Category category1 = new Category(null, "Categoria Teste", "Descrição da Categoria");
        Category category2 = new Category(null, "Categoria Teste 2", "Descrição da Categoria 2");
        Category saved = repository.save(category1);
        repository.save(category2);

        // when
        Category found = repository.findById(saved.getId());

        // then
        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
        assertEquals(saved.getName(), found.getName());
    }

    @Test
    @DisplayName("should return null when category not found")
    void shouldReturnNullWhenProductNotFound() {
        // when
        Category found = repository.findById(UUID.randomUUID());

        // then
        assertEquals(null, found);
    }

    @Test
    @DisplayName("should delete category")
    void shouldDeleteProduct() {
        // given
        Category category = new Category(null, "Categoria Teste", "Descrição da Categoria");
        Category saved = repository.save(category);

        // when
        boolean result = repository.delete(saved.getId());

        // then
        assertEquals(true, result);
        assertNull(repository.findById(saved.getId()));
    }

    @Test
    @DisplayName("should return false when category not found")
    void shouldReturnFalseWhenProductNotFound() {
        // when
        boolean result = repository.delete(UUID.randomUUID());

        // then
        assertEquals(false, result);
    }

    @Test
    @DisplayName("should save a list of categories")
    void shouldSaveListOfCategories() {
        // given
        Category category1 = new Category(null, "Categoria Teste 1", "Descrição da Categoria 1");
        Category category2 = new Category(null, "Categoria Teste 2", "Descrição da Categoria 2");

        // when
        var saved = repository.saveAll(List.of(category1, category2));

        // then
        assertNotNull(saved);
        assertEquals(2, saved.size());
    }

    @Test
    @DisplayName("should delete all categories")
    void shouldDeleteAllCategories() {
        // given
        Category category1 = new Category(null, "Categoria Teste 1", "Descrição da Categoria 1");
        Category category2 = new Category(null, "Categoria Teste 2", "Descrição da Categoria 2");
        repository.save(category1);
        repository.save(category2);

        // when
        repository.deleteAll();

        // then
        assertEquals(0, repository.findAll().size());
    }

    @Test
    @DisplayName("should update category")
    void shouldUpdateCategory() {
        // given
        Category category = new Category(null, "Categoria Teste", "Descrição da Categoria");
        Category saved = repository.save(category);
        saved.setName("Categoria Atualizada");
        saved.setDescription("Descrição Atualizada");

        // when
        var updated = repository.save(saved);

        // then
        assertNotNull(updated);
        assertEquals(saved.getId(), updated.getId());
        assertEquals("Categoria Atualizada", updated.getName());
        assertEquals("Descrição Atualizada", updated.getDescription());
    }

}