package ch.zli.clapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FavouriteActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private TextView textView;
    private Spinner spinner;
    Object item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        mQueue = Volley.newRequestQueue(this);
        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Bayern Munich",
                "Atletico Madrid", "Manchester City",
                "Fc Porto", "Real Madrid", "Borussia Moenchengladbach",
                "Liverpool", "Atalanta", "Chelsea", "Sevilla",
                "Paris Saint Germain", "RasenBallsport Leipzig",
                "Borussia Dortmund", "Lazio",
                "Juventus", "Barcelona"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        textView = findViewById(R.id.textView);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position);
                getTeams();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getTeams() {
        String url = "https://livescore-api.com/api-client/fixtures/matches.json?key=1v0EWCf9cr6Ah5rs&secret=qiRLHyczgmwmfBmh6mV2ZbcbDjLUodmP&competition_id=244";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONObject("data").getJSONArray("fixtures");
                        for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(i);
                            if (employee.getString("home_name").equals(item.toString())) {
                                String home = employee.getString("home_name");
                                String date = employee.getString("date");
                                String time = employee.getString("time");
                                String away = employee.getString("away_name");
                                String location = employee.getString("location");
                                textView.setText("");
                                textView.append("\n" + home + " VS " + away + "\n" + date + "\n" + time + "\n" + "Stadium: " + location + "\n" + "---------------------------------------------------------------------------------------");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> error.printStackTrace());
        mQueue.add(request);
    }

    public void groupsIntent(View v) {
        Intent groupsIntent = new Intent(FavouriteActivity.this, TabellenActivity.class);
        startActivity(groupsIntent);
        finish();
    }

    public void upcommingIntent(View v) {
        Intent randomIntent = new Intent(FavouriteActivity.this, UpcomingActivity.class);
        startActivity(randomIntent);
        finish();
    }

    public void favouriteIntent(View v) {
        Intent favouriteIntent = new Intent(FavouriteActivity.this, FavouriteActivity.class);
        startActivity(favouriteIntent);
        finish();
    }

    public void randomIntent(View v) {
        Intent favouriteIntent = new Intent(FavouriteActivity.this, RandomActivity.class);
        startActivity(favouriteIntent);
        finish();
    }
}