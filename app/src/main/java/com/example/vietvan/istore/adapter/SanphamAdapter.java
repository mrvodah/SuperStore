package com.example.vietvan.istore.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vietvan.istore.R;
import com.example.vietvan.istore.activity.ChiTietSanPhamActivity;
import com.example.vietvan.istore.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by VietVan on 23/04/2018.
 */

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.SanPhamViewHolder> {

    Context context;
    List<SanPham> list;

    public SanphamAdapter(Context context, List<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public SanPhamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat, parent, false);

        return new SanPhamViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SanPhamViewHolder holder, final int position) {
        holder.setData(list.get(position));
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham", list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SanPhamViewHolder extends RecyclerView.ViewHolder{

        ImageView hinhanhsp;
        TextView tensp;
        TextView giasp;
        View v;

        public SanPhamViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            hinhanhsp = itemView.findViewById(R.id.imageviewsanpham);
            tensp = itemView.findViewById(R.id.textviewtensanpham);
            giasp = itemView.findViewById(R.id.textviewgiasanpham);
        }

        public void setData(SanPham sanPham){
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            Picasso.get().load(sanPham.hinhanhsp).into(hinhanhsp);
            tensp.setText(sanPham.tensp);
            giasp.setText(decimalFormat.format(sanPham.giasp) + " Đ");
        }

    }

}
