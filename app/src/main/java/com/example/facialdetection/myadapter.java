package com.example.facialdetection;

import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.Model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

import static java.lang.String.valueOf;

public class myadapter extends FirebaseRecyclerAdapter<dataholder,myadapter.myviewholder> {

    ArrayList<String> datas;
    public myadapter(@NonNull FirebaseRecyclerOptions<dataholder> options ) {

        super(options);


    }



    @Override
    protected void onBindViewHolder(@NonNull final myviewholder myviewholder, int i, @NonNull final dataholder dataholder) {


        myviewholder.name.setText(dataholder.getName());
        Glide.with(myviewholder.img.getContext()).load(dataholder.getImage()).into(myviewholder.img);





    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder implements com.example.facialdetection.myviewholder {
        TextView name;
        ImageView img;
        Button clk;
        public myviewholder(@NonNull final View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            name=(TextView)itemView.findViewById(R.id.name);
            clk=(Button) itemView.findViewById(R.id.clk);
            clk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openn();
                }
            });

        }
        void openn(){
            String nm = getItem(getAdapterPosition()).Name;
            Intent intent = new Intent(itemView.getContext(),tra.class);
            intent.putExtra("Val",nm);
            itemView.getContext().startActivity(intent);

        }


    }

}
