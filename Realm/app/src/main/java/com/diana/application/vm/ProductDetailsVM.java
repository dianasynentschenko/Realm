package com.diana.application.vm;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.diana.application.data.model.Cart;
import com.diana.application.data.model.Product;
import com.diana.application.data.repo.ICartRepository;
import com.diana.application.data.repo.ICatalogRepository;
import com.diana.application.data.utils.IToast;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Diana on 26.06.2019.
 */

public class ProductDetailsVM {

    private final ObservableField<Product> product;
    private final ObservableField<Boolean> isLoading;
    private final ObservableField<Boolean> showError;
    private final ICatalogRepository repository;
    private final ICartRepository cartRepository;
    private final long productId;
    private final ObservableField<Boolean> inCart;
    private final IToast toast;
    private final RealmResults<Product> realProduct;
    private final RealmResults<Cart> realCart;


    public ProductDetailsVM(ICatalogRepository repository, ICartRepository cartRepository, long productId, IToast toast) {
        this.repository = repository;
        this.cartRepository = cartRepository;
        this.productId = productId;
        this.toast = toast;
        this.product = new ObservableField<>();
        this.showError = new ObservableField<>(false);
        this.isLoading = new ObservableField<>(false);
        this.inCart = new ObservableField<>(false);
        this.realProduct = repository.getProductById(productId);
        this.realCart = cartRepository.getCard(productId);

    }

    public ObservableField<Product> getProduct() {
        return product;
    }

    public ObservableField<Boolean> getInCart() {
        return inCart;
    }

    public String getPrice() {

        return product.get() == null ? "" : "grn" + product.get().getPrice();
    }

    public ObservableField<Boolean> getIsLoading() {
        return isLoading;
    }

    public ObservableField<Boolean> getShowError() {
        return showError;
    }

    public void loadProduct() {

        if (isLoading.get()) {

            return;
        }
        showError.set(false);
        isLoading.set(true);
        repository.getAndSaveProduct(productId)
                .subscribe(new Observer<Product>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Product product) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        isLoading.set(false);
                        toast.showLongToast("error connect to server");
                        showError.set(true);
                    }

                    @Override
                    public void onComplete() {

                        isLoading.set(false);

                    }
                });
    }

    public void onAddToCartClick() {

        if (!inCart.get()) {

            cartRepository.addToCart(productId);
            toast.showShortToast("add");
        } else {

            cartRepository.removeFromCard(productId);
            toast.showShortToast("remove");
        }
    }

    private RealmChangeListener<RealmResults<Product>> productChangeListener = new RealmChangeListener<RealmResults<Product>>() {
        @Override
        public void onChange(RealmResults<Product> products) {

            product.set(realProduct.get(0));
        }
    };

    private RealmChangeListener<RealmResults<Cart>> cartChangeListener = new RealmChangeListener<RealmResults<Cart>>() {
        @Override
        public void onChange(RealmResults<Cart> carts) {

            inCart.set(realCart.size() != 0);
        }
    };
}
