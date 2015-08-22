package busra.contactdb;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by busra on 22.08.2015.
 */
public class DisplayContact extends Activity {
    DatabaseOperations operations;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_contacts);

        Bundle bundle = getIntent().getExtras();
        list = (ListView) findViewById(R.id.list);

        operations = new DatabaseOperations(this);
        operations.openDB();

        ArrayList<Contact> values = operations.getAllContacts();
        ArrayAdapter<Contact> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        list.setAdapter(adapter);
    }

    public void onClick(View view) {



    }
}
