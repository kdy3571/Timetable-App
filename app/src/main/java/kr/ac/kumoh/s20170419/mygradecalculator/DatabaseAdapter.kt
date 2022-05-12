package kr.ac.kumoh.s20170419.mygradecalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listdesign.view.*

val id: String? = null

class DatabaseAdapter(
    private val model: ViewModel,
    private val onClick: (ViewModel.Subject) -> Unit
): RecyclerView.Adapter<DatabaseAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.listtext)
        init {
            itemView.setOnClickListener {
                onClick(model.getR_subject(adapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.listdesign,
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