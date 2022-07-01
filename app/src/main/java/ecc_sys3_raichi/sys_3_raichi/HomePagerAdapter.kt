package ecc_sys3_raichi.sys_3_raichi

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ecc_sys3_raichi.sys_3_raichi.purchased.PurchasedTabFragment
import ecc_sys3_raichi.sys_3_raichi.setting.SettingTabFragment
import ecc_sys3_raichi.sys_3_raichi.wantedlist.WantedListFragment
import ecc_sys3_raichi.sys_3_raichi.wantedlist.WantedTabFragment
import ecc_sys3_raichi.sys_3_raichi.wishlist.WishList

class HomePagerAdapter  (fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            //どのFragmentを表示するか
            0 -> WantedTabFragment()
            1 -> PurchasedTabFragment()
            2 -> WishList()
            3 -> SettingTabFragment()
            else -> WantedTabFragment()
        }
    }
}