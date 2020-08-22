package android.example.toys

interface IPresetsReader {
	fun getCurrent():MetronomePreset
	fun get():List<MetronomePreset>
}