package com.example.week1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.week1.R
import com.example.week1.data.Contact

class ContactsAdapter(private val onClick: (Contact) -> Unit):
    ListAdapter<Contact, ContactsAdapter.ContactViewHolder>(ContactDiffCallback) {

    class ContactViewHolder(itemView: View, val onClick: (Contact) -> Unit):
        RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.contact_name)
        private val imageImageView: ImageView = itemView.findViewById(R.id.contact_image)
        private var currentContact: Contact? = null

        init {
            itemView.setOnClickListener {
                currentContact?.let {
                    onClick(it)
                }
            }
        }

        // name과 image를 binding
        fun bind(contact: Contact) {
            currentContact = contact

            nameTextView.text = contact.name
            if (contact.image != null) {
                imageImageView.setImageResource(contact.image)
            } else {
                imageImageView.setImageResource(R.drawable.image_contact_default)
            }
        }
    }

    // creates and inflates view and return ContactViewHolder
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsAdapter.ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ContactsAdapter.ContactViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact)
    }
}

object ContactDiffCallback: DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }
}