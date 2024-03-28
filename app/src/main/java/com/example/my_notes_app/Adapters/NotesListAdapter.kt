package com.example.my_notes_app.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.my_notes_app.Models.Notes
import com.example.my_notes_app.NotesClickListener
import com.example.my_notes_app.R
import java.util.Random

class NotesListAdapter(
    var context: Context,
    var list: List<Notes>,
    var listener: NotesClickListener
) : RecyclerView.Adapter<NotesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.textViewTitle.text = list[position].title
        holder.textViewTitle.isSelected = true
        holder.textView_Notes.text = list[position].notes
        holder.textView_Date.text = list[position].date
        holder.textView_Date.isSelected = true
        if (list[position].isPinned) {
            holder.imageview_pin.setImageResource(R.drawable.pin)
        } else {
            holder.imageview_pin.setImageResource(0)
        }
        val color_code = randomColor
        holder.notes_list_view.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                color_code,
                null
            )
        )
        holder.notes_list_view.setOnClickListener {
            listener.onClick(
                list[holder.adapterPosition]
            )
        }
        holder.notes_list_view.setOnLongClickListener {
            listener.onLongClick(list[holder.adapterPosition], holder.notes_list_view)
            true
        }
    }

    private val randomColor: Int
        private get() {
            val colorCode: MutableList<Int> = ArrayList()
            colorCode.add(R.color.color1)
            colorCode.add(R.color.color2)
            colorCode.add(R.color.color3)
            colorCode.add(R.color.color4)
            colorCode.add(R.color.color5)
            val random = Random()
            val random_color = random.nextInt(colorCode.size)
            return colorCode[random_color]
        }

    fun filterList(filteredList: List<Notes>) {
        list = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var notes_list_view: CardView
    var textViewTitle: TextView
    var textView_Notes: TextView
    var textView_Date: TextView
    var imageview_pin: ImageView

    init {
        notes_list_view = itemView.findViewById(R.id.notes_list_view)
        textViewTitle = itemView.findViewById(R.id.textViewTitle)
        textView_Notes = itemView.findViewById(R.id.textView_Notes)
        textView_Date = itemView.findViewById(R.id.textView_Date)
        imageview_pin = itemView.findViewById(R.id.imageview_pin)
    }
}
