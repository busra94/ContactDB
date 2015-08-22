package busra.contactdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by busra on 22.08.2015.
 */

/**SQLiteOpenHelper class. This class provides two methods to override to deal with the database:

        onCreate(SQLiteDatabase db): invoked when the database is created,
        this is where we can create tables and columns to them, create views or triggers.

        onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion): invoked when we make a modification to the database
        such as altering, dropping , creating new tables.   */

public class DBHelper extends SQLiteOpenHelper {
    static final String dbName = "ContactsDB";
    static final String ContactsTable = "Contacts";

    static final String colID = "ContactID";
    static final String colName = "ContactName";
    static final String colSurname = "ContactSurname";
    static final String colPhone = "ContactPhone";
    static final String colEmail = "email";
    static final int databaseVersion = 1;

    //database creation sql statement
    static String Create_Database = "CREATE TABLE " + ContactsTable + " (" + colID +
            "INTEGER PRIMARY KEY AUTO INCREMENT ," + colName + "TEXT NOT NULL ," +
            colSurname + "TEXT NOT NULL" + colPhone + "TEXT NOT NULL , " + colEmail + "TEXT";

    public DBHelper (Context context){
        super(context, dbName,null,databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(Create_Database);
        /**  The method is invoked when the database is created.
         * This method is invoked when the database does not exist on the disk,
         * it’s executed only once on the same device the first time the application is run on the device.*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXIST" + ContactsTable);
        // or  db.execSQL("DROP TABLE IF EXIST ContactsTable");
        onCreate(db);


    }
}
