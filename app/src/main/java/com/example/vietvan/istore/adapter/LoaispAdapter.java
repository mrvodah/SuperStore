package com.example.vietvan.istore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vietvan.istore.R;
import com.example.vietvan.istore.model.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by VietVan on 23/04/2018.
 */

public class LoaispAdapter extends BaseAdapter {

    List<Loaisp> listsp;
    Context context;

    public LoaispAdapter(Context context, List<Loaisp> listsp) {
        this.listsp = listsp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listsp.size();
    }

    @Override
    public Object getItem(int i) {
        return listsp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.dong_listview_loaisp, null);

        ImageView imageView = view.findViewById(R.id.imageviewloaisp);
        TextView textView = view.findViewById(R.id.textviewloaisp);

        Loaisp loaisp = listsp.get(i);
        Picasso.get()
                .load(loaisp.hinhAnhloaiSP)
                .into(imageView);
        textView.setText(loaisp.tenloaiSP);

        return view;
    }
}
