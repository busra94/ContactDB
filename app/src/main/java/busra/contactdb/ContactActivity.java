package busra.contactdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class ContactActivity extends Activity {

    DatabaseOperations operations;

    EditText editName;
    EditText editSurname;
    EditText editPhone;
    EditText editEmail;
    Button btnDel;
    Button btnSave;
    Button btnModify;
    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        findViewAllIDs();

        ArrayList<Contact> values = operations.getAllContacts();
        final ArrayAdapter<Contact> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        list.setAdapter(adapter);
    }


    public void onClick(View view) {
        ArrayAdapter<Contact> adapter1 = (ArrayAdapter<Contact>) list.getAdapter();
        Contact contact = null;

        switch (view.getId()) {
            case R.id.add:
                //String[] contacts = new String[]{"Mehmet", "Candas", "05465860951", "m.candas@gmail.com"};
                // int nextInt = new Random().nextInt(3);
                // save the new contact to the database
                contact = operations.insertRecord("Mehmet", "Candas", "05465860951", "m.candas@gmail.com");
                adapter1.add(contact);
                break;

            case R.id.delete:
                if (list.getAdapter().getCount() > 0) {
                    contact = (Contact) list.getAdapter().getItem(0);
                    operations.deleteContact(contact);
                    adapter1.remove(contact);
                }
                break;
            case R.id.modify:
                if (list.getAdapter().getCount() > 0) {
                    contact = (Contact) list.getAdapter().getItem(0);
                    operations.updateContact(contact);
                    //adapter1.notifyDataSetChanged();
                }
        }
        adapter1.notifyDataSetChanged();
    }

            @Override
            protected void onResume() {
                operations.openDB();
                super.onResume();
            }

            @Override
            protected void onPause() {
                operations.closeDB();
                super.onPause();
            }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void findViewAllIDs (){
        editName = (EditText) findViewById(R.id.editName);
        editSurname = (EditText) findViewById(R.id.editSurname);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editEmail = (EditText) findViewById(R.id.editEmail);
        list =(ListView) findViewById(R.id.list);
        btnSave = (Button) findViewById(R.id.add);
        btnDel = (Button) findViewById(R.id.delete);
        btnModify = (Button) findViewById(R.id.modify);
    }
}
