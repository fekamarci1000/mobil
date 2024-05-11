package com.example.csempe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.csempe.ListActivity;
import com.example.csempe.R;
import com.example.csempe.model.ShoppingItem;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ShoppingItemAdapter extends RecyclerView.Adapter<ShoppingItemAdapter.ViewHolder> implements Filterable {
    // Member variables.
    private ArrayList<ShoppingItem> mShopingItemData = new ArrayList<>();
    private ArrayList<ShoppingItem> mShopingItemDataAll = new ArrayList<>();
    private Context mContext;
    private int lastPosition = -1;

    public ShoppingItemAdapter(Context context, ArrayList<ShoppingItem> itemsData) {
        this.mShopingItemData = itemsData;
        this.mShopingItemDataAll = itemsData;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShoppingItemAdapter.ViewHolder holder, int position) {
        ShoppingItem currentItem = mShopingItemData.get(position);
        holder.bindTo(currentItem);


        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mShopingItemData.size();
    }


    @Override
    public Filter getFilter() {
        return shopingFilter;
    }

    private Filter shopingFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ShoppingItem> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0) {
                results.count = mShopingItemDataAll.size();
                results.values = mShopingItemDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(ShoppingItem item : mShopingItemDataAll) {
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mShopingItemData = (ArrayList)filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {

        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mBrands;
        private TextView mPriceText;
        private final ImageView mItemImage;
        private RatingBar mRatingBar;

        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.itemTitle);
            mBrands = itemView.findViewById(R.id.brands);
            mItemImage = itemView.findViewById(R.id.itemImage);
            mRatingBar = itemView.findViewById(R.id.ratingBar);
            mPriceText = itemView.findViewById(R.id.price);

            itemView.findViewById(R.id.add_to_cart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ListActivity)mContext).updateAlertIcon();
                }
            });
        }

        void bindTo(ShoppingItem currentItem){
            mTitleText.setText(currentItem.getName());
            mBrands.setText(currentItem.getBrand());
            mPriceText.setText(currentItem.getPrice());
            mRatingBar.setRating(currentItem.getRatings());

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(currentItem.getImageResource()).into(mItemImage);

        }
    }
}
