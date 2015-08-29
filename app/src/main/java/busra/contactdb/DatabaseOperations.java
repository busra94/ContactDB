package busra.contactdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by busra on 22.08.2015.
 */
public class DatabaseOperations {

    public SQLiteDatabase db;
    private DBHelper helper;
    public String [] allColumns = {DBHelper.colID, DBHelper.colName, DBHelper.colSurname, DBHelper.colPhone, DBHelper.colEmail};

    public DatabaseOperations(Context context){
        helper = new DBHelper(context);  // database created..

    }
       //public void openDB () throws SQLException {

    public void openDB () throws SQLException{
        db = helper.getWritableDatabase();

    }
    public void closeDB () {
        helper.close();

    }
    //inserting records to database
    public Contact addContact(String name, String surname, String phone, String email) {

        //openDB();
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        //ContentValues values = null; ?
         values.put(DBHelper.colName, name);
         values.put(DBHelper.colSurname, surname);
         values.put(DBHelper.colPhone, phone);
         values.put(DBHelper.colEmail, email);

       /** //values.put(DBHelper.colID, contact.getID());
        values.put(DBHelper.colName, contact.getName());
        values.put(DBHelper.colSurname, contact.getSurname());
        values.put(DBHelper.colPhone, contact.getPhone());
        values.put(DBHelper.colEmail, contact.getEmail());  */
        long insertId = db.insert(DBHelper.ContactsTable, null, values);// inserting row
        Cursor cursor = db.query(DBHelper.ContactsTable, allColumns, DBHelper.colID  + " = " +
                        insertId, null, null, null,null);
        cursor.moveToFirst();
        Contact newContact = cursorToContact(cursor);
        cursor.close();

        return newContact;
    }

    //we need to call this.getWritableDatabase() to open the
    // connection with the database for reading/writing

    public int modifyContact(Contact contact) {
       // openDB();
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBHelper.colName, contact.getName());
        values.put(DBHelper.colSurname, contact.getSurname());
        values.put(DBHelper.colPhone, contact.getPhone());
        values.put(DBHelper.colEmail, contact.getEmail());

        long id = contact.getID();
        db.close();
        return  db.update(DBHelper.ContactsTable , values , DBHelper.colID + " =?" ,
                new String[]{String.valueOf(id)})  ;
    }

    public void deleteContact(Contact contact) {
       // openDB();
        SQLiteDatabase db = helper.getWritableDatabase() ;

        long id = contact.getID();
        System.out.println("Contact deleted with id" + id);

        db.delete(DBHelper.ContactsTable, DBHelper.colID + "= ?", new String[]{String.valueOf(id)});

        db.close();

        /**db.delete(DBHelper.ContactsTable, DBHelper.colID + "= ?" + id, null) ;
          */
    }

    public int countContact () {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sqlStatement = "SELECT * FROM " + DBHelper.ContactsTable;
        int count = db.rawQuery(sqlStatement, null).getCount();
        db.close();
        return count;
    }

     public Contact getOneContact (int id){
         Contact getOneContact = null;
         SQLiteDatabase db = helper.getReadableDatabase();
        /** Cursor cursor = db.query(DBHelper.ContactsTable, new String [] {DBHelper.colID, DBHelper.colName,
         * DBHelper.colSurname, DBHelper.colPhone, DBHelper.colEmail} ,  DBHelper.colID + " =? " , (or "ID =?")
         new String[] {String.valueOf(id)}, null, null, null, null);*/

         Cursor cursor = db.query(DBHelper.ContactsTable,
                 allColumns, " ID = ? ", new String[]{String.valueOf(id)}, null,
                 null, null, null);
         if (cursor != null){
             cursor.moveToFirst();
             getOneContact = cursorToContact(cursor);
             cursor.close();
         }
        //Cursor cursor = db.rawQuery("SELECT * FROM Contacts WHERE ID = " + DBHelper.colID , null) ;

         return getOneContact;
     }
    public List<String> getAllContacts () {

        Contact getContacts;
        List<String> contactList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DBHelper.ContactsTable;

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Cursor cur=db.rawQuery("SELECT "+colDeptID+" as _id,
        // "+colDeptName+" from "+deptTable,new String [] {});
        //if (cursor.moveToFirst()) {
        if(cursor != null && cursor.moveToFirst()){
            do {
                //getContacts = cursorToContact(cursor);
                //contactList.add(getContacts);

                String iName = cursor.getString(cursor.getColumnIndex(DBHelper.colName));
                String iSurname = cursor.getString(cursor.getColumnIndex(DBHelper.colSurname));
                String iPhone = cursor.getString(cursor.getColumnIndex(DBHelper.colPhone));
                String iEmail = cursor.getString(cursor.getColumnIndex(DBHelper.colEmail));
                contactList.add(iName);
                contactList.add(iSurname);
                contactList.add(iPhone);
                contactList.add(iEmail);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());    //cursor.isAfterLast() == false
        }
        //cursor.close();
        return contactList;
        }



    public Contact cursorToContact(Cursor cursor) {
        Contact contact = new Contact();
        contact.setID(Integer.parseInt(cursor.getString(0)));
        contact.setName(cursor.getString(1));
        contact.setSurname(cursor.getString(2));
        contact.setPhone(cursor.getString(3));
        contact.setEmail(cursor.getString(4));
        return contact;
    }




}
