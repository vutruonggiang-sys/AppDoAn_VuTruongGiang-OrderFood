package com.example.appdoan_vutruonggiang.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.appdoan_vutruonggiang.UI.Food_Order;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {
    private static final String DB_Name="Product.db";
    private static final int DB_Version=1;

    private static final String TB_Cart="giohang";
    private static final String DB_ID="id";
    private static final String DB_URL="url";
    private static final String DB_NAME="name";
    private static final String DB_IDNHAHANG="idnhahang";
    private static final String DB_PRICE="price";
    private static final String DB_AMOUNT="amount";

    public SqliteHelper(@Nullable Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlString="Create table giohang (" +
                " id text not null primary key," +
                "url text," +
                "name text," +
                "idnhahang text," +
                "price long," +
                "amount long" +
                ")";
        db.execSQL(sqlString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion!=newVersion){
            String sqlString="drop table if exists "+DB_Name;
            db.execSQL(sqlString);
            onCreate(db);
        }
    }
    public void onAddGioHang(Food_Order food_order){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DB_ID,food_order.getId());
        contentValues.put(DB_URL,food_order.getUrl());
        contentValues.put(DB_NAME,food_order.getName());
        contentValues.put(DB_IDNHAHANG,food_order.getIdNhaHang());
        contentValues.put(DB_PRICE,food_order.getPrice());
        contentValues.put(DB_AMOUNT,food_order.getAmount());
        sqLiteDatabase.insert(TB_Cart,null,contentValues);
        sqLiteDatabase.close();
        contentValues.clear();
    }
    public void onDeleteGioHang(String id){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete(TB_Cart,"id=?",new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }
    public void onDeleteAllGioHang(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete(TB_Cart,null,null);
        sqLiteDatabase.close();
    }
    public List<Food_Order> getAllGioHang(){
        List<Food_Order> food_orderList=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query(false,TB_Cart,null,null,null,null,
                null,null,null);
        while(cursor.moveToNext()){
            String id=cursor.getString(cursor.getColumnIndex(DB_ID)+0);
            String url=cursor.getString(cursor.getColumnIndex(DB_URL)+0);
            String name=cursor.getString(cursor.getColumnIndex(DB_NAME)+0);
            String idNhaHang=cursor.getString(cursor.getColumnIndex(DB_IDNHAHANG)+0);
            long price=cursor.getLong(cursor.getColumnIndex(DB_PRICE)+0);
            long amount=cursor.getLong(cursor.getColumnIndex(DB_AMOUNT)+0);
            Food_Order food_order=new Food_Order(amount,id,name,price,url,idNhaHang);
            food_orderList.add(food_order);
        }
        return food_orderList;
    }
    public boolean checkGioHang(String id){
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query(false,TB_Cart,null,null,null,null,
                null,null,null);
        while(cursor.moveToNext()){
            String idcheck=cursor.getString(cursor.getColumnIndex(DB_ID)+0);
            if(idcheck.equals(id)) return false;
        }
        return true;
    }
}
