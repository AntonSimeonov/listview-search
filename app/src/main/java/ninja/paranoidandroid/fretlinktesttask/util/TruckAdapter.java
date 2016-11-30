package ninja.paranoidandroid.fretlinktesttask.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ninja.paranoidandroid.fretlinktesttask.R;
import ninja.paranoidandroid.fretlinktesttask.Truck;

/**
 * Created by anton on 07.07.16.
 */
public class TruckAdapter extends ArrayAdapter<Truck> implements Filterable{

    private final static String TAG = "TruckAdapter";

    private Context mContext;
    private ArrayList<Truck> mTruckList;
    private ArrayList<Truck> mFilteredTruckList;
    private int mResource;

    public TruckAdapter(Context context, int resource, ArrayList<Truck> truckList) {
        super(context, resource, truckList);

        mContext = context;
        mResource = resource;
        mTruckList = truckList;
        mFilteredTruckList = truckList;

    }

    class Holder{
        public TextView truckName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        Holder holder = null;

        if(row == null)
        {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(mResource, null);

            holder = new Holder();
            holder.truckName = (TextView)row.findViewById(R.id.tv_truck_adapter_row_truck_name);
            row.setTag(holder);

        }
        else
        {
            holder = (Holder)row.getTag();
        }

        Truck truck = getItem(position);
        holder.truckName.setText(truck.getTruckName());

        return row;
    }

    @Override
    public int getCount() {
        return mFilteredTruckList.size();
    }

    @Override
    public Truck getItem(int position) {

        return mFilteredTruckList.get(position);
    }

    public ArrayList<Truck> getFilteredTruckList(){

        return mFilteredTruckList;
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                FilterResults results = new FilterResults();
                ArrayList<Truck> FilteredArrList = new ArrayList<Truck>();

                if (charSequence == null || charSequence.length() == 0) {

                    results.count = mTruckList.size();
                    results.values = mTruckList;

                } else {

                    charSequence = charSequence.toString().toLowerCase();
                    for (int i = 0; i < mTruckList.size(); i++) {
                        String data = mTruckList.get(i).getTruckName();
                        if (data.toLowerCase().contains(charSequence.toString())) {
                            FilteredArrList.add(new Truck(mTruckList.get(i).getTruckID(),mTruckList.get(i).getTruckName()));
                        }
                    }

                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;

                }
                return results;


            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                mFilteredTruckList = (ArrayList<Truck>) filterResults.values;
                notifyDataSetChanged();

            }
        };

        return filter;
    }
}
