package ecc_sys3_raichi.sys_3_raichi

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_notif.*


class NotifSetting: Fragment(R.layout.activity_notif) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val checkBox1 = CheckBox(context)
        val checkBox2 = CheckBox(context)

        checkBox1.setText("通知")
        checkBox2.setText("????")

        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        checkBox1.setLayoutParams(layoutParams)
        checkBox2.setLayoutParams(layoutParams)

        checkBox1.setScaleX(1.7f)
        checkBox1.setScaleY(1.7f)

        checkBox2.setScaleX(1.7f)
        checkBox2.setScaleY(1.7f)

        linearLayout.addView(checkBox1)
        linearLayout.addView(checkBox2)
    }
}