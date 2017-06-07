package cz.sedlaj19.autoskola.presentation.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.utils.Converter;
import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.presentation.presenters.InstructorRidesPresenter;
import cz.sedlaj19.autoskola.presentation.ui.listeners.OnRideClickListener;

/**
 * Created by Honza on 6. 8. 2016.
 */
public class RideAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnRideClickListener{

    private List<Ride> rideList;
    private boolean instructor;
    private InstructorRidesPresenter.View view;

    @Override
    public void onRideClick(int position) {
        view.onRideClicked(rideList.get(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.ride_item_date)
        public TextView date;
        @BindView(R.id.ride_item_car)
        public TextView car;
        @BindView(R.id.ride_item_user)
        public TextView user;
        @BindView(R.id.ride_item_note)
        public TextView notes;
        @BindView(R.id.ride_item_note_wrapper)
        public View notesWrapper;

        private boolean instructor;
        private OnRideClickListener listener;

        public ViewHolder(View itemView, boolean instructor, OnRideClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            this.instructor = instructor;
            this.listener = listener;
        }

        public void setup(Ride ride){
            Date d = new Date(ride.getDateMillis());
            String formattedDate = Converter.convertDateAndTimeToString(d);
            this.date.setText(formattedDate);
            this.car.setText(Container.getInstance().getCarByKey(ride.getCar()));
            String note = ride.getNotes();
            if(note == null || note.isEmpty()){
                this.notesWrapper.setVisibility(View.GONE);
            }else{
                this.notesWrapper.setVisibility(View.VISIBLE);
            }
            this.notes.setText(ride.getNotes());
            if(!instructor){
                this.user.setText(Container.getInstance().getUserNameByKey(ride.getInstructor()));
            }else{
                this.user.setText(Container.getInstance().getUserNameByKey(ride.getStudent()));
            }
        }

        @Override
        public void onClick(View view) {
            if(!instructor){
                return;
            }
            this.listener.onRideClick(getAdapterPosition());
        }
    }

    public RideAdapter(boolean instructor, InstructorRidesPresenter.View view){
        this.rideList = new ArrayList<>();
        this.instructor = instructor;
        this.view = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.card_ride_item, parent, false);
        return new ViewHolder(view, instructor, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Ride ride = rideList.get(position);

        ((ViewHolder)holder).setup(ride);
    }

    @Override
    public int getItemCount() {
        return rideList.size();
    }

    public Ride getRide(int position){
        return rideList.get(position);
    }

    public void removeRide(int position){
        rideList.remove(position);
        notifyItemRemoved(position);
    }

    public void addRide(Ride ride, int position){
        rideList.add(position, ride);
        notifyItemInserted(position);
    }

    public void addNewRides(List<Ride> rides){
        if(rideList != null && !rideList.isEmpty()){
            rideList.clear();
        }
//        for(Ride ride : rides){
//            rideList.add(ride);
//        }
        this.rideList = new ArrayList<>(rides);
        notifyDataSetChanged();
    }
}
