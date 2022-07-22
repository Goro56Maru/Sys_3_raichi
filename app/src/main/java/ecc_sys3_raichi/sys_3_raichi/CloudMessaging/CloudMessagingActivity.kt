package ecc_sys3_raichi.sys_3_raichi.CloudMessaging

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import ecc_sys3_raichi.sys_3_raichi.R
import ecc_sys3_raichi.sys_3_raichi.SplashActivity
import timber.log.Timber

class CloudMessagingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloud_messaging)

        // インスタンスIDの自動生成を有効化する場合、true
        // AndroidManifestにて自動生成を禁止にしていない場合、不要
        FirebaseMessaging.getInstance().isAutoInitEnabled = true

        // Current Notificationトークンの取得
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->

            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }


            // new Instance ID token
            // ここで取得したtokenをテストする際のインスタンスIDとして設定する
            val token = task.result

            val msg = "InstanceID Token: $token"
            Timber.d(msg)
            //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show(
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        })

    }
}
