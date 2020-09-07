package android.example.toys

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException

class NotesHolder//svg.setDocumentHeight((float)imageButton.getHeight() * 0.75f);
//svg.setDocumentWidth((float)imageButton.getWidth() * 0.75f);
//svg.setDocumentHeight((float)imageButton.getHeight() * 0.75f);
//svg.setDocumentWidth((float)imageButton.getWidth() * 0.75f);
//svg.setDocumentHeight((float)imageButton.getHeight() * 0.75f);
//svg.setDocumentWidth((float)imageButton.getWidth() * 0.75f);
//svg.setDocumentHeight((float)imageButton.getHeight() * 0.75f);
//svg.setDocumentWidth((float)imageButton.getWidth() * 0.75f);
internal constructor(ctx: Context) {
	lateinit var quarter: Drawable
	lateinit var triple: Drawable
	lateinit var eight: Drawable
	lateinit var sixteen: Drawable

	init {
		try {
			var svg = SVG.getFromResource(ctx, R.raw.r1_4)
			//svg.setDocumentHeight((float)imageButton.getHeight() * 0.75f);
			//svg.setDocumentWidth((float)imageButton.getWidth() * 0.75f);
			quarter = PictureDrawable(svg.renderToPicture())
			svg = SVG.getFromResource(ctx, R.raw.r1_3)
			//svg.setDocumentHeight((float)imageButton.getHeight() * 0.75f);
			//svg.setDocumentWidth((float)imageButton.getWidth() * 0.75f);
			triple = PictureDrawable(svg.renderToPicture())
			svg = SVG.getFromResource(ctx, R.raw.r1_8)
			//svg.setDocumentHeight((float)imageButton.getHeight() * 0.75f);
			//svg.setDocumentWidth((float)imageButton.getWidth() * 0.75f);
			eight = PictureDrawable(svg.renderToPicture())
			svg = SVG.getFromResource(ctx, R.raw.r1_16)
			//svg.setDocumentHeight((float)imageButton.getHeight() * 0.75f);
			//svg.setDocumentWidth((float)imageButton.getWidth() * 0.75f);
			sixteen = PictureDrawable(svg.renderToPicture())
		} catch (e: SVGParseException) {
		}
	}

	fun getNote(n: Int): Drawable?{
		return when (n) {
			1-> quarter
			2-> eight
			3-> triple
			4-> sixteen
			else-> null
		}
	}
}