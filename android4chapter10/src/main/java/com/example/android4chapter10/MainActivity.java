package com.example.android4chapter10;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast();
            }
        });
    }

    private void showToast(){
        String msg = "Cheers!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(),msg,duration);
        toast.setGravity(Gravity.TOP,0,0);

        LinearLayout ll = new LinearLayout(getApplicationContext());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorGray));

        TextView myTextView = new TextView(getApplicationContext());
        myTextView.setText(msg);

        int lHeight = LinearLayout.LayoutParams.MATCH_PARENT;
        int lWidth = LinearLayout.LayoutParams.WRAP_CONTENT;

        ll.addView(myTextView,new LinearLayout.LayoutParams(lHeight,lWidth));

        ll.setPadding(50,20,20,50);

        toast.setView(ll);
        toast.show();
    }

}
