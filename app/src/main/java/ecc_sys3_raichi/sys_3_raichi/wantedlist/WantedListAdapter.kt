package ecc_sys3_raichi.sys_3_raichi.wantedlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ecc_sys3_raichi.sys_3_raichi.R

class WantedListAdapter (private val WantedListData: MutableList<WantedListData>)
    : RecyclerView.Adapter<WantedListAdapter.ListViewHolder>(){

    lateinit var listener: OnItemClickListener

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var ListName: TextView = view.findViewById(R.id.WantedName)
        var Money: TextView = view.findViewById(R.id.WantedMoney)
        var Priority: TextView = view.findViewById(R.id.Priority)
        var Proponent: TextView = view.findViewById(R.id.Proponent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.wanted_list_sell,parent, false)
        return ListViewHolder(item)
    }

    override fun getItemCount(): Int {
        return WantedListData.size
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.ListName.text = WantedListData[position].ListName
        holder.Money.text = WantedListData[position].ListMoney.toString()+"円"
//        holder.Priority.text = WantedListData[position].ListPriority.toString()
        holder.Priority.text = (position + 1).toString()
        holder.Proponent.text = WantedListData[position].ListProp
        holder.itemView.setOnClickListener {
            listener.onItemClickListener(it, position, WantedListData[position])
        }
    }

    //インターフェースの作成
    interface OnItemClickListener{
        fun onItemClickListener(view: View, position: Int, clickedText: WantedListData) {
        }
    }

    // リスナー
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}