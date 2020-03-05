package com.zenkosrc.marketinventory.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zenkosrc.marketinventory.R;
import com.zenkosrc.marketinventory.database.ProductProperties;

import java.util.List;

public class SpinnerPropertiesAdapter extends ArrayAdapter {

    private List<ProductProperties> productPropertiesList;

    public SpinnerPropertiesAdapter(@NonNull Context context, int resource, List<ProductProperties> productPropertiesList) {
        super(context, resource);
        this.productPropertiesList = productPropertiesList;
    }

    @Override
    public int getCount(){
        return productPropertiesList.size();
    }

    @Override
    public String getItem(int position){
        return productPropertiesList.get(position).getPropertiesName();
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(productPropertiesList.get(position).getPropertiesName());


        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);


        int padding = (int) getContext().getResources().getDimension(R.dimen.padding_item);

        label.setPadding(padding,padding,padding,padding);
        label.setText(productPropertiesList.get(position).getPropertiesName());

        return label;
    }
}
