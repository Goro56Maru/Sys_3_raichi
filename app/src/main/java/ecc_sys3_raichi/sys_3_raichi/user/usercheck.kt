package ecc_sys3_raichi.sys_3_raichi.user

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ecc_sys3_raichi.sys_3_raichi.HomeActivity
import ecc_sys3_raichi.sys_3_raichi.LOGIN_ID
import ecc_sys3_raichi.sys_3_raichi.LOGIN_USER
import ecc_sys3_raichi.sys_3_raichi.R
import ecc_sys3_raichi.sys_3_raichi.databinding.ActivityUsercheckBinding
import ecc_sys3_raichi.sys_3_raichi.setting.UserData
import ecc_sys3_raichi.sys_3_raichi.setting.UserDataAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class usercheck : AppCompatActivity() {

    private lateinit var binding: ActivityUsercheckBinding

    private lateinit var auth: FirebaseAuth

    private val db = Firebase.firestore

    private var uid = ""

    //表示するリストデータ
    private var ListView : MutableList<UserData> = ArrayList()

    //RecyclerView用のアダプター
    private val adapter = UserDataAdapter(ListView)

    private var member_name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usercheck)
        binding = ActivityUsercheckBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        auth = Firebase.auth
        val currentUser = auth.currentUser
        if(currentUser != null){
            uid = currentUser.uid
        }

        val linearLayoutManager = LinearLayoutManager(view.context)
        binding.userviewcheck.layoutManager = linearLayoutManager
        binding.userviewcheck.adapter = adapter
        binding.userviewcheck.setHasFixedSize(true)

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
                updateUI(clickedText.name,clickedText.usersID)
            }
        })


        binding.useraddButton.setOnClickListener {
            member_name = binding.memberText.text.toString()
            if (member_name.isNullOrEmpty()) Toast.makeText(view.context, "メンバー名を入力してください", Toast.LENGTH_SHORT).show()
            else{
                try {
                    GlobalScope.launch {
                        val job = launch {
                            var userData = hashMapOf(
                                "name" to member_name,
                                "url" to ""
                            )
                            db.collection("user").document(uid).collection("users").add(userData)
                                .addOnSuccessListener {
                                    Toast.makeText(view.context, "登録しました！", Toast.LENGTH_SHORT).show()
                                }
                        }
                        delay(3000)
                        job.cancel()
                    }
                }catch(e: Exception){
                    Toast.makeText(view.context, "登録に失敗しました！", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun ListViewUpdate(){
        db.collection("user").document(uid).collection("users").get().addOnSuccessListener {
            ListView.clear()
            for (i in it){
                var setList = UserData(i.id, i.data["name"] as String, i.data["url"].toString())
                ListView.add(setList)

                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun updateUI(name: String, usersID: String) {
        LOGIN_USER = name
        LOGIN_ID = usersID
        startActivity(Intent(this,HomeActivity::class.java))
        finish()
    }
}