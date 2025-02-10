package com.jolpai.callblocker.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp


@Composable
fun ColoredText(
    modifier: Modifier,
    firstText: String,
    firstColor: Color,
    secondText: String,
    secondColor: Color
) {

    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(
            color = firstColor
        )) {
            append(firstText)
        }
        withStyle(
            style = SpanStyle(
                color = secondColor
            )
        ) {
            append(secondText)
        }
        //append(text.substring(4))
        //append(text)
    }

    Text(
        modifier = modifier,
        //textAlign = TextAlign.Center,
        text = annotatedText,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.headlineMedium
    )
}