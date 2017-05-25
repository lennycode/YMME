package com.example.lenny.ymme;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import com.example.lenny.ymme.network.APILoader;
import com.example.lenny.ymme.util.Config;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    List<String> databuff = new ArrayList<>();
    Retrofit myRetro;
    APILoader communicator;
    RecyclerView.LayoutManager layoutManager;

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

        SelectionFragment selectionFragment = SelectionFragment.newInstanceYears();
        getSupportFragmentManager().beginTransaction().add(R.id.frag_container, selectionFragment, getResources().getString(R.string.YEARS_FRAG)).addToBackStack(getResources().getString(R.string.YEARS_FRAG)).commit();


    }

    @Override
    public void onBackPressed() {
        SelectionFragment selectionFragment = (SelectionFragment) getSupportFragmentManager().findFragmentByTag(getResources().getString(R.string.YEARS_FRAG));
        if (selectionFragment != null && selectionFragment.isVisible()) {

            new AlertDialog.Builder(this).setTitle("Exit App").setMessage("Are you sure you want to exit?").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else {
            super.onBackPressed();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFragmentSwapEvent(FragmentSwapEvent event) {
        String[] params = event.packet;
        String action = params[0];
        String selection = event.selection;
        switch (action) {
            case "Years": //Boot up the makes fragment
                SelectionFragment makesFragment = (SelectionFragment) getSupportFragmentManager().findFragmentByTag(getResources().getString(R.string.MAKES_FRAG));
                if (makesFragment == null) {
                    makesFragment = SelectionFragment.newInstanceMakes(Config.Dtypes.Makes, Integer.parseInt(selection));
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, makesFragment, getResources().getString(R.string.MAKES_FRAG)).addToBackStack(getResources().getString(R.string.MAKES_FRAG)).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, makesFragment);
                }

                break;
            case "Makes":

                SelectionFragment modelsFragment = (SelectionFragment) getSupportFragmentManager().findFragmentByTag(getResources().getString(R.string.MODELS_FRAG));
                if (modelsFragment == null) {
                    modelsFragment = SelectionFragment.newInstanceModels(Config.Dtypes.Models, Integer.parseInt(params[1]), selection);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, modelsFragment, getResources().getString(R.string.MAKES_FRAG)).addToBackStack(getResources().getString(R.string.ENGINES_FRAG)).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, modelsFragment);
                }

                break;
            case "Models":
                SelectionFragment enginesFragment = (SelectionFragment) getSupportFragmentManager().findFragmentByTag(getResources().getString(R.string.ENGINES_FRAG));
                if (enginesFragment == null) {
                    enginesFragment = SelectionFragment.newInstanceEngines(Config.Dtypes.Engines, Integer.parseInt(params[1]), params[2], selection);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, enginesFragment, getResources().getString(R.string.ENGINES_FRAG)).addToBackStack(getResources().getString(R.string.ENGINES_FRAG)).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, enginesFragment);
                }
                break;
        }

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


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {

        super.onStop();
        EventBus.getDefault().unregister(this);
    }


}
