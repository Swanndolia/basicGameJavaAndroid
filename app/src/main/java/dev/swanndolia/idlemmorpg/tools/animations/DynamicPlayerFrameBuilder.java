package dev.swanndolia.idlemmorpg.tools.animations;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.List;

public class DynamicPlayerFrameBuilder {

    private final Context context;
    private final Integer stepNumber;
    private final String action;
    private final LayerDrawable layerDrawable;

    public DynamicPlayerFrameBuilder(Context context, String action, Integer stepNumber) {
        this.stepNumber = stepNumber;
        this.context = context;
        this.action = action;
        this.layerDrawable = new LayerDrawable(new Drawable[0]);
    }

    public void addBodyparts(List<Bodypart> bodyparts) {
        String gender = "male";
        Drawable drawable = null;
        for (Bodypart bodypart : bodyparts) {
            Log.e("currentAnim", "player_" + action + "_" + gender + "_" + stepNumber); //todo name scheme would look like "player_<action>_<gender>_<bodypart_slot>_<bodypart_name>_<step>"
            String bodypartName = bodypart.getName();
            if (bodypart.getSlot() == Bodypart.SLOTS.body) {
                gender = bodypart.getName();
                drawable = AppCompatResources.getDrawable(context, context.getResources().getIdentifier("player_" + action + "_" + gender + "_" + stepNumber, "drawable", context.getPackageName()));
                continue;
            }
            if (!bodypartName.equals("none")) {
                try {
                    drawable = AppCompatResources.getDrawable(context, context.getResources().getIdentifier("player_" + action + "_" + gender + "_" + bodypart.getSlot() + "_" + bodypartName + "_" + stepNumber, "drawable", context.getPackageName()));
                } catch (Exception e) {
                    Log.e("LayerListBuilder", "Error getting drawable for ID " + bodypartName, e);
                }
            }
            if (drawable != null) {
                layerDrawable.addLayer(drawable);
            }
        }
    }

    public Drawable build() {
        return layerDrawable;
    }
}