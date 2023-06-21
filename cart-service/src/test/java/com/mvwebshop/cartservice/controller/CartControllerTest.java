package com.mvwebshop.cartservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvwebshop.cartservice.BaseTest;
import com.mvwebshop.cartservice.dto.CartItemRequest;
import com.mvwebshop.cartservice.dto.CartItemResponse;
import com.mvwebshop.cartservice.model.CartItem;
import com.mvwebshop.cartservice.repository.CartItemRepository;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@SpringBootTest
class CartControllerTest extends BaseTest{

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void clearData(){
        cartItemRepository.deleteAll();
    }

    @Test
    void shouldCreateCartItem() throws Exception {
        CartItemRequest cartItemRequest = new CartItemRequest(1L, 3, 2L);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartItemRequest)))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, cartItemRepository.findAll().size());
    }

    @Test
    void shouldReturnAllItems() throws Exception {
        CartItem cartItemRequest = new CartItem(1L,1L, 3, 2L);
        CartItem  cartItemRequest2 = new CartItem(2L, 2L, 5, 2L);
        List<CartItem> itemRequests = Arrays.asList(cartItemRequest, cartItemRequest2);
        List<CartItemResponse> cartItemResponses = createResponse(itemRequests);
        cartItemRepository.saveAll(itemRequests);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cart"))
                .andExpect(status().isOk());
    }

    private List<CartItemResponse> createResponse(List<CartItem> itemRequests) {
        List<CartItemResponse> cartItemResponseList = new ArrayList<>();
        for(CartItem cartItem : itemRequests){
            cartItemResponseList.add(new CartItemResponse(cartItem.getId(),cartItem.getProductId(),
                    cartItem.getQuantity(), cartItem.getUserId()));
        }
        return cartItemResponseList;
    }
}