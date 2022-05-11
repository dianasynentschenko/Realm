package com.diana.application.data.repo;

import com.diana.application.data.db.ILocalData;
import com.diana.application.data.model.Cart;

import io.realm.RealmResults;

/**
 * Created by Diana on 26.06.2019.
 */

public class CartRepositoryImpl implements ICartRepository {

    private  final ILocalData localData;

    public CartRepositoryImpl(ILocalData localData) {
        this.localData = localData;
    }

    @Override
    public void addToCart(long productId) {

        localData.addToCart(productId);

    }

    @Override
    public RealmResults<Cart> getCard(long productId) {
        return localData.getCartByProduct(productId);
    }


    @Override
    public void removeFromCard(long productId) {

        localData.removeFromCart(productId);
    }

    @Override
    public long getCardCount() {
        return localData.getCartCount();
    }
}
