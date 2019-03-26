package io.mitter.typingindicators

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.mitter.models.mardle.messaging.Message
import kotlinx.android.synthetic.main.item_message_other.view.*
import kotlinx.android.synthetic.main.item_message_self.view.*

class ChatRecyclerViewAdapter(
    private val messageList: List<Any>,
    private val currentUserId: String
) : RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder>() {
    private val MESSAGE_SELF_VIEW = 0
    private val MESSAGE_OTHER_VIEW = 1
    private val TYPING_INDICATOR = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutId = when (viewType) {
            MESSAGE_SELF_VIEW -> R.layout.item_message_self
            MESSAGE_OTHER_VIEW -> R.layout.item_message_other
            else -> R.layout.item_typing_indicator
        }
        val itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = messageList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (messageList[position] is Message) {
            holder.bindMessage(messageList[position] as Message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (messageList[position] is Message) {
            return if ((messageList[position] as Message).senderId.domainId() == currentUserId)
                MESSAGE_SELF_VIEW else MESSAGE_OTHER_VIEW
        } else {
            return TYPING_INDICATOR
        }
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindMessage(message: Message) {
            with(message) {
                if (senderId.domainId() == currentUserId) {
                    itemView?.selfMessageText?.text = textPayload
                } else {
                    itemView?.otherMessageText?.text = textPayload
                }
            }
        }
    }
}
