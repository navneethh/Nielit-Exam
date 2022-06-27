package com.allahabadi;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

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
//        String quest = "Fake question to show image"+ String.valueOf(position);
        String imageuri= userlist.get(position).getImageuri();
        String name= userlist.get(position).getUid();
        String date= userlist.get(position).getPost();

        holder.questionview.setText(quest);
        holder.nameview.setText(name);
       if(imageuri!=null){
           if(!imageuri.isEmpty()){
               Picasso.with(holder.authorimage.getContext()).load(imageuri).into(holder.authorimage);
           }
       }
        holder.questlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questiondeatils();
            }

            private void questiondeatils() {
                Intent i = new Intent(holder.questionview.getContext(),qdetail.class);
                i.putExtra("uid",quest);
                holder.questionview.getContext().startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView questionview;
        private TextView nameview;
        private TextView dateview;
        private LinearLayout questlay;
        ImageView authorimage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            questlay= itemView.findViewById(R.id.question_linear);
            questionview = itemView.findViewById(R.id.quest_txtview);
            nameview= itemView.findViewById(R.id.name_txtview);
            authorimage= itemView.findViewById(R.id.imageView5);

        }
    }
}




