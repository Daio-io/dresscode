package daio.io.dresscodetests

import androidx.test.runner.AndroidJUnit4
import daio.io.dresscode.DressCode
import daio.io.dresscode.DressCodeAlreadyInitialisedException
import daio.io.dresscode.declareDressCode
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class DressCodeInitialiseTests {

    @Test(expected = DressCodeAlreadyInitialisedException::class)
    fun throwsError_IfDressCodeAlreadyStarted() {
        RuntimeEnvironment.application.declareDressCode(
            DressCode("ThemeOne", R.style.ThemeOne),
            DressCode("ThemeTwo", R.style.ThemeTwo)
        )
        RuntimeEnvironment.application.declareDressCode(
            DressCode("ThemeOne", R.style.ThemeOne),
            DressCode("ThemeTwo", R.style.ThemeTwo)
        )
    }
}
