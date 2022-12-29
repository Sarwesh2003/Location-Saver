package com.example.loacationsaver.Widget;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.loacationsaver.MainActivity;
import com.example.loacationsaver.R;
import com.example.loacationsaver.model.adapters.LocationDBAdapter;
import com.example.loacationsaver.model.db.DatabaseModel;

import java.util.List;

public class AppWidgetProvider extends android.appwidget.AppWidgetProvider {
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.gpswidget);
            views.setOnClickPendingIntent(R.id.save_location, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        context.startService(new Intent(context, GPSWidgetService.class));
    }

    public static class GPSWidgetService extends Service {
        private LocationManager manager = null;
//        DatabaseModel model = new DatabaseModel(new LocationDBAdapter(getApplicationContext()));
        private LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.d("Error Here","Location");
                SaveLocation(location.getLatitude(), location.getLongitude());
                stopSelf();
            }
        };

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            Log.d("Service Error","Oncreate");
            manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.d("Service Error","onStart");
            WaitForCoordinates();
            return super.onStartCommand(intent, flags, startId);

        }

        @Override
        public void onDestroy() {
            stopListening();
            super.onDestroy();

        }

        @Override
        public void onStart(Intent intent, int startId) {
            super.onStart(intent, startId);
            StartListening();
        }

        private void WaitForCoordinates() {
            StartListening();
        }

        private void StartListening() {
            final Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_LOW);

            final String bestProvider = manager.getBestProvider(criteria, true);

            if (bestProvider != null && bestProvider.length() > 0) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                manager.requestLocationUpdates(bestProvider, 500, 10, listener);
            }
            else {
                final List<String> providers = manager.getProviders(true);

                for (final String provider : providers) {
                    manager.requestLocationUpdates(provider, 500, 10, listener);
                }
            }
        }

        private void stopListening(){
            try {
                if (manager != null && listener != null) {
                    manager.removeUpdates(listener);
                }

                manager = null;
            }
            catch (final Exception ex) {

            }
        }

        private void SaveLocation(double lat,double lang){
            Geocoder coder = new Geocoder(this);
            List<Address> addresses = null;
            String info = "";

            try
            {
                addresses = coder.getFromLocation(lat, lang, 2);

                if(null != addresses && addresses.size() > 0){
                    int addressCount = addresses.get(0).getMaxAddressLineIndex();

                    if(-1 != addressCount){
                        for(int index=0; index<=addressCount; ++index){
                            info += addresses.get(0).getAddressLine(index);

                            if(index < addressCount)
                                info += ", ";
                        }
                    }
                    else
                    {
                        info += addresses.get(0).getFeatureName() + ", " + addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getAdminArea();
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            coder = null;
            addresses = null;

            if(info.length() <= 0){
                info = "lat " + lat + ", lon " + lang;
            }
            else{
                info += ("n" + "(lat " + lat + ", lon " + lang + ")");
            }
            Log.d("Service Error","Final");
            try {
                // Storing data into SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
                myEdit.putString("Lat", String.valueOf(lat));
                myEdit.putString("Lang", String.valueOf(lang));
                myEdit.putString("Address", info);

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                myEdit.apply();


                RemoteViews views = new RemoteViews(getPackageName(), R.layout.gpswidget);
                views.setTextViewText(R.id.save_location, "saved");
//                model.saveLocation(String.valueOf(lat), String.valueOf(lang), info);
                ComponentName thisWidget = new ComponentName(this, AppWidgetProvider.class);
                AppWidgetManager manager = AppWidgetManager.getInstance(this);
                manager.updateAppWidget(thisWidget, views);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
