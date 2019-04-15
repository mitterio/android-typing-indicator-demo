package io.mitter.typingindicators

import android.app.Application
import io.mitter.android.Mitter
import io.mitter.android.domain.model.LoggingLevel
import io.mitter.android.domain.model.MitterConfig
import io.mitter.models.mardle.messaging.*
import io.mitter.recipes.remote.ApiService
import org.greenrobot.eventbus.EventBus
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

class App : Application() {
    lateinit var mitter: Mitter
    lateinit var apiService: ApiService

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3001")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        val mitterConfig = MitterConfig(
            applicationId = "Izr37-Vm7TS-U8cIu-sVtqj",
            loggingLevel = LoggingLevel.FULL
        )

        mitter = Mitter(
            context = this,
            mitterConfig = mitterConfig
        )

        mitter.registerOnPushMessageReceivedListener(object : Mitter.OnPushMessageReceivedCallback {
            override fun onChannelStreamData(channelId: String, streamId: String, streamData: ContextFreeMessage) {

            }

            override fun onNewChannel(channel: Channel) {

            }

            override fun onNewChannelTimelineEvent(channelId: String, timelineEvent: TimelineEvent) {

            }

            override fun onNewMessage(channelId: String, message: Message) {
                EventBus.getDefault().post(message)
            }

            override fun onNewMessageTimelineEvent(messageId: String, timelineEvent: TimelineEvent) {

            }

            override fun onParticipationChangedEvent(
                channelId: String,
                participantId: String,
                newStatus: ParticipationStatus,
                oldStatus: ParticipationStatus?
            ) {

            }

            override fun onTypingIndication(channelId: String, senderId: String) {
                if (mitter.getUserId() != senderId) {
                    EventBus.getDefault().post(TypingIndicator())
                }
            }
        })
    }
}
