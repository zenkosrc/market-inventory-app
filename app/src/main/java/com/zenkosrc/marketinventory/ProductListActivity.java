package com.zenkosrc.marketinventory;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zenkosrc.marketinventory.adapters.ProductListAdapter;
import com.zenkosrc.marketinventory.database.Product;
import com.zenkosrc.marketinventory.managers.DataBaseManager;
import com.zenkosrc.marketinventory.util.Dialogs;

public class ProductListActivity extends AppCompatActivity implements ProductListAdapter.OnItemClickListener {

    private RecyclerView        productListRecyclerView;
    private ProductListAdapter  productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        initRecyclerView();
    }

    private void initRecyclerView() {

        productListRecyclerView = (RecyclerView) findViewById(R.id.productListRecyclerView);
        productListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        productListAdapter = new ProductListAdapter(DataBaseManager.getInstance(this).getProductListSortByLast(), this);
        productListRecyclerView.setAdapter(productListAdapter);
    }

    private ProductListAdapter getAdapter(){
        return productListAdapter;
    }

    @Override
    public void onProductDeleteClick(final Product product) {

        Dialogs.showDialog(this,
                getResources().getString(R.string.dialog_product_delete),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Ok
                        DataBaseManager.getInstance(ProductListActivity.this).deleteProduct(product);
                        getAdapter().deleteProductFromList(product);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //No
                        dialog.dismiss();
                    }});
    }

    @Override
    public void onProductEditClick(Product product) {
        openEditProductFragment(product);
    }

    public void openEditProductFragment(Product product) {
        NewProductFragment fragment = new NewProductFragment();
        fragment.setEditProduct(product);

        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down)
                .add(android.R.id.content, fragment)
                .commit();
    }
}
