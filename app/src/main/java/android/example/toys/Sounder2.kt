package android.example.toys

import android.app.Activity
import android.example.toys.WavFile.Companion.openWavFile
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.M)
class Sounder2 internal constructor(activity: Activity) : ISounder {
	val sampleRate = 44100 // Hz (maximum frequency is 7902.13Hz (B8))
	private val audioTrack: AudioTrack
	private val buffer: ShortArray
	private val anotherBuffer: ShortArray

	override fun play(clickType: ClickType) {
		when (clickType) {
			ClickType.MainClick -> {
				audioTrack.write(buffer, 0, buffer.size);
			}
			ClickType.AnotherClick -> {
				audioTrack.write(anotherBuffer, 0, anotherBuffer.size);
			}
			ClickType.SkippedClick -> {
			}
		}
	}

	init {

		val audioFormat = AudioFormat.Builder()
			.setEncoding(AudioFormat.ENCODING_PCM_16BIT)
			.setSampleRate(sampleRate)
			.setChannelMask((AudioFormat.CHANNEL_OUT_MONO))
			.build()

		val minSize = AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT)
		audioTrack = AudioTrack.Builder()
			.setAudioAttributes(AudioAttributes.Builder()
				.setUsage(AudioAttributes.USAGE_ALARM)
				.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
				.build())
			.setAudioFormat(audioFormat)
			.setTransferMode(AudioTrack.MODE_STREAM)
			.setBufferSizeInBytes(minSize)
			.build()

		val fclick = activity.resources.openRawResource(R.raw.click)
		val clickWav = openWavFile(fclick)
		val fanotherclick = activity.resources.openRawResource(R.raw.another_click)
		val anotherClickWav = openWavFile(fanotherclick)

		buffer = ShortArray(clickWav.numFrames.toInt())
		clickWav.readFrames(buffer, clickWav.numFrames)

		anotherBuffer = ShortArray(anotherClickWav.numFrames.toInt())
		anotherClickWav.readFrames(anotherBuffer, anotherClickWav.numFrames)

		audioTrack.play()
	}
}