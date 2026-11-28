
package com.bluebus.passenger

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private val blue = Color.rgb(18, 100, 197)
    private val darkBlue = Color.rgb(7, 59, 120)
    private val lightBg = Color.rgb(245, 249, 255)

    private lateinit var contentContainer: LinearLayout
    private lateinit var sponsoredContainer: LinearLayout
    private lateinit var resultsContainer: LinearLayout
    private lateinit var scrollView: ScrollView
    private val bottomItems = mutableMapOf<String, TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.WHITE)
        }

        val header = createHeader()
        rootLayout.addView(header, matchWidth())

        scrollView = ScrollView(this)
        contentContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 24)
            setBackgroundColor(lightBg)
        }

        scrollView.addView(
            contentContainer,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        rootLayout.addView(
            scrollView,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        rootLayout.addView(createBottomBar(), matchWidth())

        val statusBarHeight = resources.getIdentifier(
    "status_bar_height", "dimen", "android"
)

val navigationHeight = resources.getIdentifier(
    "navigation_bar_height", "dimen", "android"
)

val topSpace = if (statusBarHeight != 0) {
    resources.getDimensionPixelSize(statusBarHeight)
} else {
    dp(24)
}

val bottomSpace = if (navigationHeight != 0) {
    resources.getDimensionPixelSize(navigationHeight)
} else {
    dp(24)
}

rootLayout.setPadding(
    0,
    topSpace,
    0,
    bottomSpace
)

        window.statusBarColor = darkBlue
        window.navigationBarColor = darkBlue

        setContentView(rootLayout)

        showPage("Home")
    }

    // सभी पेजों पर एक जैसा Header
    private fun createHeader(): LinearLayout {
        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(10, 12, 10, 12)
            background = android.graphics.drawable.GradientDrawable(
                android.graphics.drawable.GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(darkBlue, blue)
            ).apply {
                cornerRadius = 20f
            }
        }

        val menuButton = TextView(this).apply {
            text = "☰"
            textSize = 29f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(8, 6, 12, 6)
            setOnClickListener { showMoreOptions(it) }
        }

        header.addView(
            menuButton,
            LinearLayout.LayoutParams(
                dp(46), ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        val logo = ImageView(this).apply {
            setImageResource(R.drawable.ic_bluebus_full)
            scaleType = ImageView.ScaleType.FIT_CENTER
        }

        header.addView(
            logo,
            LinearLayout.LayoutParams(dp(54), dp(54))
        )

        val brand = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(10, 0, 0, 0)
        }

        val title = TextView(this).apply {
            text = "BlueBus"
            textSize = 25f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
        }

        val tagline = TextView(this).apply {
            text = "हर बस की जानकारी, अब एक जगह!"
            textSize = 11f
            setTextColor(Color.WHITE)
        }

        brand.addView(title, matchWidth())
        brand.addView(tagline, matchWidth())

        header.addView(
            brand,
            LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f
            )
        )

        return header
    }


// अलग-अलग बटन वाला Bottom Navigation
private fun createBottomBar(): LinearLayout {
    bottomItems.clear()

    val bar = LinearLayout(this).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER
        setBackgroundColor(Color.WHITE)
        setPadding(3, 7, 3, 7)
    }

    addBottomItem(bar, "Home", "🏠", "होम") {
        showPage("Home")
    }

    addBottomItem(bar, "Bus Search", "🔍", "खोज") {
        showPage("Bus Search")
    }

    addBottomItem(bar, "Notifications", "🔔", "सूचना") {
        showPage("Notifications")
    }

    addBottomItem(bar, "Profile", "👤", "प्रोफाइल") {
        showPage("Profile")
    }

    addBottomItem(bar, "More Option", "☰", "More Option") {
        setActiveBottom("More Option")
        showMoreOptions(it)
    }

    return bar
}

private fun addBottomItem(
    bar: LinearLayout,
    key: String,
    emoji: String,
    label: String,
    action: (View) -> Unit
) {
    val item = TextView(this).apply {
        text = "$emoji\n$label"
        textSize = if (key == "More Option") 9f else 11f
        typeface = Typeface.DEFAULT_BOLD
        gravity = Gravity.CENTER
        setPadding(2, 9, 2, 9)
        setOnClickListener { action(it) }
    }

    bottomItems[key] = item

    bar.addView(
        item,
        LinearLayout.LayoutParams(
            0,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1f
        ).apply {
            setMargins(dp(3), 0, dp(3), 0)
        }
    )

    setBottomItemStyle(item, key == "Home")
}

private fun setBottomItemStyle(
    item: TextView,
    active: Boolean
) {
    item.setTextColor(darkBlue)

    item.background =
        android.graphics.drawable.GradientDrawable().apply {
            setColor(
                if (active) {
                    Color.rgb(255, 220, 65)
                } else {
                    Color.rgb(225, 239, 255)
                }
            )
            cornerRadius = dp(12).toFloat()
        }
}

private fun setActiveBottom(key: String) {
    bottomItems.forEach { (itemKey, item) ->
        setBottomItemStyle(item, itemKey == key)
    }
}


    // More Option की सूची - अलग पेज नहीं
    private fun showMoreOptions(anchor: View) {
        val popup = PopupMenu(this, anchor)

        val options = listOf(
            "About BlueBus",
            "Contact Us",
            "Help & Support",
            "Privacy Policy",
            "Terms & Conditions",
            "Share App",
            "Rate App"
        )

        options.forEach { popup.menu.add(it) }

        popup.setOnMenuItemClickListener { item ->
            when (item.title.toString()) {
                "Share App" -> {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "BlueBus - हर बस की जानकारी, अब एक जगह!"
                        )
                    }
                    startActivity(
                        Intent.createChooser(shareIntent, "ऐप शेयर करें")
                    )
                }

                "Rate App" -> {
                    showInfoPage(
                        "Rate App",
                        "BlueBus को रेट करने की सुविधा जल्द उपलब्ध होगी।"
                    )
                }

                "About BlueBus" -> showInfoPage(
                    "About BlueBus",
                    "BlueBus का उद्देश्य बसों की जानकारी एक जगह उपलब्ध कराना है।"
                )

                "Contact Us" -> showInfoPage(
                    "Contact Us",
                    "संपर्क जानकारी जल्द उपलब्ध होगी।"
                )

                "Help & Support" -> showInfoPage(
                    "Help & Support",
                    "यात्रा और बस खोज से जुड़ी सहायता जल्द उपलब्ध होगी।"
                )

                "Privacy Policy" -> showInfoPage(
                    "Privacy Policy",
                    "BlueBus की गोपनीयता नीति जल्द उपलब्ध होगी।"
                )

                "Terms & Conditions" -> showInfoPage(
                    "Terms & Conditions",
                    "BlueBus के नियम और शर्तें जल्द उपलब्ध होंगी।"
                )
            }
            true
        }

        popup.show()
    }

    // पेज बदलने का मुख्य तरीका
    private fun showPage(pageName: String) {
        contentContainer.removeAllViews()
        scrollView.scrollTo(0, 0)

        when (pageName) {
            "Home" -> createHomePage()
            "Bus Search" -> createSearchPage()
            else -> showInfoPage(
                pageName,
                getPageDescription(pageName)
            )
        }
    }

    // Home Page
    private fun createHomePage() {

        val banner = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(10, 12, 10, 12)
            setBackgroundColor(darkBlue)
        }

        val bannerImage = ImageView(this).apply {
            setImageResource(R.drawable.ic_bluebus_full)
            scaleType = ImageView.ScaleType.FIT_CENTER
            adjustViewBounds = true
        }

        banner.addView(
            bannerImage,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(180)
            )
        )

        val bannerText = TextView(this).apply {
            text = "सफल अब और भी आसान !"
            textSize = 24f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            setPadding(4, 12, 4, 8)
        }

        banner.addView(bannerText, matchWidth())
        contentContainer.addView(banner, matchWidth())

        addHeading("⭐ प्रायोजित बसें")

        sponsoredContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }
        contentContainer.addView(sponsoredContainer, matchWidth())

        loadSponsoredAds()

        addHeading("अपनी बस खोजें")

        val searchButton = createButton("🔍 बस खोजें") {
            showPage("Bus Search")
        }
        contentContainer.addView(searchButton, matchWidth())

        addHeading("बस सेवाएँ")

        addTileRow(
            "🚌\nबस रूट", "🕒\nसमय सारणी",
            "Bus Route", "Timetable"
        )

        addTileRow(
            "📍\nबस स्टॉप", "₹\nकिराया",
            "Bus Stop", "Fare"
        )

        addTileRow(
            "🔍\nबस खोजें", "🛣️\nरूट देखें",
            "Bus Search", "View Route"
        )
    }

    // Bus Search Page
    private fun createSearchPage() {
        addHeading("🔍 अपनी बस खोजें")

        val fromInput = createInput("कहाँ से? (शहर / बस स्टैंड)")
        val toInput = createInput("कहाँ तक? (शहर / बस स्टैंड)")

        contentContainer.addView(fromInput, matchWidth())

        val toParams = matchWidth()
        toParams.topMargin = dp(12)
        contentContainer.addView(toInput, toParams)

        contentContainer.addView(
            createButton("बस खोजें") {
                val from = fromInput.text.toString().trim()
                val to = toInput.text.toString().trim()

                if (from.isEmpty()) {
                    fromInput.error = "शुरुआती स्थान भरें"
                } else if (to.isEmpty()) {
                    toInput.error = "गंतव्य स्थान भरें"
                } else {
                    showSearchResults(from, to)
                }
            },
            matchWidth()
        )

        addHeading("बस खोज परिणाम")

        resultsContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }
        contentContainer.addView(resultsContainer, matchWidth())
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
            14f, true
        )

        addLine("जानकारी का स्रोत: ${route.source}", 13f)
        addLine(
            "अंतिम जाँच: ${route.lastVerifiedDate ?: "उपलब्ध नहीं"}",
            13f
        )

        val params = matchWidth()
        params.bottomMargin = dp(12)
        resultsContainer.addView(card, params)
    }

    // अन्य पेजों का सामान्य ढाँचा
    private fun showInfoPage(title: String, description: String) {
        contentContainer.removeAllViews()
        scrollView.scrollTo(0, 0)

        addHeading(title)

        val message = TextView(this).apply {
            text = description
            textSize = 17f
            setTextColor(Color.DKGRAY)
            setPadding(18, 22, 18, 22)
            setBackgroundColor(Color.WHITE)
        }

        contentContainer.addView(message, matchWidth())

        val homeButton = createButton("🏠 होम पर जाएँ") {
            showPage("Home")
        }

        val params = matchWidth()
        params.topMargin = dp(20)
        contentContainer.addView(homeButton, params)
    }

    private fun getPageDescription(pageName: String): String {
        return when (pageName) {
            "Bus Route" ->
                "यहाँ बसों के रूट और उनके स्टॉप की जानकारी दिखाई जाएगी।"

            "Timetable" ->
                "यहाँ बसों के आने और जाने का समय दिखाई देगा।"

            "Bus Stop" ->
                "यहाँ आसपास के बस स्टॉप की जानकारी दिखाई जाएगी।"

            "Fare" ->
                "यहाँ बस का किराया और यात्रा की जानकारी मिलेगी।"

            "View Route" ->
                "यहाँ बस का पूरा रूट और बीच के स्टॉप दिखाई देंगे।"

            "Notifications" ->
                "यहाँ BlueBus की नई सूचनाएँ दिखाई जाएँगी।"

            "Profile" ->
                "यहाँ यात्री अपनी प्रोफाइल और अकाउंट की जानकारी देख सकेंगे।"

            else ->
                "इस सुविधा की जानकारी जल्द उपलब्ध होगी।"
        }
    }

    // Home पर स्वीकृत Sponsored Ads
    private fun loadSponsoredAds() {
        SponsoredAdRepository.loadApprovedAds(
            placement = "home",
            onSuccess = { ads ->
                runOnUiThread {
                    if (!isFinishing && ::sponsoredContainer.isInitialized) {
                        sponsoredContainer.removeAllViews()

                        ads.forEach { ad ->
                            val card = SponsoredAdUi.createCard(this, ad)
                            val params = matchWidth()
                            params.bottomMargin = dp(12)
                            sponsoredContainer.addView(card, params)
                        }
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

    private fun addHeading(text: String) {
        val heading = TextView(this).apply {
            this.text = text
            textSize = 21f
            setTextColor(darkBlue)
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, 18, 0, 12)
        }
        contentContainer.addView(heading, matchWidth())
    }

    private fun addTileRow(
        firstText: String,
        secondText: String,
        firstPage: String,
        secondPage: String
    ) {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        row.addView(
            createTile(firstText) { showPage(firstPage) },
            LinearLayout.LayoutParams(0, dp(100), 1f).apply {
                setMargins(4, 5, 6, 5)
            }
        )

        row.addView(
            createTile(secondText) { showPage(secondPage) },
            LinearLayout.LayoutParams(0, dp(100), 1f).apply {
                setMargins(6, 5, 4, 5)
            }
        )

        contentContainer.addView(row, matchWidth())
    }

    private fun createTile(
        text: String,
        action: () -> Unit
    ): TextView {
        return TextView(this).apply {
            this.text = text
            textSize = 17f
            setTextColor(darkBlue)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            setPadding(8, 12, 8, 12)
            background = android.graphics.drawable.GradientDrawable().apply {
                setColor(Color.rgb(217, 240, 255))
                cornerRadius = 24f
            }
            setOnClickListener { action() }
        }
    }

    private fun createButton(
        text: String,
        action: () -> Unit
    ): Button {
        return Button(this).apply {
            this.text = text
            textSize = 16f
            setTextColor(Color.WHITE)
            setBackgroundColor(blue)
            setOnClickListener { action() }
        }
    }

    private fun createInput(hintText: String): EditText {
        return EditText(this).apply {
            hint = hintText
            textSize = 16f
            setSingleLine(true)
            setPadding(16, 12, 16, 12)
            setBackgroundColor(Color.WHITE)
        }
    }

    private fun matchWidth() = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }
}
