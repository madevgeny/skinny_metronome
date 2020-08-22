package android.example.toys

import android.app.Application
import android.content.Context

public class MetronomeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

		Globals.preferences = Preferences(getSharedPreferences(
			resources.getString(R.string.preference_file_key), Context.MODE_PRIVATE))

		Globals.notesHolder = NotesHolder(applicationContext)
    }
}
