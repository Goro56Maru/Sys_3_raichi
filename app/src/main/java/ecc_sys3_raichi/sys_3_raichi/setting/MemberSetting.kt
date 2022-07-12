package ecc_sys3_raichi.sys_3_raichi.setting

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ecc_sys3_raichi.sys_3_raichi.R
import ecc_sys3_raichi.sys_3_raichi.databinding.FragmentMemberBinding
import ecc_sys3_raichi.sys_3_raichi.databinding.FragmentSettingsBinding
import ecc_sys3_raichi.sys_3_raichi.wantedlist.LISTID
import ecc_sys3_raichi.sys_3_raichi.wantedlist.WantedListAdapter
import ecc_sys3_raichi.sys_3_raichi.wantedlist.WantedListData
import kotlinx.android.synthetic.main.fragment_member.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MemberSetting: Fragment(){

    private var _binding: FragmentMemberBinding? = null
    private val binding get() = _binding!!

    //auth
    private lateinit var auth: FirebaseAuth

    //firestore
    private val db = Firebase.firestore

    //ログインユーザーのID
    private var uid = ""

    //ログインしているユーザの情報
    private var user_name = ""

    //表示するリストデータ
    private var ListView : MutableList<UserData> = ArrayList()

    //RecyclerView用のアダプター
    private val adapter = UserDataAdapter(ListView)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    //設定のHOME画面 listView作成
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = FragmentMemberBinding.inflate(inflater, container, false)
        val view = binding.root


        //ログインユーザーのIDを取得する
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            uid = auth.uid.toString()
        }
//        uid = "afpI2ox0kncgRtbxY5S2"
//        user_name = "息子B"

        //リスト表示のためのrecyclerView設定
        val linearLayoutManager = LinearLayoutManager(view.context)
        binding.recyclerView2.layoutManager = linearLayoutManager
        binding.recyclerView2.adapter = adapter
        binding.recyclerView2.setHasFixedSize(true)

        ListViewUpdate()

        //データが更新されるたびに表示を更新する
        db.collection("user").document(uid).collection("users")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "listen:error", e)
                    return@addSnapshotListener
                }
                //更新があるとき表示を更新
                if (snapshots != null){
                    GlobalScope.launch {
                        val job = launch {
                            ListViewUpdate()
                        }
                        delay(3000)
                        job.cancel()
                    }
                }
            }

        //リストが選択された時
        adapter.setOnItemClickListener(object :UserDataAdapter.OnItemClickListener{
            override fun onItemClickListener(view: View, position: Int, clickedText: UserData) {
                //そのリストの詳細画面に遷移する
                Toast.makeText(activity, "${clickedText.name}", Toast.LENGTH_SHORT).show()
            }
        })


        binding.memberAdd.setOnClickListener {
            findNavController().navigate(R.id.action_memberSetting_to_memberAdd2)
        }

        binding.backButton2.setOnClickListener {
            findNavController().navigate(R.id.action_memberSetting_to_settings)
        }

        return view
    }

    fun ListViewUpdate(){
        db.collection("user").document(uid).collection("users").get().addOnSuccessListener {
            ListView.clear()
            for (i in it){
                var setList = UserData(i.id, i.data["name"] as String, i.data["url"].toString())
                ListView.add(setList)

                adapter.notifyDataSetChanged()
            }
        }
    }




    fun replaceFragment(fragment : Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }
}
