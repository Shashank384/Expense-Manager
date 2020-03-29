package com.example.expensemanager;

import android.content.Intent;
import android.os.Bundle;

import com.example.expensemanager.ui.Income.IncomeFragment;
import com.example.expensemanager.ui.Settings.ToolsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.expensemanager.ui.Expense.FragmentExpense;
import com.example.expensemanager.ui.Overview.OverviewFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private AppBarConfiguration mAppBarConfiguration;
    FloatingActionButton fab_list,fab_plus,fab_expense;
    Animation FabOpen,FabClose,FabClockwise,FabAnticlockwise;
    boolean isOpen=false;



//    SQLiteDatabase db; //SQLite database object creation to refer the operations of fragment classes//////
//    DatabaseHelper dbHelper;

    @Override  // Oncreate Function.............
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //////////////////Floating Action Button
        fab_plus = (FloatingActionButton) findViewById(R.id.fab_plus);
        fab_expense = (FloatingActionButton)findViewById(R.id.fab_expense);
        fab_list = (FloatingActionButton) findViewById(R.id.fab_list);
        FabOpen = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(this,R.anim.fab_close);
        FabClockwise = AnimationUtils.loadAnimation(this,R.anim.rotate_clockwise);
        FabAnticlockwise = AnimationUtils.loadAnimation(this,R.anim.rotate_anticlockwise);

        fab_plus.setOnClickListener(this);
        fab_list.setOnClickListener(this);
        fab_expense.setOnClickListener(this);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Overview Fragment as home Screen view................
        OverviewFragment overviewFragment=new OverviewFragment();
        androidx.fragment.app.FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.mainView,
                overviewFragment,
                overviewFragment.getTag()).commit();
        ////////////////////////////////////////////////////////

    }

    public void setSupportActionBar(Toolbar toolbar) {
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_overview) {
            // Handle the camera action
            //Toast.makeText(this,"OVERVIEW",Toast.LENGTH_SHORT).show();
            OverviewFragment overviewFragment=new OverviewFragment();
            androidx.fragment.app.FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainView,
                    overviewFragment,
                    overviewFragment.getTag()).commit();

        } else if (id == R.id.nav_income) {
            IncomeFragment incomeFragment=new IncomeFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainView,
                    incomeFragment,
                    incomeFragment.getTag()).commit();

        } else if (id == R.id.nav_expense) {
            FragmentExpense expenseFragment=new FragmentExpense();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainView,
                    expenseFragment,
                    expenseFragment.getTag()).commit();

        } else if (id == R.id.nav_settings) {
            ToolsFragment settingFragment=new ToolsFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainView,
                    settingFragment,
                    settingFragment.getTag()).commit();

        } else if (id == R.id.nav_share) {
            Toast.makeText(this,"SHARE",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_send) {
            Toast.makeText(this,"SEND",Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_plus:
                if (isOpen) {
                    fab_plus.startAnimation(FabAnticlockwise);
                    fab_list.startAnimation(FabClose);
                    fab_expense.startAnimation(FabClose);
                    fab_list.setClickable(false);
                    fab_expense.setClickable(false);
                    isOpen = false;
                } else {
                    fab_plus.startAnimation(FabClockwise);
                    fab_list.startAnimation(FabOpen);
                    fab_expense.startAnimation(FabOpen);
                    fab_list.setClickable(true);
                    fab_expense.setClickable(true);
                    isOpen = true;
                }
                break;

            case R.id.fab_expense:
                FragmentExpense fragment = new FragmentExpense();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainView, fragment);
                transaction.commit();

                break;
            case R.id.fab_list:
                Intent intent = new Intent(getApplicationContext(),ListView1.class);
                startActivity(intent);
                break;
        }


    }
}
