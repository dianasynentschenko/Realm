package com.diana.application.vm;

import com.diana.application.data.model.Category;
import com.diana.application.data.utils.INavigator;

/**
 * Created by Diana on 26.06.2019.
 */

public class CategoryItemVM {

    private Category category;
    private final INavigator navigator;

    public CategoryItemVM(INavigator navigator) {
        this.navigator = navigator;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void onClick(){

        navigator.openProductList(category.getName());
    }
}
