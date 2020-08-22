package android.example.toys

import androidx.lifecycle.Observer

class ClickObserver(private val lampView: LampView, private val sounder: Sounder) : Observer<ClickType> {

	override fun onChanged(c: ClickType){
		sounder.play(c)
		lampView.switchState()
		DebugText.instance()!!.draw()
	}
}