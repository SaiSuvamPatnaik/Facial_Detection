package com.example.facialdetection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myadapter extends FirebaseRecyclerAdapter<dataholder,myadapter.myviewholder> {

    public myadapter(@NonNull FirebaseRecyclerOptions<dataholder> options ) {
        super(options);

    }



    @Override
    protected void onBindViewHolder(@NonNull myviewholder myviewholder, int i, @NonNull dataholder dataholder) {
        myviewholder.name.setText(dataholder.getName());
        myviewholder.age.setText(dataholder.getAge());
        myviewholder.gender.setText(dataholder.getGender());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView name,age,gender;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            age=(TextView)itemView.findViewById(R.id.age);
            gender=(TextView)itemView.findViewById(R.id.gender);

        }
    }

}
