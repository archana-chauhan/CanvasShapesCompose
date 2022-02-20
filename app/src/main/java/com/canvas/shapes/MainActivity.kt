package com.canvas.shapes

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Loaders()
                }
            }
        }
    }
}

@Composable
fun Loaders() {
    Column() {
        var sliderValue by remember { mutableStateOf(0f) }
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            TriangularLoader(sliderValue)
            FullCircularLoader(sliderValue)
        }
        GaugeCircularLoader(sliderValue)
        Slider(
            modifier = Modifier.size(200.dp, 100.dp).padding(30.dp),
            value = sliderValue,
            onValueChange = {
                sliderValue = it
            }
        )
    }
}

@Composable
fun GaugeCircularLoader(sliderValue: Float) {
    Canvas(modifier = Modifier
        .padding(30.dp)
        .size(100.dp), onDraw = {
        drawArc(
            brush = SolidColor(Color.LightGray),
            startAngle = 120f,
            sweepAngle = 300f,
            useCenter = false,
            style = Stroke(20f, cap = StrokeCap.Round)
        )
        drawArc(
            brush = SolidColor(Color.Blue),
            startAngle = 120f,
            sweepAngle = sliderValue * 300,
            style = Stroke(20f, cap = StrokeCap.Round),
            useCenter = false

        )
        drawIntoCanvas {
            val paint = androidx.compose.ui.graphics.Paint().asFrameworkPaint()
            paint.apply {
                isAntiAlias = true
                textSize = 40f
                textAlign = Paint.Align.CENTER
            }

            it.nativeCanvas.drawText(
                "${round(sliderValue * 100).toInt()}%",
                size.width / 2,
                size.height / 2,
                paint
            )
        }
    })
}

@Composable
fun FullCircularLoader(sliderValue: Float) {
    Canvas(modifier = Modifier
        .padding(20.dp)
        .size(100.dp), onDraw = {
        drawCircle(
            SolidColor(Color.LightGray),
            size.width / 2,
            style = Stroke(20f)
        )
        drawArc(
            brush = SolidColor(Color.Blue),
            style = Stroke(20f),
            useCenter = false,
            startAngle = -90f,
            sweepAngle = sliderValue * 360
        )
    })
}

@Composable
fun TriangularLoader(sliderValue: Float) {
    Canvas(modifier = Modifier
        .padding(20.dp)
        .width(70.dp)
        .height(70.dp), onDraw = {
        val path = Path()
        path.moveTo(size.width, 0f)
        path.lineTo(size.width, size.height)
        path.lineTo(0f, size.height)
        clipPath(
            path = path,
            clipOp = ClipOp.Intersect
        ) {
            drawPath(path = path, color = Color.LightGray)
            drawRect(
                brush = SolidColor(Color.Blue),
                size = Size(sliderValue * size.width, size.height)
            )
        }
    })
}

@Preview
@Composable
fun LoaderPreview() {
    Loaders()
}
