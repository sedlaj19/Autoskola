package cz.sedlaj19.autoskola.presentation.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.presentation.presenters.InstructorStudentsPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.impl.InstructorStudentsPresenterImpl;
import cz.sedlaj19.autoskola.presentation.ui.adapters.StudentsAdapter;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;

/**
 * Created by Honza on 12. 8. 2016.
 */
public class InstructorStudentsFragment extends Fragment implements InstructorStudentsPresenter.View{

    private static final String ARG_SECTION_NUMBER = "section_number";

    @Bind(R.id.instructor_students_progress_wrapper)
    View mProgressWrapper;
    @Bind(R.id.instructor_students_recycler_view)
    RecyclerView mRecyclerView;

    private InstructorStudentsPresenter mPresenter;
    private StudentsAdapter mStudentsAdapter;

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

    private void init(){
        mPresenter = new InstructorStudentsPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
        mPresenter.getStudents();
        mStudentsAdapter = new StudentsAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mStudentsAdapter);
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
}
