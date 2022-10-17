package com.yutuo.warehouse.ui.manage.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseDraggableModule;
import com.chad.library.adapter.base.module.DraggableModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yutuo.warehouse.R;
import com.yutuo.warehouse.entity.Banner;
import com.yutuo.warehouse.entity.db.BarCode;

public class ManageAdapter extends BaseQuickAdapter<BarCode, BaseViewHolder> implements DraggableModule {

    public ManageAdapter() {
        super(R.layout.item_product);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, BarCode barCode) {
        baseViewHolder.setText(R.id.tvContent, barCode.getContent());
    }
}
