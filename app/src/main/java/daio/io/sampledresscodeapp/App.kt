package daio.io.sampledresscodeapp

import android.app.Application
import daio.io.dresscode.DressCode
import daio.io.dresscode.declareDressCode
import daio.io.dresscode.theming.Dark
import daio.io.dresscode.theming.Light

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val light = DressCode("ThemeOne", R.style.ThemeOne, Light)
        val dark = DressCode("ThemeTwo", R.style.ThemeTwo, Dark)

        declareDressCode(
            light,
            dark
        )
    }
}