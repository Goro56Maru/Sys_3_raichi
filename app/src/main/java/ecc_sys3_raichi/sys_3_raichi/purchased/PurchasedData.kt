package ecc_sys3_raichi.sys_3_raichi.purchased

import com.google.firebase.Timestamp
import java.util.*

data class PurchasedData(
    var ListID: String,
    var ListName: String,
    var ListMoney: Int,
    var ListProp : String,
    var day : Date,
    var ListComment : String
)