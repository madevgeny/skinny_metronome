package android.example.toys

import android.os.Handler
import android.view.MotionEvent
import android.widget.Button

class IncrementButton(private val button: Button, private val listener: (Int) -> Unit) {
	val BPM_BUTTON_BOOST_COOLDOWN: Long = 500
	val BPM_BUTTON_BOOST = 10

	private val buttonHandler = Handler()
	private var bpmBoost = false

	init {
		val buttonRun: Runnable = object : Runnable {
			override fun run() {
				bpmBoost = true
				listener(BPM_BUTTON_BOOST)
				buttonHandler.postDelayed(this, BPM_BUTTON_BOOST_COOLDOWN)
			}
		}

		button.setOnTouchListener{ v, e ->
			run {
				when (e.getAction()) {
					MotionEvent.ACTION_DOWN -> {
						bpmBoost = false
						buttonHandler.postDelayed(buttonRun, BPM_BUTTON_BOOST_COOLDOWN)
					}
					MotionEvent.ACTION_UP -> {
						buttonHandler.removeCallbacks(buttonRun)
						if (!bpmBoost) {
							listener(1)
						} else {
							bpmBoost = false
						}
						v.performClick()
					}
					else -> {
					}
				}
				true
			}}
	}
}