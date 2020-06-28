package daio.io.dresscode.theming

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import daio.io.dresscode.DARK_THEME_KEY
import daio.io.dresscode.LIGHT_THEME_KEY
import daio.io.dresscode.availableDressCodes
import daio.io.dresscode.currentDressCode
import daio.io.dresscode.dressCodeAutoDarkEnabled
import daio.io.dresscode.setDressCode
import daio.io.dresscode.themePreferences

typealias ThemeType = Int

const val Light: ThemeType = 0
const val Dark: ThemeType = 1
const val Unknown: ThemeType = 2

var Activity.dressCodeDarkTheme: Int
    get() = darkThemeId
    set(value) {
        darkThemeId = value
        if (dressCodeAutoDarkEnabled) resetTheme(dressCodeAutoDarkEnabled)
    }

var Activity.dressCodeLightTheme: Int
    get() = lightThemeId
    set(value) {
        lightThemeId = value
        if (dressCodeAutoDarkEnabled) resetTheme(dressCodeAutoDarkEnabled)
    }

internal fun Activity.resetTheme(autoDark: Boolean) {
    val themeId = if (autoDark) currentAutoTheme else currentTheme
    setDressCode(themeId)
}

val Activity.currentAutoTheme: Int
    get() = resources.currentThemeFromMode

internal val Resources.currentThemeFromMode: Int
    get() = when (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_NO -> lightThemeId
        Configuration.UI_MODE_NIGHT_YES -> darkThemeId
        else -> availableDressCodes.valueAt(0).themeId
    }

internal val currentTheme: Int
    get() = availableDressCodes[currentDressCode]?.themeId ?: availableDressCodes.valueAt(0).themeId

internal var lightThemeId: Int
    get() {
        val theme = themePreferences.getInt(LIGHT_THEME_KEY, -1)
        return if (theme == -1) availableDressCodes.values.first { it.type == Light }.themeId else theme
    }
    set(value) {
        themePreferences.edit().putInt(LIGHT_THEME_KEY, value).apply()
    }

internal var darkThemeId: Int
    get() {
        val theme = themePreferences.getInt(DARK_THEME_KEY, -1)
        return if (theme == -1) availableDressCodes.values.first { it.type == Dark }.themeId else theme
    }
    set(value) {
        themePreferences.edit().putInt(DARK_THEME_KEY, value).apply()
    }
