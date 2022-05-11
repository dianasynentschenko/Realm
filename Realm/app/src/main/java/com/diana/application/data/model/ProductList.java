package com.diana.application.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Diana on 25.06.2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductList {


    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
