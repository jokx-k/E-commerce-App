package com.example.ecommerce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ProductArrayAdapter extends RecyclerView.Adapter<ProductArrayAdapter.ViewHolder> {
    ArrayList<Product> AllProducts_With_Image;
    OnButtonClickListenerInProductRecyclerViewItem listener;
    public ProductArrayAdapter(ArrayList<Product> allProducts_With_Image , OnButtonClickListenerInProductRecyclerViewItem listener) {
        AllProducts_With_Image = allProducts_With_Image;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product_recyclerview_item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product p = AllProducts_With_Image.get(position);
        holder.tv_productname.setText(p.ProductName);
        holder.tv_productprice.setText("Price : " + String.valueOf(p.ProductPrice));
        holder.tv_productquantity.setText("Quantity : " + String.valueOf(p.ProductQuantity));
        holder.image_productimage.setImageResource(p.ProductImageSrc);
    }

    @Override
    public int getItemCount() {
        return AllProducts_With_Image.size();
    }


    public void removeAt(int position) {
        AllProducts_With_Image.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, AllProducts_With_Image.size());
    }

    public void FilterArrayList(ArrayList<Product> filterproductArrayList){
        AllProducts_With_Image = filterproductArrayList;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_productname , tv_productprice , tv_productquantity;
        ImageView image_productimage;
        Button btn_add_prodcut_to_cart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_productname = itemView.findViewById(R.id.tv_productname);
            image_productimage = itemView.findViewById(R.id.image_productimage);
            tv_productprice = itemView.findViewById(R.id.tv_productprice);
            tv_productquantity = itemView.findViewById(R.id.tv_productquantity);
            btn_add_prodcut_to_cart = itemView.findViewById(R.id.btn_add_product_to_cart);

            btn_add_prodcut_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.OnButtonClickListener(position , itemView);
                }
            });

        }
    }
}
