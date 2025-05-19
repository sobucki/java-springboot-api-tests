package br.com.sobucki.productmanager.repository.product;

import java.util.UUID;

import br.com.sobucki.productmanager.model.Product;
import br.com.sobucki.productmanager.repository.base.BaseRepository;

public interface ProductRepository extends BaseRepository<Product, UUID> {

}