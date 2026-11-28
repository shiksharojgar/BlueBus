package com.bluebus.passenger

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

        window.statusBarColor = darkBlue
        window.navigationBarColor = darkBlue

        val root = LinearLayout(this).apply {
    orientation = LinearLayout.VERTICAL
    setBackgroundColor(Color.WHITE)
    fitsSystemWindows = true
}

        root.addView(createHeader(), matchWidth())

        scrollView = ScrollView(this)

        contentContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 14, 16, 20)
            setBackgroundColor(lightBg)
        }

        scrollView.addView(contentContainer)

        root.addView(
            scrollView,
            LinearLayout.LayoutParams( -1, 0, 1f)
        )

        root.addView(createBottomBar(), matchWidth())

        setContentView(createSplashScreen())

        Handler(Looper.getMainLooper()).postDelayed({
            if (!isFinishing && !isDestroyed) {
                setContentView(root)
                showPage("Home")
            }
        }, 2500)
    }

    // पहली स्क्रीन
    private fun createSplashScreen(): ImageView {
        return ImageView(this).apply {
            setImageResource(R.drawable.bluebus_splash_art)
            scaleType = ImageView.ScaleType.FIT_XY
        }
    }

    // सभी पेजों का Header
    private fun createHeader(): LinearLayout {
        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(10, 12, 10, 12)
            background = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(darkBlue, blue)
            ).apply {
                cornerRadius = 20f
            }
        }

        val menu = TextView(this).apply {
            text = "☰"
            textSize = 29f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setOnClickListener { showMoreOptions(it) }
        }

        header.addView(menu, LinearLayout.LayoutParams(46, -2))

        val logo = ImageView(this).apply {
            setImageResource(R.drawable.ic_bluebus_full)
            scaleType = ImageView.ScaleType.FIT_CENTER
        }

        header.addView(logo, LinearLayout.LayoutParams(54, 54))

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

        brand.addView(title)
        brand.addView(tagline)

        header.addView(
            brand,
            LinearLayout.LayoutParams(0, -2, 1f)
        )

        return header
    }

    // पांच Bottom Menu बटन
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
            textSize = 10f
            gravity = Gravity.CENTER
            typeface = Typeface.DEFAULT_BOLD
            setPadding(2, 9, 2, 9)
            setOnClickListener { action(it) }
        }

        bottomItems[key] = item

        bar.addView(
            item,
            LinearLayout.LayoutParams(0, -2, 1f)
        )

        updateBottomStyle(item, key == "Home", key)
    }

    private fun updateBottomStyle(
        item: TextView,
        active: Boolean,
        key: String
    ) {
        val normalColor = when (key) {
            "Home" -> Color.rgb(205, 235, 255)
            "Bus Search" -> Color.rgb(215, 248, 225)
            "Notifications" -> Color.rgb(255, 230, 195)
            "Profile" -> Color.rgb(225, 215, 255)
            else -> Color.rgb(255, 225, 235)
        }

        item.setTextColor(darkBlue)
        item.background = roundedBackground(
            if (active) Color.YELLOW else normalColor, 12
        )
    }

    private fun setActiveBottom(key: String) {
        bottomItems.forEach { (name, item) ->
            updateBottomStyle(item, name == key, name)
        }
    }

    // More Option सूची
    private fun showMoreOptions(anchor: View) {
        val popup = PopupMenu(this, anchor)

        listOf(
            "About BlueBus",
            "Contact Us",
            "Help & Support",
            "Privacy Policy",
            "Terms & Conditions",
            "Share App",
            "Rate App"
        ).forEach {
            popup.menu.add(it)
        }

        popup.setOnMenuItemClickListener {
            if (it.title == "Share App") {
                val share = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "BlueBus - हर बस की जानकारी, अब एक जगह!"
                    )
                }
                startActivity(Intent.createChooser(share, "ऐप शेयर करें"))
            } else {
                showInfoPage(
                    it.title.toString(),
                    "यह सुविधा जल्द उपलब्ध होगी।"
                )
            }
            true
        }

        popup.show()
    }

    // पेज बदलना
    private fun showPage(name: String) {
        contentContainer.removeAllViews()
        scrollView.scrollTo(0, 0)

        setActiveBottom(
            when (name) {
                "Home" -> "Home"
                "Bus Search" -> "Bus Search"
                "Notifications" -> "Notifications"
                "Profile" -> "Profile"
                else -> ""
            }
        )

        when (name) {
            "Home" -> createHomePage()
            "Bus Search" -> createSearchPage()
            "View Route" -> createRoutePage()
            else -> showInfoPage(
                name,
                "इस पेज की जानकारी जल्द उपलब्ध होगी।"
            )
        }
    }

    // दूसरी स्क्रीन: Home
    private fun createHomePage() {
        val banner = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(8, 8, 8, 8)
            background = roundedBackground(Color.WHITE, 16)
        }

        val photo = ImageView(this).apply {
    setImageResource(R.drawable.bluebus_bus_photo)
    scaleType = ImageView.ScaleType.FIT_CENTER
    adjustViewBounds = true
}

banner.addView(
    photo,
    LinearLayout.LayoutParams(-1, ViewGroup.LayoutParams.WRAP_CONTENT)
)

        val slogan = TextView(this).apply {
            text = "सफर अब और भी आसान!"
            textSize = 23f
            setTextColor(darkBlue)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            setPadding(4, 10, 4, 10)
        }

        banner.addView(slogan)
        contentContainer.addView(banner, matchWidth())

        addHeading("बस सेवाएँ")

        addTileRow(
            "🚌\nबस रूट", "🕒\nसमय सारणी",
            "Bus Route", "Timetable",
            Color.rgb(205, 235, 255),
            Color.rgb(210, 245, 220)
        )

        addTileRow(
            "📍\nबस स्टॉप", "₹\nकिराया",
            "Bus Stop", "Fare",
            Color.rgb(255, 225, 190),
            Color.rgb(255, 240, 175)
        )

        addTileRow(
            "🔍\nबस खोजें", "🛣️\nरूट देखें",
            "Bus Search", "View Route",
            Color.rgb(220, 215, 255),
            Color.rgb(255, 215, 225)
        )

        addHeading("लोकप्रिय रूट")
        addPopularRoute("भोपाल → इंदौर")
        addPopularRoute("भोपाल → उज्जैन")

        addHeading("⭐ प्रायोजित बसें")

        sponsoredContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        contentContainer.addView(sponsoredContainer, matchWidth())
        loadSponsoredAds()
    }

        // दो रंगीन बटन एक पंक्ति में
    private fun addTileRow(
        text1: String,
        text2: String,
        page1: String,
        page2: String,
        color1: Int,
        color2: Int
    ) {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        row.addView(
            createTile(text1, color1) {
                showPage(page1)
            },
            LinearLayout.LayoutParams(0, 105, 1f).apply {
                setMargins(4, 5, 4, 5)
            }
        )

        row.addView(
            createTile(text2, color2) {
                showPage(page2)
            },
            LinearLayout.LayoutParams(0, 105, 1f).apply {
                setMargins(4, 5, 4, 5)
            }
        )

        contentContainer.addView(row, matchWidth())
    }

    // होम पेज के रंगीन बटन
    private fun createTile(
        text: String,
        color: Int,
        action: () -> Unit
    ): TextView {
        return TextView(this).apply {
            this.text = text
            textSize = 18f
            gravity = Gravity.CENTER
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(darkBlue)
            background = roundedBackground(color, 16)
            elevation = 4f
            setOnClickListener { action() }
        }
    }

    // पेज का शीर्षक
    private fun addHeading(text: String) {
        val heading = TextView(this).apply {
            this.text = text
            textSize = 19f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(darkBlue)
            setPadding(4, 18, 4, 10)
        }

        contentContainer.addView(heading, matchWidth())
    }

    // लोकप्रिय रूट कार्ड
    private fun addPopularRoute(routeName: String) {
        val card = TextView(this).apply {
            text = "🚌  $routeName       रूट देखें  ➜"
            textSize = 16f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(darkBlue)
            setPadding(16, 18, 16, 18)
            background = roundedBackground(Color.WHITE, 14)
            elevation = 3f
            setOnClickListener {
                showPage("View Route")
            }
        }

        val params = matchWidth()
        params.setMargins(0, 5, 0, 7)
        contentContainer.addView(card, params)
    }

    // बस खोजने वाला पेज
    private fun createSearchPage() {
        addHeading("🔍 बस खोजें")

        val fromInput = createInput("कहाँ से जाना है?")
        val toInput = createInput("कहाँ तक जाना है?")

        contentContainer.addView(fromInput, matchWidth())

        val gap1 = View(this)
        contentContainer.addView(gap1, LinearLayout.LayoutParams(1, 10))

        contentContainer.addView(toInput, matchWidth())

        val searchButton = createButton("बस खोजें", blue)

        searchButton.setOnClickListener {
            val from = fromInput.text.toString().trim()
            val to = toInput.text.toString().trim()

            if (from.isBlank() || to.isBlank()) {
                fromInput.error = if (from.isBlank()) {
                    "शुरुआती स्थान लिखें"
                } else null

                toInput.error = if (to.isBlank()) {
                    "गंतव्य स्थान लिखें"
                } else null
            } else {
                showSearchResults(from, to)
            }
        }

        contentContainer.addView(searchButton, matchWidth())

        addHeading("लोकप्रिय रूट")
        addPopularRoute("भोपाल → इंदौर")
        addPopularRoute("इंदौर → उज्जैन")
    }

    // खोज के परिणाम
    private fun showSearchResults(from: String, to: String) {
        contentContainer.removeAllViews()

        addHeading("🚌 बस खोज परिणाम")

        val info = TextView(this).apply {
            text = "$from  →  $to"
            textSize = 18f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(darkBlue)
            setPadding(8, 12, 8, 12)
        }

        contentContainer.addView(info, matchWidth())

        val result = TextView(this).apply {
            text = "इस रूट की बसों की जानकारी जल्द उपलब्ध होगी।"
            textSize = 15f
            setTextColor(Color.DKGRAY)
            setPadding(15, 20, 15, 20)
            background = roundedBackground(Color.WHITE, 14)
        }

        contentContainer.addView(result, matchWidth())
    }

    
    // तीसरी स्क्रीन: Route Page
private fun createRoutePage() {
    addHeading("🛣️ बस रूट की जानकारी")

    // तीसरी फोटो: BlueBus Route Banner
    val routeBanner = ImageView(this).apply {
    setImageResource(R.drawable.bluebus_route_banner)
    scaleType = ImageView.ScaleType.FIT_CENTER
    adjustViewBounds = true
    setBackgroundColor(Color.WHITE)
    visibility = View.VISIBLE
}

contentContainer.addView(
    routeBanner,
    LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        220
    )
)

    addHeading("भोपाल → इंदौर")

    addRouteDetail("🚌 बस का नाम", "BlueBus Express")
    addRouteDetail("📍 शुरुआती स्थान", "भोपाल")
    addRouteDetail("🏁 गंतव्य स्थान", "इंदौर")
    addRouteDetail("🕒 प्रस्थान समय", "सुबह 08:00 बजे")
    addRouteDetail("🕒 आगमन समय", "दोपहर 02:00 बजे")
    addRouteDetail("💰 अनुमानित किराया", "₹350")

    val note = TextView(this).apply {
        text = "नोट: यह अभी नमूना जानकारी है। वास्तविक बस और समय की पुष्टि ऑपरेटर से करें।"
        textSize = 13f
        setTextColor(Color.DKGRAY)
        setPadding(12, 15, 12, 15)
        background = roundedBackground(Color.WHITE, 12)
    }

    contentContainer.addView(note, matchWidth())

    val backButton = createButton("← वापस जाएँ", blue)

    backButton.setOnClickListener {
        showPage("Home")
    }

    contentContainer.addView(backButton, matchWidth())
}

    private fun addRouteDetail(label: String, value: String) {
        val card = TextView(this).apply {
            text = "$label\n$value"
            textSize = 16f
            setTextColor(darkBlue)
            setPadding(15, 12, 15, 12)
            background = roundedBackground(Color.WHITE, 12)
        }

        val params = matchWidth()
        params.setMargins(0, 4, 0, 4)
        contentContainer.addView(card, params)
    }

    // सामान्य जानकारी वाला पेज
    private fun showInfoPage(title: String, description: String) {
        contentContainer.removeAllViews()

        addHeading(title)

        val text = TextView(this).apply {
            this.text = description
            textSize = 16f
            setTextColor(Color.DKGRAY)
            setPadding(15, 20, 15, 20)
            background = roundedBackground(Color.WHITE, 14)
        }

        contentContainer.addView(text, matchWidth())
    }

        // टेक्स्ट इनपुट बॉक्स
    private fun createInput(hintText: String): EditText {
        return EditText(this).apply {
            hint = hintText
            textSize = 16f
            setTextColor(Color.BLACK)
            setHintTextColor(Color.GRAY)
            setSingleLine(true)
            setPadding(15, 12, 15, 12)
            background = roundedBackground(Color.WHITE, 12)
        }
    }

    // सामान्य बटन
    private fun createButton(
        buttonText: String,
        color: Int
    ): Button {
        return Button(this).apply {
            text = buttonText
            textSize = 16f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.WHITE)
            background = roundedBackground(color, 14)
            isAllCaps = false
        }
    }

    // गोल कोनों वाला बैकग्राउंड
    private fun roundedBackground(
        color: Int,
        radius: Int
    ): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = radius * resources.displayMetrics.density
        }
    }

    // पूरी चौड़ाई का Layout
    private fun matchWidth(): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    // Sponsored Ads लोड करना
    private fun loadSponsoredAds() {
        sponsoredContainer.removeAllViews()

        val loading = TextView(this).apply {
            text = "प्रायोजित बसें लोड हो रही हैं..."
            textSize = 14f
            setTextColor(Color.GRAY)
            setPadding(12, 15, 12, 15)
        }

        sponsoredContainer.addView(loading, matchWidth())

        try {
            SponsoredAdRepository.loadApprovedAds(
                placement = "home",
                onSuccess = { ads ->
                    runOnUiThread {
                        sponsoredContainer.removeAllViews()

                        if (ads.isEmpty()) {
                            val empty = TextView(this).apply {
                                text = "अभी कोई प्रायोजित बस उपलब्ध नहीं है।"
                                textSize = 14f
                                setTextColor(Color.GRAY)
                                setPadding(12, 15, 12, 15)
                            }

                            sponsoredContainer.addView(
                                empty,
                                matchWidth()
                            )
                        } else {
                            ads.forEach { ad ->
                                val adCard = SponsoredAdUi.createCard(
                                    this,
                                    ad
                                )

                                sponsoredContainer.addView(
                                    adCard,
                                    matchWidth()
                                )
                            }
                        }
                    }
                },
                onError = { error ->
                    runOnUiThread {
                        sponsoredContainer.removeAllViews()

                        val errorText = TextView(this).apply {
                            text = "विज्ञापन अभी लोड नहीं हो पाए।"
                            textSize = 14f
                            setTextColor(Color.GRAY)
                            setPadding(12, 15, 12, 15)
                        }

                        sponsoredContainer.addView(
                            errorText,
                            matchWidth()
                        )
                    }
                }
            )
        } catch (e: Exception) {
            sponsoredContainer.removeAllViews()

            val errorText = TextView(this).apply {
                text = "विज्ञापन अभी उपलब्ध नहीं हैं।"
                textSize = 14f
                setTextColor(Color.GRAY)
                setPadding(12, 15, 12, 15)
            }

            sponsoredContainer.addView(errorText, matchWidth())
        }
    }
}
