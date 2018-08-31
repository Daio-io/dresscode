package daio.io.dresscode

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.ArrayMap
import kotlin.reflect.KClass

internal val activities = ArrayMap<KClass<out Activity>, String>()
private lateinit var availableDressCodes: ArrayMap<String, Int>
internal lateinit var currentDressCode: String

private lateinit var themePreferences: SharedPreferences
private const val PREFS_NAME = "io.daio.dresscode.prefs"
private const val PREFS_KEY = "io.daio.dresscode.currentdresscode"

/**
 * Get the current dressCode @StyleRes resourceId set for the Activity.
 *
 * Set a new dress code by @StyleRes resourceId. The dress code must have been registered
 * via the [declareDressCode] method before attempting to set. Set this will recreate your Activity
 * to apply the new theme. Make sure you have also setup DressCode in
 * your Activity by calling [matchDressCode]
 */
var Activity.dressCodeResId: Int
    get() = availableDressCodes[currentDressCode]
        ?: throw RuntimeException("DressCode has not been declared for resourceId. make sure you add it to your declareDressCode call")
    set(value) {
        val name = resources.getResourceEntryName(value)
        checkDressCode(name)
        if (currentDressCode == name) return
        currentDressCode = name
        themePreferences.edit().putString(PREFS_KEY, name).apply()
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
 * @param themeIds vararg list of [Int] @StyleRes resource ids. 0n calling
 * this, if no prior theme has been set it will use the first item you supply as the default.
 */
fun declareDressCode(
    application: Application,
    vararg themeIds: Int
) {

    availableDressCodes = ArrayMap<String, Int>(themeIds.size).apply {
        themeIds.forEach {
            val name = application.resources.getResourceEntryName(it)
            put(name, it)
        }
    }

    themePreferences = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    currentDressCode = themePreferences.getString(PREFS_KEY, null) ?:
            application.resources.getResourceEntryName(themeIds[0])

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

