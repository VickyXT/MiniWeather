package com.vickyxt.miniweather;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by vickyxt on 2017/11/19.
 */

public class ClearEditText extends EditText implements View.OnFocusChangeListener, TextWatcher{
    private Drawable mClearDrawable;
    public ClearEditText(Context context, AttributeSet attributeSet){
        this(context, attributeSet,android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attributeSet, int defStyle){
        super(context,attributeSet,defStyle);
        init();
    }

    private void init(){
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null){
            mClearDrawable = getResources().getDrawable(R.drawable.btn);
        }
        mClearDrawable.setBounds(0,0,mClearDrawable.getIntrinsicWidth(),mClearDrawable.getIntrinsicHeight());
        setClear;
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    public boolean onTouchEvent(MotionEvent event){
        if (getCompoundDrawables()[2]!=null){
            if (event.getAction() == MotionEvent.ACTION_UP){

            }
        }
    }
}
