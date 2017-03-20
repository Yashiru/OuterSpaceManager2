package com.example.fasanol.outerspacemanager;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fasanol.outerspacemanager.models.RowAttackList;

import java.util.ArrayList;
import java.util.List;

public class CustomAttackList extends ArrayAdapter<RowAttackList> {
    private ArrayList<RowAttackList> attackList;
    private Context context;

    public CustomAttackList(Context context, ArrayList<RowAttackList> objects) {
        super(context, R.layout.activity_custom_attack_list, objects);
        this.attackList = objects;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_custom_attack_list, parent, false);

        TextView date = (TextView) rowView.findViewById(R.id.fleet);
        ProgressBar prog = (ProgressBar) rowView.findViewById(R.id.progressBar);


        return rowView;
    }
}
