package rayan.avik.recyclerviewwithjson;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    List<ItemListPojo> itemList;
    ItemListPojo itemListPojo;
    RecyclerView myRecyclerView;
    RecyclerView.Adapter adapter;

    String DATA_URL;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRecyclerView = (RecyclerView) findViewById(R.id.rcview);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
         loadData();

    }

    private void loadData() {
        DATA_URL ="https://api.myjson.com/bins/1h0v0h";

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, DATA_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(CircularParent.this, response, Toast.LENGTH_LONG).show();
                try {
                    JSONObject reader = new JSONObject(response);
                    JSONObject responseJSON = reader.getJSONObject("response");
                    String resultSuccess = responseJSON.getString("result");
                    if (resultSuccess.equals("success")) {
                        JSONArray jsonarray = responseJSON.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject c = jsonarray.getJSONObject(i);

                            itemListPojo = new ItemListPojo(
                                    c.getString("title"),
                                    c.getString("description"),
                                    c.getString("image")
                            );
                            itemList.add(itemListPojo);
                        }
                        adapter = new MyAdapter(itemList,getApplicationContext());
                        myRecyclerView.setAdapter(adapter);

                        progressDialog.dismiss();
                    }
                } catch (final JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog = ProgressDialog.show(MainActivity.this, "Please Wait...", null, true, true);
        progressDialog.setMessage("Fetching Your Data ! Please wait...!");
        progressDialog.setCancelable(false);
    }
}
