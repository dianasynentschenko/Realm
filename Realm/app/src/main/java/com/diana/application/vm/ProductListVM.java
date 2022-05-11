package com.diana.application.vm;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.diana.application.data.model.Product;
import com.diana.application.data.repo.ICatalogRepository;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.realm.RealmResults;

/**
 * Created by Diana on 26.06.2019.
 */

public class ProductListVM {

    private final ObservableField<Boolean> showError;
    private final ObservableField<Boolean> isLoading;
    private final RealmResults<Product> products;
    private final String category;
    private int page = 0;
    private Boolean hasMore = true;

    private ICatalogRepository catalogRepository;

    public ProductListVM(ICatalogRepository catalogRepository,
                         String category
    ) {
        this.category = category;
        this.catalogRepository = catalogRepository;
        this.products = catalogRepository.getProductByCategory(category);
        this.showError = new ObservableField<>(false);
        this.isLoading = new ObservableField<>(false);
    }

    public ObservableField<Boolean> getShowError() {
        return showError;
    }

    public ObservableField<Boolean> getIsLoading() {
        return isLoading;
    }

    public RealmResults<Product> getProducts() {
        return products;
    }

    public void loadForPage(final int requestPage) {

        if (!isLoading.get()) {

            isLoading.set(true);
            showError.set(false);
            catalogRepository.getAndSaveProducts(requestPage, category)
                    .subscribe(new Observer<List<Product>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(List<Product> products) {

                            hasMore = products.size() == 10;
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                            isLoading.set(false);
                            if (products.size() == 0) {
                                showError.set(true);
                            }
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {

                            isLoading.set(false);
                            page = requestPage;
                        }
                    });
        }
    }

    public int getPage() {
        return page;
    }

    public boolean hasMore() {

        return hasMore;
    }
}
