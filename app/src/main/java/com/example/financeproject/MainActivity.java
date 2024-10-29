package com.example.financeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.financeproject.adapter.AdapterExpenses;
import com.example.financeproject.dao.IExpenseDAO;
import com.example.financeproject.dao.ImplExpenseDAO;
import com.example.financeproject.entity.Expenses;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends OptionsMenuActivity {
    private List<Expenses> mLst = new ArrayList<>();
    private AdapterExpenses mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Kiểm tra dữ liệu từ Intent
        checkIntent(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Kiểm tra lại dữ liệu từ Intent khi Activity được khởi động lại
        checkIntent(getIntent());
    }

    private void checkIntent(Intent intent) {
        if (intent != null && intent.getBooleanExtra("menu_sort_clicked", false)) {
            loadDataSort();
        } else {
            loadData();
        }
    }

    private void loadData(){
        IExpenseDAO dao = new ImplExpenseDAO(this);
        mLst = dao.selectAll();
        loadLayoutListAndDetailItem(mLst);

    }

    private void loadDataSort(){
        IExpenseDAO dao = new ImplExpenseDAO(this);
        mLst = dao.selectSort();
        loadLayoutListAndDetailItem(mLst);
    }


    private void loadLayoutListAndDetailItem(List<Expenses> mLst){
        if (mLst == null) {
            mLst = new ArrayList<>();
        }
        mAdapter = new AdapterExpenses(this,R.layout.item_list,mLst);

        ListView lstView = findViewById(R.id.listVieww);

        lstView.setAdapter(mAdapter);

        // Tạo một biến tạm thời final
        final List<Expenses> finalMLst = mLst;

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int idExp = finalMLst.get(position).getId(); // position hiện tại của item

                // Intent
                Intent intentDetail = new Intent(MainActivity.this , AddActivity.class);
                // Cho tham số id vào Intent
                intentDetail.putExtra("idExp",idExp); // gửi tham số sang Activity kia
                startActivity(intentDetail);
            }
        };

        lstView.setOnItemClickListener(itemClickListener);
    }


}