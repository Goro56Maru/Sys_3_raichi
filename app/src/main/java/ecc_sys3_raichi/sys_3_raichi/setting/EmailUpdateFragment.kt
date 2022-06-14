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
import ecc_sys3_raichi.sys_3_raichi.databinding.ActivityWishListBinding
import ecc_sys3_raichi.sys_3_raichi.databinding.FragmentEmailUpdateBinding


class EmailUpdateFragment : Fragment() {

    private var _binding: FragmentEmailUpdateBinding? = null
    private val binding get() = _binding!!

    //auth
    private lateinit var auth: FirebaseAuth

    //ログインユーザーのID
    private var uid = ""
    //現在のメールアドレス
    private var old_email = ""
    //新しいメールアドレス
    private var new_email = ""
    //再入力した新しいメールアドレス
    private var again_new_email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentEmailUpdateBinding.inflate(inflater, container, false)
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