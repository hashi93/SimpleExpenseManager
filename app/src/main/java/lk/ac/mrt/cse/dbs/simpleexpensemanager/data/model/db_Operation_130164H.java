package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Hashini on 12/3/2015.
 */
public class db_Operation_130164H extends SQLiteOpenHelper {

     public static final int database_version=1;
    public static final String DATABASE_NAME="130164H";
  //  public String CREATE_QUERY="CREATE_TABLE"+ bd_130164H.Account_Info.TABLE_NAME+"("+ bd_130164H.Account_Info.ACCOUNT_NO+"TEXT,"+ bd_130164H.Account_Info.USER_NAME+"TEXT,"+ bd_130164H.Account_Info.BANK_NAME+"TEXT,"+ bd_130164H.Account_Info.ACCOUNT_BALANCE+"REAL );";
   // public String CREATE_QUERY2="CREATE_TABLE"+ bd_130164H.Tracsaction_Info.TABLE_NAME+"("+ bd_130164H.Tracsaction_Info.TRANSACTION_ID+"TEXT,"+ bd_130164H.Tracsaction_Info.ACCOUNT_NO+"TEXT,"+ bd_130164H.Tracsaction_Info.DATE+"TEXT,"+ bd_130164H.Tracsaction_Info.EXPENSE_TYPE+"TEXT,"+ bd_130164H.Tracsaction_Info.AMOUNT+"REAL );";

    public String CREATE_ACCOUNT="CREATE TABLE "+ bd_130164H.Account_Info.TABLE_NAME+" ("
                    + bd_130164H.Account_Info.ACCOUNT_NO+" TEXT NOT NULL, "
                    +bd_130164H.Account_Info.USER_NAME+" TEXT NOT NULL, "
                    + bd_130164H.Account_Info.BANK_NAME+" TEXT NOT NULL, "
                    + bd_130164H.Account_Info.ACCOUNT_BALANCE+" REAL NOT NULL CHECK ( "+ bd_130164H.Account_Info.ACCOUNT_BALANCE+">=0), "
                    +" PRIMARY KEY("+ bd_130164H.Account_Info.ACCOUNT_NO+" )      );";


   public String CREATE_TRANSACTION=" CREATE TABLE "+ bd_130164H.Tracsaction_Info.TABLE_NAME+" ("
                                    + bd_130164H.Tracsaction_Info.TRANSACTION_ID+" INTEGER NOT NULL, "
                                    + bd_130164H.Tracsaction_Info.ACCOUNT_NO+" TEXT NOT NULL, "
                                    + bd_130164H.Tracsaction_Info.DATE+" TEXT NOT NULL, "
                                    + bd_130164H.Tracsaction_Info.EXPENSE_TYPE+" TEXT NOT NULL, "
                                    + bd_130164H.Tracsaction_Info.AMOUNT+" REAL NOT NULL CHECK ( "+ bd_130164H.Tracsaction_Info.AMOUNT+">0), "
                                    +" PRIMARY KEY ("+ bd_130164H.Tracsaction_Info.TRANSACTION_ID+")"
                                    +" FOREIGN KEY ("+ bd_130164H.Tracsaction_Info.ACCOUNT_NO+") REFERENCES "+ bd_130164H.Account_Info.TABLE_NAME+"("+ bd_130164H.Account_Info.ACCOUNT_NO+") "
                                    +" ON DELETE NO ACTION ON UPDATE NO ACTION );";



    public static db_Operation_130164H DB;

    private  db_Operation_130164H(Context ctx) {




        super(ctx, DATABASE_NAME, null, database_version);
         Log.d("Database operations", "database created");

    }
       //singleton DB
    public static db_Operation_130164H getINstance(Context ctx){
        if(DB==null){
            synchronized (db_Operation_130164H.class) {
                DB = new db_Operation_130164H(ctx);
            }
        }
        return DB;
    }

    @Override
    //create table
    public void onCreate(SQLiteDatabase sdb) {


      sdb.execSQL(CREATE_ACCOUNT);
        sdb.execSQL(CREATE_TRANSACTION);
        Log.d("Database operations", "database created");



    }

    @Override
    //drop tables
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String[] TABLES={bd_130164H.Account_Info.TABLE_NAME, bd_130164H.Tracsaction_Info.TABLE_NAME};

        for (String table : TABLES) {
            db.execSQL("DROP TABLE IF EXISTS " + table); //On upgrade drop tables
        }
        onCreate(db);


    }
     //add new account
    public void putInfo(db_Operation_130164H dop,String user_name,String bank_name,String account_no,Double balance ){
     SQLiteDatabase SQ=dop.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(bd_130164H.Account_Info.ACCOUNT_NO,account_no);
        cv.put(bd_130164H.Account_Info.USER_NAME,user_name);
        cv.put(bd_130164H.Account_Info.BANK_NAME,bank_name);
        cv.put(bd_130164H.Account_Info.ACCOUNT_BALANCE, balance);
        long l=SQ.insert(bd_130164H.Account_Info.TABLE_NAME,null,cv);
        Log.d("Database operations", "account row inserted");

    }
  //add new transaction
    public void putInfo2(db_Operation_130164H db,String date,String account_no,String expense_type,Double amount){
      SQLiteDatabase SQ2=db.getWritableDatabase();
      ContentValues cv2=new ContentValues();
     // cv2.put(bd_130164H.Tracsaction_Info.TRANSACTION_ID,transaction_id);
      cv2.put(bd_130164H.Tracsaction_Info.DATE,date);
     cv2.put(bd_130164H.Tracsaction_Info.ACCOUNT_NO,account_no);
    cv2.put(bd_130164H.Tracsaction_Info.EXPENSE_TYPE,expense_type);
      cv2.put(bd_130164H.Tracsaction_Info.AMOUNT,amount);
      long l2=SQ2.insert(bd_130164H.Tracsaction_Info.TABLE_NAME,null,cv2);
      Log.d("Database Operations","Transaction row inserted");


  }


//get all transaction
 public List<Transaction> getInfo2(db_Operation_130164H db){

     String SELECT_QUERY2="SELECT * FROM "+ bd_130164H.Tracsaction_Info.TABLE_NAME;
     Transaction tr=new Transaction();
    ArrayList<Transaction> tr3=new ArrayList<>();

     SQLiteDatabase db2=null;
     Cursor cursor=null;

     try {
         db2 = this.getReadableDatabase();
         cursor = db2.rawQuery(SELECT_QUERY2, null);
         boolean x=false;
         if (x=cursor.moveToFirst()) {
             do{
                 tr.setAccountNo(cursor.getString(1));
                 String date2 = cursor.getString(2);
                 //convert to date
                 DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                 try {
                     Date date = df.parse(date2);
                     tr.setDate(date);
                 } catch (ParseException e) {
                     e.printStackTrace();
                 }

                 ExpenseType expense = ExpenseType.valueOf(cursor.getString(3));
                 tr.setExpenseType(expense);
                 Double amount = Double.parseDouble(cursor.getString(4));
                 tr.setAmount(amount);

                 tr3.add(tr);
             tr=new Transaction();
             } while (cursor.moveToNext());
         }
     }catch (SQLiteException e){
        e.printStackTrace();
     }
    finally {
         if(cursor!=null){
             cursor.close();
         }
         if(db2!=null){
             db2.close();
         }
     }

     return tr3;
 }
//get all transactions (Order by)
    public List<Transaction> getInfo3(db_Operation_130164H db){

        String SELECT_QUERY2="SELECT * FROM "+ bd_130164H.Tracsaction_Info.TABLE_NAME +" ORDER BY "+ bd_130164H.Tracsaction_Info.DATE;//+" DESC; ";
        Transaction tr=new Transaction();
        ArrayList<Transaction> tr3=new ArrayList<>();

        SQLiteDatabase db2=null;
        Cursor cursor=null;

        try {
            db2 = this.getReadableDatabase();
            cursor = db2.rawQuery(SELECT_QUERY2, null);
            boolean x=false;
            if (x=cursor.moveToFirst()) {
                do{
                    tr.setAccountNo(cursor.getString(1));
                    String date2 = cursor.getString(2);
                    //convert to date
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    try {
                        Date date = df.parse(date2);
                        tr.setDate(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    ExpenseType expense = ExpenseType.valueOf(cursor.getString(3));
                    tr.setExpenseType(expense);
                    Double amount = Double.parseDouble(cursor.getString(4));
                    tr.setAmount(amount);

                    tr3.add(tr);
                    tr=new Transaction();
                } while (cursor.moveToNext());
            }
        }catch (SQLiteException e){
            e.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
            if(db2!=null){
                db2.close();
            }
        }

        return tr3;
    }
//get all account info
    public List<Account> getInfo1(db_Operation_130164H db){

        String SELECT_QUERY="SELECT * FROM"+ bd_130164H.Account_Info.TABLE_NAME;
    ArrayList<Account> account=new ArrayList<>();
    Account acc=new Account();


        SQLiteDatabase db2=null;
        Cursor cursor=null;

    db2=this.getReadableDatabase();
    cursor=db2.rawQuery(SELECT_QUERY, null);

        try {

            if (cursor.moveToFirst()) {
                do {
                    acc.setAccountNo(cursor.getString(0));
                    acc.setAccountHolderName(cursor.getString(1));
                    acc.setBankName(cursor.getString(2));
                    acc.setBalance(Double.parseDouble(cursor.getString(3)));
                    account.add(acc);
                } while (cursor.moveToNext());
            }
        }catch (SQLiteException e){
            e.printStackTrace();
        }finally {

            if (db2 != null) {
                db2.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }
            return account;
        }

//get all account no
    public List<String> getInfo_account_no(db_Operation_130164H db){

        ArrayList<String> account=new ArrayList<>();
        String SELECT_QUERY1="SELECT " + bd_130164H.Account_Info.ACCOUNT_NO +" FROM "+ bd_130164H.Account_Info.TABLE_NAME;
        SQLiteDatabase db2=null;
        Cursor cursor=null;
     try {
         db2 = this.getReadableDatabase();
         cursor = db2.rawQuery(SELECT_QUERY1, null);
         if(cursor.moveToFirst()) {
             do {
                 account.add(cursor.getString(0));
             }while (cursor.moveToNext());
         }
     }catch (SQLiteException e){
         e.printStackTrace();
     }finally {
         if(db2!=null){
             db2.close();
         }
         if (cursor!=null){
             cursor.close();
         }
     }
        db2.close();
        return account;


    }
//get specific account
    public Account get_acc(db_Operation_130164H db, String accountNO){

        Account acc=null;
        SQLiteDatabase db2=null;
        Cursor cursor=null;
        //String QUERRY="SELECT * FROM"+ bd_130164H.Account_Info.TABLE_NAME+"WHERE"+ bd_130164H.Account_Info.ACCOUNT_NO+"="+accountNO;
        String selection= bd_130164H.Account_Info.ACCOUNT_NO+" LIKE ?";
        String columns[]={bd_130164H.Account_Info.ACCOUNT_NO, bd_130164H.Account_Info.USER_NAME, bd_130164H.Account_Info.BANK_NAME, bd_130164H.Account_Info.ACCOUNT_BALANCE};
       String args[]={accountNO};
        try{
            db2=this.getReadableDatabase();
            //cursor=db2.rawQuery(QUERRY,null);
            cursor=db2.query(bd_130164H.Account_Info.TABLE_NAME, columns, selection, args, null, null, null);
            if(cursor!= null){
                cursor.moveToFirst();
                acc = new Account(cursor.getString(0) ,cursor.getString(1) , cursor.getString(2) , Double.parseDouble(cursor.getString(3)) );
//                acc.setAccountNo(cursor.getString(0));
//                acc.setAccountHolderName(cursor.getString(1));
//                acc.setBankName(cursor.getString(2));
//                acc.setBalance(Double.parseDouble(cursor.getString(3)));
            }


        }catch (SQLiteException e){
            e.printStackTrace();
        }finally {
            if(db2!=null){
                db2.close();
            }
            if(cursor !=null){
                cursor.close();
            }
        }



 return acc;
    }
////remove account
    public void removeAcc(db_Operation_130164H db,String accountNO){

        SQLiteDatabase db2=null;
        String selection= bd_130164H.Account_Info.ACCOUNT_NO+"LIKE ?";
        String args[]={accountNO};


        try{
        db2=this.getReadableDatabase();
            db2.delete(bd_130164H.Account_Info.TABLE_NAME,selection,args);

    }catch (SQLiteException e) {
            e.printStackTrace();
        }finally {
            if(db2!=null){
                db2.close();
            }
        }
        }
//update account
    public  void updateAcc(db_Operation_130164H db,String accNo,ExpenseType exp,double amount,Account account){

     SQLiteDatabase db2=null;
        String selection= bd_130164H.Account_Info.ACCOUNT_NO+" LIKE ?";
        String args[]={accNo};
        ContentValues cv= new ContentValues();



        try{
            db2=this.getWritableDatabase();


            if(exp.equals("EXPENSE")) {
                cv.put(bd_130164H.Account_Info.ACCOUNT_BALANCE, account.getBalance() - amount);
            }
            else{
                cv.put(bd_130164H.Account_Info.ACCOUNT_BALANCE, account.getBalance() + amount);
            }

        db2.update(bd_130164H.Account_Info.TABLE_NAME,cv,selection,args);

        }catch (SQLiteException e){
            e.printStackTrace();
        }finally {
            if(db2!=null){
                db2.close();
            }
        }

    }


}
