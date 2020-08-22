package android.example.toys;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public final class LampView extends View {
	enum Color{
		RED,
		BLUE
	}

	private Bitmap redBitmap;
	private Bitmap blueBitmap;
	private Bitmap bitmap;
	private final Rect bounds = new Rect();

	private void init(Context context){
		Resources res = context.getResources();
		redBitmap = BitmapFactory.decodeResource(res, R.drawable.red);
		blueBitmap = BitmapFactory.decodeResource(res, R.drawable.blue);

		bitmap = blueBitmap;

		setContentDescription(res.getString(
			R.string.lamp_view));
	}

	public LampView(Context context){
		super(context);
		init(context);
	}

	public LampView(Context context, AttributeSet attrs){
		super(context, attrs);
		init(context);
	}

	public LampView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	void setState(Color color){
		switch (color){
			case RED:
				bitmap = redBitmap;
				break;
			case BLUE:
				bitmap = blueBitmap;
				break;
		}
		invalidate();
	}

	void switchState(){
		if(bitmap == redBitmap){
			bitmap = blueBitmap;
		}else{
			bitmap = redBitmap;
		}
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.getClipBounds(bounds);
		canvas.drawBitmap(bitmap, null, bounds, null);
	}
}
