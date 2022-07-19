package ecc_sys3_raichi.sys_3_raichi.purchased

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ecc_sys3_raichi.sys_3_raichi.R
import ecc_sys3_raichi.sys_3_raichi.databinding.FragmentPurchasedListBinding
import ecc_sys3_raichi.sys_3_raichi.databinding.FragmentWantedListBinding
import ecc_sys3_raichi.sys_3_raichi.wantedlist.WantedListAdapter
import ecc_sys3_raichi.sys_3_raichi.wantedlist.WantedListData
import ecc_sys3_raichi.sys_3_raichi.wantedlist.WantedListFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PurchasedListFragment : Fragment() {

    //binding
    private var _binding: FragmentPurchasedListBinding? = null
    private val binding get() = _binding!!

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    //ログインユーザーのID
    private var uid = ""

    //表示するリストデータ
    private var ListView: MutableList<WantedListData> = ArrayList()

    //RecyclerView用のアダプター
    private val adapter = WantedListAdapter(ListView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentPurchasedListBinding.inflate(inflater, container, false)
        val view = binding.root

        //ログインユーザーのIDを取得する
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            uid = auth.uid.toString()
        }else{
            //テスト用のID代入
            uid = "6BioaJRzzhNkBgaHP2boi9W7HGF2"
        }



        //リスト表示のためのrecyclerView設定
        val linearLayoutManager = LinearLayoutManager(view.context)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

        //テスト用データ
//        val listdata2 = arrayOf(arrayOf("id1","テストデータ1","ちち"),arrayOf("id2","テストデータ2","はは"),arrayOf("id3","テストデータ3","ちち"),arrayOf("id4","テストデータ4","ちち"),arrayOf("id5","テストデータ5","がき"))
//        for (i in 0..4){
//            //セットするデータ
//            var setList = WantedListData(listdata2[i][0],listdata2[i][1],(i+1)*1000,listdata2[i][2],(i+1))
//            ListView.add(setList)   //リストにデータを追加
//            adapter.notifyDataSetChanged()  //データを追加後、表示を更新
//        }


        binding.progressBar.visibility = android.widget.ProgressBar.VISIBLE
        binding.recyclerView.visibility = android.widget.ListView.INVISIBLE
        ListView.clear()//リスト表示前にデータを空にしておく
        GlobalScope.launch {
            val job = launch {
                ListViewUpdate()
            }
            delay(3000)
            job.cancel()
        }

        //データが更新されるたびに表示を更新する
        db.collection("user").document(uid).collection("list")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "listen:error", e)
                    return@addSnapshotListener
                }
                //更新があるとき表示を更新
                if (snapshots != null) {
                    binding.progressBar.visibility = android.widget.ProgressBar.VISIBLE
                    binding.recyclerView.visibility = android.widget.ListView.INVISIBLE
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
        adapter.setOnItemClickListener(object : WantedListAdapter.OnItemClickListener {
            override fun onItemClickListener(
                view: View,
                position: Int,
                clickedText: WantedListData
            ) {
                //選択されたリストのID
                LISTID_p = clickedText.ListID
                //そのリストの詳細画面に遷移する
                findNavController().navigate(R.id.action_purchasedListFragment2_to_purchasedDetailsFragment)
            }
        })

        return view
    }

    private fun ListViewUpdate() {
        db.collection("user").document(uid).collection("list").whereEqualTo("purchased",true).get().addOnSuccessListener {
            ListView.clear()
            for (i in it) {
                var setList = WantedListData(
                    i.id,
                    i.data["list_name"] as String,
                    i.data["list_money"].toString().toInt(),
                    i.data["list_prop"] as String,
                    i.data["list_priority"].toString().toInt(),
                    i.data["list_comment"] as String
                )
                ListView.add(setList)
                adapter.notifyDataSetChanged()
            }
            binding.progressBar.visibility = android.widget.ProgressBar.INVISIBLE
            binding.recyclerView.visibility = android.widget.ListView.VISIBLE
        }.addOnFailureListener {

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WantedListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}