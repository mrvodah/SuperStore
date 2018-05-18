package com.example.vietvan.istore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vietvan.istore.TrangChuActivity;
import com.example.vietvan.istore.R;
import com.example.vietvan.istore.model.GioHang;
import com.example.vietvan.istore.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    Toolbar toolbarChiTiet;
    ImageView imgChitiet;
    TextView tvTen, tvGia, tvMota;
    Spinner spinner;
    Button btnDatmua;
    SanPham sanPham;
    boolean isexists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        AnhXa();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();

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

    private void CatchEventSpinner() {
        Integer[] integers = new Integer[]{1,2,3,4,5,6,7,8,9};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, integers);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {
        sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        tvTen.setText(sanPham.tensp);
        tvMota.setText(sanPham.motasp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvGia.setText("Giá: " + decimalFormat.format(sanPham.giasp) + " Đ");
        Picasso.get().load(sanPham.hinhanhsp).into(imgChitiet);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbarChiTiet = findViewById(R.id.toolbarchitiet);
        imgChitiet = findViewById(R.id.imageviewchitiet);
        tvGia = findViewById(R.id.textviewgiachitiet);
        tvTen = findViewById(R.id.textviewtenchitiet);
        tvMota = findViewById(R.id.textviewmotachitiet);
        spinner = findViewById(R.id.spinner);
        btnDatmua = findViewById(R.id.buttondatmua);

        btnDatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TrangChuActivity.manggiohang.size() > 0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    for(int i = 0; i< TrangChuActivity.manggiohang.size(); i++){
                        if(TrangChuActivity.manggiohang.get(i).idsp == sanPham.id){
                            TrangChuActivity.manggiohang.get(i).soLuongsp += sl;
                            isexists = true;
                        }
                    }

                    if(!isexists){
                        long gia = sanPham.giasp;
                        TrangChuActivity.manggiohang.add(new GioHang(
                                sanPham.id,
                                sanPham.tensp,
                                sanPham.hinhanhsp,
                                gia,
                                sl
                        ));
                    }
                }
                else{
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    long gia = sanPham.giasp;
                    TrangChuActivity.manggiohang.add(new GioHang(
                            sanPham.id,
                            sanPham.tensp,
                            sanPham.hinhanhsp,
                            gia,
                            sl
                    ));
                }
                Log.d("TAG", "onClick: " + TrangChuActivity.manggiohang.size());
                Intent intent = new Intent(ChiTietSanPhamActivity.this, GioHangActivity.class);
                startActivity(intent);

            }
        });
    }
}
