package android.example.toys

import android.app.Activity
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import androidx.annotation.RequiresApi

class Sounder2 @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) internal constructor(activity: Activity) : ISounder {
	private val pool: SoundPool = SoundPool.Builder()
		.setMaxStreams(1)
		.setAudioAttributes(AudioAttributes.Builder()
			.setUsage(AudioAttributes.USAGE_MEDIA)
			.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
			.build()).build()
	private val clickId: Int
	private val anotherClickId: Int

	override fun play(clickType: ClickType) {
		when (clickType) {
			ClickType.MainClick -> pool.play(clickId, 1.0f, 1.0f, 1, 0, 1f)
			ClickType.AnotherClick -> pool.play(anotherClickId, 1.0f, 1.0f, 1, 0, 1f)
			ClickType.SkippedClick -> {
			}
		}
	}

	init {
		var afd = activity.resources.openRawResourceFd(R.raw.click)
		clickId = pool.load(afd, 1)
		afd = activity.resources.openRawResourceFd(R.raw.another_click)
		anotherClickId = pool.load(afd, 1)
	}
}