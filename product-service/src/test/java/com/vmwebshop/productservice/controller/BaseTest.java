package com.vmwebshop.productservice.controller;

import org.testcontainers.containers.PostgreSQLContainer;

public class BaseTest {
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("cart-service-test-db")
            .withUsername("testuser")
            .withPassword("password");

    static {
        postgreSQLContainer.start();
    }
}
