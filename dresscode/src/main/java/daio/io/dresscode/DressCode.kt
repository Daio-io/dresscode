package daio.io.dresscode

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.ArrayMap
import kotlin.reflect.KClass

internal val activities = ArrayMap<KClass<out Activity>, String>()
private lateinit var availableDressCodes: ArrayMap<String, DressCode>
internal lateinit var currentDressCode: String

private lateinit var themePreferences: SharedPreferences
private const val PREFS_NAME = "io.daio.dresscode.prefs"
private const val PREFS_KEY = "io.daio.dresscode.currentdresscode"

/**
 * @param name [String] - DressCode name
 * @param themeId @StyleRes [Int] - Resource id for the theme declared in your styles.xml
 */
data class DressCode(
    val name: String,
    val themeId: Int
)

/**
 * Get the current dressCode @StyleRes [Int] resourceId set for the Activity or -1 if not set.
 *
 * Set a new dress code by @StyleRes resourceId. The dress code must have been registered
 * via the [declareDressCode] method before attempting to set. Set this will recreate your Activity
 * to apply the new theme. Make sure you have also setup DressCode in
 * your Activity by calling [matchDressCode]
 */
var Activity.dressCodeStyleId: Int
    get() = availableDressCodes[currentDressCode]?.themeId ?: -1
    set(value) {
        val resourceNameKey = resources.getResourceEntryName(value)
        checkDressCode(resourceNameKey)
        if (currentDressCode == resourceNameKey) return
        currentDressCode = resourceNameKey
        themePreferences.edit().putString(PREFS_KEY, resourceNameKey).apply()
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
 * @param dressCodes vararg list of [DressCode] @StyleRes resource ids. 0n calling
 * this, if no prior theme has been set it will use the first item you supply as the default.
 */
fun Application.declareDressCode(
    vararg dressCodes: DressCode
) {

    availableDressCodes = ArrayMap<String, DressCode>(dressCodes.size).apply {
        dressCodes.forEach {
            val resourceNameKey = resources.getResourceEntryName(it.themeId)
            put(resourceNameKey, it)
        }
    }

    themePreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    currentDressCode = themePreferences.getString(PREFS_KEY, null) ?:
            resources.getResourceEntryName(dressCodes[0].themeId)

    registerActivityLifecycleCallbacks(LifecycleListener())
}

/**
 * Call within your [Activity] onCreate before setContentView to set the dress code theme on
 * the current activity. Any dress code changes that occur will make sure the Activity automatically
 * applies the new theme.
 */
fun Activity.matchDressCode() {
    val theme = availableDressCodes[currentDressCode]?.themeId
        ?: availableDressCodes.valueAt(0).themeId

    setTheme(theme)
    activities[this::class] = currentDressCode
}

