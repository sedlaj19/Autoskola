package cz.sedlaj19.autoskola.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.presentation.presenters.MainPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.impl.MainPresenterImpl;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;


public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    private MainPresenter mMainPresenter;

    @BindView(R.id.login_et_email)
    EditText mEmail;
    @BindView(R.id.login_et_password)
    EditText mPassword;
    @BindView(R.id.login_btn_login)
    Button mLoginBtn;
    @BindView(R.id.login_btn_register)
    Button mRegisterBtn;
    @BindView(R.id.login_progress_wrapper)
    View mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        mMainPresenter = new MainPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
//        mEmail.setText("sedli1@test.cz");
//        mPassword.setText("123456");
        mEmail.setText("instructor1@test.cz");
        mPassword.setText("123456");
    }

    @OnClick(R.id.login_btn_register)
    public void registrationBtnClicked(){
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.login_btn_login)
    public void loginBtnClicked(){
        String username = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        mMainPresenter.doLogin(username, password);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainPresenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMainPresenter.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMainPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMainPresenter.stop();
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message, int what) {
        switch (what){
            case Constants.Login.LOGIN_ERROR_EMAIL:
                mEmail.setError(message);
                break;
            case Constants.Login.LOGIN_ERROR_PASSWORD:
                mPassword.setError(message);
                break;
            case Constants.Login.LOGIN_ERROR_TOAST:
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onUserRetrieved(User user) {
        Intent intent;
        if(user.isInstructor()){
            intent = new Intent(getApplicationContext(), InstructorActivity.class);
        }else{
            intent = new Intent(getApplicationContext(), DashboardActivity.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginSuccessful(String email) {
        mMainPresenter.getUserByEmail(email);
    }
}
