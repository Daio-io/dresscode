package daio.io.sampledresscodeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import daio.io.dresscode.dressCodeAutoDarkEnabled
import daio.io.dresscode.dressCodeStyleId
import daio.io.dresscode.matchDressCode
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        matchDressCode()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        button.text = if (dressCodeAutoDarkEnabled) "Auto Dark On" else "Auto Dark Off"

        button.setOnClickListener {
            dressCodeAutoDarkEnabled = dressCodeAutoDarkEnabled.not()
            button.text = if (dressCodeAutoDarkEnabled) "Auto Dark On" else "Auto Dark Off"
        }

        button2.setOnClickListener {
            dressCodeStyleId =
                if (dressCodeStyleId == R.style.ThemeOne) R.style.ThemeTwo else R.style.ThemeOne
        }
    }
}
