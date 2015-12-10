package com.example.androidapplication.contacts;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Contacts> {

    LayoutInflater layoutInflater;
    Context c;
    public CustomAdapter(Context context, ArrayList<Contacts> contacts) {
        super(context, R.layout.row_layout,contacts);
        this.c=context;
    }

    public static class ViewHolder{
        TextView contact_id, contact_name, contact_address1,contact_address2, contact_county,contact_contactno;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contacts contacts =getItem(position);
        //check if the views is null, create if it is null
        if(convertView == null){
            layoutInflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.row_layout,null);
        }

        //if the convert view is not null than recycle it,
        final ViewHolder viewHolder = new ViewHolder();
        viewHolder.contact_name = (TextView) convertView.findViewById(R.id.textViewDisplayContactname);


        viewHolder.contact_name.setText(contacts.getContact_name());


        return convertView;
    }//end getView

}//end class
