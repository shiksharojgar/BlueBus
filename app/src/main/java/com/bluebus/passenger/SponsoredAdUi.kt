
package com.bluebus.passenger

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

object SponsoredAdUi {

    private val darkBlue = Color.rgb(7, 59, 120)
    private val teal = Color.rgb(32, 184, 165)

    fun createCard(
        activity: Activity,
        ad: SponsoredAd
    ): View {

        val card = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(18, 16, 18, 16)
            setBackgroundColor(Color.WHITE)
        }

        fun addLine(
            value: String,
            size: Float,
            bold: Boolean = false
        ) {
            val text = TextView(activity).apply {
                this.text = value
                textSize = size
                setTextColor(Color.DKGRAY)
                if (bold) {
                    typeface = Typeface.DEFAULT_BOLD
                }
                setPadding(0, 5, 0, 5)
            }

            card.addView(
                text,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        }

        val sponsoredTag = TextView(activity).apply {
            text = "प्रायोजित विज्ञापन"
            textSize = 13f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            setBackgroundColor(teal)
            setPadding(12, 7, 12, 7)
            gravity = Gravity.CENTER
        }

        card.addView(
            sponsoredTag,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        addLine("🚌 ${ad.busName}", 20f, true)
        addLine("ऑपरेटर: ${ad.operatorName}", 16f)

        if (ad.description.isNotBlank()) {
            addLine(ad.description, 14f)
        }

        addLine(
            "रूट: ${ad.startPoint} → ${ad.endPoint}",
            15f,
            true
        )

        if (ad.stops.isNotEmpty()) {
            addLine(
                "स्टॉप: ${ad.stops.joinToString(" → ")}",
                14f
            )
        }

        if (ad.departureTime.isNotBlank()) {
            addLine("प्रस्थान: ${ad.departureTime}", 14f)
        }

        if (ad.arrivalTime.isNotBlank()) {
            addLine("आगमन: ${ad.arrivalTime}", 14f)
        }

        if (ad.fare.isNotBlank()) {
            addLine("किराया: ${ad.fare}", 15f, true)
        }

        if (ad.contactNumber.isNotBlank()) {
            addLine("संपर्क: ${ad.contactNumber}", 14f)
        }

        return card
    }
}
