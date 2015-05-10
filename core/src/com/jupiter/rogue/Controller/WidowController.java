package com.jupiter.rogue.Controller;

import com.jupiter.rogue.Model.Creatures.Widow;
import com.jupiter.rogue.View.WidowView;

/**
 * Created by Johan on 2015-05-10.
 */
public class WidowController extends EnemyController {

    public WidowController(){
        this.enemy = new Widow();
        this.enemyView = new WidowView();
    }
}
