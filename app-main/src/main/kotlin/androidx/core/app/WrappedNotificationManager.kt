package androidx.core.app

import android.app.Notification
import android.app.NotificationChannel
import android.content.Context

/**
 * A wrapper implementation to acquire testability over NotificationManagerCompat.
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
interface WrappedNotificationManager {
    fun createNotificationChannel(channel: NotificationChannel)

    fun cancel(tag: String?, id: Int)

    fun notify(tag: String?, id: Int, notification: Notification)

    companion object {
        fun from(context: Context): WrappedNotificationManager =
            Impl(NotificationManagerCompat.from(context))
    }
}

private class Impl(private val delegate: NotificationManagerCompat) : WrappedNotificationManager {
    override fun createNotificationChannel(channel: NotificationChannel) {
        delegate.createNotificationChannel(channel)
    }

    override fun cancel(tag: String?, id: Int) {
        delegate.cancel(tag, id)
    }

    override fun notify(tag: String?, id: Int, notification: Notification) {
        delegate.notify(tag, id, notification)
    }
}
