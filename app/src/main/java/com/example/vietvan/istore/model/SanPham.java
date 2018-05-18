package com.example.vietvan.istore.model;

import java.io.Serializable;

/**
 * Created by VietVan on 23/04/2018.
 */

public class SanPham implements Serializable{
    public int id;
    public String tensp;
    public int giasp;
    public String hinhanhsp, motasp;
    public int idloaisp;

    public SanPham(int id, String tensp, int giasp, String hinhanhsp, String motasp, int idloaisp) {
        this.id = id;
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhanhsp = hinhanhsp;
        this.motasp = motasp;
        this.idloaisp = idloaisp;
    }
}
