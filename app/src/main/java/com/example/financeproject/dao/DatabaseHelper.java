package com.example.financeproject.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "finance.sqlite";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE expenses (\n" +
                "    id          INTEGER PRIMARY KEY,\n" +
                "    description VARCHAR NOT NULL,\n" +
                "    amount      REAL    DEFAULT (0),\n" +
                "    createat    DATE    NOT NULL,\n" +
                "    category    VARCHAR NOT NULL\n" +
                ");\n";

        sqLiteDatabase.execSQL(sql);

        sqLiteDatabase.execSQL("INSERT INTO expenses (id, description, amount, createat, category) " +
                "VALUES (1, 'Mua thực phẩm', 1000000, '2024-01-02', 'Thực phẩm');");
        sqLiteDatabase.execSQL("INSERT INTO expenses (id, description, amount, createat, category) " +
                "VALUES (2, 'Điện thoại di động', 500000, '2024-03-20', 'Công nghệ');");
        sqLiteDatabase.execSQL("INSERT INTO expenses (id, description, amount, createat, category) " +
                "VALUES (3, 'Xăng xe', 300000, '2024-01-22', 'Đi lại');");

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
