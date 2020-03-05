package com.zenkosrc.marketinventory;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.zenkosrc.marketinventory.adapters.ProductPropertiesListAdapter;
import com.zenkosrc.marketinventory.database.ProductProperties;
import com.zenkosrc.marketinventory.managers.DataBaseManager;
import com.zenkosrc.marketinventory.util.Dialogs;
import com.zenkosrc.marketinventory.util.OnNewPropertiesListener;

public class EditProductPropertiesActivity extends AppCompatActivity implements ProductPropertiesListAdapter.OnItemClickListener{

    public static String EXTRA_PROPERTIES_LABEL = "extraPropertiesLabel";

    private RecyclerView                    propertiesListRecyclerView;
    private ProductPropertiesListAdapter    productPropertiesListAdapter;
    private LinearLayout                    addButtonLinearLayout;

    private int                             propertiesLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product_properties);

        initproperties();
        initResources();
        initRecyclerView();
    }

    private void initproperties() {
        propertiesLabel = getIntent().getIntExtra(EXTRA_PROPERTIES_LABEL, ProductProperties.GROUP);
    }

    private void initResources() {

        addButtonLinearLayout = (LinearLayout) findViewById(R.id.addButtonLinearLayout);
        addButtonLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPropertiesDialog();
            }
        });
    }

    private void initRecyclerView() {

        propertiesListRecyclerView = (RecyclerView) findViewById(R.id.propertiesListRecyclerView);
        propertiesListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if (propertiesLabel == ProductProperties.GROUP){
            productPropertiesListAdapter = new ProductPropertiesListAdapter(DataBaseManager.getInstance(this).getProductPropertiesGroupListSortByLast(), this);
        }else {
            productPropertiesListAdapter = new ProductPropertiesListAdapter(DataBaseManager.getInstance(this).getProductPropertiesQuantityList(), this);
        }
        propertiesListRecyclerView.setAdapter(productPropertiesListAdapter);
    }

    private ProductPropertiesListAdapter getAdapter(){
        return productPropertiesListAdapter;
    }

    @Override
    public void onProductPropertiesDeleteClick(final ProductProperties productProperties) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(getResources().getString(R.string.dialog_product_delete) + productProperties.getPropertiesName());
        dialog.setPositiveButton(getResources().getString(R.string.dialog_product_delete_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataBaseManager.getInstance(getBaseContext()).deleteProductProperties(productProperties);
                getAdapter().deleteProductPropertiesFromList(productProperties);
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

    public void addNewPropertiesDialog() {

        Dialogs.showNewPropertiesDialog(this, getString(R.string.dialog_product_properties_name), new OnNewPropertiesListener() {

            @Override
            public void newPropertiesCreate(String propertiesName) {

                ProductProperties newProperties = new ProductProperties();

                if (propertiesLabel == ProductProperties.GROUP){
                    newProperties.setLabel(ProductProperties.GROUP);
                }else {
                    newProperties.setLabel(ProductProperties.QUANTITY);
                }
                newProperties.setPropertiesName(propertiesName);
                DataBaseManager.getInstance(getBaseContext()).saveProductPropertiesInDataBase(newProperties);
                productPropertiesListAdapter.addProductPropertiesToList(newProperties);
            }
        });
    }
}
