package com.architsoni.faceless;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private ArrayList<String> sender;
    private ArrayList<String> body;

    public MessageAdapter(Activity context, ArrayList<String> sender, ArrayList<String> body) {
        super(context, R.layout.mytext, sender);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.sender=sender;
        this.body=body;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mytext, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.msgSender);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.msgBody);

        titleText.setText(sender.get(position));
        subtitleText.setText(body.get(position));

        return rowView;

    };
}