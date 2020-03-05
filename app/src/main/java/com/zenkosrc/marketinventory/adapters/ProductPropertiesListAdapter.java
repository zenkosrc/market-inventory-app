package com.zenkosrc.marketinventory.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenkosrc.marketinventory.R;
import com.zenkosrc.marketinventory.database.ProductProperties;

import java.util.List;

public class ProductPropertiesListAdapter extends RecyclerView.Adapter<ProductPropertiesListAdapter.ViewHolder>{

    private List<ProductProperties>     productPropertiesList;
    private OnItemClickListener         onItemClickListener;

    public ProductPropertiesListAdapter(List<ProductProperties> productPropertiesList, OnItemClickListener onItemClickListener) {
        this.productPropertiesList  = productPropertiesList;
        this.onItemClickListener    = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.properties_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(productPropertiesList.get(position));
    }

    @Override
    public int getItemCount() {
        return productPropertiesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView    nameTextView;
        private ImageView   deleteImageView;

        public ViewHolder(final View itemView) {
            super(itemView);

            nameTextView        = itemView.findViewById(R.id.nameTextView);
            deleteImageView     = itemView.findViewById(R.id.deleteImageView);

            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onProductPropertiesDeleteClick(productPropertiesList.get(getLayoutPosition()));
                }
            });
        }

        public void bind(ProductProperties productProperties) {
            nameTextView.setText(productProperties.getPropertiesName());
        }
    }

    public void addProductPropertiesToList(ProductProperties productProperties){
        //new item top
        productPropertiesList.add(0, productProperties);
        notifyDataSetChanged();
    }

    public void deleteProductPropertiesFromList(ProductProperties productProperties){
        productPropertiesList.remove(productProperties);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onProductPropertiesDeleteClick(ProductProperties productProperties);
    }
}
