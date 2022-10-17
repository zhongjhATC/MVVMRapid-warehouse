package com.yutuo.warehouse.ui.product;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.yutuo.warehouse.BR;
import com.yutuo.warehouse.R;
import com.yutuo.warehouse.databinding.ActivityProductBinding;
import com.yutuo.warehouse.entity.Banner;
import com.yutuo.warehouse.ui.main.MainActivity;
import com.yutuo.warehouse.ui.manage.ManageActivity;
import com.yutuo.warehouse.ui.product.adapter.ProductAdapter;
import com.zhongjh.mvvmrapid.base.ui.BaseActivity;
import com.zhongjh.mvvmrapid.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品
 *
 * @author zhongjh
 * @date 2021/4/15
 */
public class ProductActivity extends BaseActivity<ActivityProductBinding, ProductViewModel> {

    ProductAdapter mProductAdapter = new ProductAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScreenUtil.setFullScreen(ProductActivity.this, false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_product;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public boolean isStartAutoHideSoftKeyboard() {
        return false;
    }

    @Override
    public void initParam() {

    }

    @Override
    public void initData() {
        initToolbar(binding.toolbar, "产品列表", true);
        binding.rlProduct.setLayoutManager(new LinearLayoutManager(this));
        binding.rlProduct.setAdapter(mProductAdapter);

        mProductAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(ProductActivity.this, ManageActivity.class));
            overridePendingTransition(0, 0);
        });

        // 上拉和下拉功能
        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                // 功能关闭，要使用记得xml修改属性
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewModel.initProductData();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void initViewObservable() {
        viewModel.uiChangeObservable.showBanners.observe(this, banners -> {
            mProductAdapter.setList(banners);
            mProductAdapter.notifyDataSetChanged();
            binding.refreshLayout.finishRefresh();
        });
    }

}
