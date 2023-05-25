package com.example.movieapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.adapter.TheatreAdapter;
import com.example.movieapp.model.EachTheatre;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TheatreFragment extends Fragment {


    private AutoCompleteTextView actvCity;
    private Button buttonSearch;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_theatre,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        actvCity = view.findViewById(R.id.actvCity);
        buttonSearch = view.findViewById(R.id.buttonSearch);
        recyclerView = view.findViewById(R.id.rvTheatre);

        final ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(requireActivity(),R.array.cities,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actvCity.setAdapter(adapter);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = actvCity.getText().toString();
                if(city == null || city.isEmpty()) {
                    Toast.makeText(requireActivity(), "Select ct", Toast.LENGTH_SHORT).show();
                    return;
                }

                showAllData(city);

            }
        });

    }

    private void showAllData(String city){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Theatures").child(city);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<EachTheatre> theatres = new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()){
                    String name = ds.child("Name:").getValue(String.class);
                    String location = ds.child("Location:").getValue(String.class);
                    String rating = String.valueOf(ds.child("Ratings:").getValue());
                    try {
                        double ratings = Double.parseDouble(rating);
                        EachTheatre theatre = new EachTheatre(location, name, ratings);
                        if (theatre != null) {
                            theatres.add(theatre);
                        }
                    }catch (Exception ignor){
                        ignor.printStackTrace();
                    }
                }
                TheatreAdapter adapter = new TheatreAdapter(requireActivity(),theatres);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
