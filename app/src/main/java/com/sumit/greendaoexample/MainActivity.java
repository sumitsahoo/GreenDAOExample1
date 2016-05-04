package com.sumit.greendaoexample;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.sumit.greendaoexample.model.DaoMaster;
import com.sumit.greendaoexample.model.DaoSession;
import com.sumit.greendaoexample.model.Person;
import com.sumit.greendaoexample.model.PersonDao;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    DaoSession daoSession;
    int count = 0;
    TextView tv_person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_person = (TextView) findViewById(R.id.text);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPerson();
                populateData(getPerson());
            }
        });

        daoSession = initDB();
    }

    private DaoSession initDB() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "person-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        return daoSession;
    }

    private List<Person> getPerson() {
        PersonDao personDao = daoSession.getPersonDao();
        List personList = personDao.loadAll();
        return personList;
    }

    private void populateData(List<Person> personList) {

        String persons = null;

        for (Person person : personList) {
            if (persons == null)
                persons = person.getName();
            else persons += "\n" + person.getName();
        }

        if (persons != null)
            tv_person.setText(persons);
        else tv_person.setText("No records yet. Tap on '+' to add a record.");
    }

    private void addPerson() {
        Person person = new Person();
        person.setName(getRandomName() + " - " + count++);
        PersonDao personDao = daoSession.getPersonDao();
        personDao.insertOrReplace(person);
    }

    private String getRandomName(){

        Random random = new Random();
        int low = 0;
        int high = 9;
        int result = random.nextInt(high-low) + low;

        if(result % 2 == 0)
            return "Superman";
        else return "Batman";
    }

    private void deleteAllRecords() {
        PersonDao personDao = daoSession.getPersonDao();
        personDao.deleteAll();
        populateData(getPerson());
        count = 0;
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
            deleteAllRecords();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
