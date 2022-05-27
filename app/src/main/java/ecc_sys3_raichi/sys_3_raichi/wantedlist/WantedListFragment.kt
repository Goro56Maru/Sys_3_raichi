package ecc_sys3_raichi.sys_3_raichi.wantedlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ecc_sys3_raichi.sys_3_raichi.R
import ecc_sys3_raichi.sys_3_raichi.databinding.FragmentWantedListBinding

class WantedListFragment : Fragment() {

    //binding
    private var _binding: FragmentWantedListBinding? = null
    private val binding get() = _binding!!

    //表示するリストデータ
    private var ListView : MutableList<WantedListData> = ArrayList()
    //RecyclerView用のアダプター
    private val adapter = WantedListAdapter(ListView)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentWantedListBinding.inflate(inflater, container, false)
        val view = binding.root

        val linearLayoutManager = LinearLayoutManager(view.context)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)


        //テスト用データ
        val listdata2 = arrayOf(arrayOf("id1","テストデータ1","ちち"),arrayOf("id2","テストデータ2","はは"),arrayOf("id3","テストデータ3","ちち"),arrayOf("id4","テストデータ4","ちち"),arrayOf("id5","テストデータ5","がき"))

        ListView.clear()    //リスト表示前にデータを空にしておく
        for (i in 0..4){
            //セットするデータ
            var setList = WantedListData(listdata2[i][0],listdata2[i][1],(i+1)*1000,listdata2[i][2],(i+1))
            ListView.add(setList)   //リストにデータを追加
            adapter.notifyDataSetChanged()  //データを追加後、表示を更新
        }

        //リストが選択された時
        adapter.setOnItemClickListener(object :WantedListAdapter.OnItemClickListener{
            override fun onItemClickListener(view: View, position: Int, clickedText: WantedListData) {
                //そのリストの詳細画面に遷移する
                findNavController().navigate(R.id.action_wantedListFragment_to_wantedDetailsFragment)
            }
        })

        //つかえる金額が押された時
        binding.useMoney.setOnClickListener {
            //navigateで収入と支出画面へ遷移する
            findNavController().navigate(R.id.action_wantedListFragment_to_incomeSpending_Fragment)
        }



        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WantedListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}