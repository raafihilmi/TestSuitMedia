package com.bumantra.testsuitmedia.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumantra.testsuitmedia.data.local.entity.User
import com.bumantra.testsuitmedia.databinding.ItemUserBinding
import com.bumantra.testsuitmedia.ui.screens.SecondScreenActivity
import com.bumptech.glide.Glide
import java.util.Locale

class UserAdapter : PagingDataAdapter<User, UserAdapter.UserViewHolder>(DIFF_CALLBACK) {

    class UserViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: User) {
            binding.apply {
                imageView.loadImage(data.avatar)
                val fullName = data.firstName + " " + data.lastName
                tvFullname.text = fullName
                tvEmail.text = data.email?.uppercase(Locale.ROOT) ?: ""
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        val fullName = user?.firstName + " " + user?.lastName

        if (user != null) {
            holder.bind(user)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, SecondScreenActivity::class.java)
            intent.putExtra("fullName", fullName)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            holder.itemView.context.startActivity(intent)
        }
    }

    companion object {
        private fun ImageView.loadImage(url: String?) {
            Glide.with(this)
                .load(url)
                .into(this)
        }

        val DIFF_CALLBACK: DiffUtil.ItemCallback<User> =
            object : DiffUtil.ItemCallback<User>() {
                override fun areItemsTheSame(
                    oldItem: User,
                    newItem: User
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: User,
                    newItem: User
                ): Boolean {
                    return oldItem.id == newItem.id
                }
            }
    }


}