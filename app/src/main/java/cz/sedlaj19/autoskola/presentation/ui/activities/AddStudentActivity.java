package cz.sedlaj19.autoskola.presentation.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.presentation.presenters.AddRidePresenter;
import cz.sedlaj19.autoskola.presentation.presenters.AddStudentPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.impl.AddRidePresenterImpl;
import cz.sedlaj19.autoskola.presentation.presenters.impl.AddStudentPresenterImpl;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;

public class AddStudentActivity extends AppCompatActivity implements
        AddStudentPresenter.View{

    @BindView(R.id.add_student_name)
    EditText mName;
    @BindView(R.id.add_student_surname)
    EditText mSurname;
    @BindView(R.id.add_student_email)
    EditText mEmail;
    @BindView(R.id.add_student_phone)
    EditText mPhone;

    private AddStudentPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        ButterKnife.bind(this);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_add_student_save:
                createStudent();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init(){
        mPresenter = new AddStudentPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
    }

    private void createStudent(){
        String name = mName.getText().toString();
        String surname = mSurname.getText().toString();
        String phone = mPhone.getText().toString();
        String email = mEmail.getText().toString();
        boolean isChecked = true;
        if(!mPresenter.checkName(name)){
            isChecked = false;
            // TODO
        }
        if(!mPresenter.checkName(surname)){
            isChecked = false;
            // TODO
        }
        if(!mPresenter.checkPhone(phone)){
            isChecked = false;
            // TODO
        }
        if(!mPresenter.checkEmail(email)){
            isChecked = false;
            // TODO
        }
        if(!isChecked){
            return;
        }
        mPresenter.createUser(name, surname, email, phone);
    }

    @Override
    public void onStudentCreated(User user) {
        Log.d(this.getClass().toString(), "TEST: student created ...");
        finish();
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
}
