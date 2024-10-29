package com.example.financeproject.dao;

import com.example.financeproject.entity.Expenses;

import java.util.List;

public interface IExpenseDAO {
    public List<Expenses> selectAll();

    public List<Expenses> selectSort();

    public Expenses selectById(int id);

    public boolean insert(Expenses exp);

    public boolean update(Expenses exp);

    public boolean delete(int id);

}
