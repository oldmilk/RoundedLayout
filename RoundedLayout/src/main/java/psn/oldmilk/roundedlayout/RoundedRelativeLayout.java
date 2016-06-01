package psn.oldmilk.roundedlayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by CarlChia on 1/6/16.
 */
public class RoundedRelativeLayout extends RelativeLayout {

    public static final int ROUNDEDTYPE_CIRCLE = 0;
    public static final int ROUNDEDTYPE_RECTANGLE = 1;

    private float ROUNDING_RADIUS = 0.0f;
    private int ROUNDED_TYPE = 0;

    private Path mPath;

    public RoundedRelativeLayout(Context context) {
        super(context);
        init(context, null);
    }

    public RoundedRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundedRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RoundedRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private  void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RoundedLayout,
                0, 0);
        try {
            ROUNDING_RADIUS = a.getDimension(R.styleable.RoundedLayout_rounding_radius, 20f);
            ROUNDED_TYPE = a.getInt(R.styleable.RoundedLayout_rounded_type, 0);
        } finally {
            a.recycle();
        }
    }

    public void setCornerRadius(int radius) {
        ROUNDING_RADIUS = radius;
        invalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.clipPath(mPath);
        super.draw(canvas);
        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if(ROUNDED_TYPE == ROUNDEDTYPE_RECTANGLE) {
            RectF r = new RectF(0, 0, w, h);
            mPath = new Path();
            mPath.addRoundRect(r, ROUNDING_RADIUS, ROUNDING_RADIUS, Path.Direction.CW);
            mPath.close();
        }
        else if(ROUNDED_TYPE == ROUNDEDTYPE_CIRCLE){

            float halfWidth = w / 2f;
            float halfHeight = h / 2f;
            float centerX = halfWidth;
            float centerY = halfHeight;
            mPath = new Path();
            mPath.addCircle(centerX, centerY, Math.min(halfWidth, halfHeight), Path.Direction.CW);
            mPath.close();

        }
    }
}
