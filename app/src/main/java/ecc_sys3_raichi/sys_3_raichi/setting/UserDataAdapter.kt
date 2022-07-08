package ecc_sys3_raichi.sys_3_raichi.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ecc_sys3_raichi.sys_3_raichi.R

class UserDataAdapter (private val UserData: MutableList<UserData>)
    : RecyclerView.Adapter<UserDataAdapter.ListViewHolder>(){

    lateinit var listener: OnItemClickListener

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var userName: TextView = view.findViewById(R.id.textView2)
        var userUrl: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.user_data_sell,parent, false)
        return ListViewHolder(item)
    }

    override fun getItemCount(): Int {
        return UserData.size
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.userName.text = UserData[position].name
//        holder.userUrl

        holder.itemView.setOnClickListener {
            listener.onItemClickListener(it, position, UserData[position])
        }
    }

    //インターフェースの作成
    interface OnItemClickListener{
        fun onItemClickListener(view: View, position: Int, clickedText: UserData) {
        }
    }

    // リスナー
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}