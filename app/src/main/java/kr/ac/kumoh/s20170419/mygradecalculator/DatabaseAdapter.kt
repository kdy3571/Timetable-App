package kr.ac.kumoh.s20170419.mygradecalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DatabaseAdapter(
    private val model: ViewModel,
    private val onClick: (ViewModel.Subject) -> Unit
): RecyclerView.Adapter<DatabaseAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(android.R.id.text1)

        init {
            itemView.setOnClickListener {
                onClick(model.getR_subject(adapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            android.R.layout.simple_list_item_1,
            parent,
            false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = model.getR_subject(position)
        var token = item.code.split('-')
        val str = "${item.name} \n${item.professor}교수님 ${token[1]}분반"
        holder.text.text = str
    }

    override fun getItemCount(): Int {
        return model.getSize()
    }
}