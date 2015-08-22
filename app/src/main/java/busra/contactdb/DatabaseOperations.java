package busra.contactdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by busra on 22.08.2015.
 */
public class DatabaseOperations {
    private SQLiteDatabase db;
    private DBHelper helper;
    //private String [] allColumns = {DBHelper.colID, DBHelper.colName, DBHelper.colSurname, DBHelper.colPhone, DBHelper.colEmail};

    public DatabaseOperations(Context context){
        helper = new DBHelper(context);

    }
    public void openDB () throws SQLException {
        db = helper.getWritableDatabase();

    }
    public void closeDB () {
        helper.close();

    }
    //inserting records to database

    public Contact insertRecord(String name,String surname, String phone, String email) {
        openDB();
        // SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DBHelper.colID, 1);
        cv.put(DBHelper.colName,name);
        cv.put(DBHelper.colSurname,surname);
        cv.put(DBHelper.colPhone,phone);
        cv.put(DBHelper.colEmail, email);
        cv.put(DBHelper.ContactsTable, "Contacts");
        db.insert(DBHelper.ContactsTable, DBHelper.colID, cv);

       /**  long insertID = db.insert(DBHelper.ContactsTable, DBHelper.colID, cv);
        *  Cursor cursor = db.query(DBHelper.ContactsTable,
                allColumns, DBHelper.colID + " = " + insertId, null,
                null, null, null); */

        Cursor cursor = db.rawQuery("SELECT * FROM Contacts WHERE ContactID = " + DBHelper.colID , null) ;
        cursor.moveToFirst();
        Contact newContact = cursorToContact(cursor);
        cursor.close();
        return newContact;

    }

    //we need to call this.getWritableDatabase() to open the
    // connection with the database for reading/writing

    public int updateContact(Contact con) {
        openDB();
        //SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.colName , con.getName());
        cv.put(DBHelper.colSurname , con.getSurname());
        cv.put(DBHelper.colPhone, con.getPhone());
        cv.put(DBHelper.colEmail, con.getEmail());
        return  db.update(DBHelper.ContactsTable ,cv , DBHelper.colID + "= ?" ,
                new String[] {String.valueOf(con.getID())})  ;
    }

    public void deleteContact(Contact con) {
        openDB();
        //SQLiteDatabase db = dbHelper.getWritableDatabase() ;
        long id = con.getID();
        System.out.println("Contact deleted with id" + id);
        db.delete(DBHelper.ContactsTable, DBHelper.colID + "= ?" + id, null) ;
        db.close();

        /**db.delete(DBHelper.ContactsTable, DBHelper.colID + "= ?", new String[]{String.valueOf(con.getID())});
          */
    }
    public ArrayList<Contact> getAllContacts (){
        ArrayList<Contact> contacts = new ArrayList<>();

        //HashMap ();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Contacts", null);
        // Cursor cur=db.rawQuery("SELECT "+colDeptID+" as _id,
        // "+colDeptName+" from "+deptTable,new String [] {});
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){   //cursor.isAfterLast() == false
            Contact contact = cursorToContact(cursor);
            contacts.add(contact);
            cursor.moveToNext();
        }
        cursor.close();
        return contacts;
    }



    private Contact cursorToContact(Cursor cursor) {
        Contact contact = new Contact();
        contact.setID(cursor.getInt(0));
        contact.setName(cursor.getString(1));
        contact.setSurname(cursor.getString(1));
        contact.setPhone(cursor.getString(1));
        contact.setEmail(cursor.getString(1));
        return contact;
    }

}
