package cz.sedlaj19.autoskola.presentation.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.presentation.presenters.InstructorRidesPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.impl.InstructorsRidePresenterImpl;
import cz.sedlaj19.autoskola.presentation.ui.activities.AddRideActivity;
import cz.sedlaj19.autoskola.presentation.ui.activities.AddStudentActivity;
import cz.sedlaj19.autoskola.presentation.ui.adapters.RideAdapter;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;
import cz.sedlaj19.autoskola.utils.ItemSwipeHelper;
import cz.sedlaj19.autoskola.utils.UiUtils;

/**
 * Created by Honza on 12. 8. 2016.
 */
public class InstructorsRideFragment extends Fragment implements
        InstructorRidesPresenter.View,
        SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener{

    private static final String ARG_SECTION_NUMBER = "section_number";

    @BindView(R.id.instructor_progress_wrapper)
    View mProgressWrapper;
    @BindView(R.id.instructor_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.instructor_rides_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.instructor_coordinator_layout)
    CoordinatorLayout mLayout;

    private InstructorRidesPresenter mPresenter;
    private RideAdapter mRideAdapter;
//    private Ride mRemovedRide;
    private List<Ride> mRemovedRideStack;
    private int mRemovedRidePosition;

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

    private void init() {
        mPresenter = new InstructorsRidePresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
        initRecyclerView();
        mRefresh.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        mRemovedRideStack = new ArrayList<>();
        mRideAdapter = new RideAdapter(true, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mRideAdapter);
        ItemTouchHelper.SimpleCallback callback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                        if(!mRemovedRideStack.isEmpty()){
//                            mPresenter.handleSwipeAction(mRemovedRideStack.pop(), Constants.ItemSwipe.SWIPE_DELETE);
//                        }
                        // TODO: predelat, ted to nefunguje dobre, zamyslet se nad tim a udelat to poradne
                        mRemovedRidePosition = viewHolder.getAdapterPosition();
                        Ride rideToRemove = mRideAdapter.getRide(mRemovedRidePosition);
                        mRemovedRideStack.add(rideToRemove);
//                        mRemovedRide = mRideAdapter.getRide(mRemovedRidePosition);
                        mRideAdapter.removeRide(mRemovedRidePosition);
                        mPresenter.onSwiped(direction);
                    }

                    @Override
                    public void onChildDraw(Canvas canvas, RecyclerView recyclerView,
                                            RecyclerView.ViewHolder viewHolder, float dX,
                                            float dY, int actionState, boolean isCurrentlyActive) {
                        if (actionState == android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE) {
                            ItemSwipeHelper.onDrawChild(getContext(), dX, R.color.green, R.color.red,
                                    R.drawable.ic_check_white_48dp, R.drawable.ic_clear_white_48dp,
                                    viewHolder, canvas);
                        }
                        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @OnClick(R.id.instructor_add_ride)
    public void onAddRideBtnClicked(){
        startActivity(new Intent(getContext(), AddRideActivity.class));
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
        onRefresh();
    }

    @Override
    public void onRideDeleted(boolean result) {
        Log.d(this.getClass().toString(), "TEST: on ride deleted ...");
    }

    @Override
    public void showSnackBar(int text, int direction) {
        Snackbar snackbar = Snackbar.make(mLayout, text, Snackbar.LENGTH_SHORT);
        snackbar.setAction(R.string.undo, this);
        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {

            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                // TODO: pak pridat filtry na udelane neudelane jizdy a to same se studentama, nebo
                // by mozna bylo lepsi je nejak presouvat do jine "tabulky", abych nemusel filtrovat
                // a nemusel hlavne vzdycky stahovat velky mnozstvi dat
                Log.d(this.getClass().toString(), "TEST: jsem tu ... " + event);
                if(event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT
                        || event == Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE){
                    Log.d(this.getClass().toString(), "TEST: size " + mRemovedRideStack.size());
                    mPresenter.handleSwipeAction(mRemovedRideStack.get(0), direction);
                    mRemovedRideStack.remove(0);
                    Log.d(this.getClass().toString(), "TEST: kurva size " + mRemovedRideStack.size());
                }
            }
        });
        snackbar.show();
    }

    @Override
    public void removeRow(int position) {

    }

    @Override
    public void onRefresh() {
        mPresenter.getInstructorsRides();
    }

    @Override
    public void onClick(View view) {
        if(mRemovedRideStack.isEmpty()){
            return;
        }
        mRideAdapter.addRide(mRemovedRideStack.get(mRemovedRideStack.size()-1), mRemovedRidePosition);
        mRemovedRideStack.remove(mRemovedRideStack.size()-1);
        mRecyclerView.scrollToPosition(mRemovedRidePosition);
    }
}
