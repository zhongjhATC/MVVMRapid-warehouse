package com.yutuo.warehouse.data.db.business;

import com.yutuo.warehouse.MyApplication;
import com.yutuo.warehouse.data.db.dao.BarCodeDao;
import com.yutuo.warehouse.entity.db.BarCode;

import java.util.List;

public class BarCodesBusiness {

    BarCodeDao mBarCodeDao;

    public BarCodesBusiness() {
        mBarCodeDao = ((MyApplication) (MyApplication.getInstance())).getDaoSession().getBarCodeDao();
    }

    /**
     * 获取本地扫描内容
     */
    public List<BarCode> getBarCodes() {
        return mBarCodeDao.queryBuilder().list();
    }

    /**
     * 添加本地扫描内容
     *
     * @param barCodeStr 扫描文本内容
     * @return 扫描id
     */
    public long addBarCodes(String barCodeStr) {
        BarCode barCode = new BarCode();
        barCode.setContent(barCodeStr);
        return mBarCodeDao.insert(barCode);
    }

    /**
     * 删除本地扫描内容
     *
     * @param id 扫描id
     */
    public boolean removeBarCode(long id) {
        mBarCodeDao.deleteByKey(id);
        return true;
    }

}
