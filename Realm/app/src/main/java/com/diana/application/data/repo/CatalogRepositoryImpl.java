package com.diana.application.data.repo;

import android.support.annotation.NonNull;

import com.diana.application.data.db.ILocalData;
import com.diana.application.data.model.Category;
import com.diana.application.data.model.Product;
import com.diana.application.data.model.ProductList;
import com.diana.application.data.model.ProductResponce;
import com.diana.application.data.network.ApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmResults;

/**
 * Created by Diana on 25.06.2019.
 */

public class CatalogRepositoryImpl implements ICatalogRepository {

    private final ILocalData localData;
    private final ApiService apiService;
    private static List<Category> categories;

    static {

        categories = new ArrayList<>();
        categories.add(new Category("test1"));
        categories.add(new Category("test2"));
        categories.add(new Category("test3"));
        categories.add(new Category("test4"));
    }

    public CatalogRepositoryImpl(ILocalData localData, ApiService apiService) {
        this.localData = localData;
        this.apiService = apiService;
    }

    @Override
    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public RealmResults<Product> getProductByCategory(String category) {
        return localData.getProductByCategory(category);
    }

    @Override
    public RealmResults<Product> getProductById(long id) {
        return localData.getProductById(id);
    }

    @Override
    public Observable<List<Product>> getAndSaveProducts(int page, String category) {
        return apiService.getProductList(page, category)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ProductList, List<Product>>() {
                    @Override
                    public List<Product> apply(@NonNull ProductList productList) throws Exception {
                        return productList.getProducts();
                    }
                })
                .doOnNext(new Consumer<List<Product>>() {
                    @Override
                    public void accept(List<Product> products) throws Exception {

                        localData.saveProduct(products);
                    }
                });
    }

    @Override
    public Observable<Product> getAndSaveProduct(long productId) {

        return apiService.getProduct(productId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ProductResponce, Product>() {
                    @Override
                    public Product apply(@io.reactivex.annotations.NonNull ProductResponce productResponse) throws Exception {
                        return productResponse.getProduct();
                    }
                })
                .doOnNext(new Consumer<Product>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Product product) throws Exception {
                        localData.saveProduct(Collections.singletonList(product));
                    }
                });

    }


}
