package com.diana.application.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diana.application.R;
import com.diana.application.data.model.Category;
import com.diana.application.data.utils.INavigator;
import com.diana.application.databinding.ItemCategoryBinding;
import com.diana.application.vm.CategoryItemVM;

/**
 * Created by Diana on 26.06.2019.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryVH> {

    private final ObservableList<Category> categories;
    private final INavigator navigator;

    public CategoryAdapter(ObservableList<Category> categories, INavigator navigator) {
        this.categories = categories;
        this.navigator = navigator;
    }

    @NonNull
    @Override
    public CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_category, parent, false);

        return new CategoryVH(binding, new CategoryItemVM(navigator));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVH holder, int position) {
        holder.bind(categories.get(position));

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryVH extends RecyclerView.ViewHolder {
        private final CategoryItemVM vm;
        private final ItemCategoryBinding binding;

        public CategoryVH(ItemCategoryBinding binding, CategoryItemVM categoryItemVM) {
            super(binding.getRoot());
            this.vm = categoryItemVM;
            this.binding = binding;
        }

        public void bind(Category category) {

            vm.setCategory(category);
            binding.setVm(vm);
            binding.executePendingBindings();
        }
    }
}
