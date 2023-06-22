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
        cartItemRepository.saveAll(itemRequests);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cart"))
                .andExpect(status().isOk());
        Assertions.assertEquals(itemRequests.size(), cartItemRepository.findAll().size());

    }

    @Test
    void shouldUpdateQuantity() throws Exception {
        int  newQuantity = 5;
        CartItem cartItemRequest = new CartItem(1L,1L, 3, 2L);
        CartItemRequest requestWithNewQuantity = new CartItemRequest(1L, newQuantity, 2L);
        long id = cartItemRepository.save(cartItemRequest).getId();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/cart/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestWithNewQuantity)))
                .andExpect(status().isOk());
        Assertions.assertEquals(newQuantity, cartItemRepository.findById(id).get().getQuantity());
    }

    @Test
    void shouldReturnAllItemsByUser() throws Exception {
        long userId = 2L;
        CartItem cartItemRequest = new CartItem(1L,1L, 3, userId);
        CartItem  cartItemRequest2 = new CartItem(2L, 2L, 5, userId);
        List<CartItem> itemRequests = Arrays.asList(cartItemRequest, cartItemRequest2);
        cartItemRepository.saveAll(itemRequests);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cart/" + userId))
                .andExpect(status().isOk());
        Assertions.assertEquals(itemRequests.get(0).getUserId(),
                cartItemRepository.findAll().get(0).getUserId());

    }

    @Test
    void shouldDeleteById() throws Exception {
        long id = 1L;
        CartItem cartItemRequest = new CartItem(id,1L, 3, 2L);
        CartItem cartItemSaved = cartItemRepository.save(cartItemRequest);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cart/" + cartItemSaved.getId()))
                .andExpect(status().isOk());
        Assertions.assertEquals(0,  cartItemRepository.findAll().size());

    }
}