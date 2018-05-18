package com.example.vietvan.istore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vietvan.istore.R;
import com.example.vietvan.istore.TrangChuActivity;
import com.example.vietvan.istore.model.GioHang;
import com.example.vietvan.istore.util.CheckConnection;
import com.example.vietvan.istore.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThongTinKhachHangActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    @BindView(R.id.edten)
    EditText edten;
    @BindView(R.id.edsdt)
    EditText edsdt;
    @BindView(R.id.edemail)
    EditText edemail;
    @BindView(R.id.btnhuy)
    Button btnhuy;
    @BindView(R.id.btnxacnhan)
    Button btnxacnhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);
        ButterKnife.bind(this);

        if (CheckConnection.HaveNetworkConnection(this)) {

            CatchButtonCLick();

        } else {

        }

    }

    private void CatchButtonCLick() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(edten.getText().toString()) &&
                        !TextUtils.isEmpty(edsdt.getText().toString()) &&
                        !TextUtils.isEmpty(edemail.getText().toString())){
                    GuiDuLieu();
                }
                else
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Vui lòng kiểm tra lại dữ liệu");

            }
        });

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GuiDuLieu() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Server.duongdanKhachHang,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String madonhang) {

                        if(Integer.parseInt(madonhang) > 0){
                            RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                            StringRequest stringRequest = new StringRequest(
                                    Request.Method.POST,
                                    Server.duongdanchitiethoadon,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            if(response.equals("ThanhCong")){
                                                TrangChuActivity.manggiohang.clear();
                                                CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn đã thêm dữ liệu giỏ hàng thành công!");
                                                startActivity(new Intent(getApplicationContext(), TrangChuActivity.class));
                                                finish();
                                            }
                                            else{
                                                CheckConnection.ShowToast_Short(getApplicationContext(), "Thêm dữ liệu giỏ hàng thất bại!");
                                            }

                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "onErrorResponse: 1");
                                            CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
                                        }
                                    }

                            ){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    List<GioHang> list = TrangChuActivity.manggiohang;
                                    JSONArray jsonArray = new JSONArray();
                                    for(int i=0;i<list.size();i++){
                                        JSONObject jsonObject = new JSONObject();
                                        try {
                                            jsonObject.put("madonhang", madonhang);
                                            jsonObject.put("masanpham", list.get(i).idsp);
                                            jsonObject.put("tensanpham", list.get(i).tensp);
                                            jsonObject.put("giasanpham", list.get(i).giasp);
                                            jsonObject.put("soluongsanpham", list.get(i).soLuongsp);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        jsonArray.put(jsonObject);
                                    }
                                    Log.d(TAG, "getParams: " + jsonArray.toString());
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("json", jsonArray.toString());

                                    return hashMap;
                                }
                            };
                            requestQueue1.add(stringRequest);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG , "onErrorResponse: 2");
                        CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("tenkhachhang", edten.getText().toString());
                hashMap.put("sodienthoai", edsdt.getText().toString());
                hashMap.put("email", edemail.getText().toString());

                return hashMap;
            }
        };
        requestQueue.add(request);
    }

}
