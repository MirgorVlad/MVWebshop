package com.mvwebshop.cartservice.controller;

import com.mvwebshop.cartservice.BaseTest;
import com.mvwebshop.cartservice.repository.CartItemRepository;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartControllerTest extends BaseTest{


    @Autowired
    private CartItemRepository cartItemRepository;
    @Test
    void addItem() {
        Assertions.assertNotNull(cartItemRepository.findAll());
    }
}