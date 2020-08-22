package android.example.toys

import android.os.Handler
import android.os.Looper

enum class ClickType {
	MainClick, AnotherClick, SkippedClick
}

class Metronome internal constructor() {
	var bpm = 0
		private set

	private var counter = 0
	private var mask = IntArray(0)
	private val handler = Handler(Looper.getMainLooper())
	private val stopwatch = Stopwatch()
	private var clicksPerQuarter = 1
	private var clickCounter = 0
	private var clickInterval: Long = 0

	var callback:(ClickType) -> Unit = {}

	private inner class Click : Runnable {
		override fun run() {
			stopwatch.start()
			DebugText.instance()!!.restartClick(clickInterval)
			var m = 1
			if (mask.isNotEmpty()) {
				m = mask[counter % mask.size]
			}
			if (clickCounter >= clicksPerQuarter) {
				clickCounter = 0
				++counter
			}
			if (m == 0) {
				callback(ClickType.SkippedClick)
			} else {
				callback(if (clickCounter == 0) ClickType.MainClick else ClickType.AnotherClick)
			}
			++clickCounter
			val t = stopwatch.restart()
			handler.postDelayed(this, (clickInterval - t).coerceAtLeast(0))
		}
	}

	private val click = Click()
	private fun calcClickInterval() {
		clickInterval = (60.0f * 1000.0f / (bpm * clicksPerQuarter)).toLong()
	}

	fun incBPM(d: Int):Int {
		bpm += d
		return setBPM(bpm)
	}

	fun decBPM(d: Int):Int {
		bpm -= d
		return setBPM(bpm)
	}

	fun setBPM(b: Int):Int {
		bpm = b.coerceAtLeast(0).coerceAtMost(600)
		calcClickInterval()
		return bpm
	}

	fun setClicksPerQuarter(nClicks: Int) {
		clicksPerQuarter = nClicks.coerceAtLeast(1)
		calcClickInterval()
	}

	fun getClicksPerQuarter(): Int {
		return clicksPerQuarter
	}

	fun setMask(mask: IntArray) {
		this.mask = mask
	}

	fun start() {
		counter = 0
		clickCounter = 0
		click.run()
	}

	fun stop() {
		counter = 0
		clickCounter = 0
		handler.removeCallbacks(click)
	}
}