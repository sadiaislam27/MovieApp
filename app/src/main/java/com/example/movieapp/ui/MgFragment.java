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
import com.example.movieapp.adapter.MovieAdapter;
import com.example.movieapp.adapter.TheatreAdapter;
import com.example.movieapp.model.EachMovie;
import com.example.movieapp.model.EachTheatre;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MgFragment extends Fragment {


    private AutoCompleteTextView actv;
    private Button buttonSearch;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_genre,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        actv = view.findViewById(R.id.actv);
        buttonSearch = view.findViewById(R.id.buttonSearch);
        recyclerView = view.findViewById(R.id.recyclerView);

        final ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(requireActivity(),R.array.genres,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actv.setAdapter(adapter);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = actv.getText().toString();
                if(key == null || key.isEmpty()) {
                    Toast.makeText(requireActivity(), "Select genre", Toast.LENGTH_SHORT).show();
                    return;
                }

                showAllData(key);

            }
        });

    }

    private void showAllData(String genre){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Genres").child(genre);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<EachMovie> movies = new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()){
                    EachMovie movie = ds.getValue(EachMovie.class);
                    if (movie != null) {
                        movies.add(movie);
                    }

                }
                if(movies.isEmpty()){
                    Toast.makeText(requireActivity(), "No movie found", Toast.LENGTH_SHORT).show();
                }
                MovieAdapter adapter = new MovieAdapter(requireActivity(),movies);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

