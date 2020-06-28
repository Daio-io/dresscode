package daio.io.dresscode

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.os.Bundle
import daio.io.dresscode.theming.darkThemeId
import daio.io.dresscode.theming.lightThemeId

internal class LifeCycleListener : Application.ActivityLifecycleCallbacks {

    private var callbacks: ComponentListener? = null

    override fun onActivityPaused(activity: Activity?) {
        callbacks?.unregister()
        callbacks = null
    }

    override fun onActivityResumed(activity: Activity) {
        val dressCodeOnActivity = activities[activity::class] ?: return
        if (currentDressCode != dressCodeOnActivity) activity.recreate().also { return }

        callbacks = ComponentListener(activity)
    }

    override fun onActivityStarted(activity: Activity?) {}

    override fun onActivityDestroyed(activity: Activity?) {}

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

    override fun onActivityStopped(activity: Activity?) {}

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {}
}

internal class ComponentListener(private val activity: Activity) : ComponentCallbacks {

    init {
        activity.registerComponentCallbacks(this)
    }

    override fun onLowMemory() {}

    override fun onConfigurationChanged(newConfig: Configuration) {
        if (activity.dressCodeAutoDarkEnabled.not())
            return

        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> activity.setDressCode(lightThemeId)
            Configuration.UI_MODE_NIGHT_YES -> activity.setDressCode(darkThemeId)
        }
    }

    fun unregister() {
        activity.unregisterComponentCallbacks(this)
    }
}
