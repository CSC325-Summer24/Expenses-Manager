package com.example.csc325_firebase_webview_auth.model;

import com.example.csc325_firebase_webview_auth.view.App;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

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

    public static void addExpense(Expense expense) throws Exception {
        if (expense.getExpenseID() == null || expense.getExpenseID().isEmpty()) {
            expense.setExpenseID(UUID.randomUUID().toString());
        }
        Firestore db = new FirestoreContext().firebase();
        expense.setUserID(App.loggedInUserId); // Ensure the user ID is set
        db.collection("expenses").document(expense.getExpenseID()).set(expense).get();
        System.out.println("Expense added: " + expense);
    }

    public static void editExpense(Expense expense) throws Exception {
        if (expense.getExpenseID() == null || expense.getExpenseID().isEmpty()) {
            throw new IllegalArgumentException("Expense ID must not be null or empty");
        }
        Firestore db = new FirestoreContext().firebase();
        db.collection("expenses").document(expense.getExpenseID()).set(expense).get();
    }

    public static void deleteExpense(String expenseID) throws Exception {
        if (expenseID == null || expenseID.isEmpty()) {
            throw new IllegalArgumentException("Expense ID must not be null or empty");
        }
        Firestore db = new FirestoreContext().firebase();
        db.collection("expenses").document(expenseID).delete().get();
    }

    public static List<Expense> viewExpenses(String userID) throws Exception {
        if (userID == null || userID.isEmpty()) {
            throw new IllegalArgumentException("User ID must not be null or empty");
        }
        Firestore db = new FirestoreContext().firebase();
        List<Expense> expenses = new ArrayList<>();
        CollectionReference expensesRef = db.collection("expenses");
        Query query = expensesRef.whereEqualTo("userID", userID);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            expenses.add(document.toObject(Expense.class));
        }
        System.out.println("Expenses retrieved: " + expenses);
        return expenses;
    }
}

