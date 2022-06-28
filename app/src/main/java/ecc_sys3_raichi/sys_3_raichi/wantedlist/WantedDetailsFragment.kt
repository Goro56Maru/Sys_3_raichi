package ecc_sys3_raichi.sys_3_raichi.wantedlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ecc_sys3_raichi.sys_3_raichi.R
import ecc_sys3_raichi.sys_3_raichi.databinding.FragmentWantedDetailsBinding
import kotlinx.android.synthetic.main.fragment_wanted_details.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//表示するリストのID
var LISTID = ""

class WantedDetailsFragment : Fragment() {

    //binding
    private var _binding: FragmentWantedDetailsBinding? = null
    private val binding get() = _binding!!

    // 選択肢
    private val spinnerItems = arrayOf("1番","2番","3番","4番","5番","6番","7番","8番","9番","10番")

    //選択されている優先順位を格納する変数
    private var selectItem = 0

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    //ログインユーザーのID
    private var uid = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentWantedDetailsBinding.inflate(inflater, container, false)
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

        var USERSID = "K0hyhEtEFCafKMmWPJoL"

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.voting_array2,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinner.adapter = adapter
        }

        /*
        選択された欲しいものの表示をする
        WantedListFragmentから選択された欲しいもののIDを取得して
        データベースから詳細情報を取得して表示する。
        */
        Displaychange(false, android.widget.TextView.INVISIBLE, android.widget.ProgressBar.VISIBLE)
        GlobalScope.launch {
            val job = launch {
                ViewUpdate()
            }
            delay(3000)
            job.cancel()
        }


        //spinnerを押した時の処理
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long, ) {
                val spinnerParent = parent as Spinner
                val itemName = spinnerParent.selectedItem as String
                val item = spinnerParent.selectedItemPosition
                when (item) {
                    0 -> selectItem = 5
                    1 -> selectItem = 4
                    2 -> selectItem = 3
                    3 -> selectItem = 2
                    4 -> selectItem = 1
                }

//                Toast.makeText(context, "$itemName \n $selectItem", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        //購入済みボタンが押された時の処理
        binding.button.setOnClickListener {
            Purchased()

            findNavController().navigate(R.id.action_wantedDetailsFragment_to_wantedListFragment)
        }


        //投票ボタンが押された時の処理
        binding.button.setOnClickListener {
            try {
                Displaychange(false, android.widget.TextView.INVISIBLE, android.widget.ProgressBar.VISIBLE)
                db.collection("user").document(uid).collection("list").document(LISTID).update("voter.${USERSID}",selectItem).addOnSuccessListener {
                    db.collection("user").document(uid).collection("list").document(LISTID).get()
                        .addOnSuccessListener { document ->
                            var pri = 0
                            var vot_data = document.data?.get("voter") as HashMap<*,*>
                            vot_data.values.forEach {
                                pri += it.toString().toInt()
                            }
                            db.collection("user").document(uid).collection("list").document(LISTID).update("list_priority",pri).addOnSuccessListener {
                                Displaychange(true, android.widget.TextView.VISIBLE, ProgressBar.INVISIBLE)
                                Toast.makeText(context, "${pri}\n${vot_data}", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }catch (e: Exception){
                Displaychange(true, android.widget.TextView.VISIBLE, ProgressBar.INVISIBLE)
                Toast.makeText(context, "投票に失敗しました", Toast.LENGTH_SHORT).show()
            }
        }


        //戻るボタンが押された時の処理
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_wantedDetailsFragment_to_wantedListFragment)
        }

        return view
    }

    //表示の更新を行う
    private fun ViewUpdate(){
        db.collection("user").document(uid).collection("list").document(LISTID).get().addOnSuccessListener {
            binding.WantedName.text = it["list_name"].toString() //欲しいものの名前
            binding.WantedMoney.text = "${it["list_money"].toString()}円" //欲しいものの金額
            binding.Priority.text = it["list_priority"].toString() //欲しいものの現在の優先順位
            binding.Proponent.text = it["list_prop"].toString() //欲しいものの提案者名
            binding.PropComment.text = it["list_comment"].toString() //欲しいものの投稿者コメント

            Displaychange(true, android.widget.TextView.VISIBLE, ProgressBar.INVISIBLE)
        }.addOnFailureListener {
            Toast.makeText(context, "情報の取得に失敗しました", Toast.LENGTH_SHORT).show()
        }
    }

    //表示されているボタンやテキストを有効(true)・無効(false)状態に変更する関数
    private fun Displaychange(display: Boolean, visible: Int, proginv: Int) {
        binding.progressBar2.visibility = proginv
        binding.WantedName.visibility = visible
        binding.WantedMoney.visibility = visible
        binding.Priorityinfo.visibility = visible
        binding.Priority.visibility = visible
        binding.Propinfo.visibility = visible
        binding.Proponent.visibility = visible
        binding.Commentinfo.visibility = visible
        binding.PropComment.visibility = visible
        binding.button.isEnabled = display
    }


    private fun Purchased(){
        db.collection("user").document(uid).collection("list").document(LISTID).update("purchased",true)
            .addOnSuccessListener {
                Toast.makeText(context,"updateにしました", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                //登録が失敗した時、Toastを表示する
                Toast.makeText(context,"updateに失敗しました", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WantedDetailsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}