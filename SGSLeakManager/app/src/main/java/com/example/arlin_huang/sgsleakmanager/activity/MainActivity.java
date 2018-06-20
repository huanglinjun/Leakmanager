package com.example.arlin_huang.sgsleakmanager.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.arlin_huang.sgsleakmanager.FactoryAdapter;
import com.example.arlin_huang.sgsleakmanager.LeakManager;
import com.example.arlin_huang.sgsleakmanager.R;
import com.example.arlin_huang.sgsleakmanager.liteclass.Factories;


import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected static final int CONTEXTMENU_LOGINITEM = 0;
    protected static final int CONTEXTMENU_EDITITEM = 1;
    protected static final int CONTEXTMENU_DELETEITEM = 2;

    private List<Factories> factories = new ArrayList<>();
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private FactoryAdapter factoryAdapter;
    private LeakManager leakManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        leakManager = (LeakManager) getApplication();
        Log.d("mainleakManager", ""+leakManager.getOnlineLogin());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white);
        }
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Data deleted", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "FAB clicked", Toast.LENGTH_SHORT).show();
                    }
                })
                        .show();

            }
        });
        getFactoriesList();

    }

    public void getFactoriesList() {
        factories = LitePal.findAll(Factories.class);
        recyclerView = (RecyclerView) findViewById(R.id.factory_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        factoryAdapter = new FactoryAdapter(factories, this);
        recyclerView.setAdapter(factoryAdapter);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
         /* Switch on the ID of the item, to get what the user selected. */
        switch (item.getItemId()) {
            case CONTEXTMENU_LOGINITEM:
                factoryAdapter.loginItem(factoryAdapter.getFactories());
                getFactoriesList();
                return true;
            case CONTEXTMENU_EDITITEM:
                factoryAdapter.editItem(factoryAdapter.getFactories());
                return true;
            case CONTEXTMENU_DELETEITEM:
                AlertDialog.Builder dialog =new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("确认删除工厂");
                dialog.setMessage("确认删除该工厂信息吗？该操作会同时删除任务和改工厂图像");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        factoryAdapter.removeItem(factoryAdapter.getFactories());
                        getFactoriesList();
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setCancelable(false).create();
                dialog.show();

               return  true;
        }
        return false;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.add_factory:
                Intent intent = new Intent(MainActivity.this, FactoryActivity.class);
                startActivityForResult(intent, 1);
                break;

            default:
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    getFactoriesList();
                }
            case 2:
                if (resultCode == RESULT_OK) {
                    getFactoriesList();
                }
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    getFactoriesList();
                }
            default:
        }

    }
}
