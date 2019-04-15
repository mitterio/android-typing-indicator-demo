package io.mitter.typingindicators

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.textChanges
import io.mitter.android.Mitter
import io.mitter.android.error.model.base.ApiError
import io.mitter.data.domain.user.User
import io.mitter.models.mardle.messaging.Message
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {
    private val channelId = "KNOqT-6GwKj-oVZG3-gws36"
    private val messageList: MutableList<Any> = mutableListOf()
    private lateinit var chatRecyclerViewAdapter: ChatRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mitter = (application as App).mitter
        val channels = mitter.Channels()
        val messaging = mitter.Messaging()
        EventBus.getDefault().register(this)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        chatRecyclerView?.layoutManager = linearLayoutManager

        messaging.getMessagesInChannel(
            channelId = channelId,
            onValueAvailableCallback = object : Mitter.OnValueAvailableCallback<List<Message>> {
                override fun onError(apiError: ApiError) {

                }

                override fun onValueAvailable(value: List<Message>) {
                    messageList.addAll(value.reversed())
                    chatRecyclerViewAdapter = ChatRecyclerViewAdapter(
                        messageList = messageList,
                        currentUserId = mitter.getUserId()
                    )

                    chatRecyclerView?.adapter = chatRecyclerViewAdapter
                }
            }
        )

        messageEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                channels.sendTypingIndicator(
                    channelId = channelId
                )
            }
        })

        sendButton?.setOnClickListener {
            messaging.sendTextMessage(
                channelId = channelId,
                message = messageEditText.text.toString()
            )
            messageEditText.text.clear()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNewMessage(message: Message) {
        messageList.add(message)
        chatRecyclerViewAdapter.notifyItemInserted(messageList.size - 1)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTypingIndication(typingIndicator: TypingIndicator) {
        if (messageList.last() != "") {
            messageList.add("")
            chatRecyclerViewAdapter.notifyItemInserted(messageList.size - 1)
            chatRecyclerView.scrollToPosition(messageList.size - 1)

            Handler().postDelayed({
                messageList.remove("")
                chatRecyclerViewAdapter.notifyItemRemoved(messageList.size - 1)
                chatRecyclerView.scrollToPosition(messageList.size - 1)
            }, 3000)
        }
    }
}
