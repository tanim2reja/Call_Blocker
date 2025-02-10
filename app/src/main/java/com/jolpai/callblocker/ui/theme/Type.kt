package com.jolpai.callblocker.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.info.callblocker.R

//Step 2, add this to Type.kt file
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Lobster Two")

// Step 3, Define your font in Type.kt file
val poppins = GoogleFont("Poppins")

// Step 4, Define you fontFamily in Type.kt file
val fontPoppins = FontFamily(
    Font(
        googleFont = poppins,
        fontProvider = provider,
    )
)

val poppinsBlack = FontFamily(
    Font(
        googleFont = poppins,
        fontProvider = provider,
        weight = FontWeight.Black,
        style = FontStyle.Normal
    )
)

val poppinsThin = FontFamily(
    Font(
        googleFont = poppins,
        fontProvider = provider,
        weight = FontWeight.Thin,
        style = FontStyle.Normal
    )
)

val poppinsNormal = FontFamily(
    Font(
        googleFont = poppins,
        fontProvider = provider,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    )
)

val poppinsBold = FontFamily(
    Font(
        googleFont = poppins,
        fontProvider = provider,
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    )
)

val AppTypography = Typography()