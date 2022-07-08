package ecc_sys3_raichi.sys_3_raichi.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ecc_sys3_raichi.sys_3_raichi.R
import ecc_sys3_raichi.sys_3_raichi.databinding.FragmentEmailUpdateBinding
import ecc_sys3_raichi.sys_3_raichi.databinding.FragmentPasswordUpdateBinding
import ecc_sys3_raichi.sys_3_raichi.login.Login
import kotlinx.android.synthetic.main.fragment_password_update.*
import kotlinx.android.synthetic.main.fragment_password_update.view.*

class PasswordUpdateFragment : Fragment() {

    private var _binding: FragmentPasswordUpdateBinding? = null
    private val binding get() = _binding!!

    //auth
    private lateinit var auth: FirebaseAuth

    //ログインユーザーのID
    private var uid = ""
    //現在のパスワード
    private var old_pass = ""
    //新しいパスワード
    private var new_pass = ""
    //再入力した新しいパスワード
    private var again_new_pass = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentPasswordUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        //ログインユーザーのIDを取得する
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            uid = auth.uid.toString()
            old_pass = currentUser.email.toString()
        }


        binding.updateButton.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            old_pass = binding.oldPasswordText.text.toString()
            new_pass = binding.newPasswordText.text.toString()
            again_new_pass = binding.againNewPasswordText.text.toString()

            if (old_pass.isNullOrEmpty() || new_pass.isNullOrEmpty() || again_new_pass.isNullOrEmpty()) {
                Toast.makeText(context, "パスワードを入力してください", Toast.LENGTH_SHORT).show()
                //入力されたメールアドレスと現在のメールアドレスが異なる、もしくは再入力した新しいメールアドレスが異なる場合、Toastを表示する
            } else if (old_pass != old_pass || new_pass != again_new_pass) {
                Toast.makeText(context, "入力されたパスワードが異なります", Toast.LENGTH_SHORT).show()
            } else {
                currentUser!!.updatePassword(new_pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "変更された", Toast.LENGTH_SHORT).show()
                        auth = Firebase.auth
                        val currentUser = auth.currentUser
                        if (currentUser != null) {
                            uid = auth.uid.toString()
                            old_pass = currentUser.email.toString()
                        }
                    }
                }
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_passwordUpdateFragment_to_settings)
        }

        return view
    }

    companion object {
    }
}