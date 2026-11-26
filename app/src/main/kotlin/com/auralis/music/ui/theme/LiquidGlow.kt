/**
 * Auralis - Liquid Glow design utilities
 */
package com.auralis.music.ui.theme

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Paints a soft liquid-glow backdrop behind content using radial neon blooms.
 * Intended for hero sections, player backgrounds, and accent surfaces.
 */
fun Modifier.liquidGlowBackdrop(
    primary: Color = AppleTokens.GlowPurple,
    secondary: Color = AppleTokens.GlowCyan,
    tertiary: Color = AppleTokens.GlowMagenta,
    intensity: Float = 0.35f,
): Modifier = drawBehind {
    val w = size.width
    val h = size.height
    if (w <= 0f || h <= 0f) return@drawBehind

    // Base deep wash
    drawRect(Color(0xFF070A14))

    drawRect(
        brush = Brush.radialGradient(
            colors = listOf(primary.copy(alpha = intensity), Color.Transparent),
            center = Offset(w * 0.25f, h * 0.20f),
            radius = (w.coerceAtLeast(h)) * 0.85f,
        )
    )
    drawRect(
        brush = Brush.radialGradient(
            colors = listOf(secondary.copy(alpha = intensity * 0.75f), Color.Transparent),
            center = Offset(w * 0.80f, h * 0.30f),
            radius = (w.coerceAtLeast(h)) * 0.75f,
        )
    )
    drawRect(
        brush = Brush.radialGradient(
            colors = listOf(tertiary.copy(alpha = intensity * 0.55f), Color.Transparent),
            center = Offset(w * 0.55f, h * 0.85f),
            radius = (w.coerceAtLeast(h)) * 0.95f,
        )
    )
}

/** A compact glow brush for buttons/cards. */
fun liquidGlowBrush(
    start: Color = AppleTokens.GlowPurple,
    end: Color = AppleTokens.GlowCyan,
): Brush = Brush.linearGradient(listOf(start, end))
