package com.diana.application.vm;

import com.diana.application.data.model.Product;
import com.diana.application.data.utils.INavigator;

/**
 * Created by Diana on 26.06.2019.
 */

public class ProductItemVM {

    private Product product;
    private INavigator navigator;

    public ProductItemVM(INavigator navigator) {
        this.navigator = navigator;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getPrice(){

        return "grn" + product.getPrice();
    }

    public void onClick (){

        navigator.openProductDetails(product.getId());
    }
}
