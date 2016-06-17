package com.xogrp.xoapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class XOAbstractFragment
        extends Fragment {
    protected Logger mLogger = LoggerFactory.getLogger(this.getClass());


    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onXOCreate(savedInstanceState);
    }


    @Override
    public final void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onXOActivityCreated(savedInstanceState);
    }

    @Override
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        return onXOCreateView(layoutInflater, container, savedInstanceState);
    }

    @Override
    public final void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onXOViewCreated(view, savedInstanceState);
    }

    @Override
    public final void onDestroyView() {
        onXODestroyView();

        super.onDestroyView();
    }

    @Override
    public final void onDetach() {
        onXODetach();

        super.onDetach();
    }

    @Override
    public final void onAttach(Activity activity) {
        super.onAttach(activity);
        onXOAttach(activity);
    }

    protected void onXOCreate(Bundle savedInstanceState) {
    }

    protected abstract View onXOCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState);

    protected void onXOViewCreated(View view, Bundle savedInstanceState) {
    }

    protected void onXOActivityCreated(Bundle savedInstanceState) {
    }


    protected void onXODestroyView() {
    }

    protected void onXODetach() {
    }

    protected void onXOAttach(Activity activity) {
    }


}
