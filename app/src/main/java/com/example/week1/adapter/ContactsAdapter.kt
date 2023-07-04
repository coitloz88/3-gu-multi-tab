package com.example.week1.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week1.R
import com.example.week1.data.Contact
import com.example.week1.databinding.ContactItemBinding
import com.example.week1.utils.getDrawableFromUri

class ContactsAdapter(private val contactList: ArrayList<Contact>, private val activity: Activity) :
    RecyclerView.Adapter<ContactsAdapter.Holder>() {
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    inner class Holder(private val binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.contactName
        val image = binding.contactImage
        val number = binding.contactPhonenum
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactsAdapter.Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }

        holder.name.text = contactList[position].name
        holder.number.text = contactList[position].number
        if(contactList[position].imageUri != null){
            holder.image.setImageDrawable(contactList[position].imageUri?.let {
                getDrawableFromUri(activity,
                    it
                )
            })
        } else {
            holder.image.setImageResource(R.drawable.image_contact_default)
        }
    }
}