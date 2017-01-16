package cz.sedlaj19.autoskola.presentation.ui.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.presentation.presenters.InstructorPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.impl.InstructorPresenterImpl;
import cz.sedlaj19.autoskola.presentation.ui.adapters.SectionsPagerAdapter;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;

public class InstructorActivity extends AppCompatActivity implements InstructorPresenter.View{

    @Bind(R.id.instructor_container)
    ViewPager mViewPager;
    @Bind(R.id.instructor_progress_wrapper)
    View mProgressWrapper;
    @Bind(R.id.instructor_tabs)
    TabLayout mTabLayout;
    @Bind(R.id.instructor_toolbar)
    Toolbar toolbar;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private InstructorPresenter mInstructorPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        ButterKnife.bind(this);

        init();
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
//        mViewPager.addOnPageChangeListener(this);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mViewPager.removeOnPageChangeListener(this);
    }

    private void init(){
        mInstructorPresenter = new InstructorPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
        // TODO nevzdy je nutne loadovat znovu
//        mInstructorPresenter.getAllStudents();
        mInstructorPresenter.getAllInstructors();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_instructor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_instructor_logout) {
            mInstructorPresenter.doLogout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.instructor_add_ride)
    public void onAddRideBtnClicked(){
        Intent intent = new Intent(getApplicationContext(), AddRideActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLogoutFinished() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

//    @Override
//    public void onStudentsRetrieved() {
//        // TODO
//    }

    @Override
    public void onInstructorsRetrieved() {
        // TODO
    }

    @Override
    public void showProgress() {
        mProgressWrapper.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mProgressWrapper.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message, int what) {

    }
}
