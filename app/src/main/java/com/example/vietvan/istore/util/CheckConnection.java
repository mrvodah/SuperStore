package com.example.vietvan.istore.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by VietVan on 23/04/2018.
 */

public class CheckConnection {
    public static boolean HaveNetworkConnection(Context context){
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
        for(NetworkInfo ni: networkInfos){
            if(ni.getTypeName().equalsIgnoreCase("WIFI"))
                if(ni.isConnected())
                    haveConnectedWifi = true;
            if(ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if(ni.isConnected())
                    haveConnectedMobile = true;
        }

        return haveConnectedWifi || haveConnectedMobile;
    }

    public static void ShowToast_Short(Context context, String message){
        Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
    }

}
