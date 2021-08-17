package com.architsoni.faceless;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<User> {

    Context context;
    public MyArrayAdapter(Context context, int resourceId, List<User> items){
        super(context, resourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        User user = getItem(position);
        if(convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        TextView name = convertView.findViewById(R.id.helloText);
        TextView age = convertView.findViewById(R.id.ageText);
        TextView gender = convertView.findViewById(R.id.genderText);
        TextView bio = convertView.findViewById(R.id.bioText);
        TextView location = convertView.findViewById(R.id.cityText);
        TextView uni = convertView.findViewById(R.id.q1Text);
        TextView reln = convertView.findViewById(R.id.q2Text);
        TextView hobby = convertView.findViewById(R.id.q3Text);
        TextView food = convertView.findViewById(R.id.q4Text);
        TextView watch = convertView.findViewById(R.id.q5Text);
        TextView listen = convertView.findViewById(R.id.q6Text);
        TextView drink = convertView.findViewById(R.id.q7Text);


        name.setText(user.getFullName());
        gender.setText(user.getGender());
        age.setText(String.valueOf(user.getAge()));
        bio.setText(user.getBio());
        location.setText(user.getLocation());
        uni.setText(user.getUni());
        reln.setText(user.getReln());
        hobby.setText(user.getHobby());
        food.setText(user.getFood());
        watch.setText(user.getWatch());
        listen.setText(user.getLocation());
        drink.setText(user.getDrink());

        return convertView;
    }

}
