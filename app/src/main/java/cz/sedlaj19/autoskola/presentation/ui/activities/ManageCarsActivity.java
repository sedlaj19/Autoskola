package cz.sedlaj19.autoskola.presentation.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.model.Car;
import cz.sedlaj19.autoskola.presentation.presenters.ManageCarsPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.impl.ManageCarsPresenterImpl;
import cz.sedlaj19.autoskola.presentation.ui.adapters.CarsAdapter;
import cz.sedlaj19.autoskola.presentation.ui.fragments.AddFragmentDialog;
import cz.sedlaj19.autoskola.presentation.ui.listeners.AddListener;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;

public class ManageCarsActivity extends AppCompatActivity implements
        ManageCarsPresenter.View, AddListener{

    @BindView(R.id.manage_cars_list)
    RecyclerView mCarsList;
    @BindView(R.id.manage_cars_progress_wrapper)
    View mProgress;

    private ManageCarsPresenter mPresenter;
    private CarsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_cars);

        ButterKnife.bind(this);

        init();
    }

    private void init(){
        mPresenter = new ManageCarsPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
        mAdapter = new CarsAdapter();
        mCarsList.setLayoutManager(new LinearLayoutManager(this));
        mCarsList.setAdapter(mAdapter);
        mPresenter.getCars();
    }

    @Override
    public void onCarsRetrieved(List<Car> cars) {
        Log.d(this.getClass().toString(), "TEST: cars retrieved " + cars.size());
        mAdapter.setCars(cars);
    }

    @Override
    public void onCarSaved(Car car) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message, int what) {

    }

    @OnClick(R.id.manage_cars_add_car_btn)
    public void addCarClicked(){
        AddFragmentDialog.newInstance(R.string.add_car, null).show(getSupportFragmentManager(), "Add car");
    }

    @Override
    public void onAddSuccessful(String name) {
        mPresenter.saveCar(name);
    }
}
