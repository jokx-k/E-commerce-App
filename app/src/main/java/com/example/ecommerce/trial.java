//package com.example.ecommerce;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.app.AlertDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import org.w3c.dom.Text;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Date;
//import java.util.Set;
//
//public class OrdersActivity extends AppCompatActivity {
//    ArrayList<Product> productArrayList;
//    RecyclerView order_recyclerView;
//    OrderArrayAdapter adapter;
//    EcommerceDataBase dobj;
//
//
//    ImageButton btn_location;
//
//
//    Button request_an_order;
//
//    @Override
//    protected void onRestart() {
//        if(!Cart.Cust_Address.equals("")){
//            btn_location.setBackground(getResources().getDrawable(R.drawable.custom_button_for_camera_and_shoppingcart_and_barcode_greencolor));
//        }
//        super.onRestart();
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_orders);
//
//        dobj = new EcommerceDataBase(getApplicationContext());
//        btn_location = (ImageButton)findViewById(R.id.btn_location);
//        if(!Cart.Cust_Address.equals("")){
//            btn_location.setBackground(getResources().getDrawable(R.drawable.custom_button_for_camera_and_shoppingcart_and_barcode_greencolor));
//        }
//        request_an_order = findViewById(R.id.btn_request_order);
//
//        FillproductArraListWithData();
//
//        order_recyclerView = findViewById(R.id.order_recyclerview);
//        adapter = new OrderArrayAdapter(productArrayList);
//        RecyclerView.LayoutManager lm = new LinearLayoutManager(getApplicationContext());
//        order_recyclerView.setLayoutManager(lm);
//        order_recyclerView.setAdapter(adapter);
//        //product_recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext() , DividerItemDecoration.VERTICAL));
//        order_recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));
//
//
//        btn_location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(Cart.Cust_Address.equals("")) {
//                    Intent i = new Intent(getApplicationContext(), GoogleMapsActivity.class);
//                    startActivity(i);
//                }
//            }
//        });
//
//        request_an_order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!Cart.Cust_Address.equals("") && Cart.product_and_quantity.size() > 0)
//                {
//                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//                    Date dateobj = new Date();
//                    String orderdate = dateFormat.format(dateobj);
//                    int custid = Cart.CustID;
//                    int ordid = dobj.GetMaxOrderID() + 1;
//                    String cust_address = Cart.Cust_Address;
//
//                    int total_price_that_customer_will_pay = 0;
//                    dobj.InsertIntoOrder(orderdate , custid , cust_address);
//                    for(int i = 0 ; i < productArrayList.size() ; i++)
//                    {
//                        total_price_that_customer_will_pay += productArrayList.get(i).ProductPrice;
//                        dobj.InsertIntoOrderDetails(ordid , productArrayList.get(i).ProductID , productArrayList.get(i).ProductQuantity);
//                    }
//
//                    //TextView popup_done_txt_total_payment = ((TextView)findViewById(R.id.popup_done_txt_total_payment));
//                    //popup_done_txt_total_payment.setText("Total Payment is " + total_price_that_customer_will_pay + " EGP");
//                    //System.out.println("RRRRRRRRRRRRRRR" + "Total Payment is " + total_price_that_customer_will_pay + " EGP");
//
//                    LayoutInflater inflater = LayoutInflater.from(OrdersActivity.this);
//                    final View dialogview = inflater.inflate(R.layout.popup_done, null);
//                    final AlertDialog dialog = new AlertDialog.Builder(OrdersActivity.this).create();
//                    dialog.setView(dialogview);
//                    ((TextView)dialogview.findViewById(R.id.popup_done_txt_total_payment)).setText("Total Payment is " + total_price_that_customer_will_pay + " EGP");
//                    dialogview.findViewById(R.id.popup_done_txt_done).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                            Intent intent = new Intent(OrdersActivity.this , CategoriesActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                            Cart.MakeCartEmpty();
//                            startActivity(intent);
//                        }
//                    });
//                    dialog.show();
//
//
//
//                }
//                else
//                {
//                    if(Cart.Cust_Address.equals("")) {
//                        // Shows Pop up menu that tells u to confirm location first
//                        LayoutInflater inflater = LayoutInflater.from(OrdersActivity.this);
//                        final View dialogview = inflater.inflate(R.layout.popup_determine_locationfirst, null);
//                        final AlertDialog dialog = new AlertDialog.Builder(OrdersActivity.this).create();
//                        dialog.setView(dialogview);
//                        dialogview.findViewById(R.id.popup_determine_location_close).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
//                        dialog.show();
//                    }
//                    if(Cart.product_and_quantity.size() == 0)
//                    {
//                        LayoutInflater myinflater = getLayoutInflater();
//                        View myview = myinflater.inflate(R.layout.custom_toast , (ViewGroup)findViewById(R.id.custom_toast1));
//
//                        Toast t = new Toast(OrdersActivity.this);
//                        t.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL , 0 , 0);
//                        t.setDuration(Toast.LENGTH_LONG);
//                        t.setView(myview);
//                        t.show();
//                    }
//                }
//
//            }
//        });
//    }
//
//    public void FillproductArraListWithData(){
//        productArrayList = new ArrayList<Product>();
//        Set<Integer> setOfCountries = Cart.product_and_quantity.keySet();
//        for(int key : setOfCountries) {
//            Product tmp = Cart.product_and_quantity.get(key).first;
//            Product p = new Product(tmp.ProductID , tmp.ProductName , tmp.ProductPrice , tmp.ProductQuantity , tmp.ProductImageSrc);
//            productArrayList.add(p);
//        }
//    }
//
//
//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        int position = item.getGroupId();
//        Product tmp = productArrayList.get(position);
//        Product p = new Product(tmp.ProductID ,tmp.ProductName , tmp.ProductPrice , tmp.ProductQuantity , tmp.ProductImageSrc);
//        if(item.getItemId() == 121){
//            // Delete it from Cart
//            Cart.DeleteProductFromCart(p);
//            // Delete From Database
//            dobj.AddMoreQuantityToProduct(p.ProductID , p.ProductQuantity);
//            // Delete it from Adapter
//            adapter.removeAt(position);
//            return true;
//        }
//        if(item.getItemId() == 122){
//            LayoutInflater inflater = LayoutInflater.from(this);
//            final View dialogview = inflater.inflate(R.layout.popup , null);
//            final AlertDialog dialog = new AlertDialog.Builder(this).create();
//            dialog.setView(dialogview);
//            dialogview.findViewById(R.id.popup_button).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    EditText entered_quantity_ed = dialogview.findViewById(R.id.popup_edittext);
//                    int entered_quantity = Integer.parseInt(entered_quantity_ed.getText().toString());
//                    int cur_quantity = p.ProductQuantity;
//                    if(entered_quantity > cur_quantity)
//                    {
//                        boolean available_in_stock_or_not = dobj.CheckIFQuantityAvailableForProduct(p.ProductID , entered_quantity - cur_quantity);
//                        if(available_in_stock_or_not == true) {
//                            //update the and price quantity in product object
//                            p.ProductQuantity = entered_quantity;
//                            int actual_product_price_from_database = dobj.GetProductPrice(p.ProductID);
//                            p.ProductPrice = p.ProductQuantity * actual_product_price_from_database;
//                            //Add the difference between them to the the quantity in database
//                            int actual_quanttiy_from_databse = dobj.GetQuantityforProduct(p.ProductID);
//                            dobj.ReducingQuantitybtOne(p.ProductID , actual_quanttiy_from_databse - (entered_quantity - cur_quantity));
//                            //Update the quantity in the Cart class
//                            Cart.UpdateProductToCart(p , p.ProductQuantity);
//                            //Update in the ArrayList of Adapter
//                            adapter.updateAt(position , p);
//                        }
//                        else{
//                            // Should Tell Him that no available in stock to add it to cart
//                        }
//                    }
//                    else if(entered_quantity < cur_quantity){
//                        //update the and price quantity in product object
//                        p.ProductQuantity = entered_quantity;
//                        int actual_product_price_from_database = dobj.GetProductPrice(p.ProductID);
//                        p.ProductPrice = p.ProductQuantity * actual_product_price_from_database;
//                        //Add the difference between them to the the quantity in database
//                        dobj.AddMoreQuantityToProduct(p.ProductID , cur_quantity - entered_quantity);
//                        //Update the quantity in the Cart class
//                        Cart.UpdateProductToCart(p , p.ProductQuantity);
//                        //Update in the ArrayList of Adapter
//                        adapter.updateAt(position , p);
//                    }
//                    dialog.dismiss();
//                }
//            });
//
//            dialog.show();
//            return true;
//        }
//        return super.onContextItemSelected(item);
//    }
//}