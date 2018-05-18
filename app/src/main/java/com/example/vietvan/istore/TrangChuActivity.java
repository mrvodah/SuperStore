package com.example.vietvan.istore;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.vietvan.istore.activity.DienThoaiActivity;
import com.example.vietvan.istore.activity.GioHangActivity;
import com.example.vietvan.istore.activity.LapTopActivity;
import com.example.vietvan.istore.activity.LienHeActivity;
import com.example.vietvan.istore.activity.ThongTinActivity;
import com.example.vietvan.istore.adapter.LoaispAdapter;
import com.example.vietvan.istore.adapter.SanphamAdapter;
import com.example.vietvan.istore.model.GioHang;
import com.example.vietvan.istore.model.Loaisp;
import com.example.vietvan.istore.model.SanPham;
import com.example.vietvan.istore.util.CheckConnection;
import com.example.vietvan.istore.util.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrangChuActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    LoaispAdapter loaispAdapter;
    SanphamAdapter sanphamAdapter;
    List<Loaisp> mangloaisp;
    List<SanPham> mangsp;
    public static List<GioHang> manggiohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Anhxa();

        if(CheckConnection.HaveNetworkConnection(this)){
            ActionBar();
            ActionViewFlipper();
            GetDuLieuLoaisp();
            GetDuLieuSanPham();
            CatchOnItemListView();
        }
        else{
            CheckConnection.ShowToast_Short(this, "Check your Connection!");
            finish();
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

    private void CatchOnItemListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if(CheckConnection.HaveNetworkConnection(TrangChuActivity.this)){
                            Intent intent = new Intent();
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.ShowToast_Short(TrangChuActivity.this, "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.HaveNetworkConnection(TrangChuActivity.this)){
                            Intent intent = new Intent(TrangChuActivity.this, DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham", mangloaisp.get(i).id);
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.ShowToast_Short(TrangChuActivity.this, "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.HaveNetworkConnection(TrangChuActivity.this)){
                            Intent intent = new Intent(TrangChuActivity.this, LapTopActivity.class);
                            intent.putExtra("idloaisanpham", mangloaisp.get(i).id);
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.ShowToast_Short(TrangChuActivity.this, "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.HaveNetworkConnection(TrangChuActivity.this)){
                            Intent intent = new Intent(TrangChuActivity.this, LienHeActivity.class);
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.ShowToast_Short(TrangChuActivity.this, "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.HaveNetworkConnection(TrangChuActivity.this)){
                            Intent intent = new Intent(TrangChuActivity.this, ThongTinActivity.class);
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.ShowToast_Short(TrangChuActivity.this, "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDuLieuSanPham() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanSanPham, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if(response != null){

                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            mangsp.add(new SanPham(jsonObject.getInt("id"),
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
                    sanphamAdapter.notifyDataSetChanged();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(TrangChuActivity.this, error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDuLieuLoaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanLoaisp, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                if(response != null){
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            mangloaisp.add(new Loaisp(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("tenloaisanpham"),
                                    jsonObject.getString("hinhanhloaisanpham")
                            ));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangloaisp.add(3, new Loaisp(
                            1,
                            "Liên hệ",
                            "https://cdn1.iconfinder.com/data/icons/mix-color-3/502/Untitled-12-512.png"
                    ));
                    mangloaisp.add(4, new Loaisp(
                            2,
                            "Thông tin",
                            "https://cdn2.iconfinder.com/data/icons/perfect-flat-icons-2/512/User_info_man_male_profile_information.png"
                    ));
                    loaispAdapter.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(TrangChuActivity.this, error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("http://freelancethietke.vn/UploadFile/Content/images/thiet-ke-banner-co-vai-tro-quan-trong-trong-quang-cao.jpg");
        mangquangcao.add("https://renanlopes.me/wp-content/uploads/2017/11/desktop-publishing-software-allows-you-to-create-brochures-and-flyers-offer-design-and-print-flyers-business-cards-labels-and-screen-template.jpg");
        mangquangcao.add("https://vietadsgroup.vn/Uploads/images/banner-hoc-thu-021.jpg");
        mangquangcao.add("https://smarttrain.edu.vn/assets/uploads/2017/04/body-content-website.jpg");
        mangquangcao.add("http://4.bp.blogspot.com/-_MGGNaFxdzA/VKiIB-qeBoI/AAAAAAAAAPM/kiLsNj48ExE/s1600/BANNER-NGANG-ton-vang.jpg");

        for(int i=0;i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(this);
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);

        Animation animation_slide_in = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);

    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbar);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerView = findViewById(R.id.recycleView);
        navigationView = findViewById(R.id.navigationView);
        listView = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerLayout);

        mangloaisp = new ArrayList<>();
        mangloaisp.add(0, new Loaisp(0, "Trang Chủ", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQK2DJ8ZqbV0plNAZb1dsnT8Knot2mHa2UyPvbjQXxOQxZZPllI"));
        loaispAdapter = new LoaispAdapter(this, mangloaisp);
        listView.setAdapter(loaispAdapter);

        mangsp = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(this, mangsp);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(sanphamAdapter);

        if(manggiohang != null){

        }
        else{
            manggiohang =  new ArrayList<>();
        }
    }
}
