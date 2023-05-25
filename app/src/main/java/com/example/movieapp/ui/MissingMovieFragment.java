package com.example.movieapp.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movieapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MissingMovieFragment extends Fragment{
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_missing_moview,container,false);
        }

    private ActivityResultLauncher<String> mGetContent;
    private Uri photoUri;
    int day, month, year;

        private ImageView ivImage;
        private AutoCompleteTextView actv;
        private TextInputEditText editTextName,editTextRating;
        private TextView tvDate;
        private Button buttonAdd;

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            ivImage = view.findViewById(R.id.iv_image);
            actv = view.findViewById(R.id.actv);
            editTextName = view.findViewById(R.id.editTextName);
            editTextRating = view.findViewById(R.id.editTextRating);
            tvDate = view.findViewById(R.id.tvMovieDate);
            buttonAdd = view.findViewById(R.id.buttonAddTicket);

            startPicker();



            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            year = cal.get(Calendar.YEAR); month = cal.get(Calendar.MONTH); day = cal.get(Calendar.DAY_OF_MONTH);

            tvDate.setText(day+"-"+month+"-"+year);

            final ArrayAdapter<CharSequence> adapter =
                    ArrayAdapter.createFromResource(requireActivity(),R.array.genres,android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            actv.setAdapter(adapter);

            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mGetContent.launch("image/*");
                }
            });

            tvDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date date = new Date();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int y, int m, int d) {
                            year = y;
                            month = m;
                            day = d;
                            tvDate.setText(day+"-"+month+"-"+year);
                        }
                    },year,month,day);
                    datePickerDialog.show();
                }
            });


            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(photoUri == null){
                        showSafeToast(requireActivity(),"Upload an image");
                        return;
                    }

                    String genre = String.valueOf(actv.getText());
                    String mName = String.valueOf(editTextName.getText());
                    String date = String.valueOf(tvDate.getText());
                    String rating = String.valueOf(editTextRating.getText());




                    if(genre.isEmpty() ||
                            mName.isEmpty() ||
                            date.isEmpty() || rating.isEmpty()
                            ){
                        showSafeToast(requireActivity(),"Fill all forms");
                        return;
                    }

                    startUploading(genre,mName,date,rating);
                }
            });

        }


    private void startUploading(String genre, String mName, String date,String rate){

        ProgressDialog dialog = new ProgressDialog(requireActivity());
        dialog.setMessage("Adding movie info...");
        dialog.setTitle("Adding");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String fName = System.currentTimeMillis()+"";

        if(photoUri != null) {

            StorageReference ref = FirebaseStorage.getInstance().getReference().child("admin").child(fName);

            ref.putFile(photoUri)
                    .addOnSuccessListener(taskSnapshot ->
                            ref.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference()
                                                .child("Genres").child(genre);


                                        HashMap<String,Object> map = new HashMap<>();
                                        map.put("name",mName);
                                        map.put("releaseDate",date);
                                        map.put("imgUrl",String.valueOf(uri));

                                        try{
                                            double rat = Double.parseDouble(rate);
                                            map.put("ratings",rat);
                                        }catch (Exception e) {
                                            map.put("ratings", 0);
                                        }


                                        ref1.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                dialog.dismiss();
                                                showSafeToast(requireActivity(), "New movie added");
                                            }
                                        });
                                    })
                                    .addOnFailureListener(e -> {
                                        dialog.dismiss();
                                        showSafeToast(requireActivity(), "Failed");

                                    })
                    )
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                    });
        }

    }

    private void startPicker(){
        mGetContent = registerForActivityResult(
                new ActivityResultContracts.GetContent(), result -> {
                    if (result != null) {
                        photoUri = result;
                        ivImage.setImageURI(photoUri); 
                    }
                }
        );
    }

    private static Toast mToast;
    public static void showSafeToast(Activity activity, String message){
        try{
            if(mToast != null) mToast.cancel();
            mToast = Toast.makeText(activity,message,Toast.LENGTH_LONG);
            mToast.show();
        }
        catch (Exception ignored){}
    }
}
