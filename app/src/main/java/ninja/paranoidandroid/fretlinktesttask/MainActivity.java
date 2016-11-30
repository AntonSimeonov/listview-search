package ninja.paranoidandroid.fretlinktesttask;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import ninja.paranoidandroid.fretlinktesttask.util.TruckAdapter;

public class MainActivity extends AppCompatActivity implements FretLinkConnectionFragment.TruckListGetter {


    private final static String TAG = "Main Activity";
    private final static String FRAGMENT_TAG = "fragment tag";

    private ArrayList<Truck> mTruckList;
    private ListView mTruckListView;
    private TruckAdapter mTruckAdapter;
    private EditText mSearchTruckEditText;

    private FretLinkConnectionFragment mFretLinkConnectionFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setFretLinkConnectionFragment();
        setOnItemListViewListener();
        setTextChangedListener();

    }

    private void init(){
        mTruckListView = (ListView) findViewById(R.id.lv_activity_main_truckList);
        mSearchTruckEditText = (EditText) findViewById(R.id.et_activity_main_search);
    }

    private void showTruckList(){

        mTruckAdapter = new TruckAdapter(this, R.layout.truck_adapter_row, mTruckList);
        mTruckListView.setAdapter(mTruckAdapter);

    }

    @Override
    public void setTruckList(ArrayList<Truck> truckList) {

        mTruckList = truckList;
        if(mTruckAdapter == null){
            showTruckList();
        }

    }

    private void setOnItemListViewListener(){
        Log.i(TAG, " setOnItemListViewListener()");
        mTruckListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Truck truck = mTruckAdapter.getFilteredTruckList().get(i);
                Toast.makeText(getApplicationContext(), truck.getTruckID() + "", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setTextChangedListener(){
        Log.i(TAG, " setTextChanhgedListener()");
        mSearchTruckEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                mTruckAdapter.getFilter().filter(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void setFretLinkConnectionFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        mFretLinkConnectionFragment = (FretLinkConnectionFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);

        if(mFretLinkConnectionFragment == null){

            mFretLinkConnectionFragment = new FretLinkConnectionFragment();
            FragmentTransaction ft  = getFragmentManager().beginTransaction();
            ft.add(mFretLinkConnectionFragment, FRAGMENT_TAG);
            ft.commit();

        }

        if(mFretLinkConnectionFragment.getTruckList() != null){

            mTruckAdapter = new TruckAdapter(this, R.layout.truck_adapter_row, mFretLinkConnectionFragment.getTruckList());
            mTruckListView.setAdapter(mTruckAdapter);

        }

    }


}
