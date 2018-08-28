package daio.io.dresscode

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.ArrayMap
import kotlin.reflect.KClass

internal val activities = ArrayMap<KClass<out Activity>, String>()
private lateinit var availableDressCodes: ArrayMap<String, Int>
private lateinit var currentDressCode: String

private lateinit var themePreferences: SharedPreferences
private const val PREFS_NAME = "io.daio.dresscode.prefs"
private const val PREFS_KEY = "io.daio.dresscode.currentdresscode"

data class DressCode(val name: String,
                     val themeId: Int)

/**
 * Get the current dressCode set for the application.
 *
 * Set a new dress code by name. The dress code must have been registered
 * via the [declareDressCode] method before attempting to set. Set this will recreate your Activity
 * to apply the new theme. Make sure you have also setup DressCode in
 * your Activity by calling [matchDressCode]
 */
var Activity.dressCodeName
    get() = currentDressCode
    set(value) {
        checkDressCode(value)
        if (currentDressCode == value) return
        currentDressCode = value
        themePreferences.edit().putString(PREFS_KEY, value).apply()
        recreate()
    }

private fun checkDressCode(value: String) {
    if (availableDressCodes[value] == null)
        throw RuntimeException("Dress Code $value does not exist")
}


/**
 * To declareDressCode your app with DressCodes you need to call [declareDressCode] from your Application class.
 * Give your dressCodes a name and the themeId defined in your styles.xml
 * This method must be called before calling [matchDressCode].
 * If you try to change dresscode to a theme that does not exits it will
 * throw an exception so make sure you declareDressCode all your dress code themes here.
 *
 * @param application [Application] class
 * @param dressCodes vararg list of [DressCode] containing name and a resource id style. 0n calling
 * this, if no prior theme has been set it will use the first [DressCode] you supply as the default.
 */
fun declareDressCode(application: Application,
                     vararg dressCodes: DressCode) {

    availableDressCodes = ArrayMap<String, Int>(dressCodes.size).apply {
        dressCodes.forEach { put(it.name, it.themeId) }
    }

    themePreferences = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    currentDressCode = themePreferences.getString(PREFS_KEY, null) ?: dressCodes[0].name
    application.registerActivityLifecycleCallbacks(LifecycleListener())
}

/**
 * Call within your [Activity] onCreate before setContentView to set the dress code theme on
 * the current activity. Any dress code changes that occur will make sure the Activity automatically
 * applies the new theme.
 */
fun Activity.matchDressCode() {
    val theme = availableDressCodes[currentDressCode]
            ?: availableDressCodes.valueAt(0)

    setTheme(theme)
    activities[this::class] = currentDressCode
}

