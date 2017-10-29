package com.example.aso.whatsnearme;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aso.whatsnearme.Model.Venue;
import com.example.aso.whatsnearme.Model.VenueResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.util.List;

public class UniversityActivity extends AppCompatActivity implements LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    private LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    List<Venue> venueList;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);




        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }



        mRequestQueue = Volley.newRequestQueue(UniversityActivity.this);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });



    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Toast.makeText(UniversityActivity.this,"Baglanti Kuruldu",Toast.LENGTH_SHORT).show();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(UniversityActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    100);

            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            Toast.makeText(UniversityActivity.this,String.valueOf(mLastLocation.getLatitude()) + String.valueOf(mLastLocation.getLongitude()),Toast.LENGTH_LONG).show();
            getVenues(String.valueOf(mLastLocation.getLatitude()) , String.valueOf(mLastLocation.getLongitude()));
        }

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v("Yavuz","Hata");
        Toast.makeText(UniversityActivity.this,"Bağlantı hatası,konum veya wifi ayarlarınızı kontrol edin.",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mLastLocation != null) {
            Toast.makeText(UniversityActivity.this,String.valueOf(mLastLocation.getLatitude()) + String.valueOf(mLastLocation.getLongitude()),Toast.LENGTH_LONG).show();

            getVenues(String.valueOf(mLastLocation.getLatitude()) , String.valueOf(mLastLocation.getLongitude()));
        }

    }

    public void processResponse(String response) {

        Gson gson = new Gson();
        VenueResponse vr = gson.fromJson(response, VenueResponse.class);

        venueList = vr.getResponse().getVenues();


        ListView lv = (ListView) findViewById(R.id.lv_venues);

        VenuesArrayAdapter adapter = new VenuesArrayAdapter(UniversityActivity.this);

        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                try {
                    Toast.makeText(UniversityActivity.this, venueList.get(position).getContact().getPhone(), Toast.LENGTH_LONG).show();
                    double latitude = venueList.get(position).getLocation().getLat();
                    double longitude = venueList.get(position).getLocation().getLng();
                    String label = venueList.get(position).getName();
                    double  getbeen=venueList.get(position).getBeenHere().getLastCheckinExpiredAt();
                    String uriBegin = "geo:" + latitude + "," + longitude;
                    String adress=venueList.get(position).getLocation().getAddress();
                    String query = latitude + "," + longitude + "(" + label + getbeen + ")";
                    //  query= adress;

                    String encodedQuery = Uri.encode(query);


                    String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";


                    Uri uri = Uri.parse(uriString);


                    Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);

                    startActivity(mapIntent);

                } catch (Exception e) {
                    Toast.makeText(UniversityActivity.this, "Telefon Yok", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }


    public void getVenues(String lat, String lon)
    {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.foursquare.com/v2/venues/search?oauth_token=VXKIZNLZJCOSWQFLTEGAGREVMDSKSFIWQAAZACDVQYCXXQNF&limit=50&v=20131016&ll="+lat+"%2C"+lon+"&intent=checkin&categoryId=4bf58dd8d48988d1ae941735";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        processResponse(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
    public class VenuesArrayAdapter extends ArrayAdapter<String> {
        private final Context context;

        public VenuesArrayAdapter(Context context) {
            super(context, -1);
            this.context = context;
        }


        @Override
        public int getCount() {
            return venueList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

            //Mekan Adi
            TextView txtVenue = (TextView) rowView.findViewById(R.id.txt_venue);
            TextView txtQuene = (TextView) rowView.findViewById(R.id.txt_query);
            txtVenue.setText(venueList.get(position).getName());


            //mekan uzaklık


            Location locationA = new Location("point A");

            locationA.setLatitude(mLastLocation.getLatitude());
            locationA.setLongitude(mLastLocation.getLongitude());

            Location locationB = new Location("point B");

            locationB.setLatitude(venueList.get(position).getLocation().getLat());
            locationB.setLongitude(venueList.get(position).getLocation().getLng());
            int distancee= (int) locationA.distanceTo(locationB);
            float distance = locationA.distanceTo(locationB);
            txtQuene.setText(String.valueOf(distancee +" metre"));


            //Mekan Kategori Ikon

            try {
                String imgUrl = venueList.get(position).getCategories().get(0).getIcon().getPrefix() + "88.png";


                NetworkImageView avatar = (NetworkImageView) rowView.findViewById(R.id.img_venue);
                avatar.setImageUrl(imgUrl, mImageLoader);
            } catch (Exception e) {

                e.printStackTrace();

            }


            return rowView;
        }
    }
}
