package dev.swanndolia.idlemmorpg.tools.animations;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.content.res.AppCompatResources;

import dev.swanndolia.idlemmorpg.characters.Player;

public abstract class CustomAnimationDrawableNew extends AnimationDrawable {

    /**
     * Handles the animation callback.
     */
    Handler mAnimationHandler;

    public CustomAnimationDrawableNew(AnimationDrawable aniDrawable, Context context, Player player) {
        // Add each frame to our animation drawable

        player.addBodypart(Bodypart.SLOTS.body, "player");
        for (int i = 0; i < aniDrawable.getNumberOfFrames(); i++) {
                DynamicCharacterFrameBuilder builder = new DynamicCharacterFrameBuilder(context,"attack" , i + 1);
                builder.addBodyparts(player.getBodyparts());
                Drawable drawable = builder.build();
            this.addFrame(drawable, aniDrawable.getDuration(i));
        }
    }

    @Override
    public void start() {
        super.start();
        mAnimationHandler = new Handler();
        mAnimationHandler.post(new Runnable() {
            @Override
            public void run() {
                onAnimationStart();
            }
        });
        mAnimationHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onAnimationFinish();
            }
        }, getTotalDuration());

    }

    public int getTotalDuration() {

        int iDuration = 0;

        for (int i = 0; i < this.getNumberOfFrames(); i++) {
            iDuration += this.getDuration(i);
        }

        return iDuration;
    }

    public abstract void onAnimationFinish();

    public abstract void onAnimationStart();
}