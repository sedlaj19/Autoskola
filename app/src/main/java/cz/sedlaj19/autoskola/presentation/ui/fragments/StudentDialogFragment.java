package cz.sedlaj19.autoskola.presentation.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.domain.model.User;

/**
 * Created by Honza on 21. 8. 2016.
 */
public class StudentDialogFragment extends DialogFragment {

    public static final String ARG_PHONE_NUMBER = "phoneNumber";
    public static final String ARG_EMAIL = "email";

    private User user;

    public StudentDialogFragment(){}

    public static StudentDialogFragment newInstance(String phone, String email){
        StudentDialogFragment fragment = new StudentDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PHONE_NUMBER, phone);
        bundle.putString(ARG_EMAIL, email);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String phone = bundle.getString(ARG_PHONE_NUMBER);
        String email = bundle.getString(ARG_EMAIL);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.student_dialog_title)
                .setMessage(phone + "\n" + email)
                .setPositiveButton(R.string.call_student, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Call student
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        return builder.create();
    }
}
