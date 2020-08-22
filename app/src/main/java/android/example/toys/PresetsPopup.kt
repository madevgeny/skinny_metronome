package android.example.toys

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.*
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

internal class PresetsPopup(context: Context, private val presetsReader: IPresetsReader) : PopupWindow(context) {
	private val popupView: ViewGroup

	fun setOnClickListener(listener: View.OnClickListener?) {}

	init { // inflate the layout of the popup window
		isFocusable = true
		val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		popupView = inflater.inflate(R.layout.presets_popup, null) as ViewGroup
		var screenWidth = 900
		var screenHeight = 400
		try {
			val wm = popupView.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
			val displaymetrics = DisplayMetrics()
			wm.defaultDisplay.getMetrics(displaymetrics)
			screenWidth = displaymetrics.widthPixels
			screenHeight = displaymetrics.heightPixels
		} catch (e: Exception) {
			e.printStackTrace()
		}


		val viewManager = LinearLayoutManager(context)
		val adapter = PresetViewAdapter(presetsReader.get())
		val rv = popupView.findViewById<RecyclerView>(R.id.presets_list).apply {
			// use this setting to improve performance if you know that changes
			// in content do not change the layout size of the RecyclerView
			setHasFixedSize(true)

			// use a linear layout manager
			layoutManager = viewManager

			// specify an viewAdapter (see also next example)
			setAdapter(adapter)
		}

		contentView = popupView

		// dismiss the popup window when touched
		popupView.setOnTouchListener { v, event ->
			dismiss()
			true
		}
	}
}