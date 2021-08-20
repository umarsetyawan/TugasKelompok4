package com.example.tugaskelompok4;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final List<User> users;

    public Adapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.email.setText(users.get(position).getEmail());
        holder.name.setText(users.get(position).getFirstName() + " " + users.get(position).getLastName());
        AndroidNetworking.get(users.get(position)
                .getAvatar())
                .setTag("imageRequestTag")
                .setPriority(Priority.MEDIUM)
                .setBitmapMaxHeight(400)
                .setBitmapMaxWidth(400)
                .setBitmapConfig(Bitmap.Config.ARGB_8888)
                .build()
                .getAsBitmap(new BitmapRequestListener() {
                    @Override
                    public void onResponse(Bitmap response) {
                        holder.avatar.setImageBitmap(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return (users == null) ? 0 : users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView email, name;
        private final ImageView avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.email);
            name = itemView.findViewById(R.id.name);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }
}
