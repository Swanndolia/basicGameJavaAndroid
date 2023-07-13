package dev.swanndolia.idlemmorpg.tools.animations;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.List;

public class DynamicCharacterFrameBuilder {

    private final Context context;
    private final Integer stepNumber;
    private final String action;
    private final LayerDrawable layerDrawable;

    public DynamicCharacterFrameBuilder(Context context, String action, Integer stepNumber) {
        this.stepNumber = stepNumber;
        this.context = context;
        this.action = action;
        this.layerDrawable = new LayerDrawable(new Drawable[0]);
    }

    public void addBodyparts(List<Bodypart> bodyparts) {
        for (Bodypart bodypart : bodyparts) {
            String name = bodypart.getName();
            Drawable drawable = null;
            if (!name.equals("none")) {
                try {
                    drawable = AppCompatResources.getDrawable(context, context.getResources().getIdentifier(name + "_" + action + "_" + stepNumber.toString() , "drawable", context.getPackageName()));
                } catch (Exception e) {
                    Log.e("LayerListBuilder", "Error getting drawable for ID " + name, e);
                }
            }

            if (drawable != null) {
                LayerItem item = new LayerItem(drawable);
                item.setId(bodypart.getName());
                layerDrawable.addLayer(item);
            }
        }
    }

    public Drawable build() {
        return layerDrawable;
    }
}

class LayerItem extends Drawable {

    private final Drawable drawable;
    private String id;

    public LayerItem(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        drawable.draw(canvas);
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    @Override
    public int getIntrinsicWidth() {
        return drawable.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return drawable.getIntrinsicHeight();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}