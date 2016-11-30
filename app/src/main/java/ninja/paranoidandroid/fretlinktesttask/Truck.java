package ninja.paranoidandroid.fretlinktesttask;

/**
 * Created by anton on 07.07.16.
 */
public class Truck {

    private String mTruckID;
    private String mTruckName;

    public Truck(String truckID, String truckName){

        mTruckID = truckID;
        mTruckName = truckName;

    }

    public String getTruckID() {

        return mTruckID;
    }

    public String getTruckName() {

        return mTruckName;
    }
}
