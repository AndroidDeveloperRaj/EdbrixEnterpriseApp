package com.edbrix.enterprise.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edbrix.enterprise.Models.CourseContents;
import com.edbrix.enterprise.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajk
 */
public class CategorySpinnerAdapter extends BaseAdapter {
    private ArrayList<CourseContents> mItems = new ArrayList<>();
    private Context context;

    public CategorySpinnerAdapter(Context context){
     this.context = context;
    }

    public void clear() {
        mItems.clear();
    }

    public void addItem(CourseContents yourObject) {
        mItems.add(yourObject);
    }

    public void addItems(ArrayList<CourseContents> yourObjectList) {
        mItems.addAll(yourObjectList);
    }

    @Override
    public int getCount() {
//        int count = mItems.size();
//        return count > 0 ? count - 1 : count;
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup parent) {
        if (view == null || !view.getTag().toString().equals("DROPDOWN")) {
            view = LayoutInflater.from(context).inflate(R.layout.toolbar_regular_spinner_item_dropdown, parent, false);
            view.setTag("DROPDOWN");
        }

        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(getTitle(position));
        return view;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null || !view.getTag().toString().equals("NON_DROPDOWN")) {
            view = LayoutInflater.from(context).inflate(R.layout.
                    toolbar_regular_spinner_item_actionbar, parent, false);
            view.setTag("NON_DROPDOWN");
        }
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(getTitle(position));
        return view;
    }

    private String getTitle(int position) {
//        return position >= 0 && position < mItems.size() ? mItems.get(position).getTitle(): "";
        return mItems.get(position).getTitle();
    }

    public int getCategoryListPosition (String categoryId){
        if(mItems!=null && !mItems.isEmpty()) {
            for (int i = 0; i < mItems.size(); i++) {
                if (categoryId.equalsIgnoreCase(mItems.get(i).getId())) {
                    return i;
                }
            }
        }
        return 0;
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        View row = LayoutInflater.from(context).inflate(R.layout.toolbar_regular_spinner_item_dropdown, parent, false);

        TextView headerText = (TextView) row.findViewById(android.R.id.text1);

        headerText.setText(getTitle(position));

        return row;
    }
}