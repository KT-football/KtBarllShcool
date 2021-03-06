package com.ktfootball.www.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.ktfootball.www.dao.SideAandB;
import com.ktfootball.www.dao.VcrPath;

import com.ktfootball.www.dao.SideAandBDao;
import com.ktfootball.www.dao.VcrPathDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig sideAandBDaoConfig;
    private final DaoConfig vcrPathDaoConfig;

    private final SideAandBDao sideAandBDao;
    private final VcrPathDao vcrPathDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        sideAandBDaoConfig = daoConfigMap.get(SideAandBDao.class).clone();
        sideAandBDaoConfig.initIdentityScope(type);

        vcrPathDaoConfig = daoConfigMap.get(VcrPathDao.class).clone();
        vcrPathDaoConfig.initIdentityScope(type);

        sideAandBDao = new SideAandBDao(sideAandBDaoConfig, this);
        vcrPathDao = new VcrPathDao(vcrPathDaoConfig, this);

        registerDao(SideAandB.class, sideAandBDao);
        registerDao(VcrPath.class, vcrPathDao);
    }
    
    public void clear() {
        sideAandBDaoConfig.getIdentityScope().clear();
        vcrPathDaoConfig.getIdentityScope().clear();
    }

    public SideAandBDao getSideAandBDao() {
        return sideAandBDao;
    }

    public VcrPathDao getVcrPathDao() {
        return vcrPathDao;
    }

}
