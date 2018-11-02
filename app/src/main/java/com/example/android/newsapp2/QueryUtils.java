package com.example.android.newsapp2;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static List<Movie> fetchMovieData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Movie> movies = extractFeatureFromJson(jsonResponse);
        return movies;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the newsStory JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();


    }


    private static List<Movie> extractFeatureFromJson(String movieJSON) {

        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }

        List<Movie> movies = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(movieJSON);

            JSONObject responseJSONObject = baseJsonResponse.getJSONObject("response");

            JSONArray movieArray = responseJSONObject.getJSONArray("results");

            for (int i = 0; i < movieArray.length(); i++) {


                JSONObject currentMovie = movieArray.getJSONObject(i);

                String title = currentMovie.getString("webTitle");

                String section = currentMovie.getString("sectionName");

                String publicationDate = currentMovie.getString("webPublicationDate");

                String url = currentMovie.getString("webUrl");
                
                String author = "";
                if (currentMovie.has("fields")) {
                    JSONObject fields = currentMovie.getJSONObject("fields");
                    author = fields.getString("byline");
                }

                Movie movie = new Movie(section, author, title, publicationDate, url);

                movies.add(movie);
            }

        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the movie JSON results", e);
        }

        return movies;
    }


}
