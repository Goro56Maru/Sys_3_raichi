package ecc_sys3_raichi.sys_3_raichi.wantedlist

import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ecc_sys3_raichi.sys_3_raichi.R
import kotlinx.android.synthetic.main.fragment_income_spending_.*

class IncomeSpending_Fragment : Fragment(R.layout.fragment_income_spending_) {

    //auth
    private lateinit var auth: FirebaseAuth

    //firestore
    private val db = Firebase.firestore

    private var income = ""
    private var spend = ""
    private var remaining = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        saveButton.setOnClickListener{

            val remaining = remainingCheck()
            setData(income,spend,remaining)

            val textView = TextView(activity)
            textView.text = remaining.toString()
            textView.textSize = 30f

            text_ll.addView(textView)
        }
    }

    fun remainingCheck(): Int {
        try {
            //収入
            income = income_text.text.toString().trim()
            //支出
            spend = spend_text.text.toString().trim()

            //使えるお金
            remaining = (income.toInt() - spend.toInt())

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

    fun setData(income: String, spend: String, remaining: Int) {
        var uid = "eLMHnvNiO4sxxD7pwXlg"

        val money = hashMapOf(
            "income" to income,
            "spend" to spend,
            "remaining" to remaining
        )

        //firebaseに保存
        val ref = db.collection("user").document(uid)
        ref.update("users" ,FieldValue.arrayUnion(money)).addOnSuccessListener {
            Toast.makeText(activity, "保存しました" , Toast.LENGTH_SHORT).show()
        }
    }

}