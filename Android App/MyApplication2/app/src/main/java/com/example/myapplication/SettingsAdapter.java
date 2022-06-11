package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder> {
    private List<String> settingsList;
    private SettingsFragment context;
    private SettingSelection settingSelection;

    public SettingsAdapter(List<String> settingsList, SettingsFragment context) {
        this.settingsList = settingsList;
        this.context = context;
        settingSelection=context;
    }

    @NonNull
    @Override
    public SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView=(TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_list_item,parent,false);
        return new SettingsViewHolder(textView,settingSelection);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsViewHolder holder, int position) {
        String settingName=settingsList.get(position);
        holder.settingName.setText(settingName);

    }

    @Override
    public int getItemCount() {
        return settingsList.size();
    }
    public static class SettingsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView settingName;
        SettingSelection settingSelection;

        public SettingsViewHolder(@NonNull TextView itemView,SettingSelection settingSelection) {
            super(itemView);
            settingName=itemView;
            this.settingSelection=settingSelection;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            settingSelection.onSettingSelected(getAdapterPosition());

        }
    }
}
