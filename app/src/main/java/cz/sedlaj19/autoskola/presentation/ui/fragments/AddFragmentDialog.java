package cz.sedlaj19.autoskola.presentation.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.presentation.ui.listeners.AddListener;

/**
 * Created by Honza on 29. 1. 2017.
 */

public class AddFragmentDialog extends DialogFragment {

    public static final String ARG_TITLE = "title";
    public static final String ARG_CONTENT = "content";

    @BindView(R.id.add_name)
    EditText mName;

    private AddListener mListener;

    public static AddFragmentDialog newInstance(int title, String content){
        AddFragmentDialog fragment = new AddFragmentDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_TITLE, title);
        args.putString(ARG_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int title = bundle.getInt(ARG_TITLE);
        String content = bundle.getString(ARG_CONTENT);
        View view = View.inflate(getContext(), R.layout.add_view, null);
        ButterKnife.bind(this, view);
        if(!TextUtils.isEmpty(content)){
            mName.setText(content);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setView(view)
                .setPositiveButton(R.string.save, (dialogInterface, i) -> {
                    if(mListener != null){
                        mListener.onAddSuccessful(mName.getText().toString());
                    }
                    dialogInterface.dismiss();
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        if(context instanceof AddListener){
            mListener = (AddListener)context;
        }
        super.onAttach(context);
    }
}
