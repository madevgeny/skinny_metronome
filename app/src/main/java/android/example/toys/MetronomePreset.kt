package android.example.toys

class MetronomePreset {
	private val bpm: Int
	private val clicksPerQuarter: Int

	internal constructor(bpm: Int, clicksPerQuarter: Int) {
		this.bpm = bpm
		this.clicksPerQuarter = clicksPerQuarter
	}

	internal constructor(metronome: Metronome) {
		bpm = metronome.bpm
		clicksPerQuarter = metronome.getClicksPerQuarter()
	}

	fun apply(metronome: Metronome) {
		metronome.setBPM(bpm)
		metronome.setClicksPerQuarter(clicksPerQuarter)
	}

	fun clone(): MetronomePreset {
		return MetronomePreset(bpm, clicksPerQuarter)
	}
}