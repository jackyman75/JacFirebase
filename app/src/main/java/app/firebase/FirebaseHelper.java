package app.firebase;

import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.firebase.Model.Note;

/**
 * Created by Jacky Tsang on 23/08/2017.
 */

public class FirebaseHelper {
    DatabaseReference db;
    Boolean saved=null;
    ArrayList<Note> notes=new ArrayList<>();
    Gson gson = new Gson();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    public Boolean save(Note note)
    {
        if(note==null)
        {
            saved=false;
        }else
        {
            try
            {
                db.child("notes").push().setValue(note);
                saved=true;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }
        return saved;
    }

    private void fetchData(DataSnapshot dataSnapshot)
    {


        if ("notes".equalsIgnoreCase(dataSnapshot.getKey())) {
            notes.clear();
            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                Note note=ds.getValue(Note.class);

                notes.add(note);

                //Log.i("note:",ds.getValue().toString());
            }
        }

    }

    public ArrayList<Note> retrieve() {
        Log.i("FirebaseHelper", "start retrieve");
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("FirebaseHelper", "add item " + s + " - " + dataSnapshot.toString());
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.i("FirebaseHelper", "load item "+ " - " + ds.toString());
                    fetchData(ds);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return notes;
    }

}
