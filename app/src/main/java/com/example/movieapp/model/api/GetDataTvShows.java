package com.example.movieapp.model.api;

import android.os.AsyncTask;

import com.example.movieapp.model.CategoryItem;
import com.example.movieapp.api.MovieFetchedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetDataTvShows extends AsyncTask<String,String,String>{

    private final String myUrl;
    private final MovieFetchedListener listener;

    public GetDataTvShows(String myUrl, MovieFetchedListener listener) {
        this.myUrl = myUrl;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {

        StringBuilder current = new StringBuilder();
        try{
            URL url;
            HttpURLConnection urlConnection;
            try{
                url = new URL(myUrl);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();

                while(data != -1){
                    current.append((char) data);
                    data = reader.read();
                }

                urlConnection.disconnect();
                return current.toString();

            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return current.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try{
            JSONObject object = new JSONObject(s);
            //JSONArray array = object.getJSONArray("moviz");
            JSONArray array = object.getJSONArray("results");

            List<CategoryItem> items = new ArrayList<>();

            for(int i=0; i<array.length(); i++){
                JSONObject object1 = array.getJSONObject(i);


                items.add(new CategoryItem(
                                object1.getInt("id"),
                                object1.getString("original_name"),
                                object1.getString("poster_path"),
                                object1.getString("overview"),
                                object1.getString("first_air_date"),
                                object1.getDouble("vote_average")
                        )
                );


            }

            listener.onMovieFetched(items);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
