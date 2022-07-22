package ecc_sys3_raichi.sys_3_raichi.purchased

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ecc_sys3_raichi.sys_3_raichi.R
import java.text.SimpleDateFormat

class PurchasedAdapter (private val PurchasedData: MutableList<PurchasedData>)
    : RecyclerView.Adapter<PurchasedAdapter.ListViewHolder>(){

    lateinit var listener: OnItemClickListener

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var ListName: TextView = view.findViewById(R.id.WantedName)
        var Money: TextView = view.findViewById(R.id.WantedMoney)
        var Day: TextView = view.findViewById(R.id.Day)
        var Proponent: TextView = view.findViewById(R.id.Proponent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.purchased_sell,parent, false)
        return ListViewHolder(item)
    }

    override fun getItemCount(): Int {
        return PurchasedData.size
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val sdf = SimpleDateFormat("yyyy年MM月dd日")
        holder.ListName.text = PurchasedData[position].ListName
        holder.Money.text = PurchasedData[position].ListMoney.toString()+"円"
//        holder.Priority.text = WantedListData[position].ListPriority.toString()
        holder.Day.text = sdf.format(PurchasedData[position].day)
        holder.Proponent.text = PurchasedData[position].ListProp
        holder.itemView.setOnClickListener {
            listener.onItemClickListener(it, position, PurchasedData[position])
        }
    }

    //インターフェースの作成
    interface OnItemClickListener{
        fun onItemClickListener(view: View, position: Int, clickedText: PurchasedData) {
        }
    }

    // リスナー
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}