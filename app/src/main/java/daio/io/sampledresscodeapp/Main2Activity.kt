package daio.io.sampledresscodeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import daio.io.dresscode.dressCodeResId
import daio.io.dresscode.matchDressCode
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        matchDressCode()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        button.setOnClickListener {
            dressCodeResId = if (dressCodeResId == R.style.ThemeOne) R.style.ThemeTwo else R.style.ThemeOne
        }
    }


}
