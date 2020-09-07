package android.example.toys

import android.example.toys.databinding.ActivityMainBinding
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
	private var playing = true

	internal class OnPresetsClickListener(private val presetPopup: PresetsPopup) : View.OnClickListener {
		override fun onClick(v: View) {
			presetPopup.showAtLocation(v, Gravity.BOTTOM, 0, 0)
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val b = ActivityMainBinding.inflate(layoutInflater)
		setContentView(b.root)

		val model by viewModels<MetronomeViewModel>()

		model.liveData.observe(this, ClickObserver(b.lamp, Sounder2(this)))
		model.metronome.callback = {clickType -> run{
			model.liveData.value = clickType
		}}

		val incrementButton = IncrementButton(b.increaseBpmButton) { d: Int -> run{
			b.bpmText.text = model.metronome.incBPM(d).toString()
		}}

		val decrementButton = IncrementButton(b.decreaseBpmButton) { d: Int -> run{
			b.bpmText.text = model.metronome.decBPM(d).toString()
		}}

		b.savePresetsButton.setOnClickListener {v -> run {
			model.getPresetsWriter().add(MetronomePreset(model.metronome))
		}}

		b.playButton.setOnClickListener{v -> run{
			playing = !playing
			if (playing) {
				b.lamp.setState(LampView.Color.BLUE)
				b.playButton.setText(R.string.play)
				model.metronome.stop()
			} else {
				b.lamp.setState(LampView.Color.BLUE)
				b.playButton.setText(R.string.stop)
				model.metronome.start()
			}
		}}

		DebugText.init(b.playButton)

		Globals.preferences.load(model.getPresetsWriter())
		model.getPresetsReader().getCurrent().apply(model.metronome)
		b.bpmText.text = model.metronome.bpm.toString()
		b.rhythmButton.setImageDrawable(Globals.notesHolder.getNote(model.metronome.getClicksPerQuarter())!!)

		b.root.keepScreenOn = Globals.preferences.keepScreenOn

		val rhythmPopup = RhythmPopup(this)
		rhythmPopup.setOnClickListener { v ->
			run {
				when(v.id){
					R.id.quarterImageButton -> model.metronome.setClicksPerQuarter(1)
					R.id.eightImageButton -> model.metronome.setClicksPerQuarter(2)
					R.id.tripleImageButton -> model.metronome.setClicksPerQuarter(3)
					R.id.sixteenImageButton -> model.metronome.setClicksPerQuarter(4)
				}
				b.rhythmButton.setImageDrawable(Globals.notesHolder.getNote(model.metronome.getClicksPerQuarter())!!)
			}
		}
		b.rhythmButton.setOnClickListener{v -> run{
			rhythmPopup.popup(v)
		}}

		val presetsPopup = PresetsPopup(this, model.getPresetsReader())
		presetsPopup.setOnClickListener(OnPresetsClickListener(presetsPopup))
		b.showPresetsButton.setOnClickListener(OnPresetsClickListener(presetsPopup))
	}

	override fun onStop() {
		super.onStop()

		val model by viewModels<MetronomeViewModel>()
		Globals.preferences.save(model.getPresetsReader())
	}
}