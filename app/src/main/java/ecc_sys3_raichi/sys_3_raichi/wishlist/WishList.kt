package ecc_sys3_raichi.sys_3_raichi.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ecc_sys3_raichi.sys_3_raichi.databinding.ActivityWishListBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WishList : Fragment() {

    private var _binding: ActivityWishListBinding? = null
    private val binding get() = _binding!!

    //auth
    private lateinit var auth: FirebaseAuth

    //firestore
    private val db = Firebase.firestore

    //ログインユーザーのID
    private var uid = ""

    //ログインしているユーザの情報
    private var user_name = ""

    //作成する欲しいものの名前
    private var list_name = ""
    //作成する欲しいものの金額
    private  var list_money = 0
    //作成する欲しいものの作成者のコメント
    private var list_comment = ""
    //作成時通知を送るかどうかを格納する変数
    private var list_alert = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = ActivityWishListBinding.inflate(inflater, container, false)
        val view = binding.root

        //ログインユーザーのIDを取得する
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            uid = auth.uid.toString()
        }

        //登録ボタンが押された時の処理
        binding.wishaddbutton.setOnClickListener {

            //欲しいものの名前が入力されなかった場合Toastを表示
            if (binding.wishname.text.isNullOrEmpty())  Toast.makeText(context,"リスト名を入力してください。", Toast.LENGTH_SHORT).show()
            //欲しいものの金額が入力されなかった場合Toastを表示
            else if(binding.wishmoney.text.isNullOrEmpty()) Toast.makeText(context,"金額を入力してください。", Toast.LENGTH_SHORT).show()
            //欲しいものの名前が入力された時に登録処理を行う
            else{
                //入力された各情報を変数に入れる
                list_name = binding.wishname.text.toString()
                //エラー回避のため、何も入力されていない時は0を格納
                if (binding.wishmoney.text.isNullOrEmpty()) list_money = 0
                else list_money = binding.wishmoney.text.toString().toInt()
                list_comment = binding.wishcomment.text.toString()

                //Firestoreに登録中する際に関数を呼び出し、ボタン等を押せない状態にしておく
                Displaychange(false)
                GlobalScope.launch {
                    val job = launch {
                        //登録処理を行う関数を呼び出す
                        CreateList()
                    }
                    delay(3000)
                    job.cancel()
                }
                //登録処理の終了後に関数を呼び出し、ボタン等を押せる状態にする
                Displaychange(true)
            }
        }
        return view
    }

    //登録処理を行う関数
    private fun CreateList(){
        //実際に登録するデータをMap形式にする
        var CreateListData = hashMapOf(
            "list_name" to list_name,
            "list_money" to list_money,
            "list_comment" to list_comment
        )

        //FireStoreへの登録処理
        db.collection("user").document(uid).collection("list").add(CreateListData)
            .addOnSuccessListener {
                //登録が完了した時、Toastを表示する
                Toast.makeText(context,"${list_name}を登録しました", Toast.LENGTH_SHORT).show()
                //入力されている内容をクリアする
                binding.wishname.text.clear()
                binding.wishmoney.text.clear()
                binding.wishcomment.text.clear()
            }.addOnFailureListener{
                //登録が失敗した時、Toastを表示する
                Toast.makeText(context,"登録に失敗しました", Toast.LENGTH_SHORT).show()
            }
    }

    //表示されているボタンやテキストを有効(true)・無効(false)状態に変更する関数
    private fun Displaychange(display: Boolean) {
        binding.wishname.isEnabled = display
        binding.wishmoney.isEnabled = display
        binding.wishaddalert.isEnabled = display
        binding.wishcomment.isEnabled = display
        binding.wishaddbutton.isEnabled = display
    }
}