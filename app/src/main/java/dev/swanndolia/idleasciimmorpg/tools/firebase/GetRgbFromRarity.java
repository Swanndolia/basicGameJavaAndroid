package dev.swanndolia.idleasciimmorpg.tools.firebase;

import android.graphics.Color;

public class GetRgbFromRarity {
    public int GetRgbFromRarity(String rarity) {
        if (rarity != null) {
            switch (rarity) {
                case "Broken":
                    return Color.rgb(180, 180, 180);
                case "Uncommon":
                    return Color.rgb(0, 255, 0);
                case "Rare":
                    return Color.rgb(0, 0, 255);
                case "Epic":
                    return Color.rgb(127, 0, 255);
                case "Legendary":
                    return Color.rgb(255, 215, 0);
                case "Unique":
                    return Color.rgb(255, 0, 0);
                case "Common":
                default:
                    return Color.rgb(0, 0, 0);
            }
        } else return Color.rgb(0, 0, 0);
    }
}
