package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {
    RecyclerView categories_recyceler_view ;
    CategoriesArrayAdapter adapter;
    ArrayList<String> titles;
    ArrayList<Integer> images;
    EcommerceDataBase dbobj;
    Cursor cursor_allcategories;


    int Customer_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);



        // This to Receive The Customer ID from Login Activity To Pass it to Product Activity
        Intent anything = getIntent();
        Customer_ID = anything.getIntExtra("custid" , -1);

        categories_recyceler_view = findViewById(R.id.categories_recyclerview);

        titles = new ArrayList<>();
        images = new ArrayList<>();


        dbobj = new EcommerceDataBase(getApplicationContext());
        cursor_allcategories = dbobj.GetAllCategories();

        while(!cursor_allcategories.isAfterLast()){
            titles.add(cursor_allcategories.getString(1));
            System.out.println(cursor_allcategories.getString(1));
            cursor_allcategories.moveToNext();
        }

        images.add(R.drawable.clothingicon);
        images.add(R.drawable.shoeicon);
        images.add(R.drawable.accessoriesicon);
        images.add(R.drawable.jerseysicon);
       /* images.add(R.drawable.watche);
        images.add(R.drawable.tv);
        images.add(R.drawable.projector);
        images.add(R.drawable.men_clothes);
        images.add(R.drawable.book);
        images.add(R.drawable.sport);*/

        adapter = new CategoriesArrayAdapter(titles, images, new OnRecyclerViewCategoryItemClickListener() {
            @Override
            public void OnItemClick(String s) {
                Intent i = new Intent(getApplicationContext() , ProductsActivity.class);
                Bundle b = new Bundle();
                b.putString("category" , s);
                b.putInt("custid" , Customer_ID);
                i.putExtras(b);
                startActivity(i);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext() , 2 , GridLayoutManager.VERTICAL , false);
        categories_recyceler_view.setAdapter(adapter);
        categories_recyceler_view.setLayoutManager(gridLayoutManager);
        int spanCount = 2; // 3 columns
        int spacing = 50; // 50px
        boolean includeEdge = true;
        categories_recyceler_view.addItemDecoration(new CategoriesItemDecoration(spanCount, spacing, includeEdge));
    }



    public void bar_chart(View view) {
        Intent i = new Intent(CategoriesActivity.this , StatisitcsBarChartActivity.class);
        startActivity(i);
    }
}