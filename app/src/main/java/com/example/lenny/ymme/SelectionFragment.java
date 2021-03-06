package com.example.lenny.ymme;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenny.ymme.models.Engines;
import com.example.lenny.ymme.models.Makes;
import com.example.lenny.ymme.models.Models;
import com.example.lenny.ymme.models.YMMEElement;
import com.example.lenny.ymme.models.Years;
import com.example.lenny.ymme.network.APILoader;
import com.example.lenny.ymme.util.Config;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectionFragment extends Fragment {

    private static final String ARG_ACTION = "ACTION";
    private static final String ARG_YEAR = "YEAR";
    private static final String ARG_MAKE = "MAKE";
    private static final String ARG_MODEL = "MODEL";


    private String mAction = null;
    private int year =  0;
    private String mMake = null;
    private String mModel = null;

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    List<String> databuff = new ArrayList<>();
    Retrofit myRetro;
    APILoader communicator;
    RecyclerView.LayoutManager layoutManager;

    public SelectionFragment() {
        // Required empty public constructor
    }



    public static SelectionFragment  newInstanceYears() {
        SelectionFragment fragment = new SelectionFragment();
        Bundle args = new Bundle();

        args.putString(ARG_ACTION, Config.Dtypes.Years.toString());

        fragment.setArguments(args);
        return fragment;
    }

    public static SelectionFragment newInstanceMakes(Config.Dtypes type, int year) {
        SelectionFragment fragment = new SelectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACTION, type.toString());
        args.putInt(ARG_YEAR, year);
        fragment.setArguments(args);
        return fragment;
    }

    public static SelectionFragment newInstanceModels(Config.Dtypes type, int year, String make) {

        SelectionFragment fragment = new SelectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACTION, type.toString());
        args.putInt(ARG_YEAR, year);
        args.putString(ARG_MAKE, make);
        fragment.setArguments(args);
        return fragment;
    }

    public static SelectionFragment newInstanceEngines(Config.Dtypes type, int year, String make, String model) {
        SelectionFragment fragment = new SelectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACTION, type.toString());
        args.putInt(ARG_YEAR, year);
        args.putString(ARG_MAKE, make);
        args.putString(ARG_MODEL, model);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAction= getArguments().getString(ARG_ACTION);
            year = getArguments().getInt(ARG_YEAR);
            mMake = getArguments().getString(ARG_MAKE);
            mModel = getArguments().getString(ARG_MODEL);

        }
//Call API no matter what for now. This can be improved.
      //  if(savedInstanceState == null) {
            if(mAction == Config.Dtypes.Years.toString()) {
                communicator.loadYears();
            }else if (mAction == Config.Dtypes.Makes.toString()){

                communicator.loadMakes(year);
            }else if(mAction == Config.Dtypes.Models.toString()) {

                communicator.loadModels(year, mMake);
            } else if(mAction == Config.Dtypes.Engines.toString()){
                communicator.loadEngines(year, mMake, mModel);
            }
       // }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = new APILoader();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selection, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rvView);


        //   myAdapter = new CarLayoutAdapter(databuff);
        //   recyclerView.setAdapter(myAdapter);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        //myAdapter = new CarLayoutAdapter(view.getContext(),databuff,mAction);

        recyclerView.setAdapter(myAdapter);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        databuff = ((YMMEElement) event.packet).getData();
        myAdapter = new CarLayoutAdapter(getActivity(), (YMMEElement) event.packet,mAction);
        recyclerView.setAdapter(myAdapter);
        updateToolbarForward(event);
    }

    private void updateToolbarForward(MessageEvent event) {
        if(event.packet instanceof Models) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(((Models)event.packet).getYear() +">"+((Models)event.packet).getMake());
        }else if (event.packet instanceof Makes){
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(((Makes)event.packet).getYear());
        }else if (event.packet instanceof Engines){
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(((Engines)event.packet).getYear() +">"+((Engines)event.packet).getMake()+">"+((Engines)event.packet).getModel() );
        }
    }


    @Override
    public void onStart() {
        super.onStart();
      EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
     ///abc
      EventBus.getDefault().unregister(this);
        super.onStop();
    }

}
