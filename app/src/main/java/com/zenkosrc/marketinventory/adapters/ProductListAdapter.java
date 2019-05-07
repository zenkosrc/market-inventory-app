package com.zenkosrc.marketinventory.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenkosrc.marketinventory.R;
import com.zenkosrc.marketinventory.database.Product;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>{

    private List<Product>           productList;
    private OnItemClickListener     onItemClickListener;

    public ProductListAdapter(List<Product> productList, OnItemClickListener onItemClickListener) {
        this.productList            = productList;
        this.onItemClickListener    = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView    barCodeTextView;
        private TextView    nameTextView;
        private TextView    groupTextView;
        private ImageView   deleteImageView;

        public ViewHolder(final View itemView) {
            super(itemView);

            barCodeTextView     = itemView.findViewById(R.id.barCodeTextView);
            nameTextView        = itemView.findViewById(R.id.nameTextView);
            groupTextView       = itemView.findViewById(R.id.groupTextView);
            deleteImageView     = itemView.findViewById(R.id.deleteImageView);


            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onItemClickListener.onProductDeleteClick(productList.get(getLayoutPosition()));
                }
            });

        }

        public void bind(Product product) {
            barCodeTextView.setText(product.getBarcode());
            nameTextView.setText(product.getName());
            groupTextView.setText(product.getGroup());
        }
    }

    public void deleteProductFromList(Product product){
        productList.remove(product);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onProductDeleteClick(Product product);
    }

}
