package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.db_Operation_130164H;

/**
 * Created by Hashini on 12/4/2015.
 */
public class DB_AccountDAO implements AccountDAO
{
   // private final Map<String, Account> accounts;
    private db_Operation_130164H db;

    public DB_AccountDAO(db_Operation_130164H db){
        this.db=db;
    }


//return account numbers
    @Override
    public List<String> getAccountNumbersList() {
        List<String> account_numbers=new ArrayList<>();
        //db_Operation_130164H db=new db_Operation_130164H();
        account_numbers=db.getInfo_account_no(db);

        return account_numbers;
    }

    @Override
    public List<Account> getAccountsList() {

        List<Account> account_info=new ArrayList<>();
        //db_Operation_130164H db=new db_Operation_130164H();
        account_info=db.getInfo1(db);

        return account_info;
    }


    //read specific accounts from DB
    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {


        Account acc=null;
// db_Operation_130164H db=new db_Operation_130164H();
        //get acc

    acc=db.get_acc(db,accountNo);

        if (acc!=null) {
            return acc;
        }
        String msg = "Account " + accountNo + " is invalid.";
        throw new InvalidAccountException(msg);
    }

    @Override
    //add new account to DB
    public void addAccount(Account account) {
           //db_Operation_130164H db=new db_Operation_130164H();
           db.putInfo(db, account.getAccountHolderName(), account.getBankName(), account.getAccountNo(), account.getBalance());
           //accounts.put(account.getAccountNo(), account);
    }
     //romove account
    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
   // db_Operation_130164H db=new db_Operation_130164H();
    db.removeAcc(db,accountNo);

    }
    //update account balance when transaction happence
    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
//  db_Operation_130164H db=new db_Operation_130164H();
        Account acc=null;

        acc=db.get_acc(db,accountNo);
        if (acc!=null) {
           db.updateAcc(db,accountNo,expenseType,amount,acc);

        }
        else {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }


    }
}
