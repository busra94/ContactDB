package busra.contactdb;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by busra on 25.08.2015.
 */
public class DisplayActivity extends ListActivity {


    ListView list;
    DatabaseOperations databaseOpr;
    List values = new ArrayList();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.list_contact_info);

        list = (ListView) findViewById(android.R.id.list);

        databaseOpr = new DatabaseOperations(this);

        values = databaseOpr.getAllContacts();
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,values);
        list.setAdapter(adapter);

        View v = findViewById(R.id.back);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }
}


/**Intent intent = getIntent();
String takeName =intent.getStringExtra(ContactActivity.SEND_NAME);
String takeSurname = intent.getStringExtra(ContactActivity.SEND_SURNAME);
String takePhone =intent.getStringExtra(ContactActivity.SEND_PHONE);
String takeEmail = intent.getStringExtra(ContactActivity.SEND_EMAIL); */


