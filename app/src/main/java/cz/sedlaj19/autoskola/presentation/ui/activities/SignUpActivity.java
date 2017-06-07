package cz.sedlaj19.autoskola.presentation.ui.activities;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.presentation.presenters.SignUpPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.impl.SignUpPresenterImpl;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;
import cz.sedlaj19.autoskola.utils.SharedPrefHelper;

public class SignUpActivity extends AppCompatActivity implements SignUpPresenter.View{

    private SignUpPresenter mSignUpPresenter;

    @BindView(R.id.sign_up_email)
    EditText mEmail;
    @BindView(R.id.sign_up_password)
    EditText mPassword;
    @BindView(R.id.sign_up_first_name)
    EditText mFirstName;
    @BindView(R.id.sign_up_surname)
    EditText mSurname;
    @BindView(R.id.sign_up_phone)
    EditText mPhone;
    @BindView(R.id.sign_up_progress_wrapper)
    View mProgress;
    @BindView(R.id.sign_up_content_wrapper)
    View mContent;
    @BindView(R.id.sign_up_instructor_password_wrapper)
    TextInputLayout mInstructorPasswordWrapper;
    @BindView(R.id.sign_up_instructor_password)
    EditText mInstructorPassword;
    @BindView(R.id.sign_up_instructor_account)
    CheckBox mInstructorCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSignUpPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSignUpPresenter.stop();
    }

    private void init(){
        mSignUpPresenter = new SignUpPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
//        setDummyUser(2);
        setDummyInstructor(2);
    }

    @OnClick(R.id.sign_up_btn_sign_up)
    public void signUpBtnClicked(){
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String firstname = mFirstName.getText().toString();
        String surname = mSurname.getText().toString();
        String phone = mPhone.getText().toString();
        boolean instructor = mInstructorCheckbox.isChecked();
        String instructorPassword = mInstructorPassword.getText().toString();
        String deviceId = SharedPrefHelper.getDeviceId(getApplicationContext());
        mSignUpPresenter.doSignUp(email, password, firstname, surname,
                phone, instructor, instructorPassword, deviceId);
    }

    public void onInstructorAccountClicked(View view){
        boolean checked = ((CheckBox)view).isChecked();
        if(checked){
            mInstructorPasswordWrapper.setVisibility(View.VISIBLE);
        }else{
            mInstructorPasswordWrapper.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
        mContent.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
        mContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message, int what) {
        switch (what){
            case Constants.SignUp.SIGN_UP_ERROR_EMAIL:
                mEmail.setError(message);
                break;
            case Constants.SignUp.SIGN_UP_ERROR_PASSWORD:
                mPassword.setError(message);
                break;
            case Constants.SignUp.SIGN_UP_ERROR_TOAST:
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onSignUpSuccessful() {
        // TODO: Pridat usera aby se nemusel dostavat z databaze
        finish();
    }

    private void setDummyUser(int i){
        mEmail.setText("sedli"+i+"@test.cz");
        mPassword.setText("123456");
        mFirstName.setText("Jan"+i);
        mSurname.setText("Sedlacek"+i);
        mPhone.setText("123456789");
    }

    private void setDummyInstructor(int i){
        mEmail.setText("instructor"+i+"@test.cz");
        mPassword.setText("123456");
        mFirstName.setText("Jan"+i);
        mSurname.setText("Sedlacek"+i);
        mPhone.setText("123456789");
        mInstructorCheckbox.setChecked(true);
        mInstructorPassword.setText("1234");
    }
}
