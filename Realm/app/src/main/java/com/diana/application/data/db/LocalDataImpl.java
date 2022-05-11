package com.diana.application.data.db;

import com.diana.application.data.model.Cart;
import com.diana.application.data.model.Product;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Diana on 25.06.2019.
 */

public class LocalDataImpl implements ILocalData {

    private final Realm realm;

    public LocalDataImpl(Realm realm) {
        this.realm = realm;
    }

    @Override
    public RealmResults<Product> getProductById(long id) {
        return realm.where(Product.class)
                .equalTo("id", id)
                .findAll();
    }

    @Override
    public RealmResults<Product> getProductByCategory(String category) {
        return realm.where(Product.class)
                .equalTo("category", category)
                .findAllSortedAsync("id", Sort.ASCENDING);
    }

    @Override
    public RealmResults<Cart> getCartByProduct(long id) {
        return realm.where(Cart.class)
               .equalTo("productId", id)
                .findAllAsync();
    }

    @Override
    public void saveProduct(final List<Product> product) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(product);
            }
        });
    }

    @Override
    public void addToCart(final long id) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Cart card = new Cart();
                card.setProductId(id);
                realm.copyToRealmOrUpdate(card);
            }
        });


    }

    @Override
    public long getCartCount() {
        return realm.where(Cart.class).count();
    }

    @Override
    public void removeFromCart(final long id) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                RealmResults<Cart> realmResults = realm.where(Cart.class)
                        .equalTo("productId", id)
                        .findAll();
                realmResults.deleteAllFromRealm();
            }
        });

    }
}
