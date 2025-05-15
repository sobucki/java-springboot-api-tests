package br.com.sobucki.productmanager.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
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

}