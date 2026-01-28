import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CarouselApp() {

    // Define the seats
    val seats = remember {
        val outer = listOf(
            CarouselSeat(Color.Cyan, 180f),
            CarouselSeat(Color.Blue, 225f),
            CarouselSeat(Color.Magenta, 270f),
            CarouselSeat(Color.Gray, 315f),
            CarouselSeat(Color.Red, 0f),
            CarouselSeat(Color(0xFFFFA500), 45f),
            CarouselSeat(Color.Yellow, 90f),
            CarouselSeat(Color.Green, 135f)
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

            // Draw Seats
            drawRect(
                color = Color.Black,
                topLeft = Offset(
                    center.x - hubSize / 2,
                    center.y - hubSize / 2
                ),
                size = Size(hubSize, hubSize)
            )

            seats.forEach { seat ->
                val angleRad =
                    (seat.initialAngle) * (PI / 180f).toFloat()
                val dist = radius * seat.distanceFactor
                val seatX = center.x + dist * cos(angleRad)
                val seatY = center.y + dist * sin(angleRad)


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
