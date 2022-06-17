package ecc_sys3_raichi.sys_3_raichi.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ecc_sys3_raichi.sys_3_raichi.R
import kotlinx.android.synthetic.main.fragment_income_spending_.*
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


            db.collection("user").document("afpI2ox0kncgRtbxY5S2").update("users",FieldValue.arrayUnion("aaa")).addOnSuccessListener {
                Toast.makeText(activity, "url = p" , Toast.LENGTH_SHORT).show()
            }
//            //val ref = db.collection("user").document("afpI2ox0kncgRtbxY5S2")
//            db.collection("user").document("afpI2ox0kncgRtbxY5S2")
//                .update("users", FieldValue.arrayUnion("www")).addOnSuccessListener {
//                Toast.makeText(activity, "url = p" , Toast.LENGTH_SHORT).show()
//
//            }
//            db.collection("user").document("afpI2ox0kncgRtbxY5S2").update("users", FieldValue.arrayUnion(url))

            //addToFirebase(name,url)

            //replaceFragment(MemberSetting())
        }
    }

    //firebaseに追加する
    fun addToFirebase(name: String,url: String){
        //ログインユーザーのIDを取得する
//        auth = Firebase.auth
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            uid = auth.uid.toString()
//        }

        //テスト用のID代入
        var uid2 = "afpI2ox0kncgRtbxY5S2"
        user_name = "息子B"

        val ref = db.collection("user").document(uid2)
        db.collection("user").document("afpI2ox0kncgRtbxY5S2").update("users", FieldValue.arrayUnion(name)).addOnSuccessListener {
            Toast.makeText(activity, "url = p" , Toast.LENGTH_SHORT).show()

        }
        ref.update("users", FieldValue.arrayUnion(url))
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