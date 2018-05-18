package com.example.vietvan.istore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vietvan.istore.TrangChuActivity;
import com.example.vietvan.istore.R;
import com.example.vietvan.istore.adapter.GioHangAdapter;
import com.example.vietvan.istore.model.GioHang;
import com.example.vietvan.istore.util.CheckConnection;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GioHangActivity extends AppCompatActivity {


    static RecyclerView recycleViewgiohang;
    static TextView textviewtongtien;
    @BindView(R.id.toolbargiohang)
    Toolbar toolbargiohang;
    @BindView(R.id.buttonthanhtoan)
    Button buttonthanhtoan;
    @BindView(R.id.buttonmuahang)
    Button buttonmuahang;

    GioHangAdapter gioHangAdapter;
    public static List<GioHang> list;
    static TextView textviewtrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        ButterKnife.bind(this);

        AnhXa();
        ActionBar();

    }

    private void ActionBar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static void tinhTien() {
        long sum = 0;
        for (GioHang gioHang : list)
            sum += gioHang.giasp * gioHang.soLuongsp;

        textviewtongtien.setText(new DecimalFormat("###,###,###").format(sum) + " Đ");
    }

    public static void kiemtra(){
        if(TrangChuActivity.manggiohang.size() > 0){
            textviewtrong.setVisibility(View.INVISIBLE);
            recycleViewgiohang.setVisibility(View.VISIBLE);
        }
        else{
            textviewtrong.setVisibility(View.VISIBLE);
            recycleViewgiohang.setVisibility(View.INVISIBLE);
        }
    }

    private void AnhXa() {
        textviewtongtien = findViewById(R.id.textviewtongtien);
        recycleViewgiohang = findViewById(R.id.recycleViewgiohang);
        textviewtrong = findViewById(R.id.textviewtrong);
        kiemtra();
        if (TrangChuActivity.manggiohang.size() > 0) {
            list = TrangChuActivity.manggiohang;
            gioHangAdapter = new GioHangAdapter(this, list);
            recycleViewgiohang.setLayoutManager(new LinearLayoutManager(this));
            recycleViewgiohang.setAdapter(gioHangAdapter);
            tinhTien();
        }

        buttonmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GioHangActivity.this, TrangChuActivity.class));
                finish();
            }
        });

        buttonthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TrangChuActivity.manggiohang.size() > 0){
                    Intent intent = new Intent(getApplicationContext(), ThongTinKhachHangActivity.class);
                    startActivity(intent);
                }
                else{
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Giỏ hàng trống!");
                }

            }
        });

    }
}
