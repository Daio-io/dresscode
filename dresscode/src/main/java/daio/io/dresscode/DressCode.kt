package daio.io.dresscode

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.ArrayMap
import daio.io.dresscode.theming.ThemeType
import daio.io.dresscode.theming.Unknown
import daio.io.dresscode.theming.Light
import daio.io.dresscode.theming.Dark
import daio.io.dresscode.theming.currentTheme
import daio.io.dresscode.theming.resetTheme
import kotlin.reflect.KClass

internal val activities = ArrayMap<KClass<out Activity>, String>()
internal lateinit var availableDressCodes: ArrayMap<String, DressCode>
internal lateinit var currentDressCode: String

internal lateinit var themePreferences: SharedPreferences
private const val PREFS_NAME = "io.daio.dresscode.prefs"
private const val PREFS_KEY = "io.daio.dresscode.currentdresscode"
internal const val AUTO_THEME_KEY = "io.daio.dresscode.autotheme"
internal const val DARK_THEME_KEY = "io.daio.dresscode.darktheme"
internal const val LIGHT_THEME_KEY = "io.daio.dresscode.lighttheme"

/**
 * @param name [String] - DressCode name
 * @param themeId @StyleRes [Int] - Resource id for the theme declared in your styles.xml
 * @param type[ThemeType] - The theme style type [Light], [Dark] or [Unknown]. Defaults to [Unknown] if not set.
 */
data class DressCode(
    val name: String,
    val themeId: Int,
    val type: ThemeType = Unknown
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
        themePreferences.edit().putBoolean(AUTO_THEME_KEY, false).apply()
        setDressCode(value)
    }

var Activity.dressCodeAutoDarkEnabled: Boolean
    get() = themePreferences.getBoolean(AUTO_THEME_KEY, false)
    set(value) {
        if (value) checkAutoSupport()
        themePreferences.edit().putBoolean(AUTO_THEME_KEY, value).apply()
        resetTheme(value)
    }

fun checkAutoSupport() {
    val noSupport = availableDressCodes.values.all { it.type == Unknown }
    if (noSupport)
        throw DressCodeTypeNotDefinedException(
            "Auto mode not possible. Make " +
                "sure to define DressCode.types when initialising."
        )
}

internal fun Activity.setDressCode(styleId: Int) {
    val resourceNameKey = resources.getResourceEntryName(styleId)
    checkDressCode(resourceNameKey)
    if (currentDressCode == resourceNameKey) return
    currentDressCode = resourceNameKey
    themePreferences.edit().putString(PREFS_KEY, resourceNameKey).apply()
    recreate()
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
    if (::availableDressCodes.isInitialized) {
        throw DressCodeAlreadyInitialisedException("declareDressCode called more than once")
    }

    availableDressCodes = ArrayMap<String, DressCode>(dressCodes.size).apply {
        dressCodes.forEach {
            val resourceNameKey = resources.getResourceEntryName(it.themeId)
            put(resourceNameKey, it)
        }
    }

    themePreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    currentDressCode = themePreferences.getString(PREFS_KEY, null)
        ?: resources.getResourceEntryName(dressCodes[0].themeId)

    registerActivityLifecycleCallbacks(LifeCycleListener())
}

/**
 * Call within your [Activity] onCreate before setContentView to set the dress code theme on
 * the current activity. Any dress code changes that occur will make sure the Activity automatically
 * applies the new theme.
 */
fun Activity.matchDressCode() {
    setTheme(currentTheme)
    activities[this::class] = currentDressCode
}

private fun checkDressCode(value: String) {
    if (availableDressCodes[value] == null) {
        throw DressCodeNotRegisteredException("Dress Code $value does not exist")
    }
}
