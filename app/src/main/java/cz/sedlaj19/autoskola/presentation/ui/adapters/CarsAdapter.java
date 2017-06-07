package cz.sedlaj19.autoskola.presentation.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.domain.model.Car;
import cz.sedlaj19.autoskola.presentation.ui.listeners.OnRideClickListener;

/**
 * Created by Honza on 29. 1. 2017.
 */

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> implements OnRideClickListener {

    private List<Car> cars;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.car_item_name)
        TextView carName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setup(Car car){
            carName.setText(car.getName());
        }

        @Override
        public void onClick(View view) {

        }
    }

    public CarsAdapter(){
        this.cars = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.car_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setup(cars.get(position));
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    @Override
    public void onRideClick(int position) {

    }

    public void setCars(List<Car> cars){
        if(cars != null && !cars.isEmpty()){
            this.cars.clear();
        }
        this.cars = new ArrayList<>(cars);
        notifyDataSetChanged();
    }

    public void addCar(Car car){
        this.cars.add(car);
        notifyItemInserted(getItemCount());
    }
}
