package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<User> {

    private ArrayList<User> userList;

    public CustomArrayAdapter(Context context, ArrayList<User> userList) {
        super(context, 0, userList);
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_user, parent, false);
        }

        User currentUser = getItem(position);

        TextView nameTextView = listItemView.findViewById(R.id.nameTextView);
        TextView emailTextView = listItemView.findViewById(R.id.emailTextView);
        ImageView avatarImageView = listItemView.findViewById(R.id.avatarImageView); // Ensure this line is correct

        if (currentUser != null) {
            nameTextView.setText(currentUser.getFullName());
            emailTextView.setText(currentUser.getEmail());

            Picasso.get()
                    .load(currentUser.getAvatar())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(avatarImageView);
        }

        return listItemView;
    }

}

