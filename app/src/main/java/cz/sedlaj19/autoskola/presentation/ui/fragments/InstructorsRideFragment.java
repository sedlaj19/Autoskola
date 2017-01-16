package cz.sedlaj19.autoskola.presentation.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.presentation.presenters.InstructorRidesPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.impl.InstructorsRidePresenterImpl;
import cz.sedlaj19.autoskola.presentation.ui.activities.AddRideActivity;
import cz.sedlaj19.autoskola.presentation.ui.adapters.RideAdapter;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;

/**
 * Created by Honza on 12. 8. 2016.
 */
public class InstructorsRideFragment extends Fragment implements InstructorRidesPresenter.View,
        SwipeRefreshLayout.OnRefreshListener{

    private static final String ARG_SECTION_NUMBER = "section_number";

    @Bind(R.id.instructor_progress_wrapper)
    View mProgressWrapper;
    @Bind(R.id.instructor_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.instructor_rides_refresh)
    SwipeRefreshLayout mRefresh;

    private InstructorRidesPresenter mPresenter;
    private RideAdapter mRideAdapter;

    public InstructorsRideFragment() {
    }

    public static InstructorsRideFragment newInstance(int sectionNumber) {
        InstructorsRideFragment fragment = new InstructorsRideFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instructor_rides, container, false);
        ButterKnife.bind(this, rootView);

        init();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    private void init(){
        mPresenter = new InstructorsRidePresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
        mRideAdapter = new RideAdapter(true, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mRideAdapter);
        mRefresh.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    public void onInstructorRidesRetrieved() {
        mRideAdapter.addNewRides(Container.getInstance().getRides());
        mRefresh.setRefreshing(false);
    }

    @Override
    public void onRideClicked(Ride ride) {
        Intent intent = new Intent(getContext(), AddRideActivity.class);
        intent.putExtra(Constants.Common.RIDE_TO_EDIT, ride);
        startActivity(intent);
    }

    @Override
    public void onRideChanged(int position) {
//        if(position == -1){
//            mRideAdapter.notifyItemChanged(position);
//            Container.getInstance().setUpdatedRidePosition(-1);
//        }
//        mRideAdapter.notifyDataSetChanged();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mPresenter.getInstructorsRides();
    }
}
