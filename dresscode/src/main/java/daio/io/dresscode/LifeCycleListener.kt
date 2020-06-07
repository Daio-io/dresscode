package daio.io.dresscode

import android.app.Activity
import android.app.Application
import android.os.Bundle

internal class LifeCycleListener : Application.ActivityLifecycleCallbacks {

    override fun onActivityPaused(activity: Activity?) {}

    override fun onActivityResumed(activity: Activity) {
        val dressCodeOnActivity = activities[activity::class] ?: return
        if (currentDressCode != dressCodeOnActivity) activity.recreate()
    }

    override fun onActivityStarted(activity: Activity?) {}

    override fun onActivityDestroyed(activity: Activity?) {}

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

    override fun onActivityStopped(activity: Activity?) {}

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {}

}
