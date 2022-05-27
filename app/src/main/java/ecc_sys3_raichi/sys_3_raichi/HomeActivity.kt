package ecc_sys3_raichi.sys_3_raichi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ecc_sys3_raichi.sys_3_raichi.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}