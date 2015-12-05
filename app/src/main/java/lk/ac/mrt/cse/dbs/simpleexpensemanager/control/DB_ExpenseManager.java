package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DB_AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DB_TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.db_Operation_130164H;

/**
 * Created by Hashini on 12/4/2015.
 */
public class DB_ExpenseManager extends ExpenseManager{

//dbhandler =null

 Context ctx = null;
 db_Operation_130164H db;
    public DB_ExpenseManager(Context conetxt)  {
        this. ctx = conetxt;
        //this . handler = new handler(context)
        this.db= db_Operation_130164H.getINstance(ctx);

        setup();
    }


    @Override
    void setup() {

        TransactionDAO DB_TransactionDAO = new DB_TransactionDAO(db);
        setTransactionsDAO(DB_TransactionDAO);

        AccountDAO DB_AccountDAO = new DB_AccountDAO(db);
        setAccountsDAO(DB_AccountDAO);

    }
}
