package com.rewu.relics;

import java.util.HashMap;

import com.rewu.relics.Abstract.Relic;
import com.rewu.relics.Enums.RelicType;
import com.rewu.relics.Relics.CravenEdge;
import com.rewu.relics.Relics.DeathBook;
import com.rewu.relics.Relics.EnderBow;
import com.rewu.relics.Relics.Fenthras;
import com.rewu.relics.Relics.GoldenEgg;
import com.rewu.relics.Relics.OmniSaddle;

public class RelicsLibrary {
    
    // Associates a once-instantiated Relic object to every Relic type 
    private static HashMap<RelicType, Relic> relics = new HashMap<RelicType, Relic>();

    // Returns the Relic object associated to a certain relic type
    public static Relic GetRelicFromType(RelicType type) { return relics.get(type); }

    // Registers every existing relic, mandatory for any relic to properly work
    public static void registerRelics() {
        relics.put(RelicType.GOLDEN_EGG, new GoldenEgg());
        relics.put(RelicType.ENDER_BOW, new EnderBow());
        relics.put(RelicType.CRAVEN_EDGE, new CravenEdge());
        relics.put(RelicType.OMNI_SADDLE, new OmniSaddle());
        relics.put(RelicType.FENTHRAS, new Fenthras());
        relics.put(RelicType.DEATH_BOOK, new DeathBook());
    }
}
