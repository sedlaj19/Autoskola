package cz.sedlaj19.autoskola.presentation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.utils.Converter;
import cz.sedlaj19.autoskola.domain.model.Ride;

/**
 * Created by Honza on 21. 8. 2016.
 */
public class AddRideAdapter extends BaseAdapter {

    private List<Ride> rides;

    public AddRideAdapter(){
        this.rides = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return rides.size();
    }

    @Override
    public Ride getItem(int position) {
        return rides.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder;
        if(view == null){
            view = inflater.inflate(R.layout.add_ride_item, parent, false);
            holder = new ViewHolder();
            holder.rideDate = (TextView)view.findViewById(R.id.add_ride_item_date);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.setup(getItem(position));
        view.setTag(holder);
        return view;
    }

    public static class ViewHolder{

        TextView rideDate;

        public ViewHolder() {
        }

        public void setup(Ride ride){
            Date d = new Date(ride.getDateMillis());
            this.rideDate.setText(Converter.convertTimeToString(d));
        }
    }

    public void addNewRides(List<Ride> rides){
        if(rides != null && !rides.isEmpty()){
            this.rides.clear();
        }
        this.rides = rides;

        notifyDataSetChanged();
    }
}
