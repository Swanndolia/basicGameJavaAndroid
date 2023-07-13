package dev.swanndolia.idlemmorpg.tools.animations;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;

import dev.swanndolia.idlemmorpg.characters.Player;

public abstract class CustomAnimationDrawableNew extends AnimationDrawable {

    Handler mAnimationHandler;

    public CustomAnimationDrawableNew(AnimationDrawable aniDrawable, Context context, Player player, String action) {

        //TODO add data to item to make them visible
        for (int animationStep = 0; animationStep < aniDrawable.getNumberOfFrames(); animationStep++) {
            DynamicPlayerFrameBuilder builder = new DynamicPlayerFrameBuilder(context, action, animationStep);
            builder.addBodyparts(player.getBodypartsList());
            this.addFrame(builder.build(), aniDrawable.getDuration(animationStep));
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