package ecc_sys3_raichi.sys_3_raichi.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import ecc_sys3_raichi.sys_3_raichi.R
import kotlinx.android.synthetic.main.fragment_member.*

class MemberSetting: Fragment(R.layout.fragment_member){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        memberAdd.setOnClickListener{
            replaceFragment(MemberAdd())
        }
    }

    //設定のHOME画面 listView作成
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val mainFrame = inflater!!.inflate(R.layout.fragment_member, container, false)
        val listView = mainFrame.findViewById(R.id.listView) as ListView

        val dataArray = arrayOf("テストMember1","テストMember2")
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