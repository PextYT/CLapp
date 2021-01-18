package ch.zli.clapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicInteger;

public class TabellenActivity extends AppCompatActivity {
    private TextView tableText;
    private RequestQueue mQueue;
    private String[] groups = {"A", "B", "C", "D", "E", "F", "G", "H"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabellen);
        tableText = findViewById(R.id.tableText);
        mQueue = Volley.newRequestQueue(this);
        getGroups();
    }

    private void getGroups() {
        tableText.append("\n\n" + "Legend: "+ "\n" + "M=Matches " + "\n" + "W=Games Won " +"\n" + "D=Games Draw " + "\n" +"L=Games Lost " +"\n" + "P=Points" + "\n" + "---------------------------------------------------------------------------------------");
        for (int y = 0; y < groups.length; y++) {
            int index = y;
            String url = "https://livescore-api.com/api-client/leagues/table.json?key=1v0EWCf9cr6Ah5rs&secret=qiRLHyczgmwmfBmh6mV2ZbcbDjLUodmP&competition_id=244&group=" + groups[y];
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            JSONArray jsonArray = response.getJSONObject("data").getJSONArray("table");
                            tableText.append("\n" + "Group " + groups[index] + "\n\n");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(i);
                                String name = employee.getString("name");
                                int matches = employee.getInt("matches");
                                int won = employee.getInt("won");
                                int drawn = employee.getInt("drawn");
                                int lost = employee.getInt("lost");
                                int points = employee.getInt("points");
                                int rank = employee.getInt("rank");
                                tableText.append(rank + ". " + name + " = " + " M: " + matches + " W: " + won + " " + " D: " + drawn + " L: " + lost + " P: " + points + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, Throwable::printStackTrace);
            mQueue.add(request);
        }
    }


    public void groupsIntent(View v) {
        Intent groupsIntent = new Intent(TabellenActivity.this, TabellenActivity.class);
        startActivity(groupsIntent);
        finish();
    }

    public void upcommingIntent(View v) {
        Intent randomIntent = new Intent(TabellenActivity.this, UpcomingActivity.class);
        startActivity(randomIntent);
        finish();
    }

    public void favouriteIntent(View v) {
        Intent favouriteIntent = new Intent(TabellenActivity.this, FavouriteActivity.class);
        startActivity(favouriteIntent);
        finish();
    }

    public void randomIntent(View v) {
        Intent favouriteIntent = new Intent(TabellenActivity.this, RandomActivity.class);
        startActivity(favouriteIntent);
        finish();
    }
}