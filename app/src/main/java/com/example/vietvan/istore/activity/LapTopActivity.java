package com.example.vietvan.istore.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vietvan.istore.R;
import com.example.vietvan.istore.adapter.DienThoaiAdapter;
import com.example.vietvan.istore.model.SanPham;
import com.example.vietvan.istore.util.CheckConnection;
import com.example.vietvan.istore.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LapTopActivity extends AppCompatActivity {

    Toolbar toolbardt;
    ListView listViewdt;
    List<SanPham> list;
    DienThoaiAdapter dienThoaiAdapter;
    int iddt = 0;
    int page = 1;
    View footerView;
    boolean isLoading = false;
    boolean limitdata = false;
    mHandle myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap_top);

        AnhXa();

        if(CheckConnection.HaveNetworkConnection(this)){
            ActionToolBar();
            GetIdLoaiSP();
            GetData(page);
            LoadMoreData();
        }
        else{
            CheckConnection.ShowToast_Short(this, "Check your Connection!");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                startActivity(new Intent(this, GioHangActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadMoreData() {
        listViewdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LapTopActivity.this, ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham", list.get(i));
                startActivity(intent);
            }
        });

        listViewdt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if(firstItem + visibleItem == totalItem && totalItem != 0 && !isLoading && !limitdata){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String duongdan = Server.duongdanDienThoai + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                duongdan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response != null && response.length() != 2){
                            listViewdt.removeFooterView(footerView);
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for(int i=0;i<jsonArray.length();i++){
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        list.add(new SanPham(
                                                jsonObject.getInt("id"),
                                                jsonObject.getString("tensp"),
                                                jsonObject.getInt("giasp"),
                                                jsonObject.getString("hinhanhsp"),
                                                jsonObject.getString("motasp"),
                                                jsonObject.getInt("idlsp")
                                        ));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            dienThoaiAdapter.notifyDataSetChanged();

                        }
                        else{
                            limitdata = true;
                            listViewdt.removeFooterView(footerView);
                            CheckConnection.ShowToast_Short(LapTopActivity.this, "Out of Data");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CheckConnection.ShowToast_Short(LapTopActivity.this, error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> parram = new HashMap<>();
                parram.put("idloaisanpham", String.valueOf(iddt));

                return parram;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbardt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetIdLoaiSP() {
        iddt = getIntent().getIntExtra("idloaisanpham", 0);
    }

    private void AnhXa() {
        toolbardt = findViewById(R.id.toolbarlaptop);

        list = new ArrayList<>();
        dienThoaiAdapter = new DienThoaiAdapter(list);
        listViewdt = findViewById(R.id.listviewlaptop);
        listViewdt.setAdapter(dienThoaiAdapter);

        myHandler = new mHandle();
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
    }

    public class mHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    listViewdt.addFooterView(footerView);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = myHandler.obtainMessage(1);
            myHandler.sendMessage(message);
            super.run();
        }
    }

}
