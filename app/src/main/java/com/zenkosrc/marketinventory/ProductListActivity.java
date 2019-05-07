package com.zenkosrc.marketinventory;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.zenkosrc.marketinventory.adapters.ProductListAdapter;
import com.zenkosrc.marketinventory.database.Product;
import com.zenkosrc.marketinventory.managers.DataBaseManager;

public class ProductListActivity extends AppCompatActivity implements ProductListAdapter.OnItemClickListener {

    private RecyclerView        productListRecyclerView;
    private ProductListAdapter  productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        initResources();
        initRecyclerView();

    }

    private void initResources() {


    }

    private void initRecyclerView() {

        productListRecyclerView = (RecyclerView) findViewById(R.id.productListRecyclerView);
        productListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        productListAdapter = new ProductListAdapter(DataBaseManager.getInstance(this).getProductList(), this);
        productListRecyclerView.setAdapter(productListAdapter);
    }

    private ProductListAdapter getAdapter(){
        return productListAdapter;
    }

    @Override
    public void onProductDeleteClick(final Product product) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(ProductListActivity.this);
        dialog.setMessage(getResources().getString(R.string.dialog_product_delete) + product.getName());
        dialog.setPositiveButton(getResources().getString(R.string.dialog_product_delete_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataBaseManager.getInstance(ProductListActivity.this).deleteProduct(product);
                getAdapter().deleteProductFromList(product);
            }
        });
        dialog.setNegativeButton(getResources().getString(R.string.dialog_product_delete_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
