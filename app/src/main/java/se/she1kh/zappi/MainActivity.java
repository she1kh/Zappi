package se.she1kh.zappi;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageButton imgbtn;
    private static String APITOKEN = "594fc16e-c3a4-41e4-9f84-402a5902bda4";

    private ArrayList<Movie_Item> movieItems = new ArrayList<>();


    static final String[] numbers = new String[] {
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};
    static final String[] MOBILE_OS = new String[] {
            "Android", "iOS","Windows", "Blackberry" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new RequestTask().execute("http://stackoverflow.com");




//        GridView gridView = (GridView) findViewById(R.id.gridView);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, numbers);
//
//        gridView.setAdapter(adapter);
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                Toast.makeText(getApplicationContext(),
//                        ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
//            }
//        });


    }
    class RequestTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... uri) {

            try {
               getJson();

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

                    Fragment somefrag = new Info_Fragment();
                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                            .show(somefrag)
                            .commit();

                }
            });
        }
    }
    public void getJson() throws IOException {
        URL yahoo = null;
        try {
            yahoo = new URL("http://api.myapifilms.com/imdb/top?token=" + APITOKEN);

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

                    movieItems.add(new Movie_Item(title,year,urlPoster,idIMDB,rating,ranking));

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
                Log.i("HOSSIIII", "TEST444444 " + test.getUrlPoster());
                Log.i("HOSSIIII", "TEST55555 " + test.getRating());
                Log.i("HOSSIIII", "TEST666666 " + test.getRanking());
                Log.i("HOSSIIII", "TEST666666 " + movieItems.size());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
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
