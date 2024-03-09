package com.example.ecommerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class EcommerceDataBase extends SQLiteOpenHelper {
    private static String dbname ="EcommerceDB";
    SQLiteDatabase EcommerceDB;

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    public EcommerceDataBase(@Nullable Context context) {
        super(context, dbname, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table Customers(CustID integer primary key autoincrement, Custname text, Username text, Password text, Birthdate text, Email text, Question text , Answer text)");

        db.execSQL("Create table Categories(CatID integer primary key autoincrement, Catname text)");


        db.execSQL("Create table Orders(OrdID integer primary key autoincrement, OrdDate text, CustID integer, Address text," +
                " foreign key(CustID) references Customers(CustID))");


        db.execSQL("Create table Products(ProID integer primary key autoincrement, ProName text, Price integer, Quantity integer, CatID integer," +
                " foreign key(CatID) references Categories(CatID))");


        db.execSQL("Create table OrderDetails(OrdID integer not null , Quantity integer , ProID integer not null , primary key(OrdID,ProID) ," +
                " foreign key(OrdID) references Orders(OrdID) , foreign key(ProID) references Products(ProID))");

        String [] catarr = {
                "Clothing",
                "Shoes",
                "Equipments / Accessories",
                "Jerseys",

        };

        String [] productname_arr = {
                "Adidas AEROREADY",  // Clothing
                "Intersport Black Shorts",
                "Intersport White Shorts",
                "Nike Kids Sweatpants",
                "Nike Kids Hoodie",
                "Puma-Women's Classic ",



                "Adidas 4DFWD",  // Shoes
                "Adidas Ultraboost Men",
                "Nike Jordan",
                "Puma Cali Dream",
                "Puma Dua Lipa",
                "Skechers  Go Walk Max",



                "Nike BucketHat",  // Equipments / Accessories
                "Puma Backpack",
                "Puma LaLiga Ball",
                "Puma Watch",
                "Puma Water Bottle",
                "Swimming Glasses",



                "Al Ahly Home Kit",  // Jerseys
                "Al Ahly Away Kit",
                "Al Zamalek Home Kit",
                "Al Zamalek Away Kit",
                "BVB Home Kit",
                "Manchester City Away Kit"



        };


        for(int i = 0 ; i < 4 ; i++){
            ContentValues content = new ContentValues();
            content.put("Catname" , catarr[i]);
            db.insert("Categories" , null , content);
        }

        int catid = 1;
        for(int i = 0 ; i < 24 ; i ++){
            String product_name = productname_arr[i];
            product_name = product_name.replace('_' , ' ');


            int quantity , price;
            quantity = getRandomNumber(2 , 30);
            price    = getRandomNumber(1500 , 2500);

            int j = i + 1;
            if(j % 6 == 1 && j > 1){
                catid++;
            }
            ContentValues content = new ContentValues();
            content.put("ProName" , product_name);
            content.put("Price" , price);
            content.put("Quantity" , quantity);
            content.put("CatID" , catid);
            db.insert("Products" , null , content);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Customers");
        db.execSQL("drop table if exists Categories");
        db.execSQL("drop table if exists Orders");
        db.execSQL("drop table if exists Products");
        db.execSQL("drop table if exists OrderDetails");
        onCreate(db);
    }


    // User Function
    public void SignUp(Customer c)
    {
        EcommerceDB = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("Custname" , c.Name);
        content.put("Username" , c.Username);
        content.put("Password" , c.Password);
        content.put("Email"    , c.Email );
        content.put("Birthdate" , c.BirthDate);
        content.put("Question" , c.Question);
        content.put("Answer" , c.Answer);

        long ret = EcommerceDB.insert("Customers" , null , content);
        EcommerceDB.close();
    }

    public boolean Login(String inusername , String inpassword){
        EcommerceDB = getReadableDatabase();
        String[] mycols = new String[] {"Username" , "Password" , "Email" , "Question" , "Answer"};
        String[] myargs = new String[] {inusername , inpassword};
        Cursor c = EcommerceDB.query("Customers" , mycols , "Username = ? and Password = ?" ,  myargs , null , null , null);


        if(c.getCount() > 0)
            return true;
        return false;
    }


    public Cursor ForgetPassword(String inusername){
        EcommerceDB = getReadableDatabase();
        String[] mycols = new String[] {"Username" , "Password" , "Email" , "Question" , "Answer"};
        String[] myargs = new String[] {inusername};
        Cursor c = EcommerceDB.query("Customers" , mycols , "Username = ?" ,  myargs , null , null , null);
        if(c != null)
            c.moveToFirst();
        return c;
    }

    public Cursor Get_Cust_Data(String inusername){
        EcommerceDB = getReadableDatabase();
        String[] mycols = new String[] {"Custname", "Username" , "Password" , "Birthdate" , "Email" , "Question" , "Answer"};
        String[] myargs = new String[] {inusername};
        Cursor c = EcommerceDB.query("Customers" , mycols , "Username = ?" ,  myargs , null , null , null);
        if(c != null)
            c.moveToFirst();
        return c;
    }

    public int GetCustomerrId(String username){
        EcommerceDB = getReadableDatabase();
        Cursor c = EcommerceDB.rawQuery("select CustID from Customers where Username = ?" , new String[]{username});
        c.moveToFirst();
        return Integer.parseInt(c.getString(0));
    }
    //----------------------------------------------------------------------------------


    //Categories
    //-----------------------------------------------------------------------------------
    public Cursor GetAllCategories(){
        EcommerceDB = getReadableDatabase();
        String[] mycols = new String[] {"CatID" , "Catname"};
        String[] myargs = new String[] {};
        Cursor c = EcommerceDB.query("Categories" , mycols , "" ,  myargs , null , null , null);
        if(c != null)
            c.moveToFirst();
        return c;

    }

    public int GetCategoryID(String category_name){
        EcommerceDB = getReadableDatabase();
        Cursor c = EcommerceDB.rawQuery("select CatID from Categories where Catname = ?" , new String[] {category_name});
        c.moveToFirst();
        return Integer.parseInt(c.getString(0).toString());
    }
    //-----------------------------------------------------------------------------------



    //Product
    //-----------------------------------------------------------------------------------
    public Cursor GetProducts_ForSpecificCategory(int category_id){
        EcommerceDB = getReadableDatabase();
        String str_id = String.valueOf(category_id);
        Cursor c = EcommerceDB.rawQuery("select ProID,ProName,Price,Quantity from Products where CatID = ?" , new String[] {str_id});
        if(c != null)
            c.moveToFirst();
        return c;
    }
    // add it to cart
    public void NewQuantity(int productid , int new_quantity){
        EcommerceDB = getWritableDatabase();
        String q = "update Products set Quantity=" + new_quantity + " where ProID=" + productid;
        EcommerceDB.execSQL(q);
    }

    public int GetQuantityforProduct(int productid){
        EcommerceDB = getReadableDatabase();
        String str_id = String.valueOf(productid);
        Cursor c = EcommerceDB.rawQuery("select Quantity from Products where ProID = ?" , new String[] {str_id});
        if(c != null)
            c.moveToFirst();
        return Integer.parseInt(c.getString(0));
    }
    public void AddMoreQuantityToProduct(int productid , int quantity){
        int oldquantity = GetQuantityforProduct(productid);
        int new_quantity = quantity + oldquantity;
        EcommerceDB = getWritableDatabase();
        String q = "update Products set Quantity=" + new_quantity + " where ProID=" + productid;
        EcommerceDB.execSQL(q);
    }

    public int GetProductPrice(int productid){
        EcommerceDB = getReadableDatabase();
        String str_id = String.valueOf(productid);
        Cursor c = EcommerceDB.rawQuery("select Price from Products where ProID = ?" , new String[] {str_id});
        if(c != null)
            c.moveToFirst();
        return Integer.parseInt(c.getString(0));
    }

    public boolean CheckIFQuantityAvailableForProduct(int productid , int requred_quantity){
        EcommerceDB = getReadableDatabase();
        String str_productid = String.valueOf(productid);
        String str_requred_quantity = String.valueOf(requred_quantity);

        Cursor c = EcommerceDB.rawQuery("select * from Products where ProID = ? and Quantity >= ?" , new String[] {str_productid , str_requred_quantity});
        if(c.getCount() > 0)
            return true;
        return false;
    }
    //-----------------------------------------------------------------------------------



    //Orders
    //-----------------------------------------------------------------------------------
    public int GetMaxOrderID(){
        EcommerceDB = getReadableDatabase();
        Cursor c = EcommerceDB.rawQuery("select max(OrdID) from Orders" , new String[] {});
        int answer = 0;
        try {
            if(c.getCount() > 0) {
                c.moveToFirst();
                answer = Integer.parseInt(c.getString(0).toString());
            }
        }
        catch (Exception e){
            answer = 0;
        }
        return answer;
    }

    public void InsertIntoOrder(String date , int custid , String address){
        EcommerceDB = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("OrdDate" , date);
        content.put("CustID" , custid);
        content.put("Address" , address);

        long ret = EcommerceDB.insert("Orders" , null , content);
        EcommerceDB.close();
    }
    //-----------------------------------------------------------------------------------



    //OrderDetails
    //----------------------------------------------------------------------------------
    public void InsertIntoOrderDetails(int orderid , int prodid , int quantity){
        EcommerceDB = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("OrdID" , orderid);
        content.put("ProID" , prodid);
        content.put("Quantity" , quantity);

        long ret = EcommerceDB.insert("OrderDetails" , null , content);
        EcommerceDB.close();
    }
    //----------------------------------------------------------------------------------




    //Statistics
    public Cursor Stats(){
        EcommerceDB = getReadableDatabase();
        Cursor c = EcommerceDB.rawQuery("select ProID,Quantity from OrderDetails" , new String[] {});
        c.moveToFirst();
        EcommerceDB.close();
        return c;
    }




    public String BarChartFunction(int productid){
        EcommerceDB = getReadableDatabase();
        Cursor c = EcommerceDB.rawQuery("select ProName from Products where ProID=?" , new String[] {String.valueOf(productid)});
        c.moveToFirst();
        EcommerceDB.close();
        return c.getString(0);
    }
}
