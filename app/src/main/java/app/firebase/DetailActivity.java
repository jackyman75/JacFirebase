package app.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Jacky Tsang on 23/08/2017.
 */

public class DetailActivity extends AppCompatActivity {

    TextView nameTxt,descTxt, propTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        nameTxt = (TextView) findViewById(R.id.subjectDetailTxt);
        descTxt= (TextView) findViewById(R.id.descDetailTxt);

        //GET INTENT
        Intent i=this.getIntent();


        //RECEIVE DATA
        String subject=i.getExtras().getString("SUBJECT_KEY");
        String desc=i.getExtras().getString("DESC_KEY");

        //BIND DATA
        nameTxt.setText(subject);
        descTxt.setText(desc);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
