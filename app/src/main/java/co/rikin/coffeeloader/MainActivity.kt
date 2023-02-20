package co.rikin.coffeeloader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import app.rive.runtime.kotlin.RiveAnimationView
import co.rikin.coffeeloader.ui.theme.CoffeeLoaderTheme
import co.rikin.coffeeloader.ui.theme.CoffeeShop
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      App()
    }
  }
}

@Preview
@Composable
fun App() {
  CoffeeLoaderTheme {
    val progress = remember {
      Animatable(0f)
    }
    val scope = rememberCoroutineScope()
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(
          color = CoffeeShop
        )
        .pointerInput(Unit) {
          detectDragGestures { change, dragAmount ->
            val y = dragAmount.y
            val current = progress.value
            val new = (current - y).coerceIn(0f, 100f)
            scope.launch {
              progress.animateTo(new)
            }
          }
        },
      contentAlignment = Alignment.Center,

      ) {
      AndroidView(
        modifier = Modifier.size(100.dp),
        factory = { context ->
          RiveAnimationView(context).apply {
            setRiveResource(R.raw.cup_loader)
            setNumberState("State Machine 1", "numLoader", 0f)
          }
        }, update = { view ->
          view.setNumberState("State Machine 1", "numLoader", progress.value)
        })
    }  }
}


