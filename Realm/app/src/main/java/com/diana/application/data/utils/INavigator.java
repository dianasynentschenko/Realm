package com.diana.application.data.utils;

/**
 * Created by Diana on 26.06.2019.
 */

public interface INavigator {

    void openCategoryList();

    void openProductList(String category);

    void openProductDetails(long productId);
}
