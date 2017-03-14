package com.example.fasanol.outerspacemanager.models;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fasanol.outerspacemanager.R;
import com.example.fasanol.outerspacemanager.interfaces.UserInterface;
import com.example.fasanol.outerspacemanager.models.HttpResponses.httpSimpleResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mac1 on 13/03/2017.
 */

public class BuildingsAdapter extends RecyclerView.Adapter<BuildingsAdapter.BuildingsViewHolder> {
    private final List<Building> buildings;
    private final Context context;
    private Retrofit ret = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private SharedPreferences settings;
    private String token;

    public BuildingsAdapter(ArrayList<Building> buildings, Context context) {
        this.buildings = buildings;
        this.context = context;
    }

    @Override
    public BuildingsAdapter.BuildingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_buildings, parent, false);

        this.settings = context.getSharedPreferences("apiSettings", 0);
        this.token = settings.getString("token", "");

        BuildingsViewHolder viewHolder = new BuildingsViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BuildingsAdapter.BuildingsViewHolder holder, int position) {
        final int pos = position;
        final BuildingsAdapter.BuildingsViewHolder hol = holder;
        holder.buildingName.setText(buildings.get(position).getName());
        holder.level.setText("Niveau "+buildings.get(position).getLevel());
        holder.cost.setText("Gaz "+buildings.get(position).getGasCostToUp()+"\nMineraux "+buildings.get(position).getMineralCostToUp());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hol.loadingLayout.getVisibility() == View.GONE){
                    new AlertDialog.Builder(context)
                            .setTitle("Construire un nouveau bâtiment ")
                            .setMessage("Êtes-vous sure de vouloire construire un(e) "+buildings.get(pos).getName()+"?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    UserInterface service = ret.create(UserInterface.class);
                                    Call<httpSimpleResponse> request = service.createBuilding(token, pos);
                                    request.enqueue(new Callback<httpSimpleResponse>() {
                                        @Override
                                        public void onResponse(Call<httpSimpleResponse> call, Response<httpSimpleResponse> response) {
                                            CharSequence text = buildings.get(pos).getName()+" débute son amélioration";
                                            int duration = Toast.LENGTH_SHORT;

                                            Toast toast = Toast.makeText(context, text, duration);
                                            toast.show();
                                        }

                                        @Override
                                        public void onFailure(Call<httpSimpleResponse> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_input_add)
                            .show();
                }
                else {
                    new AlertDialog.Builder(context)
                            .setTitle("Patience ...")
                            .setMessage("Vôtre "+buildings.get(pos).getName()+" est déjà en cours d'amélioration")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });

        if(buildings.get(position).isBuilding())
        {
            Log.d("isBuilding", buildings.get(position).isBuilding().toString());
            holder.loadingLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return buildings.size();
    }



    public class BuildingsViewHolder extends RecyclerView.ViewHolder{
        private TextView buildingName;
        private TextView level;
        private TextView cost;
        private LinearLayout loadingLayout;
        private View separator;

        public BuildingsViewHolder(View itemView) {
            super(itemView);
            buildingName = (TextView) itemView.findViewById(R.id.name);
            level = (TextView) itemView.findViewById(R.id.level);
            cost = (TextView) itemView.findViewById(R.id.cost);
            loadingLayout = (LinearLayout) itemView.findViewById(R.id.loadingBuildingLayout);
        }
    }

}
