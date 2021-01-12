package ch.zli.clapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TabellenActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabellen);
        mTextViewResult = findViewById(R.id.text);
        mQueue = Volley.newRequestQueue(this);
        getGroups();
    }

    private void getGroups() {
        String url = "https://livescore-api.com/api-client/leagues/table.json?key=1v0EWCf9cr6Ah5rs&secret=qiRLHyczgmwmfBmh6mV2ZbcbDjLUodmP&competition_id=244&group=A";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONObject("data").getJSONArray("fixtures");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject employee = jsonArray.getJSONObject(i);
                            String home = employee.getString("name");
                            int name = employee.getInt("matches");
                            int won = employee.getInt("won");
                            int drawn = employee.getInt("drawn");
                            int lost = employee.getInt("lost");
                            int points = employee.getInt("points");
                            mTextViewResult.append(home + "\n " + "M: " + name +"W: " + won + " "+ "U: " + drawn + "N: " + lost + "P: " + points + "\n\n");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> error.printStackTrace());
        mQueue.add(request);
    }
}