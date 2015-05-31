package com.jupiter.rogue.Model.Items;

/**
 * Created by Johan on 2015-05-30.
 */
@lombok.Data
public class SpeedRing extends Ring{

    public SpeedRing(){

        this.strengthRequirement = 0;
        this.agilityRequirement = 0;
        this.intellectRequirement = 0;

        this.description = ("A thorned creature with a blue hue, had the need to traverse swiftly.");
    }

}
