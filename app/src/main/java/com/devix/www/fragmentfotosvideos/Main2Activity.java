package com.devix.www.fragmentfotosvideos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int selectPosition = bundle.getInt("select");
            Log.e("AQUI", String.valueOf(selectPosition));
        }
    }
}
