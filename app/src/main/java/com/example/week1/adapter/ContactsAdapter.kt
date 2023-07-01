package com.example.week1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week1.R
import com.example.week1.data.Contact
import com.example.week1.databinding.ContactItemBinding

class ContactsAdapter(val contactList: ArrayList<Contact>) :
    RecyclerView.Adapter<ContactsAdapter.Holder>() {
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null

    inner class Holder(val binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.contactName
        val image = binding.contactImage
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

        val image = contactList[position].image
        if(image != null){
            holder.image.setImageResource(image)
        } else {
            holder.image.setImageResource(R.drawable.image_contact_default)
        }
    }
}