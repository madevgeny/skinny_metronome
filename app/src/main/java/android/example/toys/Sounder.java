package android.example.toys;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;

import androidx.annotation.RequiresApi;

final public class Sounder {
	private final SoundPool pool;
	private final int clickId, anotherClickId;

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	Sounder(Activity activity){
		pool = new SoundPool.Builder()
			.setMaxStreams(1)
			.setAudioAttributes(new AudioAttributes.Builder()
				.setUsage(AudioAttributes.USAGE_MEDIA)
				.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
				.build()).build();

		AssetFileDescriptor afd = activity.getResources().openRawResourceFd(R.raw.click);
		clickId = pool.load(afd, 1);

		afd = activity.getResources().openRawResourceFd(R.raw.another_click);
		anotherClickId = pool.load(afd, 1);
	}

	void play(ClickType clickType){
		switch (clickType){
			case MainClick:
				pool.play(clickId, 1.0f, 1.0f, 1, 0, 1);
				break;
			case AnotherClick:
				pool.play(anotherClickId, 1.0f, 1.0f, 1, 0, 1);
				break;
			case SkippedClick:
				break;
		}
	}
}
