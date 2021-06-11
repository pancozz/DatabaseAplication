package id.ac.unhas.crud

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.unhas.databaseaplication.R
import id.ac.unhas.databaseaplication.adapter.OrangModel

class OrangAdapter : RecyclerView.Adapter<OrangAdapter.OrangViewHolder>() {
    private var orgList: ArrayList<OrangModel> = ArrayList()
    private var onClickItem: ((OrangModel) ->Unit )? = null
    private var onClickDeleteItem: ((OrangModel) ->Unit )? = null

    fun addItem(item: ArrayList<OrangModel>){
        this.orgList = item
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (OrangModel) -> Unit ){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (OrangModel) -> Unit ){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OrangViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_view_item, parent, false)
    )

    override fun onBindViewHolder(holder: OrangViewHolder, position: Int) {
        val org = orgList[position]
        holder.bindView(org)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(org) }
        holder.tmblDel.setOnClickListener { onClickDeleteItem?.invoke(org) }
    }

    override fun getItemCount(): Int {
        return orgList.size
    }

    class OrangViewHolder(view: View): RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var nama = view.findViewById<TextView>(R.id.tvNama)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        var tmblDel = view.findViewById<TextView>(R.id.tmblDel)

        fun bindView(org: OrangModel){
            id.text = org.id.toString()
            nama.text = org.nama
            email.text = org.email
        }
    }
}