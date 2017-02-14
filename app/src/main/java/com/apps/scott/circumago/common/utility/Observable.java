package com.apps.scott.circumago.common.utility;

import java.util.ArrayList;

/**
 * Created by Scott on 10/24/2016.
 */
public class Observable {
    private ArrayList<Observer> mObservers;
    private int mId;

    public Observable() {
        mObservers = new ArrayList<>();
        mId = 0;
    }

    public void setId(int id) { mId = id; }
    public int getId() { return mId; }

    public void addObserver(Observer observer) { mObservers.add(observer); }

    public void removeObserver(Observer observer) { mObservers.remove(observer); }

    public void notifyObservers() {
        for (Observer observer : mObservers) observer.update(mId);
    }
}