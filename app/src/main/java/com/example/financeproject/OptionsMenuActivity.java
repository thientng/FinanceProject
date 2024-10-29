package com.example.financeproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class OptionsMenuActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Kiểm tra nếu activity hiện tại không phải hoặc là phải thì ẩn hoặc ...
        if ((this instanceof AddActivity)) {
            MenuItem searchItem = menu.findItem(R.id.menu_add_icon);
            searchItem.setVisible(false); // hoặc searchItem.setEnabled(false);
        }

        if (!(this instanceof MainActivity)) {
            MenuItem sortItem = menu.findItem(R.id.menu_sort);
            MenuItem searchItem = menu.findItem(R.id.menu_search);
            sortItem.setVisible(false);
            searchItem.setVisible(false);
        }

        //menu.add("Hehe");
        return super.onCreateOptionsMenu(menu);
    }

    // Xử lý sự kiện khi một mục menu được chọn
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.menu_back:
                onBackPressed();
                break;
            case R.id.menu_add:
            case R.id.menu_add_icon:
                intent = new Intent(this, AddActivity.class);
                break;
            case R.id.menu_search:
                Toast.makeText(this, "Đề bài không yêu cầu chức năng này", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_sort:
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("menu_sort_clicked", true);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        if (intent != null && intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            return true;
        } else if (intent != null) {
            Toast.makeText(this, "Không có hoạt động phù hợp", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
