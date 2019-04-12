package com.zenkosrc.marketinventory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zenkosrc.marketinventory.database.Product;
import com.zenkosrc.marketinventory.managers.DataBaseManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView       productCountTextView;
    private RelativeLayout addPropertiesButton;
    private RelativeLayout addProductButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "Count of products in the database: " + DataBaseManager.getInstance(this).getProductCountInDataBase());

        initResources();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshProductCount();
    }

    private void initResources() {

        productCountTextView    = (TextView) findViewById(R.id.productCountTextView);
        addPropertiesButton     = (RelativeLayout) findViewById(R.id.addPropertiesButton);
        addProductButton        = (RelativeLayout) findViewById(R.id.addProductButton);

        addPropertiesButton.setOnClickListener(this);
        addProductButton.setOnClickListener(this);
    }

    private void refreshProductCount() {
        productCountTextView.setText(Long.toString(DataBaseManager.getInstance(this).getProductCountInDataBase()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addPropertiesButton:

                openAddNewProductFragment();

                break;

            case R.id.addProductButton:

                addTestProduct();
                break;
        }
    }

    private void addTestProduct(){
        Product test = new Product();
        test.setBarcode("123hj1h4v243h5v234h5vj324");
        test.setName("Apple");
        test.setQuantity("KG");
        test.setGroup("Fruit");
        test.setDescription("Fresh apples");
        test.setTime(System.currentTimeMillis());
        DataBaseManager.getInstance(this).saveProductInDataBase(test);

        refreshProductCount();
    }

    public void openAddNewProductFragment() {
        NewProductFragment fragment = new NewProductFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down)
                .add(android.R.id.content, fragment)
                .commit();
    }
}
