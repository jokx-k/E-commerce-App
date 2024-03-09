package com.example.ecommerce;

public class Product {
    String ProductName;
    int ProductID , ProductPrice , ProductQuantity , ProductImageSrc;

    public Product(int productId , String productName, int productPrice, int productQuantity, int productImageSrc) {
        ProductID = productId;
        ProductName = productName;
        ProductPrice = productPrice;
        ProductQuantity = productQuantity;
        ProductImageSrc = productImageSrc;
    }
}
