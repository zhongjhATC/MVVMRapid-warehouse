package com.yutuo.warehouse.ui.manage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.yutuo.warehouse.BR;
import com.yutuo.warehouse.R;
import com.yutuo.warehouse.databinding.ActivityManageBinding;
import com.yutuo.warehouse.databinding.ActivityProductBinding;
import com.yutuo.warehouse.ui.manage.adapter.ManageAdapter;
import com.yutuo.warehouse.ui.product.ProductActivity;
import com.yutuo.warehouse.ui.qrcodescanning.QRCodeScanningActivity;
import com.zhongjh.mvvmrapid.base.ui.BaseActivity;
import com.zhongjh.mvvmrapid.utils.ScreenUtil;

/**
 * 管理界面 - 扫描后有相应处理返回数据添加到本地
 *
 * @author zhongjh
 * @date 2021/4/15
 */
public class ManageActivity extends BaseActivity<ActivityManageBinding, ManageModel> {

    ManageAdapter mManageAdapter = new ManageAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScreenUtil.setFullScreen(ManageActivity.this, false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_manage;
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
        initToolbar(binding.toolbar, "扫描列表", true);
        binding.toolbar.inflateMenu(R.menu.manage);
        binding.rlProduct.setLayoutManager(new LinearLayoutManager(this));
        binding.rlProduct.setAdapter(mManageAdapter);
        mManageAdapter.getDraggableModule().setSwipeEnabled(true);
        mManageAdapter.getDraggableModule().setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                // 删除数据
                viewModel.removeBarCodeData(pos);
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
            }
        });


        // 上拉和下拉功能
        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                // 功能关闭，要使用记得xml修改属性
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewModel.initBarCodeData();
            }
        });

        // 菜单功能
        binding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.toolbar_scan) {
                startActivityForResult(new Intent(ManageActivity.this, QRCodeScanningActivity.class), 100);
                overridePendingTransition(0, 0);
            }
            return true;
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void initViewObservable() {
        viewModel.uiChangeObservable.barCodes.observe(this, banners -> {
            mManageAdapter.setList(banners);
            mManageAdapter.notifyDataSetChanged();
            binding.refreshLayout.finishRefresh();
        });

        viewModel.uiChangeObservable.addBarCode.observe(this, aLong -> {
            if (aLong > 0) {
                // 添加成功刷新数据
                viewModel.initBarCodeData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            // 添加数据到本地
            if (data != null) {
                viewModel.addBarCodeData(data.getStringExtra(QRCodeScanningActivity.SCANNING_RESULT_KEY));
            }
        }
    }
}
