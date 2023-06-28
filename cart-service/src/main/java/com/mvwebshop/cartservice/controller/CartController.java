package com.mvwebshop.cartservice.controller;

import com.mvwebshop.cartservice.dto.CartItemRequest;
import com.mvwebshop.cartservice.dto.CartItemResponse;
import com.mvwebshop.cartservice.dto.CartResponse;
import com.mvwebshop.cartservice.model.CartItem;
import com.mvwebshop.cartservice.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartItemService cartItemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addItem(@RequestBody CartItemRequest cartItemRequest){
        cartItemService.save(CartItemService.mapRequestToCartItem(cartItemRequest));
    }

    @GetMapping
    public List<CartItemResponse> getAllItems(){
        return cartItemService.getAll();
    }

    @GetMapping("/{userId}")
    public CartResponse getAllItemsByUser(@PathVariable Long userId){
        List<CartItemResponse> responses = cartItemService.getAllByUserId(userId)
                .stream()
                .map(cartItemService::mapToCartItemResponse)
                .toList();

        double price = 0;
        for(CartItemResponse response : responses){
            price += response.getProductResponse().getPrice();
        }

        return CartResponse.builder()
                .cartItemResponses(responses)
                .totalPrice(price)
                .build();
    }


    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteItem(@PathVariable(name = "id") Long id){
        Optional<CartItem> cartItem = cartItemService.find(id);
        if(cartItem.isPresent()){
            cartItemService.delete(id);
            return ResponseEntity.ok("Deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> editQuantity(@RequestBody CartItemRequest cartItemRequest,
                                               @PathVariable(name = "id") Long id){
        Optional<CartItem> cartItem = cartItemService.find(id);
        if(cartItem.isPresent()){
            CartItem cartItem1 = cartItem.get();
            cartItem1.setQuantity(cartItemRequest.getQuantity());
            cartItemService.save(cartItem1);
            return ResponseEntity.ok("Updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/clear/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> clearUserCart(@PathVariable(name = "userId") Long id){
        List<CartItem> cartItem = cartItemService.getAllByUserId(id);
        if(!cartItem.isEmpty()){
           cartItemService.deleteAll(cartItem);
            return ResponseEntity.ok("Cleared successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
