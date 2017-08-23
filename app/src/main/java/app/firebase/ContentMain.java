package app.firebase;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.firebase.Model.Note;

/**
 * Created by Jacky Tsang on 23/08/2017.
 */

public class ContentMain extends AppCompatActivity {
    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    ListView noteslist;
    EditText subjectEditTxt,descTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        noteslist = (ListView) findViewById(R.id.noteslist);
        db = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db);
        adapter = new CustomAdapter(this, helper.retrieve());
        adapter.notifyDataSetChanged();
        noteslist.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayInputDialog();
            }
        });
    }

    private void displayInputDialog()
    {
        Dialog d=new Dialog(this);
        d.setTitle("Save To Firebase");
        d.setContentView(R.layout.input_dialog);
        subjectEditTxt= (EditText) d.findViewById(R.id.subjectEditText);
        descTxt= (EditText) d.findViewById(R.id.descEditText);
        Button saveBtn= (Button) d.findViewById(R.id.saveBtn);
        //SAVE
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GET DATA
                String subject=subjectEditTxt.getText().toString();
                String desc=descTxt.getText().toString();
                //SET DATA
                Note s=new Note();
                s.setSubject(subject);
                s.setDescription(desc);
                //SIMPLE VALIDATION
                if(subject != null && subject.length()>0)
                {
                    //THEN SAVE
                    if(helper.save(s))
                    {
                        //IF SAVED CLEAR EDITXT
                        subjectEditTxt.setText("");
                        descTxt.setText("");
                        adapter=new CustomAdapter(ContentMain.this,helper.retrieve());
                        noteslist.setAdapter(adapter);
                    }
                }else
                {
                    Toast.makeText(ContentMain.this, "Subject Must Not Be Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        d.show();
    }
}
