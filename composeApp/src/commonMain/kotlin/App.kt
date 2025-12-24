import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CarouselApp() {
    // State
    var isSpinning by remember { mutableStateOf(false) }

    // Total degrees rotated
    var currentRotation by remember { mutableStateOf(0f) }

    var targetRevolutions by remember { mutableStateOf(1f) }

    // Audio
    val soundPlayer = remember { SoundPlayer("carousel.mp3") }

    DisposableEffect(Unit) {
        onDispose {
            soundPlayer.dispose()
        }
    }

    // Constants
    val maxSpeed = 1.0f // Degrees per frame roughly
    
    // Animation Loop
    LaunchedEffect(isSpinning) {
        if (isSpinning) {
            soundPlayer.play()
            while (isSpinning) {
                withFrameNanos { _ ->
                    val totalDegrees = targetRevolutions * 360f
                    
                    if (currentRotation >= totalDegrees) {
                        isSpinning = false
                        currentRotation = totalDegrees
                    } else {
                        // Calculate speed based on position
                        var velocity = maxSpeed
                        val rampRange = 90f
                        val minSpeed = 0.25f
                        
                        // Acceleration phase (first 90 deg)
                        if (currentRotation < rampRange) {
                            val progress = currentRotation / rampRange
                            velocity =
                                minSpeed + (maxSpeed - minSpeed) * progress
                        }

                        // Deceleration phase (last 90 deg)
                        else if (currentRotation > totalDegrees - rampRange) {
                            val remaining = totalDegrees - currentRotation
                            val progress = remaining / rampRange
                            // progress goes from 1.0 down to 0.0
                            // as we approach the end
                             velocity =
                                 minSpeed + (maxSpeed - minSpeed) * progress
                        }
                        
                        // Update volume proportional to velocity
                        soundPlayer.setVolume(velocity / maxSpeed)
                        
                        currentRotation += velocity
                    }
                }
            }
            soundPlayer.stop()
        } else {
            soundPlayer.stop()
        }
    }

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
        // Inner circle: 0.6f radius, offset by 90 degrees relative to outer
        val inner = listOf(
            CarouselSeat(Color.Cyan.copy(alpha=0.7f), 180f + 90f, 0.6f),
            CarouselSeat(Color.Blue.copy(alpha=0.7f), 225f + 90f, 0.6f),
            CarouselSeat(Color.Magenta.copy(alpha=0.7f), 270f + 90f, 0.6f),
            CarouselSeat(Color.Gray.copy(alpha=0.7f), 315f + 90f, 0.6f),
            CarouselSeat(Color.Red.copy(alpha=0.7f), 0f + 90f, 0.6f),
            CarouselSeat(Color(0xFFFFA500).copy(alpha=0.7f),
                45f + 90f, 0.6f),
            CarouselSeat(Color.Yellow.copy(alpha=0.7f), 90f + 90f, 0.6f),
            CarouselSeat(Color.Green.copy(alpha=0.7f), 135f + 90f, 0.6f)
        )
        outer + inner
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
            drawCircle(color = Color.LightGray,
                radius = radius, center = center)
            
            // Draw Seats with currentRotation
            // We rotate the entire platform context or calculate positions
            // Let's rotate the context for the platform spin effect
            withTransform({
                rotate(degrees = currentRotation, pivot = center)
            }) {
                // Draw Rotating Center Hub (Square)
                val hubSize = 10.dp.toPx()
                drawRect(
                    color = Color.Black,
                    topLeft = Offset(center.x - hubSize / 2,
                        center.y - hubSize / 2),
                    size = Size(hubSize, hubSize)
                )

                seats.forEach { seat ->
                   val angleRad =
                       (seat.initialAngle) * (PI / 180f).toFloat()
                   val dist = radius * seat.distanceFactor
                   val seatX = center.x + dist * cos(angleRad)
                   val seatY = center.y + dist * sin(angleRad)
                   
                   rotate(degrees = seat.initialAngle + 90f,
                       pivot = Offset(seatX, seatY)) {
                        val w = 40.dp.toPx()
                        val h = 15.dp.toPx()
                        drawRect(
                            color = seat.color,
                            topLeft = Offset(seatX - w/2, seatY - h/2),
                            size = Size(w, h)
                        )
                   }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Controls
        Button(
            onClick = { 
                currentRotation = 0f
                isSpinning = true 
            },
            colors =
                ButtonDefaults.buttonColors(backgroundColor =
                    Color(0xFF673AB7),
                    contentColor = Color.White) // Deep Purple
        ) {
            Text("Start Ride")
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Button(
            onClick = { isSpinning = false },
            colors =
                ButtonDefaults.buttonColors(backgroundColor = Color.Red,
                contentColor = Color.White)
        ) {
            Text("Emergency Stop")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text("Revolutions: ${targetRevolutions.toInt()}")
        
        Slider(
            value = targetRevolutions,
            onValueChange = { targetRevolutions = kotlin.math.round(it) },
            valueRange = 1f..10f,
            steps = 8,
            modifier = Modifier.width(300.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF673AB7),
                activeTrackColor = Color(0xFFD1C4E9)
            )
        )
        
        Text("Status: ${if (isSpinning) "Spinning" else "Stopped"}")

        Text("Revolutions: ${(currentRotation / 360f).toInt()}")
    }
}
