package com.vmwebshop.productservice.service;

import com.vmwebshop.productservice.dto.ProductRequest;
import com.vmwebshop.productservice.dto.ProductResponse;
import com.vmwebshop.productservice.model.Product;
import com.vmwebshop.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public Product dtoToProduct(ProductRequest productRequest) {
        return modelMapper.map(productRequest, Product.class);
    }

    public ProductResponse productToDto(Product product) {
        return modelMapper.map(product, ProductResponse.class);
    }

    public void create(ProductRequest productRequest) {
        Product product = dtoToProduct(productRequest);

        productRepository.save(product);
    }

    public void update(Integer id, ProductRequest productRequest) {
        Optional<Product> productToUpdate = productRepository.findById(id);
        Product product = dtoToProduct(productRequest);

        product.setId(productToUpdate.get().getId());

        productRepository.save(product);
    }


    public void delete(Integer id) {
        productRepository.deleteById(id);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::productToDto).toList();

    }

    public ProductResponse getProductById(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        return productToDto(product);
    }

    public List<ProductResponse> getAllProductsByUserId(Integer userId) {
        List<Product> products = productRepository.findAllByUserId(userId);
        return products.stream().map(this::productToDto).toList();
    }
}
