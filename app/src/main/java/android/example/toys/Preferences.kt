package android.example.toys

import android.content.SharedPreferences
import android.example.toys.MetronomePreset
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Preferences(private val sharedPref: SharedPreferences) {
	var keepScreenOn = true

	private val gson = Gson()

	private val presetsType = object : TypeToken<List<MetronomePreset>>() {}.type

	fun load(presetsWriter: IPresetsWriter){
		val ver = sharedPref.getInt(VERSION_KEY, 0)

		//String presetsJson = sharedPref.getString(PRESETS_KEY,
		val presetsJson = "[{\"bpm\":65, \"clicksPerQuarter\":3}]" //);
		val newPresets = gson.fromJson<List<MetronomePreset>>(presetsJson, presetsType)
		for(p in newPresets) {
			presetsWriter.add(p)
		}
		val workPresetJson = sharedPref.getString(LAST_PRESET_KEY,
			"{\"bpm\":70, \"clicksPerQuarter\":1}")
		presetsWriter.setCurrent(gson.fromJson(workPresetJson, MetronomePreset::class.java))
		keepScreenOn = sharedPref.getBoolean(KEEP_SCREEN_ON, keepScreenOn)
	}

	fun save(presetsReader: IPresetsReader) {
		val editor = sharedPref.edit()
		editor.putInt(VERSION_KEY, version)
		val presetsJson = gson.toJson(presetsReader.get(), presetsType)
		editor.putString(PRESETS_KEY, presetsJson)
		val lastPresetJson = gson.toJson(presetsReader.getCurrent(), MetronomePreset::class.java)
		editor.putString(LAST_PRESET_KEY, lastPresetJson)
		editor.putBoolean(KEEP_SCREEN_ON, keepScreenOn)
		editor.apply()
	}

	companion object {
		private const val VERSION_KEY = "version"
		private const val PRESETS_KEY = "presets"
		private const val LAST_PRESET_KEY = "lastPreset"
		private const val KEEP_SCREEN_ON = "keepScreenOn"
		private const val version = 1
	}
}