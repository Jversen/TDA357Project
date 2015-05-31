package com.jupiter.rogue.Model.Items;

/**
 * Created by Johan on 2015-05-30.
 */
@lombok.Data
public class StrengthRing extends Ring{

    public StrengthRing(){

        this.strengthRequirement = 0;
        this.agilityRequirement = 0;
        this.intellectRequirement = 0;

        this.description = ("Legends tell of a man of tremendous strength, from the northern capital of Donnerda. " +
                "Perhaps this ring was the source of his power?");
    }

}
