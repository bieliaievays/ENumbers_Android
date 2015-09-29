package com.example.eNumbers;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Iuliia on 22.09.2015.
 */
public class ENumbFlag extends ImageView {
    private static final int[] STATE_RED = {R.attr.state_red};
    private static final int[] STATE_YELLOW = {R.attr.state_yellow};
    private static final int[] STATE_GREEN = {R.attr.state_green};
    private static final int[] STATE_GREY = {R.attr.state_grey};
    private Context context;

    private boolean mIsRed;
    private boolean mIsGreen;
    private boolean mIsYellow;
    private boolean mIsGrey;

    public ENumbFlag(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ENumbFlag(Context context) {
        super(context);
    }

    public void setmIsRed(boolean mIsRed) {
        setDefaults();
        this.mIsRed = mIsRed;
        // Refresh the drawable state so that it includes the message unread
        // state if required.
        refreshDrawableState();
    }

    public void setmIsGreen(boolean mIsGreen) {
        setDefaults();
        this.mIsGreen = mIsGreen;
        // Refresh the drawable state so that it includes the message unread
        // state if required.
        refreshDrawableState();
    }

    public void setmIsYellow(boolean mIsYellow) {
        setDefaults();
        this.mIsYellow = mIsYellow;
        // Refresh the drawable state so that it includes the message unread
        // state if required.
        refreshDrawableState();
    }

    public void setmIsGrey(boolean mIsGrey) {
        setDefaults();
        this.mIsGrey = mIsGrey;
        // Refresh the drawable state so that it includes the message unread
        // state if required.
        refreshDrawableState();
    }

    //TODO why I should call this? But deletion gives error - few states in the same time
    public void setDefaults() {
        mIsGrey = false;
        mIsRed = false;
        mIsYellow = false;
        mIsGreen = false;
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        try {
            // If the message is unread then we merge our custom message unread state into
            // the existing drawable state before returning it.
            if (mIsGreen && !mIsGrey && !mIsRed && !mIsYellow) {
                // We are going to add 1 extra state.
                final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

                mergeDrawableStates(drawableState, STATE_GREEN);
                return drawableState;
            }
            if (mIsYellow && !mIsGrey && !mIsRed && !mIsGreen) {
                // We are going to add 1 extra state.
                final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

                mergeDrawableStates(drawableState, STATE_YELLOW);
                return drawableState;
            }
            if (mIsRed && !mIsYellow && !mIsGreen && !mIsGrey) {
                // We are going to add 1 extra state.
                final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

                mergeDrawableStates(drawableState, STATE_RED);
                return drawableState;
            }
            if (mIsGrey && !mIsGreen && !mIsYellow && !mIsRed) {
                // We are going to add 1 extra state.
                final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

                mergeDrawableStates(drawableState, STATE_GREY);
                return drawableState;
            } else {
                return super.onCreateDrawableState(extraSpace);
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return null;
    }
}
