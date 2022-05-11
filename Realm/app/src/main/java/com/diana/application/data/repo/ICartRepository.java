package com.diana.application.data.repo;


import com.diana.application.data.model.Cart;

import io.realm.RealmResults;

/**
 * Created by Diana on 26.06.2019.
 */

public interface ICartRepository {

    void addToCart(long productId);

    RealmResults<Cart> getCard(long productId);

    void removeFromCard(long productId);

    long getCardCount();
}
