package com.example.csc325_firebase_webview_auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Expense {

    private String expenseID;

    private String userID;

    private String description;

    private Double amount;

    private Date dueDate;


    public static void addExpense(Expense expense) throws Exception
    {

    }

    public static void editExpense(Expense expense) throws Exception
    {

    }

    public static void deleteExpense(Expense expense) throws Exception
    {

    }

    public static List<Expense> viewExpenses()
    {
        return Collections.EMPTY_LIST;
    }


}