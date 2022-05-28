package kr.ac.kumoh.s20170419.mygradecalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

val id: String? = null

class DatabaseAdapter(
    private val model: ViewModel,
    private val onClick: (ViewModel.Subject) -> Unit
): RecyclerView.Adapter<DatabaseAdapter.ViewHolder>(), Filterable {
    inner class ViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.name)
        val text2: TextView = itemView.findViewById(R.id.profe)
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
        val str = "${item.name}"
        val str2 = "${item.professor} ${token[1]}분반"
        holder.text.text = str
        holder.text2.text = str2
    }

    override fun getItemCount(): Int {
        return model.getSize()
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }

    fun getFilter(name: String): MutableLiveData<ArrayList<ViewModel.Subject>> {
        return model.filteredList
    }
}