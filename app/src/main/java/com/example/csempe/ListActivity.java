package com.example.csempe;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csempe.Adapter.ShoppingItemAdapter;
import com.example.csempe.model.ShoppingItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private static final int LOG_OUT_BUTTON_ID = R.id.log_out_button;
    private static final int PROFILE_BUTTON_ID = R.id.profile_button;
    private static final int CART_BUTTON_ID = R.id.cart;
    private static final String LOG_TAG = ListActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private FrameLayout purpleCircle;
    private TextView countTextView;
    private int cartItems = 0;
    private int gridNumber = 1;

    // Member variables.
    private RecyclerView mRecyclerView;
    private ArrayList<ShoppingItem> mItemsData;
    private ShoppingItemAdapter mAdapter;

    private SharedPreferences preferences;

    private boolean viewRow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user == null){
            finish();
        }
        else {
            Log.d(LOG_TAG,"Authenticated user");
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(
                this, gridNumber));
        mItemsData = new ArrayList<>();
        mAdapter = new ShoppingItemAdapter(this, mItemsData);
        mRecyclerView.setAdapter(mAdapter);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
    }

    private void initializeData() {
        // Get the resources from the XML file.
        String[] itemsList = getResources()
                .getStringArray(R.array.shopping_item_names);
        String[] itemsInfo = getResources()
                .getStringArray(R.array.shopping_item_brands);
        String[] itemsPrice = getResources()
                .getStringArray(R.array.shopping_item_price);
        TypedArray itemsImageResources =
                getResources().obtainTypedArray(R.array.shopping_item_images);
        TypedArray itemRate = getResources().obtainTypedArray(R.array.shopping_item_rates);
        mItemsData.clear();
        for (int i = 0; i < itemsList.length; i++) {
            mItemsData.add(new ShoppingItem(itemsList[i], itemsInfo[i], itemsPrice[i], itemRate.getFloat(i, 0),
                    itemsImageResources.getResourceId(i, 0)));
        }
        ((TypedArray) itemsImageResources).recycle();
        mAdapter.notifyDataSetChanged();
    }




    public void navigateToWebshop(View view){
        setContentView(R.layout.activity_list);
    }
    public void navigateToHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void navigateToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.webshop_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(LOG_TAG, s);
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == LOG_OUT_BUTTON_ID) {
            Log.d(LOG_TAG, "Logout clicked!");
            FirebaseAuth.getInstance().signOut();
            finish();
        }
        if (item.getItemId() == PROFILE_BUTTON_ID){
            Log.d(LOG_TAG, "profile clicked!");
            FirebaseAuth.getInstance().signOut();
            return true;
        }
        if (item.getItemId() == CART_BUTTON_ID){
            Log.d(LOG_TAG, "Cart clicked!");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem alertMenuItem = menu.findItem(R.id.cart);
        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();

        purpleCircle = (FrameLayout) rootView.findViewById(R.id.view_alert_purple_circle);
        countTextView = (TextView) rootView.findViewById(R.id.view_alert_count_textview);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(alertMenuItem);
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }

    public void updateAlertIcon() {
        cartItems = (cartItems + 1);
        if (0 < cartItems) {
            countTextView.setText(String.valueOf(cartItems));
        } else {
            countTextView.setText("");
        }

        purpleCircle.setVisibility((cartItems > 0) ? VISIBLE : GONE);
    }

}