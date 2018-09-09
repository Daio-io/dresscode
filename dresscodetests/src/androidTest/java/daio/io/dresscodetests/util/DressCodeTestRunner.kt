package daio.io.dresscodetests.util

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import daio.io.dresscode.DressCode
import daio.io.dresscode.declareDressCode
import daio.io.dresscodetests.R

class DressCodeTestRunner : AndroidJUnitRunner() {

    override fun callApplicationOnCreate(app: Application) {
        app.applicationContext
            .getSharedPreferences("io.daio.dresscode.prefs", Context.MODE_PRIVATE)
            .edit()
            .clear()
            .commit()

        app.declareDressCode(
            DressCode("themeone", R.style.ThemeOne),
            DressCode("themetwo", R.style.ThemeTwo)
        )
        super.callApplicationOnCreate(app)
    }

}
