package com.bluebus.passenger

data class SponsoredAd(
    val adId: String = "",
    val operatorName: String = "",
    val busName: String = "",
    val description: String = "",

    // बस और रूट की जानकारी
    val startPoint: String = "",
    val endPoint: String = "",
    val stops: List<String> = emptyList(),
    val departureTime: String = "",
    val arrivalTime: String = "",
    val fare: String = "",
    val contactNumber: String = "",

    // तस्वीर का HTTPS लिंक
    val imageUrl: String = "",

    // विज्ञापन भुगतान
    val amount: Long = 0,
    val paymentMethod: String = "manual",
    val paymentReference: String = "",
    val paymentVerified: Boolean = false,

    // विज्ञापन की स्थिति
    val status: String = "pending",

    // विज्ञापन कहाँ दिखेगा
    val placements: List<String> = listOf(
        "home",
        "search",
        "operators"
    ),

    // विज्ञापन की अवधि (milliseconds)
    val startAtMillis: Long = 0,
    val endAtMillis: Long = 0,

    // एडमिन रिकॉर्ड
    val adminNote: String = "",
    val createdAtMillis: Long = 0,
    val approvedAtMillis: Long = 0
)
