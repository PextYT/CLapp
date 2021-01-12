package ch.zli.clapp;

import androidx.appcompat.app.AppCompatActivity;

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
    private TextView mTextViewResult;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);
        mTextViewResult = findViewById(R.id.text);
        mQueue = Volley.newRequestQueue(this);
        jsonParse();
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
                            String away = employee.getString("away_name");
                            String location = employee.getString("location");
                            mTextViewResult.append(home + " VS " + away + "\n"+ "Stadium: " + location + "\n\n");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> error.printStackTrace());
        mQueue.add(request);
    }

    public void groupsIntent(View v) {
         Intent homeIntent = new Intent(UpcomingActivity.this, TabellenActivity.class);
         startActivity(homeIntent);
         finish();
    }

}