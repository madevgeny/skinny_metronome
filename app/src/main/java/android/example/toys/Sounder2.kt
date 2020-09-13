package android.example.toys

import android.app.Activity
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.M)
class Sounder2 internal constructor(activity: Activity) : ISounder {
	val duration = 10 // duration of sound
	val sampleRate = 44100 // Hz (maximum frequency is 7902.13Hz (B8))
	val numSamples = duration * sampleRate
	private val audioTrack: AudioTrack
	private val buffer = ShortArray(numSamples)

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

		val samples = DoubleArray(numSamples)
		var t = 0.0;
		for (i in 0 until numSamples) {
			//samples[i] = Math.sin(2 * Math.PI * i / (sampleRate / 5)) // Sine wave
			samples[i] = Math.sin(t) // Sine wave
			t += 0.5;

			buffer[i] =(samples[i] * Short.MAX_VALUE).toShort() // Higher amplitude increases volume
			if(i < 241000){
				buffer[i] = 30000
			}
			//buffer[i] = (samples[241000] * Short.MAX_VALUE).toShort()
		}
		buffer[0] = 0;
		audioTrack.play()
		var l = audioTrack.write(buffer, 0, buffer.size, AudioTrack.WRITE_BLOCKING);
		l = 1;
	}
/*
	fun genTone() {
		// fill out the array
		for (i in 0 until numSamples) {
			sample.get(i) = Math.sin(2 * Math.PI * i / (sampleRate / freqOfTone))
		}

		// convert to 16 bit pcm sound array
		// assumes the sample buffer is normalised.
		var idx = 0
		for (dVal in sample) {
			// scale to maximum amplitude
			val `val` = (dVal * 32767).toShort()
			// in 16 bit wav PCM, first byte is the low order byte
			generatedSnd.get(idx++) = (`val` and 0x00ff)
			generatedSnd.get(idx++) = (`val` and 0xff00 ushr 8)
		}
	}
*/
}