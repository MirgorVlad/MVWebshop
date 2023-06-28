package com.mvwebshop.cartservice.service;

import com.mvwebshop.cartservice.dto.CartItemRequest;
import com.mvwebshop.cartservice.dto.CartItemResponse;
import com.mvwebshop.cartservice.dto.ProductResponse;
import com.mvwebshop.cartservice.model.CartItem;
import com.mvwebshop.cartservice.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final WebClient webClient;

    public void save(CartItem cartItem){
        log.info("Saved item with id " + cartItem.getId());
        cartItemRepository.save(cartItem);
    }


    public void delete(Long id) {
        cartItemRepository.deleteById(id);
        log.info("Item with id " + id + " deleted");
    }

    public void deleteByProductId(Long productId) {
        cartItemRepository.deleteCartItemByProductId(productId);
        log.info("Item with productId " + productId + " deleted");
    }

    public List<CartItemResponse> getAll() {
        return cartItemRepository.findAll().stream()
                .map(this::mapToCartItemResponse)
                .toList();
    }

    public List<CartItem> getAllByUserId(Long userId) {
        return cartItemRepository.findAllByUserId(userId);
    }

    public Optional<CartItem> find(Long id) {
        return cartItemRepository.findById(id);
    }


    public void deleteAll(List<CartItem> cartItem) {
        cartItemRepository.deleteAll(cartItem);
    }

    private ProductResponse getProductResponseById(Long productId){
        List<CartItemResponse> cartItemResponses = new ArrayList<>();
        ProductResponse productResponse = webClient.get()
                .uri("http://localhost:8081/api/product/get/" + productId)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .block();
        return productResponse;
    }

    public CartItemResponse mapToCartItemResponse(CartItem cartItem){

        return CartItemResponse.builder()
                .id(cartItem.getId())
                .userId(cartItem.getUserId())
                .quantity(cartItem.getQuantity())
                .productResponse(getProductResponseById(cartItem.getProductId()))
                .build();
    }

    public static CartItem mapRequestToCartItem(CartItemRequest cartItemRequest) {
        CartItem cartItem = CartItem.builder()
                .productId(cartItemRequest.getProductId())
                .quantity(cartItemRequest.getQuantity())
                .userId(cartItemRequest.getUserId())
                .build();
        return cartItem;
    }
}
