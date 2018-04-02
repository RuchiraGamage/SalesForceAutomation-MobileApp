package com.example.salinda.salseforseautomation.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.model.LeaveHistryModel;

import java.util.List;

public class MyLeavesAdapter extends RecyclerView.Adapter<MyLeavesAdapter.MyViewHolder> {
    Context context;
    List<LeaveHistryModel> leaveModels;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView leave,status;
        CardView card;

        public MyViewHolder(View itemView) {
            super(itemView);
            leave = (TextView) itemView.findViewById(R.id.leave);
            status = (TextView) itemView.findViewById(R.id.status);
        }
    }

    public MyLeavesAdapter(Context context, List<LeaveHistryModel> leaveModels){
        this.context = context;
        this.leaveModels = leaveModels;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leave_row_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.leave.setText(leaveModels.get(position).getStartingDate()+" to "+leaveModels.get(position).getEndingDate());
        holder.status.setText(leaveModels.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return leaveModels.size();
    }

}
