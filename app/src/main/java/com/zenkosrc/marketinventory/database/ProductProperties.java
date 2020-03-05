package com.zenkosrc.marketinventory.database;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(active = true, nameInDb = "PROPERTIES")
public class ProductProperties {

    public final static int GROUP = 0;
    public final static int QUANTITY = 1;

    @Id(autoincrement = true)
    private Long    id;

    @NonNull
    private int     label;

    @NonNull
    private String  propertiesName;

    private int     usageCount;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1139330382)
    private transient ProductPropertiesDao myDao;

    @Generated(hash = 151630452)
    public ProductProperties(Long id, int label, @NonNull String propertiesName,
            int usageCount) {
        this.id = id;
        this.label = label;
        this.propertiesName = propertiesName;
        this.usageCount = usageCount;
    }

    @Generated(hash = 830118204)
    public ProductProperties() {
    }

    public Long getId() {
        return id;
    }

    @NonNull
    public int getLabel() {
        return label;
    }

    public void setLabel(@NonNull int label) {
        this.label = label;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getPropertiesName() {
        return propertiesName;
    }

    public void setPropertiesName(@NonNull String propertiesName) {
        this.propertiesName = propertiesName;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1071796036)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProductPropertiesDao() : null;
    }
}
