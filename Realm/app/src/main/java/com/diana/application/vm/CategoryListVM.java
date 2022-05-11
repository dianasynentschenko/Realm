package com.diana.application.vm;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.diana.application.data.model.Category;
import com.diana.application.data.repo.ICatalogRepository;

import javax.inject.Inject;

/**
 * Created by Diana on 26.06.2019.
 */

public class CategoryListVM {

    private final ICatalogRepository repository;
    private final ObservableList<Category> categories;

    @Inject
    public CategoryListVM(ICatalogRepository catalogRepository) {
        this.repository = catalogRepository;
        this.categories = new ObservableArrayList<>();
    }

    public void loadCategories() {

        categories.addAll(repository.getCategories());
    }

    public ObservableList<Category> getCategories() {

        return categories;
    }
}
