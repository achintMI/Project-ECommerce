package com.example.achint.ecommerce.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.achint.ecommerce.R;
import com.example.achint.ecommerce.View.SearchActivity;

import java.util.List;
import java.util.Locale;


public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<String> searchResults;

    public ListViewAdapter(Context context, List<String> searchResults) {
        mContext = context;
        this.searchResults = searchResults;
        inflater = LayoutInflater.from(mContext);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return searchResults.size();
    }

    @Override
    public String getItem(int position) {
        return searchResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_items, null);
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(searchResults.get(position));

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(mContext, SearchActivity.class);
                searchIntent.putExtra("search", searchResults.get(position));
                mContext.startActivity(searchIntent);
                //((Activity)mContext).finish();
            }
        });
        return view;
    }
}