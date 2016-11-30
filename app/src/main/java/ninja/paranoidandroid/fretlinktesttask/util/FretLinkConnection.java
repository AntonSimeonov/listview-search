package ninja.paranoidandroid.fretlinktesttask.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by anton on 07.07.16.
 */
public class FretLinkConnection {

    private final static String TAG = "FretLinkConnection";

    private HttpURLConnection mConnection;

    public void setConnection(){

        String url = "http://app.fretlink.com/service/trucks";
        URL urlObj = null;

        try {

            urlObj = new URL(url);
            mConnection = (HttpURLConnection) urlObj.openConnection();


        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void setRequestHeader(){

        try {

            mConnection.setDoOutput(true);
            mConnection.setRequestMethod("POST");

        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    public void sendPostRequest(String postParameters){

        String urlParameters = postParameters;

        DataOutputStream wr = null;

        try {

            wr = new DataOutputStream(mConnection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getServerResponce(){

        BufferedReader in = null;
        String inputLine = null;
        StringBuffer response = null;

        try {

            int responseCode = mConnection.getResponseCode();

            if(responseCode == 200){

                in = new BufferedReader(new InputStreamReader(mConnection.getInputStream()));
                response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {

                    response.append(inputLine);

                }

            }else if(responseCode == 400){
                Log.i(TAG, "Request is malformed");
            }

            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            mConnection.disconnect();
        }

        return response.toString();
    }

}
