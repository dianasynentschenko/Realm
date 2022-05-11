package com.diana.application.data.db;

import com.diana.application.data.model.Cart;
import com.diana.application.data.model.Product;

import java.util.List;

import io.realm.RealmResults;

/**
 * Created by Diana on 25.06.2019.
 */

public interface ILocalData {


    RealmResults<Product> getProductById(long id);

    RealmResults<Product> getProductByCategory(String category);

    RealmResults<Cart> getCartByProduct(long id);

    void saveProduct(List<Product> product);

    void addToCart(long id);

    long getCartCount();

    void removeFromCart(long id);
}
