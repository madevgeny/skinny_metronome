package android.example.toys

interface IPresetsWriter {
	fun setCurrent(mp: MetronomePreset)
	fun add(mp: MetronomePreset)
	fun remove(f: (MetronomePreset) -> Boolean)
	fun clear()
}