package com.example.week1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.week1.databinding.FragmentCurrencyBinding
import com.example.week1.retrofit.CurrencyService
import com.example.week1.retrofit.RetrofitClass
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [CurrencyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CurrencyFragment : Fragment() {
    private var _binding: FragmentCurrencyBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        val view = binding.root

        val adapterFrom = ArrayAdapter.createFromResource(requireContext(), R.array.currency_code, android.R.layout.simple_spinner_dropdown_item)
        val adapterTo = ArrayAdapter.createFromResource(requireContext(), R.array.currency_code, android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerFrom?.let { spinner ->
            spinner.adapter = adapterFrom
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Log.d(TAG, "selected from code: " + spinner.selectedItem.toString())
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        }

        binding.spinnerTo?.let { spinner ->
            spinner.adapter = adapterTo
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Log.d(TAG, "selected to code: " + spinner.selectedItem.toString())
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        }

        binding.confirmButton.setOnClickListener {
            calculateAmountResult("usd", "krw", 1.0)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun calculateAmountResult(from: String, to: String, inputAmount: Double) {
        val apiService = RetrofitClass.getInstance().create(CurrencyService::class.java)
        apiService.getExchangeRate("$from/$to")?.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful) {
                    val jsonObject = JSONObject(response.body().toString())
                    val rate = jsonObject.getDouble(to)
                    binding.tvResult.text = (rate * inputAmount).toString()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, "failed to get data from URL")
            }
        })
    }

    companion object {
        private const val TAG = "CurrencyFragment"
    }
}