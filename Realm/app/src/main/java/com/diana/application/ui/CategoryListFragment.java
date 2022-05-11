package com.diana.application.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diana.application.MainActivity;
import com.diana.application.R;
import com.diana.application.data.di.module.ContextModule;
import com.diana.application.data.utils.INavigator;
import com.diana.application.ui.BaseFragment;
import com.diana.application.ui.adapter.CategoryAdapter;
import com.diana.application.vm.CategoryItemVM;
import com.diana.application.vm.CategoryListVM;

import javax.inject.Inject;

/**
 * Created by Diana on 26.06.2019.
 */

public class CategoryListFragment extends BaseFragment {

    @Inject
    CategoryListVM vm;

    @Inject
    INavigator navigator;

    private CategoryAdapter categoryAdapter;

    public CategoryListFragment() {

        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void inject() {
        ((MainActivity) getActivity()
                .getApplication())
                .getApplicationComponent()
                .plusContextModule(new ContextModule(getActivity()))
                .inject(this);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        inject();
        vm.loadCategories();
        categoryAdapter = new CategoryAdapter(vm.getCategories(), navigator);
        return inflater.inflate(R.layout.fragment_category_list, container, false);


    }

    private void setupRv(RecyclerView rvList) {
        rvList.setAdapter(categoryAdapter);
        rvList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvList.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        rvList.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.HORIZONTAL));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvList = view.findViewById(R.id.rvList);
        setupRv(rvList);
    }
}
