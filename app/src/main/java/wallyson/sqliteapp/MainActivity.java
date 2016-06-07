package wallyson.sqliteapp;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText edit_id, edit_name, edit_surname, edit_marks;
    Button btnAddData, btnView, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        myDb = new DatabaseHelper(this);
        edit_id = (EditText) findViewById(R.id.editText_id);
        edit_name = (EditText) findViewById(R.id.editText_name);
        edit_surname = (EditText) findViewById(R.id.editText_surname);
        edit_marks = (EditText) findViewById(R.id.editText_marks);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnView = (Button) findViewById(R.id.button_view);
        btnUpdate = (Button) findViewById(R.id.button_update);
        btnDelete = (Button) findViewById(R.id.button_delete);
        addData();
        viewAll();
        updateData();
        deleteData();
    }

    public void addData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(edit_name.getText().toString(),
                                edit_surname.getText().toString(),
                                edit_marks.getText().toString());

                        if (isInserted == true )
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll() {
        btnView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();

                        if ( res.getCount() == 0 ) {
                            // show Message
                            showMessage("Error", "Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while ( res.moveToNext() ) {
                            buffer.append("\nID: " + res.getString(0) );
                            buffer.append("\nNAME: " + res.getString(1) );
                            buffer.append("\nSURNAME: " + res.getString(2) );
                            buffer.append("\nMARKS: " + res.getString(3) );
                        }

                        // Show all data
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void updateData() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(
                                edit_id.getText().toString(),
                                edit_name.getText().toString(),
                                edit_surname.getText().toString(),
                                edit_marks.getText().toString()
                        );

                        if ( isUpdate == true ) {
                            Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Data Not Update", Toast.LENGTH_LONG).show();
                        }
                    }


        }
        );
    }

    public void deleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer deleteRows = myDb.deleteData(edit_id.getText().toString());

                    if ( deleteRows > 0 ) {
                        Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_LONG).show();
                    }

                }
                }
        );
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
