package com.example.movieapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movieapp.R;
import com.example.movieapp.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    DatabaseReference reference;
    FirebaseUser user;
    CircleImageView imageView;
    TextView userName,userAge,userPhone;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView=view.findViewById(R.id.image_pro_frag);
        userName=view.findViewById(R.id.text_pro2);
        userAge=view.findViewById(R.id.age_pro);
        userPhone=view.findViewById(R.id.phone_pro);
        user= FirebaseAuth.getInstance().getCurrentUser();


        if(user != null) {
            reference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Users users = snapshot.getValue(Users.class);
                    if (users == null) return;

                    userName.setText(users.getName());
                    userAge.setText(users.getAge());
                    userPhone.setText(users.getPhone());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            Toast.makeText(requireActivity(), "You are not logged in", Toast.LENGTH_SHORT).show();
        }

    }

}
