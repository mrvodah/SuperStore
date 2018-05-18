package com.example.vietvan.istore.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vietvan.istore.R;
import com.example.vietvan.istore.activity.LapTopActivity;
import com.example.vietvan.istore.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by VietVan on 25/04/2018.
 */

public class LaptopAdapter extends BaseAdapter {

    public List<SanPham> list;

    public LaptopAdapter(List<SanPham> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dong_laptop, null);

        ImageView ivHinhAnh = view.findViewById(R.id.imageviewlaptop);
        TextView tvTenSanPham = view.findViewById(R.id.textviewlaptop);
        TextView tvGiaSanPham = view.findViewById(R.id.textviewgialaptop);
        TextView tvMoTaSanPham = view.findViewById(R.id.textviewmotalaptop);

        SanPham sanPham = list.get(i);

        Picasso.get().load(sanPham.hinhanhsp).into(ivHinhAnh);
        tvTenSanPham.setText(sanPham.tensp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvGiaSanPham.setText("Giá: " + decimalFormat.format(sanPham.giasp) + " Đ");
        tvMoTaSanPham.setText(sanPham.motasp);

        return view;
    }
}
