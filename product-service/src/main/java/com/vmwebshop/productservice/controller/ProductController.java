package com.vmwebshop.productservice.controller;

import com.vmwebshop.productservice.dto.ProductRequest;
import com.vmwebshop.productservice.dto.ProductResponse;
import com.vmwebshop.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createProduct(@RequestBody ProductRequest productRequest) {
        productService.create(productRequest);
        return "Product created successfully";
    }

    @PostMapping("/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String editProduct(@RequestBody ProductRequest productRequest, @PathVariable Integer id) {
        productService.update(id, productRequest);
        return "Product updated successfully";
    }

    @PostMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
        return "Product deleted successfully";
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAll() {

        List<ProductResponse> products = productService.getAllProducts();

        return products;
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getById(@PathVariable Integer id) {
        ProductResponse product = productService.getProductById(id);

        return product;
    }

    @GetMapping("/get_by_user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllByUserId(@PathVariable Integer userId) {

        List<ProductResponse> products = productService.getAllProductsByUserId(userId);

        return products;
    }
}
