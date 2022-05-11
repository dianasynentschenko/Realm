package com.diana.application.data.repo;

import com.diana.application.data.model.Category;
import com.diana.application.data.model.Product;

import java.util.List;

import io.reactivex.Observable;
import io.realm.RealmResults;

/**
 * Created by Diana on 25.06.2019.
 */

public interface ICatalogRepository {

    List<Category> getCategories();

    RealmResults<Product> getProductByCategory (String category);

    RealmResults<Product> getProductById (long id);

    Observable<List<Product>> getAndSaveProducts(int page, String category);

    Observable<Product> getAndSaveProduct(long productId);


}
