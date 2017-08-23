package app.firebase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.firebase.Model.Note;

/**
 * Created by Jacky Tsang on 23/08/2017.
 */

public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Note> notes;
    public CustomAdapter(Context c, ArrayList<Note> notes) {
        this.c = c;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.model,parent,false);
        }
        TextView subjectTxt= (TextView) convertView.findViewById(R.id.subjectTxt);
        TextView descTxt= (TextView) convertView.findViewById(R.id.descTxt);
        final Note s= (Note) this.getItem(position);
        subjectTxt.setText(s.getSubject());
        descTxt.setText(s.getDescription());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OPEN DETAIL
                openDetailActivity(s.getSubject(),s.getDescription());
            }
        });
        return convertView;
    }
    private void openDetailActivity(String...details)
    {
        Intent i=new Intent(c,DetailActivity.class);
        i.putExtra("SUBJECT_KEY",details[0]);
        i.putExtra("DESC_KEY",details[1]);
        c.startActivity(i);
    }
}
