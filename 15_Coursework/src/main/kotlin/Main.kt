import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

fun main() {
    val channel: Channel<Goods>
    runBlocking {
        val cargoLoadPoint1 = launch {
            println("Truck is coming...")

        }
    }
}