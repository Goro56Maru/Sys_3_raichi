package ecc_sys3_raichi.sys_3_raichi.login

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ecc_sys3_raichi.sys_3_raichi.R
import ecc_sys3_raichi.sys_3_raichi.user.usercheck
import kotlinx.android.synthetic.main.activity_login.*

private var uid = ""
private var email1 = ""
private var pass1 = ""

// [START declare_auth]
private lateinit var auth: FirebaseAuth

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        // Initialize Firebase Auth
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if(currentUser != null){
            updateUI(currentUser)
            reload()
        }

        loginbutton.setOnClickListener {
            email1 = email_text.text.toString()
            pass1 = pass_text.text.toString()
            if (email1.isNullOrEmpty() || pass1.isNullOrEmpty()){
                Toast.makeText(applicationContext,"メールアドレスとパスワードを入力してください", Toast.LENGTH_SHORT).show()
            }else{
                signIn(email1, pass1)
                reload()
            }
        }

        register_button.setOnClickListener {

            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }


    }

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(ContentValues.TAG, "signInWithEmail:success")
                val user = auth.currentUser
                uid = auth.currentUser?.uid.toString()
                updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                updateUI(null)
            }
        }
        // [END sign_in_with_email]
    }

    //ログイン処理
    private fun updateUI(user: FirebaseUser?) {
        if(user != null){
            Toast.makeText(applicationContext,"ログイン成功！", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext,usercheck::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }else{
            signOut()
            Toast.makeText(applicationContext,"ログイン失敗！", Toast.LENGTH_SHORT).show()
        }

    }

    //ログアウト処理
    private fun signOut() {
        auth.signOut()
    }

    private fun reload() {

    }
}