package ecc_sys3_raichi.sys_3_raichi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ecc_sys3_raichi.sys_3_raichi.login.Login
import ecc_sys3_raichi.sys_3_raichi.user.usercheck

class SplashActivity : AppCompatActivity() {
    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    private var SPLASH_SCREEN_TIME:Long=3500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)
        Handler(Looper.myLooper()!!).postDelayed({

            //既にログインしたことがある時ホームに遷移
            // Initialize Firebase Auth
            auth = Firebase.auth
            val currentUser = auth.currentUser
            if(currentUser != null){
                updateUI(currentUser)
            }else{
                updateUI(null)
            }
        },SPLASH_SCREEN_TIME)

    }

    private fun updateUI(user: FirebaseUser?) {
        if(user != null){
            Toast.makeText(this,"ログイン成功！", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,usercheck::class.java))
            finish()
        }else{
            signOut()
//          Toast.makeText(this,"ログイン失敗！", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }

    private fun signOut() {
        auth.signOut()
    }

}