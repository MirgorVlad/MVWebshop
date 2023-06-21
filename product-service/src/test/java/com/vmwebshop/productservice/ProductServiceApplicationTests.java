package com.vmwebshop.productservice;

import com.vmwebshop.productservice.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
class ProductServiceApplicationTests {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	@Container
	static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres");

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
	}
	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productrequeststring = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productrequeststring))
				.andExpect(status().isCreated());
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
