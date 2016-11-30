package ninja.paranoidandroid.fretlinktesttask.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ninja.paranoidandroid.fretlinktesttask.Truck;

/**
 * Created by anton on 07.07.16.
 */
public class ServerResponceParcer {

    private final static String TAG = "ServerResponceParcer";

    private String mServerResponceString;
    private ArrayList<Truck> mTruckList;

    public ServerResponceParcer(String serverResponceString){

        mServerResponceString = serverResponceString;
        mTruckList = new ArrayList<Truck>();

    }

    public ArrayList<Truck> getTruckArrayList(){

        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        try {

            jsonObject = new JSONObject(mServerResponceString);
            jsonArray = jsonObject.getJSONArray("response");

            for (int i = 0; i < jsonArray.length(); i++)
            {

                String truckName = jsonArray.getJSONObject(i).getString("vehicleName");
                String truckID = jsonArray.getJSONObject(i).getString("truck_id");
                mTruckList.add(new Truck(truckID, truckName));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
       return mTruckList;
    }

}
