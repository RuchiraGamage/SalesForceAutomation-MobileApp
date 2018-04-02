package com.example.salinda.salseforseautomation.Message.Chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.salinda.salseforseautomation.Message.Message.Messagechat;
import com.example.salinda.salseforseautomation.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{


    private List<UserModel> usermodel;
    private Context context;

    public UserAdapter(List<UserModel> usermodel, Context context) {
        this.usermodel = usermodel;
        this.context = context;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_single,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {

        //bind the actual data to recycle view

        UserModel usethis=usermodel.get(position);
        holder.user.setText(usethis.getUser_name());
        holder.status.setText(usethis.getUser_type());
        holder.city.setText(usethis.getCity());

        final String user_name=usethis.getUser_name();
        final int user_id=usethis.getId();


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent chatActivity=new Intent(view.getContext(),Messagechat.class);
                chatActivity.putExtra("user_id",user_id);
                chatActivity.putExtra("user_name",user_name);
                view.getContext().startActivity(chatActivity);
            }
        });
        if(!usethis.getImage().equals("null")){
            Glide.with(context).load(usethis.getImage()).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return usermodel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //  View mvView;

        private TextView user;
        private TextView status;
        private TextView city;
        private ImageView image;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            // mvView=itemView;

            user=(TextView)itemView.findViewById(R.id.user_single_name);
            status=(TextView)itemView.findViewById(R.id.user_single_status);
            city=(TextView)itemView.findViewById(R.id.user_single_city);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.layout_user);
            image=(ImageView)itemView.findViewById(R.id.user_single_image);
        }
    }
}
