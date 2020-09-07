package android.example.toys

import android.graphics.*
import android.graphics.drawable.Drawable


class PresetDrawable(private val text: String) : Drawable() {
	private val paint: Paint = Paint()

	override fun draw(canvas: Canvas) {
		canvas.drawText(text, 0f, 0f, paint)
	}

	override fun setAlpha(alpha: Int) {
		paint.alpha = alpha
	}

	override fun setColorFilter(cf: ColorFilter?) {
		paint.colorFilter = cf
	}

	override fun getOpacity(): Int {
		return PixelFormat.TRANSLUCENT
	}

	init {
		paint.color = Color.WHITE
		paint.textSize = 22f
		paint.isAntiAlias = true
		paint.isFakeBoldText = true
		paint.setShadowLayer(6f, 0f, 0f, Color.BLACK)
		paint.style = Paint.Style.FILL
		paint.textAlign = Paint.Align.LEFT
	}
}