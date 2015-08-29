package busra.contactdb;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;



public class ContactActivity extends Activity{

    public static final String SEND_NAME= "";
    public static final String SEND_SURNAME= "";
    public static final String SEND_PHONE= "";
    public static final String SEND_EMAIL= "";

    TextView countRecords;
    EditText editName;
    EditText editSurname;
    EditText editPhone;
    EditText editEmail;
    ListView list;
    Button btnDelete;
    Button btnAdd;
    Button btnModify;


    DatabaseOperations dbOperations;

    List values = new ArrayList();

    @Override
        protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        findViewAllIDs();

        dbOperations = new DatabaseOperations(this);
        dbOperations.openDB();


        values = dbOperations.getAllContacts();

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,values);
        list.setAdapter(adapter);


    /**    View v = findViewById(R.id.listButton);
        v.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (arg0.getId() == R.id.listButton) {
                    //define new intent to DisplayActivity

                    Intent intent = new Intent(ContactActivity.this, DisplayActivity.class);
                    String sendName = values.get(0).toString();
                    intent.putExtra(SEND_NAME, sendName);


                    String sendSurname = values.get(1).toString();
                    intent.putExtra(SEND_SURNAME, sendSurname);


                    String sendPhone = values.get(2).toString();
                    intent.putExtra(SEND_PHONE, sendPhone);


                    String sendEmail = values.get(3).toString();
                    intent.putExtra(SEND_EMAIL, sendEmail);


                    //start the second activity
                    startActivity(intent);
                }
            }
        }); */


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 modify();
            }
        });

         count();   // textview e yazilacak.
    }

    @Override
    protected void onResume() {
        dbOperations.openDB();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dbOperations.closeDB();
        super.onPause();
    }

    public void add(){
             ArrayAdapter adapter1 = (ArrayAdapter) list.getAdapter();
          // dbOperations.addContact(new Contact());

              //getContactFromViews();
              Contact contact = dbOperations.addContact(editName.getText().toString(),
                      editSurname.getText().toString(),editPhone.getText().toString(),
                      editEmail.getText().toString());

              adapter1.add(contact);
              adapter1.notifyDataSetChanged();

    }
    public void delete(){
        Contact contact = null;
          ArrayAdapter adapter1 = (ArrayAdapter) list.getAdapter();
          if(list.getAdapter().getCount() > 0){
              contact = (Contact) list.getAdapter().getItem(0);
              dbOperations.deleteContact(contact);
              adapter1.remove(contact);
              adapter1.notifyDataSetChanged();
          }
    }
    public void modify () {
           Contact contact = null;
           ArrayAdapter adapter1 = (ArrayAdapter) list.getAdapter();
          if (list.getAdapter().getCount() > 0) {
              contact = (Contact) list.getAdapter().getItem(0);
              dbOperations.modifyContact(contact);
              //adapter. ???
              adapter1.notifyDataSetChanged();
          }
    }
    public void count () {
        int countContact = new DatabaseOperations(this).countContact();
        countRecords.setText(countContact + " records found.");
    }




    private void findViewAllIDs (){
        editName = (EditText) findViewById(R.id.editName);
        editSurname = (EditText) findViewById(R.id.editSurname);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editEmail = (EditText) findViewById(R.id.editEmail);
        list = (ListView) findViewById(R.id.list_contact);
        countRecords = (TextView) findViewById(R.id.count_records);
        btnAdd = (Button) findViewById(R.id.add);
        btnDelete = (Button) findViewById(R.id.delete);
        btnModify = (Button) findViewById(R.id.modify);
    }


   /** private Contact getContactFromViews () {
        String name = editName.getText().toString();
        String surname = editSurname.getText().toString();
        String phone = editPhone.getText().toString();
        String email = editEmail.getText().toString();


        Contact contact = new Contact();
        contact.setName(name);
        contact.setSurname(surname);
        contact.setPhone(phone);
        contact.setEmail(email);

        return contact;
   }    */
}
