package ecc_sys3_raichi.sys_3_raichi.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ecc_sys3_raichi.sys_3_raichi.R
import ecc_sys3_raichi.sys_3_raichi.databinding.FragmentEmailUpdateBinding
import ecc_sys3_raichi.sys_3_raichi.databinding.FragmentPasswordUpdateBinding

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
        }


        binding.updateButton.setOnClickListener {

        }

        return view
    }

    companion object {
    }
}