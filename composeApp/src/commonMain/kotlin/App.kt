/**
 * Starter code for carousel project
 */

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CarouselApp() {

    // Define the seats
    val seats = remember {
        val outer = listOf(
            CarouselSeat(Color.Red, 0f),  // E
            CarouselSeat(Color(0xFFFFA500), 45f),  // SE
            CarouselSeat(Color.Yellow, 90f),  // S
            CarouselSeat(Color.Green, 135f),  // SW
            CarouselSeat(Color.Cyan, 180f),  // W
            CarouselSeat(Color.Blue, 225f),  // NW
            CarouselSeat(Color.Magenta, 270f),  // N
            CarouselSeat(Color.Gray, 315f)  // NE
        )
        outer
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Carousel Canvas
        Canvas(modifier = Modifier.size(400.dp)) {
            val center = this.center
            val radius = size.minDimension / 2

            // Draw Platform
            drawCircle(
                color = Color.LightGray,
                radius = radius, center = center
            )

            // Draw Center Hub (Square)
            val hubSize = 10.dp.toPx()

            drawRect(
                color = Color.Black,
                topLeft = Offset(
                    center.x - hubSize / 2,
                    center.y - hubSize / 2
                ),
                size = Size(hubSize, hubSize)
            )

            // Draw Seats
            seats.forEach { seat ->
                val angleRad =
                    (seat.initialAngle) * (PI / 180f).toFloat()
                val dist = radius * seat.distanceFactor
                val seatX = center.x + dist * cos(angleRad)
                val seatY = center.y + dist * sin(angleRad)

                // Each seat must be rotated its center
                rotate(degrees = seat.initialAngle + 90f,
                    pivot = Offset(seatX, seatY)) {
                    val w = 40.dp.toPx()
                    val h = 15.dp.toPx()
                    drawRect(
                        color = seat.color,
                        topLeft = Offset(seatX - w / 2, seatY - h / 2),
                        size = Size(w, h)
                    )
                }
            }
        }
    }
}
