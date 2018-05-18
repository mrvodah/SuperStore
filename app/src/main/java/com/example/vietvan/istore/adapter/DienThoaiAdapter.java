package com.example.vietvan.istore.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vietvan.istore.R;
import com.example.vietvan.istore.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by VietVan on 23/04/2018.
 */

public class DienThoaiAdapter extends BaseAdapter {

    List<SanPham> sanPhamList;

    public DienThoaiAdapter(List<SanPham> sanPhamList) {
        this.sanPhamList = sanPhamList;
    }

    @Override
    public int getCount() {
        return sanPhamList.size();
    }

    @Override
    public Object getItem(int i) {
        return sanPhamList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dong_dienthoai, null);

        ImageView ivHinhAnh = view.findViewById(R.id.imageviewdienthoai);
        TextView tvTenSanPham = view.findViewById(R.id.textviewdienthoai);
        TextView tvGiaSanPham = view.findViewById(R.id.textviewgiadienthoai);
        TextView tvMoTaSanPham = view.findViewById(R.id.textviewmotadienthoai);

        SanPham sanPham = sanPhamList.get(i);

        Picasso.get().load(sanPham.hinhanhsp).into(ivHinhAnh);
        tvTenSanPham.setText(sanPham.tensp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvGiaSanPham.setText("Giá: " + decimalFormat.format(sanPham.giasp) + " Đ");
        tvMoTaSanPham.setText(sanPham.motasp);

        return view;
    }
}
