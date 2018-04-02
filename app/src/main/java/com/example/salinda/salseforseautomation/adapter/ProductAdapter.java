package com.example.salinda.salseforseautomation.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.salinda.salseforseautomation.Other.Animation;
import com.example.salinda.salseforseautomation.Other.CircleTransform;
import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.activity.ShowSingleProductActivity;
import com.example.salinda.salseforseautomation.model.ProductsModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> implements Filterable {
    private List<ProductsModel> productsModels;
    private List<ProductsModel> productsModelFiltered;
    public static final String KEY_PRODUCTS = "Products";
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, brand, price, quantity, category;
        CardView card;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.Product);
            brand = (TextView) itemView.findViewById(R.id.BrandName);
            price = (TextView) itemView.findViewById(R.id.Price);
            quantity = (TextView) itemView.findViewById(R.id.Quantity);
            category = (TextView) itemView.findViewById(R.id.Category);
            card = (CardView) itemView.findViewById(R.id.product_card);
            image = (ImageView) itemView.findViewById(R.id.productImage);
        }

    }

    public ProductAdapter(Context context, List<ProductsModel> productsModels) {
        this.context = context;
        this.productsModels = productsModels;
        this.productsModelFiltered = productsModels;

//        Intent intent = new Intent(context, ShowAlert.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(KEY_PRODUCTS, (Serializable) productsModelFiltered);
//        intent.putExtras(bundle);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final ProductsModel productsModel = productsModelFiltered.get(position);
        holder.name.setText(productsModel.getName());
        holder.brand.setText(productsModel.getBrand());
        holder.price.setText(Float.toString(productsModel.getPrice()));
        holder.quantity.setText(Integer.toString(productsModel.getQuantity()));
        holder.category.setText(productsModel.getCategory());
        holder.card.setOnClickListener(new CardView.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Animation().onClickAnimation(v);
            }
        });
        holder.card.setOnLongClickListener(new CardView.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new Animation().onLongClickAnimation(v);
                return false;
            }
        });
        Glide.with(context).load(productsModelFiltered.get(position).getImage()).crossFade().thumbnail(0.5f).bitmapTransform(new CircleTransform(context)).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return productsModelFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    productsModelFiltered = productsModels;
                } else {
                    List<ProductsModel> filteredList = new ArrayList<>();
                    for (ProductsModel row : productsModels) {

                        // name match condition. this might differ depending on your requirement
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getBrand().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getCategory().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    productsModelFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productsModelFiltered;
                //Toast.makeText(context,productsModelFiltered.size(), Toast.LENGTH_SHORT).show();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                //productsModelFiltered = (ArrayList<ProductsModel>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public void selectSingleProduct(int position){
        Intent intent = new Intent(context, ShowSingleProductActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_PRODUCTS, (Serializable) productsModelFiltered);
        intent.putExtra("position", position);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public ProductsModel getProductByPosition(int position){
        return productsModelFiltered.get(position);
    }
}
