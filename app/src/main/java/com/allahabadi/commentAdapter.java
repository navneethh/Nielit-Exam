package com.allahabadi;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class commentAdapter extends RecyclerView.Adapter<commentAdapter.MyViewHolder> {
    ArrayList<Question> answerlist;
    public  commentAdapter (ArrayList<Question> answerlist){
        this.answerlist=answerlist;
    }
    @NonNull
    @Override
    public commentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemquestionanswer,parent,false);

        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull commentAdapter.MyViewHolder holder, int position) {
        String answer= answerlist.get(position).getPost();
//        String quest = "Fake question to show image"+ String.valueOf(position);
        String imageuri= answerlist.get(position).getImageuri();
        String key= answerlist.get(position).getUid();
        Long time = answerlist.get(position).getTime();
        String name= answerlist.get(position).getName();
        if(position==0
        ){
            holder.anwerLay.setVisibility(View.GONE);
            holder.replytag.setText("Asked by");
            holder.replytag.setBackgroundResource(R.drawable.coursedetailback);
            holder.questionhere.setBackgroundResource(R.drawable.quest_back_round);
        }else
        {
            holder.anwerLay.setVisibility(View.VISIBLE);

            holder.replytag.setBackgroundResource(R.color.white);
            holder.questionhere.setBackgroundResource(R.drawable.answer_back_circle);
            holder.replytag.setText("replied by");

        }


       holder.answerview.setText(answer);
       holder.nameview.setText(name);
       holder.dateview.setText(getTime(time));
        if(imageuri!=null)
        {
            if ( !imageuri.isEmpty()){
                Picasso.with(holder.prof.getContext()).load(imageuri).into(holder.prof);
            }}

//       holder.anwerLay.setBackgroundResource(R.drawable.answer_back_circle);

    }

    private String getTime(Long time) {
        Long now=  System.currentTimeMillis();
        Long timediff= now-time;
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
        return answerlist.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView answerview;
        private TextView nameview,replytag;
        private TextView dateview;
        private Space anwerLay;
        private ImageView prof;
        private LinearLayout questionhere;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            anwerLay = itemView.findViewById(R.id.spaceinans);
            answerview = itemView.findViewById(R.id.quest_txtviewa);
            replytag = itemView.findViewById(R.id.textView10);
            dateview = itemView.findViewById(R.id.dateida);
            nameview= itemView.findViewById(R.id.name_txtviewa);
            prof = itemView.findViewById(R.id.imageView5a);
            questionhere = itemView.findViewById(R.id.linearanswer);
        }

    }
}
