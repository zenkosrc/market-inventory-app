package com.zenkosrc.marketinventory.managers;

import android.content.Context;
import android.util.Log;

import com.zenkosrc.marketinventory.database.DaoMaster;
import com.zenkosrc.marketinventory.database.DaoSession;
import com.zenkosrc.marketinventory.database.Product;
import com.zenkosrc.marketinventory.database.ProductDao;

import org.greenrobot.greendao.database.Database;

public class DataBaseManager {

    private static final String TAG = DataBaseManager.class.getSimpleName();

    private static DataBaseManager  dataBaseManager;
    private        Context          context;
    private        ProductDao       productDao;

    public DataBaseManager(Context context) {
        this.context = context;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "appDB");
        Database db = helper.getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();

        productDao = daoSession.getProductDao();
    }

    //Singleton
    public static DataBaseManager getInstance(Context context){
        if (dataBaseManager == null) {
            dataBaseManager = new DataBaseManager(context);
        }
        return dataBaseManager;
    }

    public void saveProductInDataBase(Product product){

        //Insert to DB
        productDao.insertOrReplaceInTx(product);

        Log.d(TAG, "Product insert to DB: " + product.getName());
        Log.d(TAG, "Count of products in the database: " + getProductCountInDataBase());
    }

    public long getProductCountInDataBase(){
        return productDao.count();
    }
}
