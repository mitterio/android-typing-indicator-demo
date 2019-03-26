package io.mitter.typingindicators

import android.app.Application
import android.util.Log
import io.mitter.android.Mitter
import io.mitter.android.domain.model.MitterConfig
import io.mitter.android.domain.model.UserAuth
import io.mitter.models.mardle.messaging.*

class App : Application() {
    lateinit var mitter: Mitter

    override fun onCreate() {
        super.onCreate()

        val mitterConfig = MitterConfig(
            applicationId = "Izr37-Vm7TS-U8cIu-sVtqj"
        )

        val userAuth = UserAuth(
            userId = "XQQ8U-7JuFc-tPLON-AdGMl",
            userAuthToken = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJtaXR0ZXItaW8iLCJ1c2VyVG9rZW5JZCI6IlpPRTZ0ZGVsb29aaXdrTmEiLCJ1c2VydG9rZW4iOiJwdTRhMzFoN2N2ZGM5a3RqY3FkOXBoNXBzOSIsImFwcGxpY2F0aW9uSWQiOiJJenIzNy1WbTdUUy1VOGNJdS1zVnRxaiIsInVzZXJJZCI6IlhRUThVLTdKdUZjLXRQTE9OLUFkR01sIn0.aEygFwxhCvtHAiExAEl5xvQ97HVuuvo7MS5M7ijY7BpfAB_VIWpiGFQY_MImIjtTocy2P2IY14m-GSf3j_2JwA"
        )

        mitter = Mitter(
            context = this,
            mitterConfig = mitterConfig,
            userAuth = userAuth
        )

        mitter.registerOnPushMessageReceivedListener(object : Mitter.OnPushMessageReceivedCallback {
            override fun onChannelStreamData(channelId: String, streamId: String, streamData: ContextFreeMessage) {

            }

            override fun onNewChannel(channel: Channel) {

            }

            override fun onNewChannelTimelineEvent(channelId: String, timelineEvent: TimelineEvent) {

            }

            override fun onNewMessage(channelId: String, message: Message) {

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
                Log.d("MainAc", "Typing Indicator for $channelId by $senderId")
            }
        })
    }
}
