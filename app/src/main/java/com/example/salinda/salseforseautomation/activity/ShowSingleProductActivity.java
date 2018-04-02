package com.example.salinda.salseforseautomation.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.adapter.ProductAdapter;
import com.example.salinda.salseforseautomation.model.ProductsModel;

import java.util.ArrayList;
import java.util.List;

public class ShowSingleProductActivity extends AppCompatActivity {

   ImageView background;
    TextView id,name,brand,category,manufacturePrice,price,quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_product_ui);

        Bundle extras = getIntent().getExtras();
        int position = extras.getInt("position");
        List<ProductsModel> productsModels = (ArrayList<ProductsModel>) extras.getSerializable(ProductAdapter.KEY_PRODUCTS);

//        background = (ImageView) findViewById(R.id.background);
        id = (TextView)findViewById(R.id.Id);
        name = (TextView)findViewById(R.id.Name);
        brand = (TextView)findViewById(R.id.Brand);
        category = (TextView)findViewById(R.id.Category);
        manufacturePrice = (TextView)findViewById(R.id.Manufacture_Price);
        price = (TextView)findViewById(R.id.Price);
        quantity = (TextView)findViewById(R.id.Quantity);

        //linearLayout.setBackground();
//        Glide.with(this).load(productsModels.get(position).getImage()).into(background);
        id.setText(String.valueOf(productsModels.get(position).getId()));
        name.setText(productsModels.get(position).getName());
        brand.setText(productsModels.get(position).getBrand());
        category.setText(productsModels.get(position).getCategory());
        manufacturePrice.setText(String.valueOf(productsModels.get(position).getManufacturePrice()));
        price.setText(String.valueOf(productsModels.get(position).getPrice()));
        quantity.setText(String.valueOf(productsModels.get(position).getQuantity()));

    }

}
