package dev.swanndolia.idlemmorpg.tools.firebase;

import android.graphics.Color;

public class GetRgbFromRarity {
    public int GetRgbFromRarity(String rarity) {
        if (rarity != null) {
            switch (rarity) {
                case "F":
                    return Color.rgb(180, 180, 180);
                case "C":
                    return Color.rgb(0, 255, 0);
                case "B":
                    return Color.rgb(0, 0, 255);
                case "A":
                    return Color.rgb(127, 0, 255);
                case "S":
                    return Color.rgb(255, 215, 0);
                case "SS":
                    return Color.rgb(255, 0, 0);
                default:
                    return Color.rgb(0, 0, 0);
            }
        } else return Color.rgb(0, 0, 0);
    }
}
