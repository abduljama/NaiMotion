package com.example.abduljama.naimotion;


import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransitFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {
    MapView mapView;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
    private GoogleApiClient mGoogleApiClient;
    Location location;

    static final LatLng uhuruHighway = new LatLng(-1.2961,36.8225);
    static final LatLng mombasaRoad = new LatLng(-1.3298, 36.871);
    static final LatLng langataRoad = new LatLng(-1.3363 ,36.7757);

    int[] colors = {
            Color.rgb(102, 225, 0), // green
            Color.rgb(201, 95, 121)    // red
    };

    float[] startPoints = {
            0.2f, 1f
    };



    private GoogleMap map;

    private IconGenerator mIconGenerator ;
    private  IconGenerator mClusterIconGenerator;
    private ImageView mImageView= null;
    private  ImageView mClusterImageView = null;
    private  int mDimension = 0;

    Button btn_start , btn_end;
    TextView txtDistance , lblDistance , txtDuration , lblDuration ;
    private long mCallStart = 0;

    TileOverlay mOverlay ,mOverlay1;
    HeatmapTileProvider mProvider, mProvider1;


    public TransitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.fragment_transit, container, false);
        mapView = (MapView) x.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        btn_start=(Button)x.findViewById(R.id.btn_start);
        btn_end = (Button)x.findViewById(R.id.btnend);
        txtDistance=(TextView)x.findViewById(R.id.distance);
        txtDuration=(TextView)x.findViewById(R.id.duration);
        lblDistance = (TextView)x.findViewById(R.id.lbldistance);
        lblDuration=(TextView)x.findViewById(R.id.lblduration);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        if (ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        //  True  shows  my  location
        map.setMyLocationEnabled(false);

   /*     Marker melbourne = map.addMarker(new MarkerOptions()
                .position(uhuruHighway)
                .icon(BitmapDescriptorFactory.defaultMarker())
                .title("100+ Reports"));
        melbourne.showInfoWindow();
/*
       MapsInitializer.initialize(this.getActivity());
        MarkerOptions marker = new MarkerOptions().position(uhuruHighway).title("Uhuru Highway");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        map.addMarker(marker);
        MarkerOptions marker1 = new MarkerOptions().position(mombasaRoad).title("Mombasa Road ");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        map.addMarker(marker1);
        MarkerOptions marker2 = new MarkerOptions().position(langataRoad).title("Langata Road ");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        map.addMarker(marker2);
        */
    /*   IconGenerator iconFactory = new IconGenerator(getActivity());
       iconFactory.setStyle(IconGenerator.STYLE_ORANGE);
       addIcon(iconFactory, "100+ reports ", new LatLng(-1.2961,36.8225));
///*
//        iconFactory.setStyle(IconGenerator.STYLE_ORANGE);
//        addIcon(iconFactory, "Mombasa Road", new LatLng(-1.3298, 36.871));
//
//        iconFactory.setRotation(90);
//        iconFactory.setStyle(IconGenerator.STYLE_RED);
//        addIcon(iconFactory, "Langata Road", new LatLng(-1.3363 ,36.7757));
//        */
        Gradient gradient = new Gradient(colors, startPoints);
        mProvider = new HeatmapTileProvider.Builder()
                .data(getGeoCode())
                .gradient(gradient)
                .build();
        mOverlay = map.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
         mProvider.setOpacity(0.7);
     //   mOverlay.clearTileCache();


//        iconFactory.setContentRotation(-90);
//        iconFactory.setStyle(IconGenerator.STYLE_PURPLE);
//        addIcon(iconFactory, "Rotate=90, ContentRotate=-90", new LatLng(-1.2887195777332237 , 36.81848734617233));

//        iconFactory.setRotation(0);
//        iconFactory.setContentRotation(90);
//        iconFactory.setStyle(IconGenerator.STYLE_GREEN);
        // addIcon(iconFactory, "ContentRotate=90", new LatLng(-33.7677, 151.244));

//        iconFactory.setRotation(0);
//        iconFactory.setContentRotation(0);
//        iconFactory.setStyle(IconGenerator.STYLE_ORANGE);
        //  addIcon(iconFactory, makeCharSequence(), new LatLng(-33.77720, 151.12412));


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_start.setVisibility(View.INVISIBLE);
                lblDuration.setVisibility(View.VISIBLE);
                txtDuration.setVisibility(View.VISIBLE);
                lblDistance.setVisibility(View.VISIBLE);
                txtDistance.setVisibility(View.VISIBLE);
                btn_end.setVisibility(View.VISIBLE);
                mCallStart = System.currentTimeMillis();

                txtDuration.setText("00:50");
                txtDistance.setText("3.2KM");


                ArrayList<LatLng> triangle = new ArrayList<>();
                triangle.add(new LatLng( -1.304588825992221,36.82649374008179));
                triangle.add(new LatLng(-1.300099967963058 ,  36.824299693107605));
                triangle.add(new LatLng(-1.2996762879992767 , 36.82409048080444 ));
                triangle.add(new LatLng(-1.2987511637807228 , 36.8239751458168));
                triangle.add(new LatLng(-1.298233630374494, 36.82374984025955 ));
                triangle.add(new LatLng(-1.295190102760576,  36.821936666965485));
                triangle.add(new LatLng( -1.2929134880847413, 36.8206062912941 ));
                triangle.add(new LatLng(  -1.292626564770991,  36.82056337594986 ));
                triangle.add(new LatLng(  -1.29239595348597  , 36.82035148143768 ));
                triangle.add(new LatLng(   -1.291218762995568 , 36.8197426199913 ));
                triangle.add(new LatLng( -1.2887195777332237 , 36.81848734617233 ));
                triangle.add(new LatLng(  -1.2867888749928875,   36.817572712898254 ));



                MarkerOptions start = new MarkerOptions().position( new LatLng( -1.304588825992221,36.82649374008179)).title("Uhuru Highway");
                start.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                map.addMarker(start);

                MarkerOptions end = new MarkerOptions().position( new LatLng( -1.2867888749928875,   36.817572712898254 )).title("Uhuru Highway");
                end.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                map.addMarker(end);

                map.addPolyline(new PolylineOptions().width(10).color(Color.BLUE).geodesic(true)
                        .addAll(triangle));
            }
        });

        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_start.setVisibility(View.VISIBLE);
                lblDuration.setVisibility(View.INVISIBLE);
                txtDuration.setVisibility(View.INVISIBLE);
                lblDistance.setVisibility(View.INVISIBLE);
                txtDistance.setVisibility(View.INVISIBLE);
                btn_end.setVisibility(View.INVISIBLE);

            }
        });


//        // Updates the location and zoom of the MapView
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(-1.29206590,36.82194620),13);
//        map.animateCamera(cameraUpdate);
        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(-1.2961,36.8225),14);
        map.animateCamera(cameraUpdate);

        return  x;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }
    private void addIcon(IconGenerator iconFactory, CharSequence text, LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon((String) text))).
                position(position).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

        map.addMarker(markerOptions);
    }
    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    public  ArrayList<LatLng>  getGeoCode ()
    {
        ArrayList<LatLng> triangle = new ArrayList<>();
        triangle.add(new LatLng( -1.304588825992221,36.82649374008179));
        triangle.add(new LatLng(-1.300099967963058 ,  36.824299693107605));
        triangle.add(new LatLng(-1.2996762879992767 , 36.82409048080444 ));
        triangle.add(new LatLng(-1.2987511637807228 , 36.8239751458168));
        triangle.add(new LatLng(-1.298233630374494, 36.82374984025955 ));
        triangle.add(new LatLng(-1.295190102760576,  36.821936666965485));
        triangle.add(new LatLng( -1.2929134880847413, 36.8206062912941 ));
        triangle.add(new LatLng(  -1.292626564770991,  36.82056337594986 ));
        triangle.add(new LatLng(  -1.29239595348597  , 36.82035148143768 ));
        triangle.add(new LatLng(   -1.291218762995568 , 36.8197426199913 ));
        triangle.add(new LatLng( -1.2887195777332237 , 36.81848734617233 ));
        triangle.add(new LatLng(  -1.2867888749928875,   36.817572712898254 ));
        triangle.add(new LatLng(-1.3124524 , 36.8160644));
        triangle.add(new LatLng(   -1.3104789,36.8176094 ));
        triangle.add(new LatLng(-1.3084195,36.8190685));
        triangle.add(new LatLng( -1.3061026,36.8212143));
        triangle.add(new LatLng( -1.3141686,36.8113437));
        triangle.add(new LatLng(  -1.3133963,36.8069664));
        triangle.add(new LatLng(  -1.3054162,36.8268791));
        triangle.add(new LatLng(   -1.3085053,36.8283382));
        triangle.add(new LatLng(   -1.3102214,36.8291965));
        triangle.add(new LatLng(-1.3125383,36.8153778));
        triangle.add(new LatLng(-1.3132247,36.8137470));
        triangle.add(new LatLng( -1.3144260,36.8106571));
        triangle.add(new LatLng( -1.3144260,36.8082538));
        triangle.add(new LatLng( -1.3117660,36.8061081));
        triangle.add(new LatLng(  -1.2894669,36.8109546));
        triangle.add(new LatLng(  -1.2916979,36.8062339));
        triangle.add(new LatLng(  -1.2950444,36.8025432));
        triangle.add(new LatLng(  -1.2916979,36.8062339));
        triangle.add(new LatLng(  -1.2950444,36.8025432));
        triangle.add(new LatLng(   -1.2950444,36.8025432));
        triangle.add(new LatLng(    -1.2963316,36.8024573));
        triangle.add(new LatLng(   -1.2971897,36.8027148));
        triangle.add(new LatLng(    -1.2975329,36.8028007));
        triangle.add(new LatLng(   -1.2978761,36.8019423));
        triangle.add(new LatLng(    -1.2982194,36.8005691));
        triangle.add(new LatLng(   -1.2985626,36.7987666));
        triangle.add(new LatLng( -1.2986055,36.7984662));
        triangle.add(new LatLng(    -1.2992491,36.7956338));
        triangle.add(new LatLng(   -1.2992920,36.7944751));
        triangle.add(new LatLng(  -1.2994207,36.7930589));
        triangle.add(new LatLng(   -1.2993778,36.7926726));
        triangle.add(new LatLng(  -1.2995494,36.7906556));

        triangle.add(new LatLng( -1.2852193,36.7889819));
        triangle.add(new LatLng(    -1.2850906,36.7898402
        ));
        triangle.add(new LatLng(  -1.2850048,36.7903123
        ));
        triangle.add(new LatLng(   -1.2850048,36.7909560
        ));
        triangle.add(new LatLng(   -1.2851335,36.7919431
        ));
        triangle.add(new LatLng(  -1.2850906,36.7928443
        ));
        triangle.add(new LatLng(  -1.2851764,36.7936168
        ));
        triangle.add(new LatLng(   -1.2853480,36.7943463
        ));
        triangle.add(new LatLng(   -1.2855626,36.7952905
        ));

        return  triangle;
    }

}
