package cz.sedlaj19.autoskola.presentation.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.presentation.presenters.DashboardPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.impl.DashboardPresenterImpl;
import cz.sedlaj19.autoskola.presentation.ui.adapters.RideAdapter;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;

public class DashboardActivity extends AppCompatActivity implements DashboardPresenter.View{

    private DashboardPresenter mDashboardPresenter;
    private RideAdapter mRideAdapter;

    @Bind(R.id.dashboard_ride_list)
    RecyclerView mRideList;
    @Bind(R.id.dashboard_progress_wrapper)
    View mProgressWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        mDashboardPresenter = new DashboardPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );

        mRideAdapter = new RideAdapter(false, null);
        mRideList.setLayoutManager(new LinearLayoutManager(this));
        mRideList.setAdapter(mRideAdapter);

        mDashboardPresenter.getInstructors();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_dashboard_logout:
                mDashboardPresenter.doLogout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLogoutFinished() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onUserRidesRetrieved() {
        Log.d(this.getClass().toString(), "Tradaaaa");
        mRideAdapter.addNewRides(Container.getInstance().getRides());
    }

    @Override
    public void onInstructorRetrieved() {
        Log.d(this.getClass().toString(), "Instructors retrieved");
        mDashboardPresenter.getUserRides();
    }

    @Override
    public void showProgress() {
        mProgressWrapper.setVisibility(View.VISIBLE);
        mRideList.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mProgressWrapper.setVisibility(View.GONE);
        mRideList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message, int what) {

    }
}
