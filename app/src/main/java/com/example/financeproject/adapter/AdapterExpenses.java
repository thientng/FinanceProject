package com.example.financeproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.financeproject.R;
import com.example.financeproject.Utils;
import com.example.financeproject.entity.Expenses;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterExpenses extends ArrayAdapter<Expenses> {
    private Context mCtx;
    private int mLayoutItem;
    private List<Expenses> mLst;

    public AdapterExpenses(@NonNull Context context, int resource, @NonNull List<Expenses> objects) {
        super(context, resource, objects);
        this.mCtx = context;
        this.mLayoutItem = resource;
        this.mLst = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item = convertView;
        if (item == null) {
            // Load layout resource
            item = LayoutInflater.from(mCtx).inflate(mLayoutItem, null);
        }
        Expenses exp = mLst.get(position);

        TextView description = item.findViewById(R.id.TextViewName);
        TextView createat = item.findViewById(R.id.TextViewDate);
        TextView amount = item.findViewById(R.id.TextViewNum);

        description.setText(exp.getDescription());

        createat.setText(Utils.convertDateToString(exp.getCreateat()));

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        amount.setText(formatter.format(exp.getAmount()));

        return item;
    }
}
