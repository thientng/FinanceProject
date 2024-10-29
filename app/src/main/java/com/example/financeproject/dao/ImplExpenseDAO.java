package com.example.financeproject.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.financeproject.Utils;
import com.example.financeproject.entity.Expenses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImplExpenseDAO implements IExpenseDAO {
    private Context mCtx;
    private SQLiteDatabase mDb;

    public ImplExpenseDAO(Context mCtx) {
        this.mCtx = mCtx;
        // Khởi tạo đối tượng DatabaseHelper
        DatabaseHelper helper = new DatabaseHelper(mCtx);
        mDb = helper.getWritableDatabase();
    }

    @Override
    @SuppressLint("Range")
    public List<Expenses> selectAll() {
        String sql = "SELECT * FROM expenses";
        Cursor c = mDb.rawQuery(sql, null);

        List<Expenses> lstItem = new ArrayList<>();
        try {
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndex("id"));
                String description = c.getString(c.getColumnIndex("description"));
                float amount = c.getFloat(c.getColumnIndex("amount"));

                SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
                Date createat = fmtDate.parse(c.getString(c.getColumnIndex("createat")));
                String category = c.getString(c.getColumnIndex("category"));

                Expenses exp = new Expenses(id,description,amount,createat,category);

                lstItem.add(exp);
            }
            return lstItem;
        } catch (ParseException e) {
            Log.e("Hệ thống lỗi : ", e.getMessage());
            e.printStackTrace();

        }
        return null;
    }

    @Override
    @SuppressLint("Range")
    public List<Expenses> selectSort() {
        String sql = "SELECT * FROM expenses ORDER BY createat ASC";
        Cursor c = mDb.rawQuery(sql, null);

        List<Expenses> lstItem = new ArrayList<>();
        try {
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndex("id"));
                String description = c.getString(c.getColumnIndex("description"));
                float amount = c.getFloat(c.getColumnIndex("amount"));

                SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
                Date createat = fmtDate.parse(c.getString(c.getColumnIndex("createat")));
                String category = c.getString(c.getColumnIndex("category"));

                Expenses exp = new Expenses(id,description,amount,createat,category);

                lstItem.add(exp);
            }
            return lstItem;
        } catch (ParseException e) {
            Log.e("Hệ thống lỗi : ", e.getMessage());
            e.printStackTrace();

        }
        return null;
    }

    @Override
    @SuppressLint("Range")
    public Expenses selectById(int id) {
        String sql  = "SELECT * FROM expenses WHERE id = ?";
        String idStu = String.valueOf(id);
        String args[] = {idStu}; // Mảng tham số nếu dùng nhiều tham số thì cho vào đây
        Cursor c = mDb.rawQuery(sql,args);

        try {
            while (c.moveToNext()) {
                int idd = c.getInt(c.getColumnIndex("id"));
                String description = c.getString(c.getColumnIndex("description"));
                float amount = c.getFloat(c.getColumnIndex("amount"));

                SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
                Date createat = fmtDate.parse(c.getString(c.getColumnIndex("createat")));
                String category = c.getString(c.getColumnIndex("category"));

                Expenses exp = new Expenses(idd,description,amount,createat,category);

                return exp;
            }
        } catch (ParseException e) {
            Log.e("Hệ thống lỗi : ", e.getMessage());
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public boolean insert(Expenses exp) {
        ContentValues cv = new ContentValues();
        cv.put("description",exp.getDescription());
        cv.put("amount",exp.getAmount());
        cv.put("createat", Utils.convertDateToString(exp.getCreateat()));
        cv.put("category",exp.getCategory());
        long newID = mDb.insert("expenses",null, cv);
        if(newID > 0 ){
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Expenses exp) {
        ContentValues cv = new ContentValues();
        cv.put("description",exp.getDescription());
        cv.put("amount",exp.getAmount());
        cv.put("createat", Utils.convertDateToString(exp.getCreateat()));
        cv.put("category",exp.getCategory());

        String whereClauses = "id = ?";
        String whereArgs[] = {String.valueOf(exp.getId())} ;

        int rowUpdate = mDb.update("expenses", cv,whereClauses,whereArgs);
        // kiểm tra xem có cập nhật thành công không
        if(rowUpdate > 0 ){
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        // Tham số truyền vào
        String whereClauses = "id = ?";
        String whereArgs[] = {String.valueOf(id)} ;

        int rowDel = mDb.delete("expenses", whereClauses,whereArgs);
        // kiểm tra xem có cập nhật thành công không
        if(rowDel > 0 ){
            return true;
        }
        return false;
    }
}
