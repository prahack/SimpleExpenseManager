package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DatabaseName = "170199F";
    public static final int DatabaseVersion = 7;

    public static final String CreateTable1 = "create table accounts " +
            "(accountNo text, bankName text, accountHolderName text, balance number);";
    public static final String DropTable1 = "drop table if exists accounts;";

    public static final String CreateTable2 = "create table transactions " +
            "(date text, accountNo text, expenseType text, balance number);";
    public static final String DropTable2 = "drop table if exists transactions;";

    public DbHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        Log.d("Database operations", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CreateTable1);
        Log.d("Database operations", "Table1 Created");
        sqLiteDatabase.execSQL(CreateTable2);
        Log.d("Database operations", "Table2 Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DropTable1);
        sqLiteDatabase.execSQL(DropTable2);
        onCreate(sqLiteDatabase);
    }

    public void addAccount(String accountNo, String bankName, String accountHolderName,
                           double balance, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("accountNo", accountNo);
        contentValues.put("bankName", bankName);
        contentValues.put("accountHolderName", accountHolderName);
        contentValues.put("balance", balance);

        database.insert("accounts", null, contentValues);
        Log.d("Database operation", "One raw inserted");
    }

    public Cursor readAccounts(SQLiteDatabase database) {
        String[] projections = {
                "accountNo",
                "bankName",
                "accountHolderName",
                "balance"
        };
        Cursor cursor = database.query(
                "accounts",projections,null,null,null,null,null
        );
        return cursor;
    }

    public void addTransaction(Date date,String accountNo, ExpenseType expenseType, double amount, SQLiteDatabase database ){
        ContentValues contentValues = new ContentValues();
        contentValues.put("date",new SimpleDateFormat("dd/MM/YYYY", Locale.getDefault()).format(date));
        contentValues.put("accountNo",accountNo);
        contentValues.put("expenseType",expenseType.name());
        contentValues.put("balance",amount);

        database.insert("transactions", null, contentValues);
        Log.d("Database operation", "One raw inserted");
    }

    public Cursor readTransactions(SQLiteDatabase database) {
        String[] projections = {
                "date",
                "accountNo",
                "expenseType",
                "balance"
        };
        Cursor cursor = database.query(
                "transactions",projections,null,null,null,null,null
        );
        return cursor;
    }
    public Cursor readTransactionsbylimit(SQLiteDatabase database, int limit) {
        String[] projections = {
                "date",
                "accountNo",
                "expenseType",
                "balance"
        };
        Cursor cursor = database.query(
                "transactions",projections,null,null,null,null,null, limit+""
        );
        return cursor;
    }
}
