package android.example.toys

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PresetViewAdapter(private val presets: List<MetronomePreset>) :
        RecyclerView.Adapter<PresetViewAdapter.PresetViewHolder>() {

    class PresetViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): PresetViewHolder {
        // create a new view
        val imageView = LayoutInflater.from(parent.context)
                .inflate(R.layout.preset_view, parent, false) as ImageView
        // set the view's size, margins, paddings and layout parameters
        return PresetViewHolder(imageView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: PresetViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.imageView.setImageDrawable(Globals.notesHolder.getNote(1))//presets[position].toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = presets.size
}
