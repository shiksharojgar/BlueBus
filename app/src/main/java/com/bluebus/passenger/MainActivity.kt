
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
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : Activity() {

    private val blue = Color.rgb(18, 100, 197)
    private val darkBlue = Color.rgb(7, 59, 120)
    private lateinit var resultsContainer: LinearLayout
    private lateinit var sponsoredContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 36, 24, 24)
            setBackgroundColor(Color.rgb(245, 249, 255))
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

        
        // BlueBus Home Banner Photo

        val bannerCard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(12, 14, 12, 14)
            setBackgroundColor(darkBlue)
        }

        val bannerImage = ImageView(this).apply {
            setImageResource(R.drawable.ic_bluebus_full)
            scaleType = ImageView.ScaleType.FIT_CENTER
            adjustViewBounds = true
        }

        bannerCard.addView(
            bannerImage,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (190 * resources.displayMetrics.density).toInt()
            )
        )

        val bannerText = TextView(this).apply {
            text = "सफल अब और भी आसान !"
            textSize = 25f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            setPadding(4, 14, 4, 8)
        }

        bannerCard.addView(bannerText, matchWidth())

        val bannerParams = matchWidth()
        bannerParams.bottomMargin = 18
        page.addView(bannerCard, bannerParams)

        val sponsoredHeading = TextView(this).apply {
    text = "⭐ प्रायोजित बसें"
    textSize = 20f
    setTextColor(darkBlue)
    typeface = Typeface.DEFAULT_BOLD
    setPadding(0, 12, 0, 12)
}
page.addView(sponsoredHeading, matchWidth())

sponsoredContainer = LinearLayout(this).apply {
    orientation = LinearLayout.VERTICAL
}
page.addView(sponsoredContainer, matchWidth())

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
            setSingleLine(true)
            setPadding(16, 14, 16, 14)
            setBackgroundColor(Color.WHITE)
        }
        page.addView(fromInput, inputParams())

        val toInput = EditText(this).apply {
            hint = "कहाँ तक? (शहर / बस स्टैंड)"
            textSize = 16f
            setSingleLine(true)
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
                    from.isEmpty() -> {
                        fromInput.error = "शुरुआती स्थान भरें"
                    }

                    to.isEmpty() -> {
                        toInput.error = "गंतव्य स्थान भरें"
                    }

                    else -> {
                        showSearchResults(from, to)
                    }
                }
            }
        }

        val buttonParams = matchWidth()
        buttonParams.topMargin = 20
        page.addView(searchButton, buttonParams)

        
        // BlueBus रंगीन मेनू बटन

        fun createMenuTile(
            title: String,
            emoji: String,
            color: Int
        ): TextView {
            return TextView(this).apply {
                text = "$emoji\n$title"
                textSize = 17f
                setTextColor(Color.rgb(7, 59, 120))
                typeface = Typeface.DEFAULT_BOLD
                gravity = Gravity.CENTER
                setPadding(8, 16, 8, 16)

                background = android.graphics.drawable.GradientDrawable().apply {
                    setColor(color)
                    cornerRadius = 24f
                }
            }
        }

        fun addMenuRow(
            first: TextView,
            second: TextView
        ) {
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
            }

            val firstParams = LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f
            ).apply {
                setMargins(4, 5, 6, 5)
            }

            val secondParams = LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f
            ).apply {
                setMargins(6, 5, 4, 5)
            }

            row.addView(first, firstParams)
            row.addView(second, secondParams)
            page.addView(row, matchWidth())
        }

        addMenuRow(
            createMenuTile("बस रूट", "🚌", Color.rgb(217, 240, 255)),
            createMenuTile("समय सारणी", "🕒", Color.rgb(221, 248, 229))
        )

        addMenuRow(
            createMenuTile("बस स्टॉप", "📍", Color.rgb(255, 233, 206)),
            createMenuTile("किराया", "₹", Color.rgb(255, 242, 184))
        )

        

        // बस खोजें और रूट देखें बटन

        val busSearchTile = createMenuTile(
            "बस खोजें",
            "🔍",
            Color.rgb(210, 230, 255)
        )

        busSearchTile.setOnClickListener {
            fromInput.requestFocus()
            val imm = getSystemService(
                android.content.Context.INPUT_METHOD_SERVICE
            ) as android.view.inputmethod.InputMethodManager
            imm.showSoftInput(
                fromInput,
                android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
            )
        }

        val routeViewTile = createMenuTile(
            "रूट देखें",
            "🛣️",
            Color.rgb(225, 235, 255)
        )

        routeViewTile.setOnClickListener {
            android.widget.Toast.makeText(
                this,
                "रूट की जानकारी जल्द उपलब्ध होगी।",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }

        addMenuRow(busSearchTile, routeViewTile)

        val resultsHeading = TextView(this).apply {
            text = "बस खोज परिणाम"
            textSize = 20f
            setTextColor(darkBlue)
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, 28, 0, 12)
        }
        page.addView(resultsHeading, matchWidth())

        resultsContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }
        page.addView(resultsContainer, matchWidth())

        val note = TextView(this).apply {
            text = "शुरुआत मध्य प्रदेश से • सत्यापित जानकारी पर विशेष ध्यान"
            textSize = 13f
            setTextColor(Color.DKGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 28, 0, 0)
        }
        page.addView(note, matchWidth())

        
        val scrollView = ScrollView(this).apply {
            addView(
                page,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        }

        // मुख्य स्क्रीन: ऊपर स्क्रॉल, नीचे स्थायी मेनू
        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.WHITE)
        }

        rootLayout.addView(
            scrollView,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        // नीचे का नीला Bottom Navigation Bar
        val bottomBar = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setBackgroundColor(darkBlue)
            setPadding(2, 8, 2, 8)
        }

        fun addBottomItem(
            emoji: String,
            label: String,
            action: () -> Unit
        ) {
            val item = TextView(this).apply {
                text = "$emoji\n$label"
                textSize = 12f
                setTextColor(Color.WHITE)
                typeface = Typeface.DEFAULT_BOLD
                gravity = Gravity.CENTER
                setPadding(2, 8, 2, 8)
                setOnClickListener { action() }
            }

            bottomBar.addView(
                item,
                LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1f
                )
            )
        }

        addBottomItem("🏠", "होम") {
            scrollView.smoothScrollTo(0, 0)
        }

        addBottomItem("🔍", "खोज") {
            fromInput.requestFocus()
            val imm = getSystemService(
                android.content.Context.INPUT_METHOD_SERVICE
            ) as android.view.inputmethod.InputMethodManager
            imm.showSoftInput(
                fromInput,
                android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
            )
        }

        addBottomItem("🔔", "सूचना") {
            android.widget.Toast.makeText(
                this, "सूचना सुविधा जल्द उपलब्ध होगी।",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }

        addBottomItem("👤", "प्रोफाइल") {
            android.widget.Toast.makeText(
                this, "प्रोफाइल सुविधा जल्द उपलब्ध होगी।",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }

        addBottomItem("☰", "अधिक") {
            android.widget.Toast.makeText(
                this, "अधिक विकल्प जल्द उपलब्ध होंगे।",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }

        rootLayout.addView(
            bottomBar,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        window.statusBarColor = darkBlue
        // मोबाइल के सिस्टम नेविगेशन के लिए जगह सुरक्षित रखें
rootLayout.setPadding(
    0,
    0,
    0,
    resources.getDimensionPixelSize(
        resources.getIdentifier(
            "navigation_bar_height",
            "dimen",
            "android"
        )
    )
)
        window.navigationBarColor = darkBlue

        setContentView(rootLayout)

        SponsoredAdRepository.loadApprovedAds(
    placement = "home",
    onSuccess = { ads ->
        runOnUiThread {
            sponsoredContainer.removeAllViews()

            ads.forEach { ad ->
                val card = SponsoredAdUi.createCard(this, ad)

                val params = matchWidth()
                params.bottomMargin = 14

                sponsoredContainer.addView(card, params)
            }
        }
    },
    onError = { exception ->
        android.util.Log.e(
            "BlueBus",
            "Sponsored ads load failed",
            exception
        )
    }
)
    }

    private fun showSearchResults(from: String, to: String) {
        resultsContainer.removeAllViews()

        val foundRoutes = BusRouteRepository.findRoutes(from, to)

        if (foundRoutes.isEmpty()) {
            val message = TextView(this).apply {
                text = "इस रूट की सत्यापित बस जानकारी अभी उपलब्ध नहीं है।\n\nहम जल्द ही और बसों की जानकारी जोड़ेंगे।"
                textSize = 16f
                setTextColor(Color.DKGRAY)
                setPadding(16, 20, 16, 20)
                setBackgroundColor(Color.WHITE)
            }
            resultsContainer.addView(message, matchWidth())
            return
        }

        val count = TextView(this).apply {
            text = "इस रूट पर ${foundRoutes.size} बस मिलीं।"
            textSize = 16f
            setTextColor(darkBlue)
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, 8, 0, 12)
        }
        resultsContainer.addView(count, matchWidth())

        foundRoutes.forEach { route ->
            addRouteCard(route)
        }
    }

    private fun addRouteCard(route: BusRoute) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(18, 16, 18, 16)
            setBackgroundColor(Color.WHITE)
        }

        fun addLine(value: String, size: Float, bold: Boolean = false) {
            val text = TextView(this).apply {
                text = value
                textSize = size
                setTextColor(Color.DKGRAY)
                if (bold) typeface = Typeface.DEFAULT_BOLD
                setPadding(0, 4, 0, 4)
            }
            card.addView(text, matchWidth())
        }

        addLine("🚌 ${route.busName}", 19f, true)
        addLine("ऑपरेटर: ${route.operatorName}", 15f)
        addLine("रूट: ${route.startPoint} → ${route.endPoint}", 15f)
        addLine("स्टॉप: ${route.stops.joinToString(" → ")}", 14f)
        addLine("प्रस्थान: ${route.departureTime ?: "उपलब्ध नहीं"}", 14f)
        addLine("आगमन: ${route.arrivalTime ?: "उपलब्ध नहीं"}", 14f)
        addLine("किराया: ${route.fare ?: "उपलब्ध नहीं"}", 14f)

        addLine(
            if (route.isVerified) "✓ सत्यापित बस" else "⚠ सत्यापन बाकी",
            14f,
            true
        )

        addLine("जानकारी का स्रोत: ${route.source}", 13f)
        addLine(
            "अंतिम जाँच: ${route.lastVerifiedDate ?: "उपलब्ध नहीं"}",
            13f
        )

        val params = matchWidth()
        params.bottomMargin = 14
        resultsContainer.addView(card, params)
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
