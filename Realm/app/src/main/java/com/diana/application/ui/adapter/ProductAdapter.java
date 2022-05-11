package com.diana.application.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diana.application.R;
import com.diana.application.data.model.Product;
import com.diana.application.data.utils.INavigator;
import com.diana.application.databinding.ItemProductBinding;
import com.diana.application.vm.ProductItemVM;

import io.realm.RealmResults;

/**
 * Created by Diana on 26.06.2019.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductVH> {

    private final RealmResults<Product> products;
    private final INavigator navigator;

    public ProductAdapter(RealmResults<Product> products, INavigator navigator) {
        this.products = products;
        this.navigator = navigator;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_product, parent, false);
        return new ProductVH(binding, new ProductItemVM(navigator));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductVH holder, int position) {

        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductVH extends RecyclerView.ViewHolder {

        private final ItemProductBinding binding;
        private final ProductItemVM vm;

        public ProductVH(ItemProductBinding binding, ProductItemVM itemVM) {
            super(binding.getRoot());
            this.binding = binding;
            this.vm = itemVM;
        }

        void bind(Product product) {
            vm.setProduct(product);
            binding.setVm(vm);
            binding.executePendingBindings();
        }
    }
}
