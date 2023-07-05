package com.example.week1

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week1.adapter.ContactsAdapter
import com.example.week1.data.Contact
import com.example.week1.databinding.FragmentContactBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.NumberFormatException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        val view = binding.root

        // 연락처 조회를 위한 permission 확인
        val status = ContextCompat.checkSelfPermission(requireContext(), "android.permission.READ_CONTACTS")
        if(status == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf<String>("android.permission.READ_CONTACTS"), 100)
        }

        binding.fabAddContact.setOnClickListener {
            startActivity(Intent(requireActivity(), ContactAddActivity::class.java))
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(IO).launch {
            val contactList = getContacts()
            withContext(Main) {
                val adapter = ContactsAdapter(contactList, requireActivity())
                adapter.itemClick = object : ContactsAdapter.ItemClick {
                    override fun onClick(view: View, position: Int) {
                        val intent = Intent(requireActivity(), ContactDetailActivity::class.java)
                        val contact: Contact = contactList[position]
                        intent.putExtra("name", contact.name)
                        intent.putExtra("number", contact.number)
                        intent.putExtra("imageUri", contact.imageUri)
                        intent.putExtra("email", contact.email)
                        startActivityForResult(intent, 101)
                    }
                }
                binding.rvContacts.layoutManager = LinearLayoutManager(activity)
                binding.rvContacts.adapter = adapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun getContacts(): ArrayList<Contact> {
        val contactList = ArrayList<Contact>()

        val contactCursor = activity?.contentResolver!!.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null, null)
        if (contactCursor != null) {
            while(contactCursor.moveToNext()) {
                try {
                    val id =
                        contactCursor.getString(contactCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))
                    val name =
                        contactCursor.getString(contactCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val phoneNumber =
                        contactCursor.getString(contactCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val imageUri =
                        contactCursor.getString(contactCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                    val email =
                        contactCursor.getString(contactCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS))

                    contactList.add(Contact(id.toInt(), name, phoneNumber, imageUri, email))
                } catch (e: NumberFormatException) {
                    Log.e(TAG, e.toString())
                } catch (e: IllegalArgumentException) {
                    Log.e(TAG, e.toString())
                }
            }
            contactCursor.close()
        }

        return contactList
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        private const val TAG = "ContactFragment"
    }
}