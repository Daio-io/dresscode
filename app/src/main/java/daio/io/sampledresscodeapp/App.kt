package daio.io.sampledresscodeapp

import android.app.Application
import daio.io.dresscode.DressCode
import daio.io.dresscode.register

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        register(this,
                DressCode("themeone", R.style.ThemeOne),
                DressCode("themetwo", R.style.ThemeTwo))
    }
}