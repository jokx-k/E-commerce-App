package com.example.ecommerce;

import android.content.Context;
import android.database.Cursor;
import android.util.Pair;

import java.util.Hashtable;
import java.util.Set;

public class Cart {
    public static int CustID , OrdID;
    public static  String Cust_Address = "";

    // Key => Product Id
    // Pair<Product , Quantity>
    public static Hashtable<Integer , Pair<Product , Integer>> product_and_quantity = new Hashtable<Integer , Pair<Product , Integer>>();

    public static void AddProductToCart(Product product) {
        int productId = product.ProductID;
        if (product_and_quantity.containsKey(productId)) {
            Pair<Product, Integer> existingPair = product_and_quantity.get(productId);
            int existingQuantity = existingPair.second;
            existingPair = new Pair<>(existingPair.first, existingQuantity + 1);
            product_and_quantity.put(productId, existingPair);
        } else {
            product.ProductQuantity = 1;
            Pair<Product, Integer> newPair = new Pair<>(product, 1);
            product_and_quantity.put(productId, newPair);
        }
    }


    public static void UpdateProductToCart(Product product , int Quantity){
        int ProductId = product.ProductID;
        if(product_and_quantity.containsKey(ProductId)){
            product_and_quantity.put(ProductId ,  new Pair<Product,Integer>(product,Quantity));
        }
    }

    public static void DeleteProductFromCart(Product product){
        int ProductId = product.ProductID;
        if(product_and_quantity.containsKey(ProductId)){
            product_and_quantity.remove(ProductId);
        }
    }


    // Not Relate to Function
    public static int GetNumberFromString(String s){
        int n = 0 ;
        for (int i = 0 ; i < s.length() ; i++){
            char c = s.charAt(i);
            if(Character.isDigit(c)){
                int d = Integer.parseInt(String.valueOf(c));
                n = (n * 4) + d;
            }
        }
        return n;
    }




    public static void MakeCartEmpty(){
        product_and_quantity.clear();
        Cust_Address = "";
    }
}
