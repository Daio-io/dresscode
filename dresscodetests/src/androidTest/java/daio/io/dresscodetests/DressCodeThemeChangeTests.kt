package daio.io.dresscodetests

import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.IntegerRes
import androidx.test.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import daio.io.dresscode.DressCodeNotRegisteredException
import daio.io.dresscode.dressCodeStyleId
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DressCodeThemeChangeTests {

    @get:Rule
    val rule = ActivityTestRule<DressCodeActivity>(DressCodeActivity::class.java)

    @Test
    fun updateThemeAtRuntime() {
        assertEquals(R.style.ThemeOne, rule.activity.dressCodeStyleId)
        rule.activity.theme.assertThemeAttr(R.color.colorAccentThemeOne, R.attr.colorAccent)

        rule.runOnUiThread {
            rule.activity.dressCodeStyleId = R.style.ThemeTwo
        }

        waitForUI()
        assertEquals(R.style.ThemeTwo, rule.activity.dressCodeStyleId)
        rule.activity.theme.assertThemeAttr(R.color.colorAccentThemeTwo, R.attr.colorAccent)
    }

    @Test(expected = DressCodeNotRegisteredException::class)
    fun throwsError_IfThemeNotRegistered() {

        rule.runOnUiThread {
            rule.activity.dressCodeStyleId = R.style.UnregisteredTheme
        }
    }
}

internal fun Resources.Theme.assertThemeAttr(@IntegerRes expected: Int, @IntegerRes attr: Int) {
    val typeVal = TypedValue()
    resolveAttribute(attr, typeVal, true)
    assertEquals(expected, typeVal.resourceId)
}

internal fun waitForUI() {
    InstrumentationRegistry.getInstrumentation().waitForIdleSync()
}
