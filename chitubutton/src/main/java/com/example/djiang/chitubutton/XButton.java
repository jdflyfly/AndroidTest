package com.example.djiang.chitubutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;

/**
 * 按照UI设计规范，配置了XButton的背景颜色、字号、字色、圆角等默认属性，绝大多数情况不需要在xml中每次都显式指定这些属性（除非一定需要覆盖这些默认的属性），
 * 支持覆盖背景颜色、字号、圆角、描边等属性，具体参考XButtonsAttrs 的定义。
 * <p/>
 * 使用时一般只需在xml中指定XButton的 宽、高、xText、xEnable属性，并且可以通过enable() 和 disable()两个方法动态切换XButton的状态。
 */
public class XButton extends Button {

    Context context;

    // background color
    private int chituButtonGreenBgColor = 0xFF39BF9E;
    private int chituButtonPressedBgColor = 0xFF33AC8E;
    private int chituButtonDisableBgColor = 0xFFE4E4E4;
    //text color
    private int chituButtonTextColor = Color.WHITE;
    private int chituButtonDisableTextColor = 0xFF949494;


    // # Background Attributes
    private int mDefaultBackgroundColor = chituButtonGreenBgColor;
    private int mFocusBackgroundColor = chituButtonPressedBgColor;


    // # Text Attributes
    private int mDefaultTextColor = chituButtonTextColor;
    private int mDefaultTextSize = Math.round(15 * getResources().getDisplayMetrics().scaledDensity);//default 15sp
    private int mDefaultTextGravity = Gravity.CENTER;
    private String mText = null;

    //border Attributes
    private int mBorderColor = Color.TRANSPARENT;
    private int mBorderWidth = 0;
    private int mRadius = Math.round(4 * getResources().getDisplayMetrics().density);//default 7dp

    //enable status
    private boolean xEnabled = true;


    public XButton(Context context) {
        super(context);
        this.context = context;
    }

    public XButton(Context context, AttributeSet attrs) {
        super(context, attrs, android.R.style.Widget_DeviceDefault_Button_Borderless);
        this.context = context;
        TypedArray attrsArray = context.obtainStyledAttributes(attrs, R.styleable.XButtonsAttrs, 0, 0);
        initAttributeArray(attrsArray);
        attrsArray.recycle();
        initializeXButton();

    }

    /**
     * 重新激活按钮，切换为可点击状态
     */
    public void enable() {
        xEnabled = true;
        initializeXButton();
    }

    /**
     * 按钮切换为 不可点击状态
     */
    public void disable() {
        xEnabled = false;
        initializeXButton();
    }


    private void initializeXButton() {

        if (xEnabled) {
            super.setEnabled(true);
            this.setClickable(true);
            this.setFocusable(true);
        } else {
            super.setEnabled(false);
        }


        if (mText != null) {
            this.setText(mText);
            this.setTextSize(mDefaultTextSize);

            if (xEnabled)
                setTextColor(mDefaultTextColor);
            else
                setTextColor(chituButtonDisableTextColor);

        }

        this.setGravity(mDefaultTextGravity | Gravity.CENTER_VERTICAL);
        setupBackground();

    }


    @SuppressLint("NewApi")
    private void setupBackground() {

        if (xEnabled) {
            // Default Drawable
            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadius(mRadius);
            drawable.setColor(mDefaultBackgroundColor);
            if (mBorderColor != 0) {
                drawable.setStroke(mBorderWidth, mBorderColor);
            }

            // Focus/Pressed Drawable
            GradientDrawable drawable2 = new GradientDrawable();
            drawable2.setCornerRadius(mRadius);
            drawable2.setColor(mFocusBackgroundColor);
            if (mBorderColor != 0) {
                drawable2.setStroke(mBorderWidth, mBorderColor);
            }

            StateListDrawable states = new StateListDrawable();

            if (mFocusBackgroundColor != 0) {
                states.addState(new int[]{android.R.attr.state_pressed}, drawable2);
                states.addState(new int[]{android.R.attr.state_focused}, drawable2);
            }
            states.addState(new int[]{}, drawable);

            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                this.setBackgroundDrawable(states);
                setPadding(0, 0, 0, 0);
            } else {
                this.setBackground(states);
                setPadding(0, 0, 0, 0);
            }
        } else {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadius(mRadius);
            drawable.setColor(chituButtonDisableBgColor);
            setBackgroundDrawable(drawable);

            super.setEnabled(false);
        }
    }



    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawableLeft = drawables[0];
            if (drawableLeft != null) {
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = 0;
                drawableWidth = drawableLeft.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
            }
        }
        super.onDraw(canvas);
    }

    private void initAttributeArray(TypedArray attrsArray) {
        mDefaultBackgroundColor = attrsArray.getColor(R.styleable.XButtonsAttrs_xdefaultColor, mDefaultBackgroundColor);
        mFocusBackgroundColor = attrsArray.getColor(R.styleable.XButtonsAttrs_xfocusColor, mFocusBackgroundColor);

        mDefaultTextColor = attrsArray.getColor(R.styleable.XButtonsAttrs_xtextColor, mDefaultTextColor);
        mDefaultTextSize = (int) attrsArray.getDimension(R.styleable.XButtonsAttrs_xtextSize, mDefaultTextSize);
        float sizeInSP = mDefaultTextSize / getResources().getDisplayMetrics().scaledDensity;
        mDefaultTextSize = Math.round(sizeInSP);
        mDefaultTextGravity = attrsArray.getInt(R.styleable.XButtonsAttrs_xtextGravity, mDefaultTextGravity);

        mBorderColor = attrsArray.getColor(R.styleable.XButtonsAttrs_xborderColor, mBorderColor);
        mBorderWidth = (int) attrsArray.getDimension(R.styleable.XButtonsAttrs_xborderWidth, mBorderWidth);
        mRadius = (int) attrsArray.getDimension(R.styleable.XButtonsAttrs_xcornerRadius, mRadius);

        String text = attrsArray.getString(R.styleable.XButtonsAttrs_xtext);
        if (text != null)
            mText = text;

        xEnabled = attrsArray.getBoolean(R.styleable.XButtonsAttrs_xenable, xEnabled);

    }
}
