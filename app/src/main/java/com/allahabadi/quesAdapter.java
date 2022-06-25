package com.allahabadi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class quesAdapter extends RecyclerView.Adapter<quesAdapter.MyViewHolder>{
    ArrayList<Question> userlist;

    public quesAdapter( ArrayList<Question> user){
        this.userlist=user;
    }


    @NonNull
    @Override
    public quesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemquestion,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull quesAdapter.MyViewHolder holder, int position) {
        String quest= userlist.get(position).getPost();
        String name= userlist.get(position).getUid();
        String date= userlist.get(position).getPost();

        holder.questionview.setText(quest);
        holder.nameview.setText(name);

    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView questionview;
        private TextView nameview;
        private TextView dateview;
        ImageView authorimage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            questionview = itemView.findViewById(R.id.quest_txtview);
            nameview= itemView.findViewById(R.id.name_txtview);
            authorimage= itemView.findViewById(R.id.imageView5);
        }
    }
}




