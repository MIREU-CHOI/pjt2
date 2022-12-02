package com.example.study02;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class SampleView extends View {

    private Paint paint = new Paint();  // 글씨 출력하기 위함
    private Bitmap bmp;

    public SampleView(Context context) {
        super(context);
        setBackgroundColor(Color.WHITE); // 바탕이 하얀색으로

        Resources res = context.getResources();
        bmp = BitmapFactory.decodeResource(res, R.drawable.grinning3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setTextSize(70);
        canvas.drawText("Hello world!", 10, 100, paint);

        canvas.drawBitmap(bmp, 0, 0, null);
    }   // => 이걸 액티비티에 뿌려줘야 됨  -  MainActivity
}
