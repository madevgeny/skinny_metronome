package android.example.toys

import android.widget.Button
import java.util.concurrent.atomic.AtomicLong

internal class DebugText private constructor(private val debugTest: Button) {
	private val click = Stopwatch()
	private val dClick = AtomicLong(0)
	private val dClickInterval = AtomicLong(0)
	private val versionCode = BuildConfig.VERSION_CODE

	fun restartClick(clickInterval: Long) {
		dClick.set(click.restart())
		dClickInterval.set(clickInterval)
	}

	fun draw() {
		var str = debugTest.text.subSequence(0, 4).toString()
		str += """ -> Click: ${dClick.get()}, ${dClickInterval.get()}
Version: $versionCode"""
		debugTest.text = str
	}

	companion object {
		private lateinit var inst: DebugText
		fun init(debugTest: Button) {
			inst = DebugText(debugTest)
		}

		fun instance(): DebugText? {
			return inst
		}
	}
}