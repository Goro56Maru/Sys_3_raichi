package ecc_sys3_raichi.sys_3_raichi.setting

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
    //現在のメールアドレス
    private var use_old_email = ""
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
            old_email = currentUser.email.toString()
        }


        binding.updateButton.setOnClickListener {
            //入力されたメールアドレスを変数に格納する
            use_old_email = binding.oldEmailText.text.toString()
            new_email = binding.newEmailText.text.toString()
            again_new_email = binding.againNewEmailText.text.toString()

            //メールアドレスが入力されていない場合Toastを表示
            if (use_old_email.isNullOrEmpty() || new_email.isNullOrEmpty() || again_new_email.isNullOrEmpty()){
                Toast.makeText(context,"メールアドレスを入力してください", Toast.LENGTH_SHORT).show()
            //入力されたメールアドレスと現在のメールアドレスが異なる、もしくは再入力した新しいメールアドレスが異なる場合、Toastを表示する
            }else if (old_email != use_old_email || new_email != again_new_email){
                Toast.makeText(context,"入力されたメールアドレスが異なります", Toast.LENGTH_SHORT).show()
            }else{
                currentUser!!.updateEmail(new_email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context,"メールアドレスを変更しました", Toast.LENGTH_SHORT).show()
                            //ログインユーザーのIDを取得する
                            auth = Firebase.auth
                            val currentUser = auth.currentUser
                            if (currentUser != null) {
                                uid = auth.uid.toString()
                                old_email = currentUser.email.toString()
                            }
                        }
                    }
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_emailUpdateFragment_to_settings)
        }

        return view
    }

    companion object {
    }
}