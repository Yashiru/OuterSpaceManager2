package com.example.fasanol.outerspacemanager.models;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fasanol.outerspacemanager.R;
import com.example.fasanol.outerspacemanager.interfaces.ReportInterface;
import com.example.fasanol.outerspacemanager.interfaces.UserInterface;
import com.example.fasanol.outerspacemanager.models.HttpResponses.ReportsReponse;
import com.example.fasanol.outerspacemanager.models.HttpResponses.UserResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fasanol on 21/03/2017.
 */
public class AttackInProgressAdapter extends RecyclerView.Adapter<AttackInProgressAdapter.ProgressViewHolder> {
    private ArrayList<RowAttackList> list;
    private Context context;

    public AttackInProgressAdapter(ArrayList<RowAttackList> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_custom_attack_list, parent, false);

        ProgressViewHolder viewHolder = new ProgressViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProgressViewHolder holder, final int position) {
        final int pos = position;
        final ProgressViewHolder hol = holder;

        holder.fleet.setText(list.get(position).getFleetString());
        holder.progressBar.setProgress((int) list.get(position).getProgress());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder{
        private TextView fleet;
        private ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            fleet = (TextView) itemView.findViewById(R.id.fleet);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

}
