import androidx.compose.ui.graphics.Color

data class CarouselSeat(
    val color: Color,
    val initialAngle: Float,
    val distanceFactor: Float = 0.8f
)
