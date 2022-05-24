package ecc_sys3_raichi.sys_3_raichi.wantedlist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import ecc_sys3_raichi.sys_3_raichi.R
import ecc_sys3_raichi.sys_3_raichi.databinding.FragmentWantedDetailsBinding

class WantedDetailsFragment : Fragment() {

    //binding
    private var _binding: FragmentWantedDetailsBinding? = null
    private val binding get() = _binding!!

    // 選択肢
    private val spinnerItems = arrayOf("1番","2番","3番","4番","5番","6番","7番","8番","9番","10番")

    //選択されている優先順位を格納する変数
    private var selectItem = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentWantedDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        /*
        選択された欲しいものの表示をする
        WantedListFragmentから選択された欲しいもののIDを取得して
        データベースから詳細情報を取得して表示する。
        */
        binding.WantedName.text = "テストデータ" //欲しいものの名前
        binding.WantedMoney.text = "10000円" //欲しいものの金額
        binding.Priority.text = "1番" //欲しいものの現在の優先順位
        binding.Proponent.text = "ちち" //欲しいものの提案者名
        binding.PropComment.text = "これはテスト用です" //欲しいものの投稿者コメント

        //ArrayAdapter
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //spinnerにアダプターをセットする
        binding.spinner.adapter = adapter

        //spinnerを押した時の処理
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long, ) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as String
                selectItem = item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        //投票ボタンが押された時の処理
        binding.button.setOnClickListener {
            //確認用Toast
            Toast.makeText(context, "${selectItem}", Toast.LENGTH_SHORT).show()
        }




        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WantedDetailsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}