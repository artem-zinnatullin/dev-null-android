package com.artemzin.android.dev_null.activity;

import android.app.Activity;
import android.os.Bundle;

import com.artemzin.android.dev_null.R;
import com.artemzin.android.dev_null.fragment.MainFragment;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }
    }
}
