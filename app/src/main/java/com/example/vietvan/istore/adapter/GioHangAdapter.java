package com.example.vietvan.istore.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vietvan.istore.TrangChuActivity;
import com.example.vietvan.istore.R;
import com.example.vietvan.istore.activity.GioHangActivity;
import com.example.vietvan.istore.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by VietVan on 27/04/2018.
 */

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.GioHangViewHolder> {

    List<GioHang> list;
    Context context;
    int sl;

    public GioHangAdapter(Context context, List<GioHang> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public GioHangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_giohang, parent, false);

        return new GioHangViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final GioHangViewHolder holder, final int position) {
        holder.setData(list.get(position));
        holder.v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog builder = new AlertDialog.Builder(context)
                        .setTitle("Thông báo!")
                        .setMessage("Bạn có muốn xóa sản phẩm?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //list.remove(position);
                                TrangChuActivity.manggiohang.remove(position);
                                notifyDataSetChanged();
                                GioHangActivity.tinhTien();
                                GioHangActivity.kiemtra();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
                return true;
            }
        });
        holder.btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sl = TrangChuActivity.manggiohang.get(position).soLuongsp;
                if(sl > 0){
                    sl--;
                    holder.setSoLuong(sl);
                    TrangChuActivity.manggiohang.get(position).soLuongsp = sl;
                    GioHangActivity.tinhTien();
                }
            }
        });

        holder.btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sl = TrangChuActivity.manggiohang.get(position).soLuongsp;
                if(sl < 15){
                    sl++;
                    holder.setSoLuong(sl);
                    TrangChuActivity.manggiohang.get(position).soLuongsp = sl;
                    GioHangActivity.tinhTien();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class GioHangViewHolder extends RecyclerView.ViewHolder{

        ImageView ivHinhAnh;
        TextView tvTenHang, tvGiaHang, tvSoLuong;
        Button btnTang, btnGiam;
        View v;

        public GioHangViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            ivHinhAnh = itemView.findViewById(R.id.imageviewgiohang);
            tvTenHang = itemView.findViewById(R.id.textviewtenhang);
            tvGiaHang = itemView.findViewById(R.id.textviewgiahang);
            tvSoLuong = itemView.findViewById(R.id.textviewsoluonghang);
            btnTang = itemView.findViewById(R.id.buttontang);
            btnGiam = itemView.findViewById(R.id.buttongiam);
        }

        public void setSoLuong(int sl){
            tvSoLuong.setText(sl + "");
        }

        public void setData(GioHang gioHang){
            Picasso.get().load(gioHang.hinhsp).into(ivHinhAnh);
            tvTenHang.setText(gioHang.tensp);
            tvGiaHang.setText("Giá: " + new DecimalFormat("###,###,###").format(gioHang.giasp) + " Đ");
            tvSoLuong.setText(gioHang.soLuongsp + "");
        }
    }

}
