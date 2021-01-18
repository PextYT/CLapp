package ch.zli.clapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.tbouron.shakedetector.library.ShakeDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.Random;

public class RandomActivity extends AppCompatActivity {
    private SensorManager mSensorManager;
    private float mAccelCurrent;
    private float mAccelLast;
    private float shake;
    private TextView textShake;
    private RequestQueue mQueue;
    private String[] groups = {"A", "B", "C", "D", "E", "F", "G", "H"};
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        /*mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;*/
        mQueue = Volley.newRequestQueue(this);
        textShake = findViewById(R.id.shaketext);
        ShakeDetector.create(this, () -> {
            textShake.setText("");
            Random random = new Random();
            int groupInt = random.nextInt(7);
            int placeInt = random.nextInt(3);
            String url = "https://livescore-api.com/api-client/leagues/table.json?key=1v0EWCf9cr6Ah5rs&secret=qiRLHyczgmwmfBmh6mV2ZbcbDjLUodmP&competition_id=244&group=" + groups[groupInt];
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            JSONArray jsonArray = response.getJSONObject("data").getJSONArray("table");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(i);
                                if (employee.getInt("rank") == placeInt) {
                                    String home = employee.getString("name");
                                    int name = employee.getInt("matches");
                                    int won = employee.getInt("won");
                                    int drawn = employee.getInt("drawn");
                                    int lost = employee.getInt("lost");
                                    int points = employee.getInt("points");
                                    int rank = employee.getInt("rank");
                                    textShake.append(rank + ". " + home + "\n " + "M: " + name + "W: " + won + " " + "D: " + drawn + "L: " + lost + "P: " + points + "\n\n");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> error.printStackTrace());
            mQueue.add(request);
            ShakeDetector.stop();
        });
    }

    public void startAgain(View v) {
        ShakeDetector.start();
    }

    public void groupsIntent(View v) {
        Intent groupsIntent = new Intent(RandomActivity.this, TabellenActivity.class);
        startActivity(groupsIntent);
        finish();
    }

    public void upcommingIntent(View v) {
        Intent randomIntent = new Intent(RandomActivity.this, UpcomingActivity.class);
        startActivity(randomIntent);
        finish();
    }

    public void favouriteIntent(View v) {
        Intent favouriteIntent = new Intent(RandomActivity.this, FavouriteActivity.class);
        startActivity(favouriteIntent);
        finish();
    }
}