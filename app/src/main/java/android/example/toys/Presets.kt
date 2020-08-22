package android.example.toys

class Presets {
	private val presets = HashSet<MetronomePreset>()

	private val reader = PresetsReader()
	private val writer = PresetsWriter()

	private var currentPreset = MetronomePreset(70, 4)

	private inner class PresetsReader : IPresetsReader {
		override fun getCurrent(): MetronomePreset {
			return currentPreset
		}

		override fun get(): List<MetronomePreset> {
			return presets.toList()
		}
	}

	private inner class PresetsWriter: IPresetsWriter {
		override fun add(mp: MetronomePreset) {
			presets.add(mp.clone())
		}

		override fun remove(f: (MetronomePreset) -> Boolean) {
			for(p in presets){
				if(f(p)){
					presets.remove(p)
					return
				}
			}
		}

		override fun setCurrent(mp: MetronomePreset) {
			currentPreset = mp.clone()
		}

		override fun clear() {
			presets.clear()
		}
	}

	fun getReader(): IPresetsReader{
		return reader
	}

	fun getWriter(): IPresetsWriter{
		return writer
	}
}