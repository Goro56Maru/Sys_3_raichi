package ecc_sys3_raichi.sys_3_raichi.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ecc_sys3_raichi.sys_3_raichi.MainActivity
import ecc_sys3_raichi.sys_3_raichi.R
import ecc_sys3_raichi.sys_3_raichi.login.Login
import kotlinx.android.synthetic.main.fragment_settings.*

class Settings : Fragment(R.layout.fragment_settings) {

    private val dataArray = arrayOf("メールアドレス設定","パスワード設定", "メンバー設定", "通知", "ログアウト", "アカウント削除")
    private val jump = arrayOf(R.id.action_settings_to_emailUpdateFragment,
                                R.id.action_settings_to_passwordUpdateFragment,
                                R.id.action_settings_to_memberSetting,
                                R.id.action_settings_to_notifSetting,)

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        //listViewから選択されたとき
//        listView.onItemClickListener =
//            AdapterView.OnItemClickListener { parent, view, position, id ->
//                Toast.makeText(activity, "position = " + position, Toast.LENGTH_SHORT).show()
//                if (position == 4) auth.signOut()
//                else if (position > 4){}
//                else findNavController().navigate(jump[position])
//
////                replaceFragment(jump[position] as Fragment)
//            }
    }

    //設定のHOME画面 listView作成
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val mainFrame = inflater!!.inflate(R.layout.fragment_settings, container, false)
        val listView = mainFrame.findViewById(R.id.listView) as ListView

        val adapter = ArrayAdapter<String>(requireContext(), R.layout.txt_design, dataArray)
        listView.adapter = adapter

        //ログインユーザーのIDを取得する
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {

        }

        //listViewから選択されたとき
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                Toast.makeText(activity, "position = " + position, Toast.LENGTH_SHORT).show()
                if (position == 4) SignOut()
                else if (position == 5) SignOut()
                else if (position < 4) findNavController().navigate(jump[position])
//                replaceFragment(jump[position] as Fragment)
            }

        return mainFrame
    }

    fun replaceFragment(fragment : Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }
    private fun SignOut(){
        auth.signOut()
        Toast.makeText(context,"ログアウトしました。", Toast.LENGTH_SHORT).show()
        startActivity(Intent(context, Login::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
    }
}