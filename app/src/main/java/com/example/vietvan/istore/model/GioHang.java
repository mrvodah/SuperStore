package com.example.vietvan.istore.model;

/**
 * Created by VietVan on 27/04/2018.
 */

public class GioHang {
    public int idsp;
    public String tensp, hinhsp;
    public long giasp;
    public int soLuongsp;

    public GioHang(int idsp, String tensp, String hinhsp, long giasp, int soLuongsp) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.hinhsp = hinhsp;
        this.giasp = giasp;
        this.soLuongsp = soLuongsp;
    }
}
