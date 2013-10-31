package com.artemzin.android.dev_null.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.artemzin.android.dev_null.R;
import com.artemzin.android.dev_null.api.dev_null.DevNullApi;
import com.artemzin.android.dev_null.api.network.NetworkException;
import com.artemzin.android.dev_null.util.ActivityUtil;
import com.artemzin.android.dev_null.util.StringUtil;
import com.artemzin.android.dev_null.util.TextValidator;
import com.artemzin.android.dev_null.util.ThreadUtil;

public class MainFragment extends Fragment {

    private static final String STATE_BUNDLE_NULL_IT_TEXT = "STATE_BUNDLE_NULL_IT_TEXT";

    private EditText nullItEditText;
    private Button   nullItButton;

    private Intent lastIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nullItEditText = (EditText) view.findViewById(R.id.null_it_edit_text);
        nullItButton   = (Button)   view.findViewById(R.id.null_it_button);
        nullItButton.setEnabled(false);

        new TextValidator(nullItEditText) {

            @Override
            protected String validate(String text) {
                if (StringUtil.isNullOrEmpty(text)) {
                    return getString(R.string.null_it_edit_text_validation_error_empty);
                }

                return null;
            }
        }.addOnValidationChangedListener(new TextValidator.OnValidationChangedListener() {
            @Override
            public void onValidationChanged(boolean isValid, String text, String validationError) {
                nullItButton.setEnabled(isValid);
            }
        });

        nullItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.hideSoftKeyboard(getActivity());

                final ProgressDialogFragment progressDialogFragment = ProgressDialogFragment.newInstance(getString(R.string.progress_dialog_nulling));
                progressDialogFragment.setCancelable(false);
                progressDialogFragment.show(getFragmentManager(), "progressDialogFragment");

                final long requestStartTime = SystemClock.elapsedRealtime();

                new AsyncTask<Void, Void, Void>() {

                    private NetworkException networkException;

                    @Override
                    protected Void doInBackground(Void... params) {

                        try {
                            DevNullApi.nullIt(nullItEditText.getText().toString());
                        } catch (NetworkException e) {
                            networkException = e;
                        }

                        // request duration should be at least 3 sec
                        ThreadUtil.sleepIfRequired(requestStartTime, 3000);

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        progressDialogFragment.dismiss();

                        if (networkException == null) {
                            nullItEditText.setText(null); // /dev/null!!! local
                            Toast.makeText(getActivity(), R.string.toast_null_success, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), R.string.toast_null_network_error, Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute();
            }
        });

        if (savedInstanceState != null) {
            nullItEditText.setText(savedInstanceState.getString(STATE_BUNDLE_NULL_IT_TEXT, ""));
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        final Intent intent = getActivity().getIntent();

        if (intent == null || intent.equals(lastIntent)) return;

        if ("android.intent.action.SEND".equals(intent.getAction())) {
            if ("text/plain".equals(intent.getType())) {
                nullItEditText.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
                nullItEditText.setSelection(nullItEditText.getText().toString().length());
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        final String nullItText = nullItEditText.getText().toString();

        if (!StringUtil.isNullOrEmpty(nullItText)) {
            outState.putString(STATE_BUNDLE_NULL_IT_TEXT, nullItText);
        }

        super.onSaveInstanceState(outState);
    }
}
