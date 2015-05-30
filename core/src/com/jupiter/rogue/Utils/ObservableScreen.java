package com.jupiter.rogue.Utils;

import com.badlogic.gdx.Screen;

import java.beans.PropertyChangeListener;

/**
 * Created by Johan on 30/05/15.
 */
public interface ObservableScreen extends Screen {
    public void addObserver(PropertyChangeListener observer);
    public void removeObserver(PropertyChangeListener observer);
}
