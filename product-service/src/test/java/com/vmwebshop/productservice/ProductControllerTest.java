package com.vmwebshop.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmwebshop.productservice.controller.BaseTest;
import com.vmwebshop.productservice.dto.ProductRequest;
import com.vmwebshop.productservice.model.Product;
import com.vmwebshop.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest
public class ProductControllerTest extends BaseTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();
        String productrequeststring = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productrequeststring))
                .andExpect(status().isCreated());

        Assertions.assertTrue(productRepository.findAll().size() != 0);
    }

    @Test
    void shouldEditProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();
        String productrequeststring = objectMapper.writeValueAsString(productRequest);

        List<Product> productList = productRepository.findAll();
        Integer id = productList.get(0).getId();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product/edit/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productrequeststring))
                .andExpect(status().isOk());

        Assertions.assertTrue(productRepository.findById(id).get().getTitle().equals(getProductRequest().getTitle()));
//        Assertions.assertTrue(productRepository.findById(id).get().getDescription().equals(getProductRequest().getDescription()));
//        Assertions.assertTrue(productRepository.findById(id).get().getCondition().equals(getProductRequest().getCondition()));
//        Assertions.assertTrue(productRepository.findById(id).get().getPrice().equals(getProductRequest().getPrice()));
//        Assertions.assertTrue(productRepository.findById(id).get().getImage().equals(getProductRequest().getImage()));
//        Assertions.assertTrue(productRepository.findById(id).get().getStock().equals(getProductRequest().getStock()));
//        Assertions.assertTrue(productRepository.findById(1).get().getUserId().equals(getProductRequest().getUserId()));

    }

    private ProductRequest getProductRequest() {
        return new ProductRequest().builder()
                .title("product1")
                .description("product1")
                .image("pathtoimg")
                .price(10.)
                .stock(2)
                .userId(3)
                .condition("good")
                .build();
    }
}
