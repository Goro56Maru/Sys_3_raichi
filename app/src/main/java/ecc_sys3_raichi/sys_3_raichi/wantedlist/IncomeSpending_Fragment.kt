package ecc_sys3_raichi.sys_3_raichi.wantedlist

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import ecc_sys3_raichi.sys_3_raichi.R
import kotlinx.android.synthetic.main.fragment_income_spending_.*

class IncomeSpending_Fragment : Fragment(R.layout.fragment_income_spending_) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        saveButton.setOnClickListener{

            val remaining = remainingCheck()

            val textView = TextView(activity)
            textView.text = remaining.toString()
            textView.textSize = 30f

            text_ll.addView(textView)
        }
    }
    fun remainingCheck(): Int {
        try {
            //収入
            val income = income_text.text.toString().trim()
            //支出
            val spend = spend_text.text.toString().trim()

            //使えるお金
            val remaining = (income.toInt() - spend.toInt())

            if(remaining > 0){
                return remaining
            }
            Toast.makeText(activity, "使えるお金はありません", Toast.LENGTH_SHORT).show()
            return 0
        }catch(e: NumberFormatException){
            Toast.makeText(activity, "正しく入力してください", Toast.LENGTH_SHORT).show()
        }
        return 0
    }
}