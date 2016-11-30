package ninja.paranoidandroid.fretlinktesttask;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ninja.paranoidandroid.fretlinktesttask.util.FretLinkConnection;
import ninja.paranoidandroid.fretlinktesttask.util.ServerResponceParcer;
import ninja.paranoidandroid.fretlinktesttask.util.TruckAdapter;

/**
 * Created by anton on 07.07.16.
 */
public class FretLinkConnectionFragment extends Fragment {

    private final static String TAG = "FLCF";
    private TruckListGetter mTruckListGetter;
    private ArrayList<Truck> mTruckList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mTruckListGetter = (TruckListGetter) activity;
        asyncExecuete();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mTruckListGetter = null;

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void asyncExecuete(){

        new FretLinkAsynkConnection().execute();

    }

    private class FretLinkAsynkConnection extends AsyncTask<Void, Void, ArrayList<Truck>> {

        @Override
        protected ArrayList<Truck> doInBackground(Void... voids) {

            FretLinkConnection fretLinkConnection = new FretLinkConnection();
            fretLinkConnection.setConnection();
            fretLinkConnection.setRequestHeader();
            fretLinkConnection.sendPostRequest("email=demo@fretlink.com");
            String responce = fretLinkConnection.getServerResponce();

            ServerResponceParcer serverResponceParcer = new ServerResponceParcer(responce);

            return serverResponceParcer.getTruckArrayList();
        }

        @Override
        protected void onPostExecute(ArrayList<Truck> trucks) {

            super.onPostExecute(trucks);
            mTruckList = trucks;
            mTruckListGetter.setTruckList(trucks);

        }
    }

    public ArrayList<Truck> getTruckList(){

        return mTruckList;
    }

    interface TruckListGetter{

       void setTruckList(ArrayList<Truck> truckList);

    }
}
