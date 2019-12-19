package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.DbHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentAccountDAO implements AccountDAO {
    private final Map<String, Account> accounts;

    public PersistentAccountDAO(Context context) {
        this.accounts = new HashMap<>();
        DbHelper accountDbHelper = new DbHelper(context);
        SQLiteDatabase database = accountDbHelper.getReadableDatabase();
        Cursor cursor = accountDbHelper.readAccounts(database);
        while (cursor.moveToNext()) {
            String an = cursor.getString(cursor.getColumnIndex("accountNo"));
            String bn = cursor.getString(cursor.getColumnIndex("bankName"));
            String ahn = cursor.getString(cursor.getColumnIndex("accountHolderName"));
            float balance = cursor.getFloat(cursor.getColumnIndex("balance"));
            Account account = new Account(an,bn,ahn,balance);
            String accountNo = account.getAccountNo();
            accounts.put(accountNo,account);
            Log.d("account",accounts.toString());
        }
    }
    @Override
    public List<String> getAccountNumbersList() {
        return new ArrayList<>(accounts.keySet());
    }

    @Override
    public List<Account> getAccountsList(Context context) {
        DbHelper accountDbHelper = new DbHelper(context);
        SQLiteDatabase database = accountDbHelper.getReadableDatabase();
        Cursor cursor = accountDbHelper.readAccounts(database);
        while (cursor.moveToNext()) {
            String an = cursor.getString(cursor.getColumnIndex("accountNo"));
            String bn = cursor.getString(cursor.getColumnIndex("bankName"));
            String ahn = cursor.getString(cursor.getColumnIndex("accountHolderName"));
            float balance = cursor.getFloat(cursor.getColumnIndex("balance"));
            Account account = new Account(an,bn,ahn,balance);
            String accountNo = account.getAccountNo();
            accounts.put(accountNo,account);
            Log.d("account",accounts.toString());
        }
        return new ArrayList<>(accounts.values());
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        if (accounts.containsKey(accountNo)) {
            return accounts.get(accountNo);
        }
        String msg = "Account " + accountNo + " is invalid.";
        throw new InvalidAccountException(msg);
    }

    @Override
    public void addAccount(Account account,Context context) {
        DbHelper accountDbHelper = new DbHelper(context);
        SQLiteDatabase database = accountDbHelper.getWritableDatabase();
       accountDbHelper.addAccount(account.getAccountNo(),account.getBankName(),account.getAccountHolderName(),
                account.getBalance(), database);
        accountDbHelper.close();
        getAccountsList(context);
        accounts.put(account.getAccountNo(), account);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

    }

}
