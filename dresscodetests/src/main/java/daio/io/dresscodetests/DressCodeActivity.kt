package daio.io.dresscodetests

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import daio.io.dresscode.matchDressCode

class DressCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        matchDressCode()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
