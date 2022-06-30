package com.allahabadi;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


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
        String key= userlist.get(position).getUid();
        Long time = userlist.get(position).getTime();
        String name= userlist.get(position).getName();


        if (quest.length() > 510) {
            final String quest1 = quest.substring(0, 500);

            holder.questionview.setText(quest1);
        } else holder.questionview.setText(quest);
        holder.nameview.setText(name);
        if (imageuri != null) {
            if (!imageuri.isEmpty()) {
                Picasso.with(holder.authorimage.getContext()).load(imageuri).into(holder.authorimage);
            }
        }

        holder.dateview.setText(gettime(time));


        holder.favimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.favimage.setBackgroundResource(R.drawable.ic_fav_red);
//                FirebaseDatabase.getInstance().getReference("likes").child(qid).push().setValue("key");
            }
        });
        holder.questlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questiondeatils();
            }

            private void questiondeatils() {
                Intent i = new Intent(holder.questionview.getContext(), qdetail.class);
                i.putExtra("key", key);
                i.putExtra("post",quest);
                i.putExtra("time",time);
                i.putExtra("name",name);
                i.putExtra("image",imageuri);
                holder.questionview.getContext().startActivity(i);
            }
        });
    }

    private String gettime(Long time) {
      Long now=  System.currentTimeMillis();
      Long timediff= now-time;
        long seconds= TimeUnit.MILLISECONDS.toSeconds(timediff);
        long minutes= TimeUnit.MILLISECONDS.toMinutes(timediff);
        long hour= TimeUnit.MILLISECONDS.toHours(timediff);
        long days= TimeUnit.MILLISECONDS.toDays(timediff);

        if(minutes<60)
        {
            return (minutes+" min");
        }

        else if(hour<24)
        {
           return (hour+" hrs");
        }
        else if (days < 30)
        {

            return (days+" days");}
        else {
            DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = new Date(time);
            return dateFormat.format(date).toLowerCase();
        }

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
        ImageView authorimage,favimage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            questlay = itemView.findViewById(R.id.question_linear);
            questionview = itemView.findViewById(R.id.quest_txtview);
            nameview = itemView.findViewById(R.id.name_txtview);
            authorimage = itemView.findViewById(R.id.imageView5);
            dateview = itemView.findViewById(R.id.dateid);
            favimage= itemView.findViewById(R.id.favimage);

        }
    }
}




