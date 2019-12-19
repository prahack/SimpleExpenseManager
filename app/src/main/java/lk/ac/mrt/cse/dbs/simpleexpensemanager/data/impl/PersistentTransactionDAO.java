package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.DbHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO implements TransactionDAO {
    Context context;
    private final List<Transaction> transactions;

    public PersistentTransactionDAO(Context context) {
        this.context = context;
        transactions = new LinkedList<>();
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        DbHelper accountDbHelper = new DbHelper(context);
        SQLiteDatabase database = accountDbHelper.getReadableDatabase();
        accountDbHelper.addTransaction(date,accountNo,expenseType,amount,database);
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        DbHelper accountDbHelper = new DbHelper(context);
        SQLiteDatabase database = accountDbHelper.getReadableDatabase();
        Cursor cursor = accountDbHelper.readTransactions(database);
        while (cursor.moveToNext()) {
            try {
                Date date = new SimpleDateFormat("dd/MM/YYYY", Locale.getDefault()).parse(cursor.getString(cursor.getColumnIndex("date")));
                String accountNo = cursor.getString(cursor.getColumnIndex("accountNo"));
                ExpenseType expenseType = ExpenseType.valueOf(cursor.getString(cursor.getColumnIndex("expenseType")));
                float amount = cursor.getFloat(cursor.getColumnIndex("balance"));
                Transaction transaction = new Transaction(date, accountNo, expenseType, amount);
                transactions.add(transaction);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        DbHelper accountDbHelper = new DbHelper(context);
        SQLiteDatabase database = accountDbHelper.getReadableDatabase();
        Cursor cursor = accountDbHelper.readTransactionsbylimit(database,limit);
        while (cursor.moveToNext()) {
            try {
                Date date = new SimpleDateFormat("dd/MM/YYYY", Locale.getDefault()).parse(cursor.getString(cursor.getColumnIndex("date")));
                String accountNo = cursor.getString(cursor.getColumnIndex("accountNo"));
                ExpenseType expenseType = ExpenseType.valueOf(cursor.getString(cursor.getColumnIndex("expenseType")));
                float amount = cursor.getFloat(cursor.getColumnIndex("balance"));
                Transaction transaction = new Transaction(date, accountNo, expenseType, amount);
                transactions.add(transaction);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return transactions;
    }
}
