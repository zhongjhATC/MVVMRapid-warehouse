package com.yutuo.warehouse.ui.product.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yutuo.warehouse.R;
import com.yutuo.warehouse.entity.Banner;

public class ProductAdapter extends BaseQuickAdapter<Banner, BaseViewHolder> {

    public ProductAdapter() {
        super(R.layout.item_product);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Banner banner) {
        baseViewHolder.setText(R.id.tvContent, banner.getTitle());
    }

}
