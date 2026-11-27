
package com.bluebus.passenger

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private val blue = Color.rgb(18, 100, 197)
    private val darkBlue = Color.rgb(7, 59, 120)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 36, 24, 24)
            setBackgroundColor(Color.rgb(245, 249, 255))
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        val brand = TextView(this).apply {
            text = "🚌 BlueBus"
            textSize = 30f
            setTextColor(darkBlue)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
        }
        page.addView(brand, matchWidth())

        val tagline = TextView(this).apply {
            text = "हर बस की जानकारी, अब एक जगह!"
            textSize = 16f
            setTextColor(darkBlue)
            gravity = Gravity.CENTER
            setPadding(0, 8, 0, 28)
        }
        page.addView(tagline, matchWidth())

        val heading = TextView(this).apply {
            text = "अपनी बस खोजें"
            textSize = 22f
            setTextColor(Color.BLACK)
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, 8, 0, 16)
        }
        page.addView(heading, matchWidth())

        val fromInput = EditText(this).apply {
            hint = "कहाँ से? (शहर / बस स्टैंड)"
            textSize = 16f
            singleLine = true
            setPadding(16, 14, 16, 14)
            setBackgroundColor(Color.WHITE)
        }
        page.addView(fromInput, inputParams())

        val toInput = EditText(this).apply {
            hint = "कहाँ तक? (शहर / बस स्टैंड)"
            textSize = 16f
            singleLine = true
            setPadding(16, 14, 16, 14)
            setBackgroundColor(Color.WHITE)
        }
        val toParams = inputParams()
        toParams.topMargin = 14
        page.addView(toInput, toParams)

        val searchButton = Button(this).apply {
            text = "बस खोजें"
            textSize = 17f
            setTextColor(Color.WHITE)
            setBackgroundColor(blue)
            setOnClickListener {
                val from = fromInput.text.toString().trim()
                val to = toInput.text.toString().trim()

                when {
                    from.isEmpty() -> fromInput.error = "शुरुआती स्थान भरें"
                    to.isEmpty() -> toInput.error = "गंतव्य स्थान भरें"
                    else -> Toast.makeText(
                        this@MainActivity,
                        "रूट खोज सुविधा अगले चरण में जोड़ी जाएगी।",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        val buttonParams = matchWidth()
        buttonParams.topMargin = 20
        page.addView(searchButton, buttonParams)

        val note = TextView(this).apply {
            text = "शुरुआत मध्य प्रदेश से • सत्यापित जानकारी पर विशेष ध्यान"
            textSize = 13f
            setTextColor(Color.DKGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 28, 0, 0)
        }
        page.addView(note, matchWidth())

        setContentView(page)
    }

    private fun matchWidth() = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    private fun inputParams() = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
}
