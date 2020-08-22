package android.example.toys

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MetronomeViewModel : ViewModel() {
	val metronome = Metronome()
	private val presets = Presets()

	val liveData = MutableLiveData<ClickType>()

	fun getPresetsWriter():IPresetsWriter{
		return presets.getWriter()
	}

	fun getPresetsReader():IPresetsReader{
		return presets.getReader()
	}
}