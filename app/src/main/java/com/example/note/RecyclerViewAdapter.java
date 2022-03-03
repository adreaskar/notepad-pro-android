package com.example.note;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myViewHolder>{

    ArrayList<Note> dataholder;
    String context;
    Context conText;
    SessionManagement sessionManagement;

    public RecyclerViewAdapter(ArrayList<Note> dataholder) {
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext().toString();
        conText = parent.getContext();
        sessionManagement = new SessionManagement(parent.getContext());

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.private_note_view,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, final int position) {
        holder.ptitle.setText(dataholder.get(position).getTitle());
        holder.pbody.setText(dataholder.get(position).getBody());
        holder.pdate.setText(dataholder.get(position).getDate());

        String visibility = dataholder.get(position).getVisibility();
        String favourite = dataholder.get(position).getFavourite();
        String user = dataholder.get(position).getUser();

        // Viewing the Home Activity --
        if (context.contains("Home")) {

            if (visibility.equals("public")) {
                holder.linearLayout.addView(holder.tag);
            }
            if (favourite.equals("yes")) {
                ViewGroup.MarginLayoutParams marginLayoutParamsYellow = (ViewGroup.MarginLayoutParams) holder.tagFav.getLayoutParams();
                marginLayoutParamsYellow.setMargins(15,0,0,0);

                if (visibility.equals("public")) {
                    ViewGroup.MarginLayoutParams marginLayoutParamsBlue = (ViewGroup.MarginLayoutParams) holder.tag.getLayoutParams();
                    marginLayoutParamsBlue.setMargins(13,0,0,0);
                }
                holder.linearLayout.addView(holder.tagFav);
            }

        // Viewing the Favourites Activity ---------
        } else if (context.contains("Favourites")) {

            if (visibility.equals("public")) {
                holder.linearLayout.addView(holder.tag);

                ViewGroup.MarginLayoutParams marginLayoutParamsBlue = (ViewGroup.MarginLayoutParams) holder.tag.getLayoutParams();
                marginLayoutParamsBlue.setMargins(13,0,0,0);
            }

        // Viewing the Global Activity ---------
        } else if (context.contains("Global")) {

            if (sessionManagement.getSession().equals(user)) {
                holder.user.setText("by you");
                holder.linearLayout.addView(holder.user);
            } else {
                holder.user.setText("by "+user);
                holder.linearLayout.addView(holder.user);
            }
        }

        // On click of an item, go to Edit Activity -------------------
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context.contains("Global")) {
                    Intent intent = new Intent(conText, EditGlobalActivity.class);
                    intent.putExtra("note", dataholder.get(position));
                    conText.startActivity(intent);
                } else {
                    if (context.contains("Home")) {
                        Intent intent = new Intent(conText, EditActivity.class);
                        //intent.putExtra("note", dataholder.get(position));

                        Bundle extras = new Bundle();
                        extras.putParcelable("note",dataholder.get(position));
                        extras.putString("from", "home");
                        intent.putExtras(extras);

                        conText.startActivity(intent);
                    } else if (context.contains("Favourites")) {
                        Intent intent = new Intent(conText, EditActivity.class);
                        //intent.putExtra("note", dataholder.get(position));

                        Bundle extras = new Bundle();
                        extras.putParcelable("note",dataholder.get(position));
                        extras.putString("from", "favourites");
                        intent.putExtras(extras);

                        conText.startActivity(intent);
                    }

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataholder == null ? 0 : dataholder.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView ptitle,pdate,pbody;
        LinearLayout linearLayout;
        TextView tag, tagFav, user;

        public myViewHolder(View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.notebody);
            ptitle = itemView.findViewById(R.id.privatetitle);
            pbody = itemView.findViewById(R.id.privatebody);
            pdate = itemView.findViewById(R.id.privatedate);

            // Add Public Tag attributes ------------
            tag =  new TextView(ptitle.getContext());
            tag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            tag.setText("Public");
            tag.setTextSize(12);
            tag.setTextColor(Color.parseColor("#2D73B1"));
            tag.setBackgroundTintList(ptitle.getContext().getResources().getColorStateList(R.color.tagblue));
            tag.setBackgroundResource(R.drawable.rounded_corner);
            tag.setPadding(15, 7, 15, 7);// in pixels

            // Add Favourite Tag attributes ------------
            tagFav =  new TextView(ptitle.getContext());
            tagFav.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            tagFav.setText("Favorite");
            tagFav.setTextSize(12);
            tagFav.setTextColor(Color.parseColor("#876708"));
            tagFav.setBackgroundTintList(ptitle.getContext().getResources().getColorStateList(R.color.tagyellow));
            tagFav.setBackgroundResource(R.drawable.rounded_corner);
            tagFav.setPadding(15, 7, 15, 7);// in pixels

            // Add Username attributes ---------------
            user =  new TextView(ptitle.getContext());
            user.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            user.setTextColor(Color.parseColor("#ACACAC"));
        }
    }

}
