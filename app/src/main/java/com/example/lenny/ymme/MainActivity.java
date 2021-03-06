package com.example.lenny.ymme;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    String state;
    Toolbar toolbar;
    final String baseToolbar = "Please Select a Year";
    private FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if( savedInstanceState == null) {
            toolbar.setSubtitle(baseToolbar);
        }


        SelectionFragment selectionFragment = SelectionFragment.newInstanceYears();
        getSupportFragmentManager().beginTransaction().add(R.id.frag_container, selectionFragment, getResources().getString(R.string.YEARS_FRAG)).addToBackStack(getResources().getString(R.string.YEARS_FRAG)).commit();


    }

    @Override
    public void onBackPressed() {

        updateToolBarReverse();


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

    private void updateToolBarReverse() {
        String[] tbInfo = toolbar.getSubtitle().toString().split(">");
        switch (tbInfo.length){
            case 3:
                toolbar.setSubtitle(tbInfo[0]+">"+tbInfo[1]);
                break;
            case 2:
                toolbar.setSubtitle(tbInfo[0]);
                break;
            case 1:
                toolbar.setSubtitle(baseToolbar);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFragmentSwapEvent(FragmentSwapEvent event) {
        String[] params = event.packet;
        String action = params[0];
        String selection = event.selection;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.forward_animation,
                R.anim.static_animation, R.anim.static_animation, R.anim.reverse_animation);

        try {
            switch (action) {
                case "Years": //Boot up the makes fragment
                    SelectionFragment makesFragment = (SelectionFragment) getSupportFragmentManager().findFragmentByTag(getResources().getString(R.string.MAKES_FRAG));
                    if (makesFragment == null) {
                        makesFragment = SelectionFragment.newInstanceMakes(Config.Dtypes.Makes, Integer.parseInt(selection));
                        fragmentTransaction.replace(R.id.frag_container, makesFragment, getResources().getString(R.string.MAKES_FRAG)).addToBackStack(getResources().getString(R.string.MAKES_FRAG)).commit();
                    } else {
                        fragmentTransaction.replace(R.id.frag_container, makesFragment).commit();
                    }

                    break;
                case "Makes":

                    SelectionFragment modelsFragment = (SelectionFragment) getSupportFragmentManager().findFragmentByTag(getResources().getString(R.string.MODELS_FRAG));
                    if (modelsFragment == null) {
                        modelsFragment = SelectionFragment.newInstanceModels(Config.Dtypes.Models, Integer.parseInt(params[1]), selection);
                        fragmentTransaction.replace(R.id.frag_container, modelsFragment, getResources().getString(R.string.MAKES_FRAG)).addToBackStack(getResources().getString(R.string.ENGINES_FRAG)).commit();
                    } else {
                        fragmentTransaction.replace(R.id.frag_container, modelsFragment).commit();
                    }

                    break;
                case "Models":
                    SelectionFragment enginesFragment = (SelectionFragment) getSupportFragmentManager().findFragmentByTag(getResources().getString(R.string.ENGINES_FRAG));
                    if (enginesFragment == null) {
                        enginesFragment = SelectionFragment.newInstanceEngines(Config.Dtypes.Engines, Integer.parseInt(params[1]), params[2], selection);
                        fragmentTransaction.replace(R.id.frag_container, enginesFragment, getResources().getString(R.string.ENGINES_FRAG)).addToBackStack(getResources().getString(R.string.ENGINES_FRAG)).commit();
                    } else {
                        fragmentTransaction.replace(R.id.frag_container, enginesFragment).commit();
                    }
                    break;
            }
        }catch (Exception e){
            Log.d("FRG-SWAP", e.getMessage());
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
