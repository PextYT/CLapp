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

public class UpcomingActivity extends AppCompatActivity {
    private TextView homeTeam;
    private RequestQueue mQueue;
    private static final String CHANNEL_ID = "xyzChannel";
    private static final String CHANNEL_Name = "xyz Channel";

    private NotificationManager nManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);
        homeTeam = findViewById(R.id.title);
        mQueue = Volley.newRequestQueue(this);
        this.nManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_Name, NotificationManager.IMPORTANCE_HIGH);
        nManager.createNotificationChannel(channel);
        jsonParse();
    }
    @Override
    protected void onStart() {
        super.onStart();
        showNotification();
    }

    private void jsonParse() {
        String url = "https://livescore-api.com/api-client/fixtures/matches.json?key=1v0EWCf9cr6Ah5rs&secret=qiRLHyczgmwmfBmh6mV2ZbcbDjLUodmP&competition_id=244";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONObject("data").getJSONArray("fixtures");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject employee = jsonArray.getJSONObject(i);
                            String home = employee.getString("home_name");
                            String date = employee.getString("date");
                            String time = employee.getString("time");
                            String away = employee.getString("away_name");
                            String location = employee.getString("location");
                            homeTeam.append("\n" + home + " VS " + away + "\n" + date + "\n" + time + "\n" + "Stadium: " + location +"\n" +  "---------------------------------------------------------------------------------------" );
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> error.printStackTrace());
        mQueue.add(request);
    }

    public void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Wellcome to the APPlication")
                .setContentText("If you have Questions visit championsleague.com")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        nManager.notify(0, builder.build());
    }

    public void groupsIntent(View v) {
         Intent groupsIntent = new Intent(UpcomingActivity.this, TabellenActivity.class);
         startActivity(groupsIntent);
         finish();
    }

    public void randomIntent(View v) {
        Intent randomIntent = new Intent(UpcomingActivity.this, RandomActivity.class);
        startActivity(randomIntent);
        finish();
    }

    public void favouriteIntent(View v) {
        Intent favouriteIntent = new Intent(UpcomingActivity.this, FavouriteActivity.class);
        startActivity(favouriteIntent);
        finish();
    }




}