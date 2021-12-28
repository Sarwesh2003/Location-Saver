package com.example.loacationsaver.model.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loacationsaver.R;
import com.example.loacationsaver.model.db.DatabaseModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collection;

public class ShowLocationsAdapter extends RecyclerView.Adapter<ShowLocationsAdapter.MyViewHolder> implements Filterable {

    Context context;
    ArrayList<String> address,addressAll;
    ArrayList<LatLng> latlng;
    FusedLocationProviderClient client;
    Bundle mSavedInstance;
    DatabaseModel model;
    OnLocationClickListener mOnLocationClickListener;

    public ShowLocationsAdapter(Context context, ArrayList<String> address, ArrayList<LatLng> latlang,
            OnLocationClickListener onLocationClickListener,Bundle savedInstance) {
        this.context = context;
        this.address = address;
        this.latlng = latlang;
        client = LocationServices.getFusedLocationProviderClient(context);
        this.mSavedInstance=savedInstance;
        this.addressAll=new ArrayList<>(address);
        this.model=new DatabaseModel(new LocationDBAdapter(context));
        this.mOnLocationClickListener=onLocationClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.location_list_design, parent, false);
        return new MyViewHolder(v,mOnLocationClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.address.setText(address.get(position));
        LatLng instance = latlng.get(position);

        holder.mapview.onCreate(mSavedInstance);
        holder.mapview.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.addMarker(new MarkerOptions().position(instance));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(instance, 15));
                googleMap.getUiSettings().setAllGesturesEnabled(false);
                holder.mapview.onResume();
            }
        });
        holder.delete.setOnClickListener(v -> {
            try {
                if(model.Delete(String.valueOf(instance.latitude),String.valueOf(instance.longitude))){
                    address.remove(position);
                    latlng.remove(position);
                    addressAll.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context,"Record Deleted",Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(context,"Error deleting record. Try Again",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return address.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<String> filteredList=new ArrayList<>();
            if(constraint.toString().isEmpty()){
                filteredList.addAll(addressAll);
            }
            else{
                for(String add:addressAll){
                    if(add.toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(add);
                    }
                }
            }
            FilterResults filterResults =new FilterResults();
            filterResults.values=filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            address.clear();
            address.addAll((Collection<? extends String>) results.values);
            notifyDataSetChanged();
        }
    };

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView address;
        SupportMapFragment supportMapFragment;
        MapView mapview;
        ImageButton delete;
        OnLocationClickListener onLocationClickListener;


        public MyViewHolder(@NonNull View itemView,OnLocationClickListener onLocationClickListener) {
            super(itemView);
            this.onLocationClickListener=onLocationClickListener;
            address = itemView.findViewById(R.id.show_address);
            //supportMapFragment = ((SupportMapFragment) ((FragmentActivity) itemView.getContext()).getSupportFragmentManager().findFragmentById(R.id.map_preview_fragment));
            mapview=(MapView)itemView.findViewById(R.id.map_preview_fragment);
            delete=itemView.findViewById(R.id.delete);
            RelativeLayout rl = itemView.findViewById(R.id.back);
            rl.setClipToOutline(true);
            mapview.setClickable(false);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onLocationClickListener.onLocationClick(getAdapterPosition());
        }
    }
    public interface OnLocationClickListener{
        void onLocationClick(int position);
    }
}
