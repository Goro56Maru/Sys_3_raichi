package ecc_sys3_raichi.sys_3_raichi.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import ecc_sys3_raichi.sys_3_raichi.MainActivity
import ecc_sys3_raichi.sys_3_raichi.R
import kotlinx.android.synthetic.main.fragment_settings.*

class Settings : Fragment(R.layout.fragment_settings) {

    private val dataArray = arrayOf("アカウント設定", "メンバー設定", "通知", "ログアウト", "アカウント削除")
    private val jump = arrayOf(MainActivity(), MemberSetting(), NotifSetting())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //listViewから選択されたとき
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                Toast.makeText(activity, "position = " + position, Toast.LENGTH_SHORT).show()

                replaceFragment(jump[position] as Fragment)
            }
    }

    //設定のHOME画面 listView作成
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val mainFrame = inflater!!.inflate(R.layout.fragment_settings, container, false)
        val listView = mainFrame.findViewById(R.id.listView) as ListView

        val adapter = ArrayAdapter<String>(requireContext(), R.layout.txt_design, dataArray)
        listView.adapter = adapter

        return mainFrame
    }

    fun replaceFragment(fragment : Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }
}