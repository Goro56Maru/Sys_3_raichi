package ecc_sys3_raichi.sys_3_raichi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import ecc_sys3_raichi.sys_3_raichi.databinding.ActivityHomeBinding

val TabArray = arrayOf("欲しいもの\n一覧","購入済み\n一覧","欲しいもの\n登録","設定")

var LOGIN_USER = ""
var LOGIN_ID = ""

class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewPager = binding.viewPager2
        val tabLayout = binding.tabLayout

        val adapter = HomePagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = TabArray[position]
        }.attach()
    }
}