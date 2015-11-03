package se.she1kh.zappi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String APITOKEN = "594fc16e-c3a4-41e4-9f84-402a5902bda4";

    private ArrayList<Movie_Item> movieItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new RequestTask().execute("http://stackoverflow.com");

    }
    class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {

            try {

               getJson("http://api.myapifilms.com/imdb/top?start=1&end=250&token=");

            } catch (IOException e) {
                //TODO Handle problems..
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..



            GridView gridView = (GridView) findViewById(R.id.gridView);

            gridView.setAdapter(new Grid_Adapter(getApplicationContext(), movieItems));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    Toast.makeText(
                            getApplicationContext(),
                            ((TextView) v.findViewById(R.id.grid_item_label))
                                    .getText(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
    public void getJson(String url_path) throws IOException {
        URL yahoo = null;
        try {
            yahoo = new URL(url_path + APITOKEN);

        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(yc.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            try { 
                String jsonLine = inputLine;
                JSONObject jsonObject = new JSONObject(jsonLine);
                Log.i("HOSSI", "HAHA "+ jsonLine);
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("movies");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject explrObject = jsonArray.getJSONObject(i);
                    Log.i("HOSSIIII", "" + explrObject.get("title"));

                    String title = explrObject.get("title").toString();
                    String year = explrObject.get("year").toString();
                    String urlPoster = explrObject.get("urlPoster").toString();
                    String idIMDB = explrObject.get("idIMDB").toString();
                    String rating = explrObject.get("rating").toString();
                    String ranking = explrObject.get("ranking").toString();

                    Bitmap bitmapPoster = getBitmapFromURL(urlPoster);
                    movieItems.add(new Movie_Item(title,year,bitmapPoster,idIMDB,rating,ranking));

                }

            } catch (JSONException e) {

                e.printStackTrace();

            }

        in.close();
            for (int i = 0; i < movieItems.size(); i++) {

                Movie_Item test = movieItems.get(i);
                Log.i("HOSSIIII", "TES11111 " + test.getTitle());
                Log.i("HOSSIIII", "TEST222222 " + test.getYear());
                Log.i("HOSSIIII", "TEST33333 " + test.getIdIMDB());
                Log.i("HOSSIIII", "TEST444444 " + test.getBitmapPoster());
                Log.i("HOSSIIII", "TEST55555 " + test.getRating());
                Log.i("HOSSIIII", "TEST666666 " + test.getRanking());
                Log.i("HOSSIIII", "TEST666666 " + movieItems.size());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");

            return myBitmap;

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
