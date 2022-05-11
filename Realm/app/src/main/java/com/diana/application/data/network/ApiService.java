package com.diana.application.data.network;

import com.diana.application.data.model.ProductList;
import com.diana.application.data.model.ProductResponce;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Diana on 25.06.2019.
 */

public interface ApiService {

    @GET("api/v1/products.json")
    Observable<ProductList> getProductList(@Query("page") int page, @Query("category") String category);

    @GET("api/v1/products/{id}.json")
    Observable<ProductResponce> getProduct(@Path("id") long id);
}
