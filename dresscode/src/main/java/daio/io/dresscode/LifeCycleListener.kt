package daio.io.dresscode

import android.app.Activity
import android.app.Application
import android.os.Bundle

internal class LifecycleListener : Application.ActivityLifecycleCallbacks {

    override fun onActivityPaused(activity: Activity?) {

    }

    override fun onActivityResumed(activity: Activity) {
        val theme = activities[activity::class] ?: return
        if (activity.dressCodeName != theme) activity.recreate()
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    }

}