package com.example.djiang.listviewtest;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    MyAdapter adapter;
    List<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);
        adapter = new MyAdapter(this, items);
        listView.setAdapter(adapter);

        adapter.resetItems(getSomeItems());

    }


    private List<Item> getSomeItems() {
        List<Item> items = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            items.add(new Item("name" + i, "company" + i, "title" + i));
        }
        return items;
    }


}


class Item {
    String name;
    String company;
    String title;

    public Item(String name, String company, String title) {
        this.name = name;
        this.company = company;
        this.title = title;
    }
}


class MyAdapter extends BaseAdapter {

    Activity activity;
    List<Item> items = new ArrayList<>();

    MyAdapter(Activity activity, List<Item> items) {
        this.activity = activity;
        this.items = items;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return items.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position The position of the item within the adapter's data set of the item whose view
     *                 we want.
     * @param view     The old view to reuse, if possible. Note: You should check that this view
     *                 is non-null and of an appropriate type before using. If it is not possible to convert
     *                 this view to display the correct data, this method can create a new view.
     *                 Heterogeneous lists can specify their number of view types, so that this View is
     *                 always of the right type (see {@link #getViewTypeCount()} and
     *                 {@link #getItemViewType(int)}).
     * @param parent   The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        final Item item = items.get(position);

        ViewHolder viewHolder = null;

        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = activity.getLayoutInflater().inflate(R.layout.item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

        }

        final TextView name = viewHolder.name;
        name.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (true) {
                    name.setText(name.getText() + item.name);
                }
                name.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        viewHolder.name.setText(item.name);
        viewHolder.company.setText(item.company);
        viewHolder.title.setText(item.title);


        return view;
    }

    public void addItems(List<Item> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void resetItems(List<Item> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

}

class ViewHolder {

    TextView name;
    TextView company;
    TextView title;


    public ViewHolder(View view) {
        name = (TextView) view.findViewById(R.id.name);
        company = (TextView) view.findViewById(R.id.company);
        title = (TextView) view.findViewById(R.id.title);

    }

}