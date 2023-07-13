package dev.swanndolia.idlemmorpg.tools.animations;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;

import dev.swanndolia.idlemmorpg.characters.Player;

public abstract class CustomAnimationDrawableNew extends AnimationDrawable {

    /**
     * Handles the animation callback.
     */
    Handler mAnimationHandler;

    public CustomAnimationDrawableNew(AnimationDrawable aniDrawable, Context context, Player player) {
        // Add each frame to our animation drawable

        player.addBodypart(Bodypart.SLOTS.body, "player");
        player.addBodypart(Bodypart.SLOTS.head, "test");
        for (int i = 0; i < aniDrawable.getNumberOfFrames(); i++) {
            DynamicCharacterFrameBuilder builder = new DynamicCharacterFrameBuilder(context, "attack", i + 1);
            builder.addBodyparts(player.getBodyparts());
            this.addFrame(builder.build(), aniDrawable.getDuration(i));
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