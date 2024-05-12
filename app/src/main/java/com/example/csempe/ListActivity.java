package com.example.csempe;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.ktx.Firebase;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private static final int LOG_OUT_BUTTON_ID = R.id.log_out_button;
    private static final int PROFILE_BUTTON_ID = R.id.profile_button;
    private static final int CART_BUTTON_ID = R.id.cart;
    private static final String LOG_TAG = ListActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private FrameLayout purpleCircle;
    private TextView countTextView;
    private int cartItems = 0;
    private CollectionReference mItems;
    private Integer itemLimit = 5;

    private RecyclerView mRecyclerView;
    private ArrayList<ShoppingItem> mItemsData;
    private ShoppingItemAdapter mAdapter;

    private SharedPreferences preferences;
    private boolean sortByPrice = false;
    private boolean groupByBrand = false;
    private Button sortByPriceButton;
    private int sortByPriceButtonClicked=0;
    private int groupByBrandButtonClicked=0;
    private Button groupByBrandButton;

    private boolean isSortByPriceVisible = true;
    private boolean isGroupByBrandVisible = true;


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
                this, 1));
        mItemsData = new ArrayList<>();
        mAdapter = new ShoppingItemAdapter(this, mItemsData);
        mRecyclerView.setAdapter(mAdapter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("ShoppingItem");
        queryData();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        this.registerReceiver(powerReceiver, filter);

        sortByPriceButton = findViewById(R.id.sort_by_price_button);
        groupByBrandButton = findViewById(R.id.group_by_brand_button);

        /*sortByPriceButton.setOnClickListener(v -> {
            sortByPrice = !sortByPrice;
            sortByPriceButtonClicked++;
            queryData();
        });

        groupByBrandButton.setOnClickListener(v -> {
            groupByBrand = !groupByBrand;
            groupByBrandButtonClicked++;
            queryData();
        });*/
        sortByPriceButton.setOnClickListener(v -> {
            isSortByPriceVisible = !isSortByPriceVisible;
            sortByPrice = !sortByPrice;
            updateButtonVisibility();
            queryData();
        });

        groupByBrandButton.setOnClickListener(v -> {
            isGroupByBrandVisible = !isGroupByBrandVisible;
            groupByBrand = !groupByBrand;
            updateButtonVisibility();
            queryData();
        });

    }
    private void updateButtonVisibility() {

        sortByPriceButton.setVisibility(isGroupByBrandVisible ? View.VISIBLE : View.GONE);
        groupByBrandButton.setVisibility(isSortByPriceVisible ? View.VISIBLE : View.GONE);
    }
    /*private void updateButtonVisibility() {
        if (sortByPriceButtonClicked == 0 && groupByBrandButtonClicked==0) {
            sortByPriceButton.setVisibility(View.VISIBLE);
            groupByBrandButton.setVisibility(View.VISIBLE);
        } else if (sortByPriceButtonClicked>0) {
            // Only sort by price is selected, hide group by brand
            if(sortByPriceButtonClicked>1){
                sortByPriceButtonClicked=0;
            }
            sortByPriceButton.setVisibility(View.VISIBLE);
            groupByBrandButton.setVisibility(View.GONE);
        } else if (groupByBrandButtonClicked>0) {
            if (groupByBrandButtonClicked>1) groupByBrandButtonClicked=0;
            sortByPriceButton.setVisibility(View.GONE);
            groupByBrandButton.setVisibility(View.VISIBLE);
        }
    }*/
    BroadcastReceiver powerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();

            if (intentAction == null)
                return;

            switch (intentAction) {
                case Intent.ACTION_POWER_CONNECTED:
                    itemLimit = 10;
                    queryData();
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    itemLimit = 5;
                    queryData();
                    break;
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(powerReceiver);
    }
    private void queryData() {
        mItemsData.clear();
        Query query = mItems;


        if (sortByPrice) {
            query = query.orderBy("price", Query.Direction.ASCENDING);
        }

        if (groupByBrand) {
            query = query.orderBy("brand", Query.Direction.ASCENDING);
        }

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            Log.d(LOG_TAG, "Query successful!");
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                ShoppingItem item = document.toObject(ShoppingItem.class);
                mItemsData.add(item);
                Log.d(LOG_TAG, item.getName());
            }

            if (mItemsData.size() == 0) {
                Log.d(LOG_TAG, "No data");
                initializeData();
                queryData();
            }
            Log.d(LOG_TAG, "Updating adapter...");
            mAdapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            Log.d(LOG_TAG, "Query failed!");
        });
    }

    private void initializeData() {
        // Get the resources from the XML file.
        String[] itemsList = getResources()
                .getStringArray(R.array.shopping_item_names);

        String[] itemsBrand = getResources()
                .getStringArray(R.array.shopping_item_brands);

        String[] itemsPrice = getResources()
                .getStringArray(R.array.shopping_item_price);
        TypedArray itemsImageResources =
                getResources().obtainTypedArray(R.array.shopping_item_images);
        TypedArray itemRate = getResources().obtainTypedArray(R.array.shopping_item_rates);
        for (int i = 0; i < itemsList.length; i++) {
            Log.d(LOG_TAG, ""+itemsList[i]+" "+ itemsBrand[i]+" "+ itemsPrice[i]);
            mItems.add(new ShoppingItem(
                    itemsList[i],
                    itemsBrand[i],
                    itemsPrice[i],
                    itemRate.getFloat(i, 0),
                    itemsImageResources.getResourceId(i, 0)));
        }

        // Recycle the typed array.
        itemsImageResources.recycle();
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
    @Override
    protected void onResume() {
        super.onResume();
        queryData();
    }

}