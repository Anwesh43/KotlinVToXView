package ui.anwesome.com.kotlinvtoxview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.vtoxview.VToXView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VToXView.create(this)
    }
}
