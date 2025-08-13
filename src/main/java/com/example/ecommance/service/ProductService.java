package com.example.ecommance.service;

import com.example.ecommance.model.Product;
import com.example.ecommance.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    public Product createProduct(Product product, Long categoryId, List<Long> tagIds) {
        product.setCategory(categoryService.get(categoryId));
        product.setTags(Set.copyOf(tagService.getByIds(tagIds)));
        return productRepository.save(product);
    }

	/*
	 * public Product createProduct(Product product) { return
	 * productRepository.save(product); }
	 */

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = getProductById(id);
        existing.setName(updatedProduct.getName());
        existing.setPrice(updatedProduct.getPrice());
        existing.setQuantity(updatedProduct.getQuantity());
        return productRepository.save(existing);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }
}

