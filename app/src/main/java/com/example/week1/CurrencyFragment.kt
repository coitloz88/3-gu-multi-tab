package com.example.week1


//import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.week1.databinding.FragmentCurrencyBinding
import com.example.week1.retrofit.CurrencyService
import com.example.week1.retrofit.RetrofitClass
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyFragment : Fragment() {
    private var _binding: FragmentCurrencyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCurrencyBinding.inflate(inflater, container, false)

        val adapterFrom = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.currency_code,
            android.R.layout.simple_spinner_dropdown_item
        )
        val adapterTo = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.currency_code,
            android.R.layout.simple_spinner_dropdown_item
        )


        binding.spinnerFrom.let { spinner ->
            spinner.adapter = adapterFrom
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        }

        binding.spinnerTo.let { spinner ->
            spinner.adapter = adapterTo
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        }

        binding.confirmButton.setOnClickListener {
            val hintString = binding.etAmount.text.toString()

            val hint: Double? = hintString.toDoubleOrNull()

            if (binding.spinnerFrom != null && binding.spinnerTo != null && hint != null && hint >= 0) {
                calculateAmountResult(
                    binding.spinnerFrom.selectedItem.toString(),
                    binding.spinnerTo.selectedItem.toString(),
                    hint
                )
            }

            else {
                Toast.makeText(requireContext(), "Invalid input", Toast.LENGTH_SHORT).show()
            }

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun calculateAmountResult(from: String, to: String, inputAmount: Double) {
        val apiService = RetrofitClass.getInstance().create(CurrencyService::class.java)
        apiService.getExchangeRate("$from/$to").enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val jsonObject = JSONObject(response.body().toString())
                    val rate = jsonObject.getDouble(to)
                    val result = rate * inputAmount
                    binding.tvResult.text = String.format("%.2f", result)
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