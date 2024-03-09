package com.example.ecommerce;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class ProductsActivity extends AppCompatActivity {

    String coming_category;
    int Customer_ID;

    int [] Clothing = {R.drawable.clothing1 , R.drawable.clothing2 , R.drawable.clothing3 , R.drawable.clothing4 , R.drawable.clothing5 , R.drawable.clothing6};
    int [] Shoes =   {R.drawable.shoe1 , R.drawable.shoe2 , R.drawable.shoe3 , R.drawable.shoe4 , R.drawable.shoe5 , R.drawable.shoe6};
    int [] Equipments_Accessories = {R.drawable.eq1 , R.drawable.eq2 , R.drawable.eq3 , R.drawable.eq4 , R.drawable.eq5,R.drawable.eq6};

    int [] Jerseys = {R.drawable.jersey1 , R.drawable.jersey2 , R.drawable.jersey3 , R.drawable.jersey4 , R.drawable.jersey5 , R.drawable.jersey6};

    int [][] all_categories = {Clothing,Shoes,Equipments_Accessories,Jerseys};


    EcommerceDataBase dbobj;

    RecyclerView product_recyclerView;
    ArrayList<Product> productArrayList;
    ProductArrayAdapter adapter;



    ImageButton btn_cart;
    EditText ed_productname;

    ImageButton btn_camera;


    ImageButton btn_voice;
    String str_voice;
    private static final int REQUEST_CODE = 100;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    str_voice  = result.get(0);
                    ed_productname.setText(str_voice);
                }
                break;
            }

        }
    }


    @Override
    protected void onRestart() {
        while (productArrayList.size() > 0){
            adapter.removeAt(0);
        }
        SpecifyDisplayedProducts();
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        btn_cart = (ImageButton)findViewById(R.id.btn_cart_that_in_productactivity);
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , OrdersActivity.class);
                startActivity(i);
            }
        });



        Bundle b = getIntent().getExtras();
        coming_category = b.getString("category");
        Customer_ID = b.getInt("custid");


        dbobj = new EcommerceDataBase(getApplicationContext());
        productArrayList = new ArrayList<Product>();

        SpecifyDisplayedProducts();

        adapter = new ProductArrayAdapter(productArrayList, new OnButtonClickListenerInProductRecyclerViewItem() {
            @Override
            public void OnButtonClickListener(int position, View item) {
                Product tmp = productArrayList.get(position);


                Product p = new Product(tmp.ProductID , tmp.ProductName , tmp.ProductPrice , tmp.ProductQuantity , tmp.ProductImageSrc);


                TextView tv_quan = (TextView)item.findViewById(R.id.tv_productquantity);
                int tv_new_quantity = Cart.GetNumberFromString(tv_quan.getText().toString()) - 1;


                Cart.AddProductToCart(p);
                ((TextView)findViewById(R.id.txt_count_badge)).setText(String.valueOf(Cart.product_and_quantity.size()));
                if(tv_new_quantity > 0) {
                    dbobj.NewQuantity(p.ProductID , tv_new_quantity);
                    tv_quan.setText("Quantity : " + tv_new_quantity);
                }
                else{
                    adapter.removeAt(position);
                }
            }
        });
                product_recyclerView = findViewById(R.id.product_recyclerview);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getApplicationContext());
        product_recyclerView.setLayoutManager(lm);
        product_recyclerView.setAdapter(adapter);
        //product_recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext() , DividerItemDecoration.VERTICAL));
        product_recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));



        ed_productname = findViewById(R.id.ed_search_for_product);
        ed_productname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                FilterbyText(s.toString());
            }
        });





        btn_voice = findViewById(R.id.btn_voice);
        btn_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                try {
                    startActivityForResult(intent, REQUEST_CODE);
                } catch (ActivityNotFoundException a) {

                }
            }
        });

        btn_camera=findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),BarcodeScanner.class);
                startActivity(intent);

              /* try{
                   Intent intent = new Intent();
                   intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                   startActivity(intent);
               }catch (Exception e)
               {
                   e.printStackTrace();
               }
*/
            }
        });


    }

    public void SpecifyDisplayedProducts(){
        int indx_in_array_all_categories = dbobj.GetCategoryID(coming_category) - 1;
        Cursor c = dbobj.GetProducts_ForSpecificCategory(indx_in_array_all_categories + 1);
        int pos = 0;
        while(!c.isAfterLast())
        {
            String name = c.getString(1);
            int id = Integer.parseInt(c.getString(0)), price = Integer.parseInt(c.getString(2)) , quantity = Integer.parseInt(c.getString(3));
            Product product = new Product(id , name , price , quantity , all_categories[indx_in_array_all_categories][pos]);
            productArrayList.add(product);
            System.out.println(id + " " + name +" "+price+" "+quantity+" "+all_categories[indx_in_array_all_categories][pos]);
            pos++;
            c.moveToNext();
        }
        ((TextView)findViewById(R.id.txt_count_badge)).setText(String.valueOf(Cart.product_and_quantity.size()));
    }
    public void FilterbyText(String text){
        ArrayList<Product> filteredArrayList = new ArrayList<Product>();
        for(int i = 0 ; i < productArrayList.size() ; i++){
            Product p = productArrayList.get(i);
            if(p.ProductName.toLowerCase().contains(text.toLowerCase()))
            {
                filteredArrayList.add(p);
            }
        }
        adapter.FilterArrayList(filteredArrayList);
    }
}