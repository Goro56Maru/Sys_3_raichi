package ecc_sys3_raichi.sys_3_raichi.setting

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ecc_sys3_raichi.sys_3_raichi.R
import kotlinx.android.synthetic.main.fragment_member_add.*

class MemberAdd: Fragment(R.layout.fragment_member_add) {

    private var url = ""

    //auth
    private lateinit var auth: FirebaseAuth

    //firestore
    private val db = Firebase.firestore

    //ログインユーザーのID
    private var uid = ""

    //ログインしているユーザの情報
    private var user_name = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //imageButton選択するとギャラリーを開く
        imageButton.setOnClickListener{
            selectPhoto()
        }

        //保存確定ボタン
        memberSaveButton.setOnClickListener{
            val name : String = nameText.text.toString().trim()

            Toast.makeText(activity, "url = " + url , Toast.LENGTH_SHORT).show()
            Toast.makeText(activity, "name = " + name , Toast.LENGTH_SHORT).show()

            addToFirebase(name,url)

            //replaceFragment(MemberSetting())
        }
    }

    //firebaseに追加する
    fun addToFirebase(name: String,url: String){

        //ログインユーザーのIDを取得する
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            uid = auth.uid.toString()
        }

        //テスト用のID代入
        uid = "afpI2ox0kncgRtbxY5S2"
        user_name = "息子B"

        var userData = hashMapOf(
            "name" to name,
            "url" to url
        )

        //firebaseに保存
        db.collection("user").document(uid).collection("users").add(userData)
            .addOnSuccessListener {
            Toast.makeText(activity, "保存しました" , Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener{
                Toast.makeText(activity, "失敗！！！！${it}", Toast.LENGTH_SHORT).show()
            }
    }

    //選択した画像に変更 (変更した画像を円形にしたい)
    private val launcher = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
        imageButton.setImageURI(it)
        url = it.toString()
    }
    private fun selectPhoto() {
        launcher.launch(arrayOf("image/*"))
    }

    fun replaceFragment(fragment : Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }
}