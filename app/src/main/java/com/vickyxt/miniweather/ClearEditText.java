package com.vickyxt.miniweather;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by vickyxt on 2017/11/19.
 */

public class ClearEditText extends android.support.v7.widget.AppCompatEditText implements View.OnFocusChangeListener, TextWatcher{
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
//            mClearDrawable = getResources().getDrawable(R.drawable.btn);
        }
        mClearDrawable.setBounds(0,0,mClearDrawable.getIntrinsicWidth(),mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (getCompoundDrawables()[2]!=null){
            if (event.getAction() == MotionEvent.ACTION_UP){
                boolean touchable = event.getX() > (getWidth() - getPaddingRight() - mClearDrawable.getIntrinsicWidth())&&(event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable){
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View view,boolean hasFocus){
        if (hasFocus){
            setClearIconVisible(getText().length()>0);
        }
        else {
            setClearIconVisible(false);
        }
    }

    protected void setClearIconVisible(boolean visible){
        Drawable right = visible?mClearDrawable:null;
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],right,getCompoundDrawables()[3]);
    }

    @Override
    public void onTextChanged(CharSequence s,int start,int count,int after){
        setClearIconVisible(s.length()>0);
    }

    @Override
    public void beforeTextChanged(CharSequence s,int start,int count,int after){

    }

    @Override
    public void afterTextChanged(Editable s){

    }

//    晃动动画
//    public void setShakeAnimation(){
//        this.setAnimation(shakeAnimation(5));
//    }
}
