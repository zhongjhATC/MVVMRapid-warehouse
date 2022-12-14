package com.yutuo.warehouse.data.db.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.yutuo.warehouse.entity.db.BarCode;

import com.yutuo.warehouse.data.db.dao.BarCodeDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig barCodeDaoConfig;

    private final BarCodeDao barCodeDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        barCodeDaoConfig = daoConfigMap.get(BarCodeDao.class).clone();
        barCodeDaoConfig.initIdentityScope(type);

        barCodeDao = new BarCodeDao(barCodeDaoConfig, this);

        registerDao(BarCode.class, barCodeDao);
    }
    
    public void clear() {
        barCodeDaoConfig.clearIdentityScope();
    }

    public BarCodeDao getBarCodeDao() {
        return barCodeDao;
    }

}
