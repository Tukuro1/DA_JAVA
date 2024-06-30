package com.example.demo.Service;

import com.example.demo.Model.Product;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final Path imageStoragePath = Paths.get("path/to/image/storage");

    // Retrieve all products from the database
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Retrieve a product by its id
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Add a new product to the database
    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        if (!imageFile.isEmpty()) {
            String imageName = saveImage(imageFile);
            product.setImageName(imageName);
        }
        return productRepository.save(product);
    }

    // Update an existing product
    public Product updateProduct(@NotNull Product product, MultipartFile imageFile) throws IOException {
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID " +
                        product.getId() + " does not exist."));

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());

        if (!imageFile.isEmpty()) {
            String imageName = saveImage(imageFile);
            existingProduct.setImageName(imageName);
        }

        return productRepository.save(existingProduct);
    }

    // Delete a product by its id
    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        productRepository.deleteById(id);
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        String imageName = UUID.randomUUID().toString() + "-" + imageFile.getOriginalFilename();
        Path imagePath = imageStoragePath.resolve(imageName);
        Files.copy(imageFile.getInputStream(), imagePath);
        return imageName;
    }
}
