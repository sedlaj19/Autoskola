package cz.sedlaj19.autoskola.presentation.ui.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.utils.Converter;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.presentation.presenters.AddRidePresenter;
import cz.sedlaj19.autoskola.presentation.presenters.impl.AddRidePresenterImpl;
import cz.sedlaj19.autoskola.presentation.ui.adapters.AddRideAdapter;
import cz.sedlaj19.autoskola.presentation.ui.fragments.DatePickerFragment;
import cz.sedlaj19.autoskola.presentation.ui.fragments.TimePickerFragment;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;

public class AddRideActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, AddRidePresenter.View{

    @Bind(R.id.add_ride_progress_wrapper)
    View mProgressWrapper;
    @Bind(R.id.add_ride_date_picker)
    TextView mDatePicker;
    @Bind(R.id.add_ride_time_picker)
    TextView mTimePicker;
    @Bind(R.id.add_ride_students)
    Spinner mStudents;
    @Bind(R.id.add_ride_instructors)
    Spinner mInstructors;
    @Bind(R.id.add_ride_cars)
    Spinner mCars;
    @Bind(R.id.add_ride_notes)
    EditText mNotes;
    @Bind(R.id.add_ride_rides_title)
    TextView mAddRideTitle;
    @Bind(R.id.add_ride_rides_list)
    ListView mRidesList;

    private AddRidePresenter mAddRidePresenter;
    private Calendar mCalendar;
    private boolean mDateSet;
    private boolean mTimeSet;
    private boolean mIsEdit;
    private Ride mRideToEdit;
    private ArrayAdapter<String> mStudentsAdapter;
    private ArrayAdapter<String> mInstructorAdapter;
    private boolean mAreStudentsRetrieved;
    private boolean mAreInstructorsRetrieved;
    private AddRideAdapter mAddRideAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ride);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        this.mCalendar = Calendar.getInstance();
        this.mDateSet = false;
        this.mTimeSet = false;
        this.mAreInstructorsRetrieved = false;
        this.mAreStudentsRetrieved = false;
        mIsEdit = false;
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            mRideToEdit = bundle.getParcelable(Constants.Common.RIDE_TO_EDIT);
            mIsEdit = true;
        }
        mAddRidePresenter = new AddRidePresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
        mAddRidePresenter.getStudentsNames();
        mAddRidePresenter.getInstructorsNames();
        // TODO prozatimni reseni aut
        List<String> names = new ArrayList<>();
        names.add("Mitsubishi ASX");
        names.add("Opel Astra");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCars.setAdapter(adapter);

        mAddRideAdapter = new AddRideAdapter();
        this.mRidesList.setAdapter(mAddRideAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_ride, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_add_ride_save:
                createRide();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.add_ride_date_picker)
    public void onDatePickerClicked(){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setListener(this);
        datePickerFragment.show(getFragmentManager(), Constants.AddRide.DATE_PICKER_FRAGMENT_TAG);
    }

    @OnClick(R.id.add_ride_time_picker)
    public void onTimePickerClicked(){
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setListener(this);
        timePickerFragment.show(getFragmentManager(), Constants.AddRide.TIME_PICKER_FRAGMENT_TAG);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        this.mDateSet = true;
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mCalendar.set(Calendar.MONTH, monthOfYear);
        mCalendar.set(Calendar.YEAR, year);
        Date date = mCalendar.getTime();
        mDatePicker.setText(Converter.convertDateToShortString(date));

        mAddRidePresenter.getDayRides(date.getTime());
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
        mTimeSet = true;
        mCalendar.set(Calendar.HOUR_OF_DAY, hours);
        mCalendar.set(Calendar.MINUTE, minutes);
        Date date = mCalendar.getTime();
        mTimePicker.setText(Converter.convertTimeToString(date));
    }

    @Override
    public void onStudentsNamesRetrieved(List<String> names) {
        mAreStudentsRetrieved = true;
        mStudentsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, names);
        mStudentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStudents.setAdapter(mStudentsAdapter);
        setData();
    }

    @Override
    public void onInstructorsNamesRetrieved(List<String> names) {
        mAreInstructorsRetrieved = true;
        mInstructorAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, names);
        mInstructorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mInstructors.setAdapter(mInstructorAdapter);
        setData();
    }

    @Override
    public void onRideCreated(Ride ride) {
        // TODO updatovat seznam jizd v instructor aktivite
        finish();
    }

    @Override
    public void onRideUpdated(int position) {

    }

    @Override
    public void onRidesRetrieved(List<Ride> rides) {
        if(rides == null || rides.isEmpty()){
            mRidesList.setVisibility(View.GONE);
            return;
        }else{
            mRidesList.setVisibility(View.VISIBLE);
        }
        mAddRideAdapter.addNewRides(rides);
    }

    @Override
    public void showProgress() {
        mProgressWrapper.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressWrapper.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message, int what) {

    }

    private void createRide(){
        // TODO udelat lepsi oznameni ze to neni vyplneno ...
        if(!mDateSet){
            mDatePicker.setError("Date has to be set.");
            return;
        }
        if(!mTimeSet){
            mTimePicker.setError("Time has to be set.");
            return;
        }
        String car = mCars.getSelectedItem().toString();
        String instructor = mInstructors.getSelectedItem().toString();
        String student = mStudents.getSelectedItem().toString();
        String notes = mNotes.getText().toString();
        if(mIsEdit) {
            mAddRidePresenter.createRide(mCalendar.getTimeInMillis(), car,
                    instructor, student, mRideToEdit.getId(), notes);
        }else{
            mAddRidePresenter.createRide(mCalendar.getTimeInMillis(), car,
                    instructor, student, null, notes);
        }
    }

    private void setData(){
        if(!mAreStudentsRetrieved || !mAreInstructorsRetrieved){
            return;
        }
        Date date = new Date(mRideToEdit.getDateMillis());
        this.mTimePicker.setText(Converter.convertTimeToString(date));
        this.mDatePicker.setText(Converter.convertDateToShortString(date));
        this.mNotes.setText(mRideToEdit.getNotes());
        mDateSet = true;
        mTimeSet = true;
        Container container = Container.getInstance();
        String instructor = container.getUserNameByKey(mRideToEdit.getInstructor());
        String student = container.getUserNameByKey(mRideToEdit.getStudent());
        this.mInstructors.setSelection(mInstructorAdapter.getPosition(instructor));
        this.mStudents.setSelection(mStudentsAdapter.getPosition(student));
        mCalendar.setTimeInMillis(date.getTime());
        mAddRidePresenter.getDayRides(date.getTime());
    }

}
