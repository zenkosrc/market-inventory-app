package com.zenkosrc.marketinventory.managers;

import android.content.Context;
import android.util.Log;

import com.zenkosrc.marketinventory.database.DaoMaster;
import com.zenkosrc.marketinventory.database.DaoSession;
import com.zenkosrc.marketinventory.database.Product;
import com.zenkosrc.marketinventory.database.ProductDao;
import com.zenkosrc.marketinventory.database.ProductProperties;
import com.zenkosrc.marketinventory.database.ProductPropertiesDao;

import org.greenrobot.greendao.database.Database;

import java.util.List;

public class DataBaseManager {

    private static final String TAG = DataBaseManager.class.getSimpleName();

    private static DataBaseManager      dataBaseManager;

    private        Context              context;
    private        ProductDao           productDao;
    private        ProductPropertiesDao productPropertiesDao;

    public DataBaseManager(Context context) {
        this.context = context;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "appDB");
        Database db = helper.getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();

        productDao           = daoSession.getProductDao();
        productPropertiesDao = daoSession.getProductPropertiesDao();
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

        //Increment Product Properties Usage Count
        incrementUsageProductPropeties(getProductProperties(product.getGroup()));
    }

    public long getProductCountInDataBase(){
        return productDao.count();
    }

    public List<Product> getProductList(){
        return productDao.loadAll();
    }


    //Sort By Add Time 99 to 1
    public List<Product> getProductListSortByLast(){
        return productDao.queryBuilder()
                .orderDesc(ProductDao.Properties.Time)
                .list();
    }

    public void deleteProduct(Product product){
        productDao.delete(product);
    }

    public void saveProductPropertiesInDataBase(ProductProperties productProperties){

        //Insert to DB
        productPropertiesDao.insertOrReplaceInTx(productProperties);

        Log.d(TAG, "Product Properties insert to DB: " + productProperties.getPropertiesName());
        Log.d(TAG, "Count of product properties in the database: " + getProductPropertiesCountInDataBase());
    }

    public long getProductPropertiesCountInDataBase(){
        return productPropertiesDao.count();
    }

    public ProductProperties getProductProperties(String propertiesName){
        return productPropertiesDao.queryBuilder()
                .where(ProductPropertiesDao.Properties.PropertiesName.eq(propertiesName))
                .build()
                .unique();
    }

    public List<ProductProperties> getProductPropertiesList(){
        return productPropertiesDao.loadAll();
    }

    //Sort By Usage Count 99 to 1
    public List<ProductProperties> getProductPropertiesGroupList(){
        return productPropertiesDao.queryBuilder()
                .where(ProductPropertiesDao.Properties.Label.eq(ProductProperties.GROUP))
                .orderDesc(ProductPropertiesDao.Properties.UsageCount)
                .build()
                .list();
    }

    //Sort By Last adding 99 to 1
    public List<ProductProperties> getProductPropertiesGroupListSortByLast(){
        return productPropertiesDao.queryBuilder()
                .where(ProductPropertiesDao.Properties.Label.eq(ProductProperties.GROUP))
                .orderDesc(ProductPropertiesDao.Properties.Id)
                .build()
                .list();
    }

    public List<ProductProperties> getProductPropertiesQuantityList(){
        return productPropertiesDao.queryBuilder()
                .where(ProductPropertiesDao.Properties.Label.eq(ProductProperties.QUANTITY))
                .orderAsc(ProductPropertiesDao.Properties.UsageCount)
                .build()
                .list();
    }

    public void deleteProductProperties(ProductProperties productProperties){
        productPropertiesDao.delete(productProperties);
    }

    public void incrementUsageProductPropeties(ProductProperties productProperties){
        int usageCount = productProperties.getUsageCount();
        productProperties.setUsageCount(usageCount+1);
        saveProductPropertiesInDataBase(productProperties);

        Log.d(TAG, "Count of products Group: " + productProperties.getUsageCount());
    }
}
