package com.diana.application.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diana.application.MainActivity;
import com.diana.application.R;
import com.diana.application.data.di.module.ContextModule;
import com.diana.application.data.model.Product;
import com.diana.application.data.repo.ICatalogRepository;
import com.diana.application.data.utils.INavigator;
import com.diana.application.databinding.FragmentProductListBinding;
import com.diana.application.ui.adapter.ProductAdapter;
import com.diana.application.vm.ProductListVM;

import javax.inject.Inject;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmResults;

/**
 * Created by Diana on 26.06.2019.
 */

public class ProductListFragment extends BaseFragment {

    public static final String CATEGORY = "category";
    private String getCategory = "";

    @Inject
    ICatalogRepository catalogRepository;
    @Inject
    INavigator navigator;

    private ProductListVM vm;
    private ProductAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    public ProductListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCategory = getArguments().getString(CATEGORY);
        inject();
        vm =  new ProductListVM(catalogRepository, getCategory);
        vm.getProducts().addChangeListener(changeListener);
        adapter = new ProductAdapter(vm.getProducts(), navigator);
        vm.loadForPage(1);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentProductListBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_product_list, container, false);
        binding.setVm(vm);
        return binding.getRoot();
    }



    private void inject() {
        ((MainActivity)getActivity()
                .getApplication())
                .getApplicationComponent()
                .plusContextModule(new ContextModule(getActivity()))
                .inject(this);
    }


    private void setUpRv(RecyclerView rvList) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(linearLayoutManager);
        rvList.setAdapter(adapter);
        rvList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!vm.hasMore()) {
                    return;
                }
                int currentPage = (linearLayoutManager.findLastCompletelyVisibleItemPosition()+1)/10;
                if (currentPage >= vm.getPage()) {
                    vm.loadForPage(currentPage + 1);
                }

            }
        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvList = view.findViewById(R.id.rvList);
        setUpRv(rvList);
    }



    @Override
    public String getTitle() {
        return getCategory;
    }

    private final OrderedRealmCollectionChangeListener<RealmResults<Product>> changeListener =
            new OrderedRealmCollectionChangeListener<RealmResults<Product>>() {
                @Override
                public void onChange(RealmResults<Product> products, OrderedCollectionChangeSet changeSet) {

                    if(changeSet == null){
                        adapter.notifyDataSetChanged();
                        return;
                    }

                    OrderedCollectionChangeSet.Range[] deletions = changeSet.getDeletionRanges();
                    for (int i = deletions.length - 1; i >= 0; i--) {
                        OrderedCollectionChangeSet.Range range = deletions[i];
                        adapter.notifyItemRangeRemoved(range.startIndex, range.length);
                    }

                    OrderedCollectionChangeSet.Range[] insertions = changeSet.getInsertionRanges();
                    for (OrderedCollectionChangeSet.Range range : insertions) {
                        adapter.notifyItemRangeInserted(range.startIndex, range.length);
                    }

                    OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();
                    for (OrderedCollectionChangeSet.Range range : modifications) {
                        adapter.notifyItemRangeChanged(range.startIndex, range.length);
                    }

                }
            };

}
