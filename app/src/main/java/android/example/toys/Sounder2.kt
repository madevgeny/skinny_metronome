package android.example.toys

import android.app.Activity
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.math.exp
import kotlin.math.sin

@RequiresApi(Build.VERSION_CODES.M)
class Sounder2 internal constructor(activity: Activity) : ISounder {
	val duration = 10 // duration of sound
	val sampleRate = 44100 // Hz (maximum frequency is 7902.13Hz (B8))
	val numSamples = duration * sampleRate
	private val audioTrack: AudioTrack
	private val buffer = ShortArray(numSamples)

	private val freqOfTone = 440.0 // hz
	private val sample = DoubleArray(numSamples)

	override fun play(clickType: ClickType) {
		when (clickType) {
			ClickType.MainClick -> {
				audioTrack.write(buffer, 0, buffer.size);
			}
			ClickType.AnotherClick -> {
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

		genTone()
		audioTrack.play()
		audioTrack.write(buffer, 0, buffer.size);
	}

	fun genTone() {
		for (i in 0 until numSamples) {
			sample[i] = sin((2 * Math.PI - .001) * i / (sampleRate / freqOfTone)) * (1 / exp(i.toDouble() / 10000))
		}

		for (i in 0 until numSamples) {
			buffer[i] = (sample[i] * Short.MAX_VALUE).toInt().toShort()
		}
	}
}