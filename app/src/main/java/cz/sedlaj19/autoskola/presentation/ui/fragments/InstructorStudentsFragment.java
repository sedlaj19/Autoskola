package cz.sedlaj19.autoskola.presentation.ui.fragments;

import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.presentation.presenters.InstructorStudentsPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.impl.InstructorStudentsPresenterImpl;
import cz.sedlaj19.autoskola.presentation.ui.adapters.StudentsAdapter;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;
import cz.sedlaj19.autoskola.utils.ItemSwipeHelper;

/**
 * Created by Honza on 12. 8. 2016.
 */
public class InstructorStudentsFragment extends Fragment implements
        InstructorStudentsPresenter.View,
        View.OnClickListener{

    private static final String ARG_SECTION_NUMBER = "section_number";

    @BindView(R.id.instructor_students_progress_wrapper)
    View mProgressWrapper;
    @BindView(R.id.instructor_students_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.instructor_coordinator_layout)
    CoordinatorLayout mLayout;

    private InstructorStudentsPresenter mPresenter;
    private StudentsAdapter mStudentsAdapter;

    private int mRemovedStudentPosition;
    private User mRemovedStudent;

    public InstructorStudentsFragment() {
    }

    public static InstructorStudentsFragment newInstance(int sectionNumber) {
        InstructorStudentsFragment fragment = new InstructorStudentsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instructor_students, container, false);
        ButterKnife.bind(this, rootView);

        init();
        return rootView;
    }

    @Override
    public void onPause() {
        mPresenter.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mPresenter.resume();
        super.onResume();
    }

    private void init(){
        initPresenter();
        initRecyclerView();
    }

    private void initPresenter(){
        mPresenter = new InstructorStudentsPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
    }

    private void initRecyclerView(){
        mStudentsAdapter = new StudentsAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mStudentsAdapter);
        ItemTouchHelper.SimpleCallback callback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        mRemovedStudentPosition = viewHolder.getAdapterPosition();
                        mRemovedStudent = mStudentsAdapter.getStudent(mRemovedStudentPosition);
                        mStudentsAdapter.removeStudent(mRemovedStudentPosition);
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

    @Override
    public void onStudentsRetrieved() {
        mStudentsAdapter.addNewStudents(Container.getInstance().getStudents());
    }

    @Override
    public void onStudentClicked(User user) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+user.getPhone()));
        startActivity(intent);
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
                if(event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT
                        || event == Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE){
                    mPresenter.handleSwipeAction(mRemovedStudent, direction);
                }
            }
        });
        snackbar.show();
    }

    @Override
    public void onClick(View view) {
        if(mRemovedStudent == null){
            return;
        }
        mStudentsAdapter.addStudent(mRemovedStudent, mRemovedStudentPosition);
        mRecyclerView.scrollToPosition(mRemovedStudentPosition);
        mRemovedStudent = null;
    }
}
