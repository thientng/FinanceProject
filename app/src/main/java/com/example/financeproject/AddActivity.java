package com.example.financeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financeproject.dao.IExpenseDAO;
import com.example.financeproject.dao.ImplExpenseDAO;
import com.example.financeproject.entity.Expenses;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends OptionsMenuActivity {

    private Integer idExpUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        spinner(findViewById(R.id.spinner));

        accessDate(findViewById(R.id.editTextDate));

        if (getIntent().hasExtra("idExp")) {
            idExpUpdate = getIntent().getExtras().getInt("idExp");
        }

        if (idExpUpdate != null) {
            loadDataItem();
            findViewById(R.id.btnAddd).setVisibility(View.GONE);

            setTitle("Cập nhật");
        } else {
            findViewById(R.id.btnDelete).setVisibility(View.GONE);
            findViewById(R.id.btnUpdate).setVisibility(View.GONE);

        }

        findViewById(R.id.btnDelete).setOnClickListener(view -> delete());
    }


    public void actionSave(View view) {
        if (validateForm()) {
            String description = ((EditText) findViewById(R.id.editTextName)).getText().toString();

            Spinner spinnerCategory = findViewById(R.id.spinner);
            String category = spinnerCategory.getSelectedItem().toString();

            EditText editTextAmount = findViewById(R.id.editTextNumber);
            float amount = Float.parseFloat(editTextAmount.getText().toString());

            String strCreateat = ((EditText) findViewById(R.id.editTextDate)).getText().toString();
            Date createat = Utils.convertStringToDate(strCreateat);

            Expenses exp;
            IExpenseDAO iExpDAO = new ImplExpenseDAO(this);

            boolean isDone;
            if (idExpUpdate != null) {
                int stuId = idExpUpdate;

                exp = new Expenses(stuId, description, amount, createat, category);
                isDone = iExpDAO.update(exp);
                if (isDone) {
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_LONG).show();
                }
            } else {
                //Thêm mới dữ liệu
                exp = new Expenses(0, description, amount, createat, category);
                isDone = iExpDAO.insert(exp);

                if (isDone) {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Thêm thất bại!", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    private void loadDataItem() {
        int idExp = idExpUpdate;

        IExpenseDAO dao = new ImplExpenseDAO(this);
        Expenses itemExp = dao.selectById(idExp);

        ((EditText) findViewById(R.id.editTextName)).setText(itemExp.getDescription());

        //NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        //((EditText) findViewById(R.id.editTextNumber)).setText(formatter.format(itemExp.getAmount()));

        ((EditText) findViewById(R.id.editTextNumber)).setText(String.valueOf((int) itemExp.getAmount()));
        ((EditText) findViewById(R.id.editTextDate)).setText(Utils.convertDateToString(itemExp.getCreateat()));

        // Spinner
        Spinner spinnerCategory = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinnerCategory.getAdapter();
        int position = adapter.getPosition(itemExp.getCategory());
        spinnerCategory.setSelection(position);

        // Xóa dữ liệu idStu từ Intent sau khi load dữ liệu xong
        //getIntent().removeExtra("idExp");
    }

    private void spinner(Spinner spinner) {
        // Tạo mảng dữ liệu cho Spinner
        String[] languages = {"Tiêu vặt", "Đi chợ", "Nhận lương", "Mừng cưới", "Thực phẩm", "Công nghệ", "Đi lại"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Gán ArrayAdapter vào Spinner
        spinner.setAdapter(adapter);
    }

    private void accessDate(EditText editTextDate) {
        DatePickerDialog datePickerDialog = null; // Khởi tạo biến datePickerDialog

        // Lấy ngày hiện tại
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Thiết lập DatePickerDialog
        datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    // Cập nhật TextView với ngày tháng năm đã chọn
                    calendar.set(Calendar.YEAR, year1);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth1);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String selectedDateTextFmt = sdf.format(calendar.getTime());
                    Date selectedDate = calendar.getTime();

                    // Gán giá trị nhận được ở datePickerDialog vào text view và edit text sau khi chọn xong
                    editTextDate.setText(Utils.convertDateToString(selectedDate));
                }, year, month, dayOfMonth);

        // Thiết lập sự kiện khi editTextDate được nhấn để hiển thị DatePicker
        final DatePickerDialog finalDatePickerDialog = datePickerDialog;
        editTextDate.setOnClickListener(v -> {
            if (finalDatePickerDialog != null) {
                finalDatePickerDialog.show();
            }
        });
    }

    private boolean validateForm() {
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextAmount = findViewById(R.id.editTextNumber);
        EditText editTextDate = findViewById(R.id.editTextDate);
        Spinner spinnerCategory = findViewById(R.id.spinner);

        if (editTextName.getText().toString().trim().isEmpty()) {
            editTextName.setError("Không được bỏ trống");
            return false;
        }

        if (editTextAmount.getText().toString().trim().isEmpty()) {
            editTextAmount.setError("Không được bỏ trống");
            return false;
        }

        if (editTextDate.getText().toString().trim().isEmpty()) {
            editTextDate.setError("Không được bỏ trống");
            return false;
        }

        if (spinnerCategory.getSelectedItem() == null) {
            Toast.makeText(this, "Vui lòng chọn một mục", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void delete(){
        IExpenseDAO dao = new ImplExpenseDAO(this);
        boolean isDone = dao.delete(idExpUpdate);
        if(isDone) {
            Toast.makeText(this,"Xóa thành công",Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this,"Xóa thất bại",Toast.LENGTH_SHORT).show();
        }
    }
}