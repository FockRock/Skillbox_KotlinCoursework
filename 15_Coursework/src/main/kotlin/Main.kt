import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlin.random.Random

fun main() {
    runBlocking {
        val scope = CoroutineScope(this.coroutineContext)
        val traffic = mutableListOf<Truck>()
        val channel = Channel<Int>()


        val cargoLoadPoint1 = scope.launch {

            Depot.flow.collect { truck ->
                val a = Random.nextInt(2)
                if (!truck.isEmpty) truck.unloading()
                else if (a == 0) {
                    if (Warehouse.notEatableGoods.isEmpty()) {
                        println("${truck.name} go to the traffic!")
                        traffic.add(truck)
                    } else {
                        while (truck.weight < truck.maxWeight && Warehouse.notEatableGoods.isNotEmpty() && Warehouse.notEatableGoods[0].weight < (truck.maxWeight - truck.weight))
                            truck.addGoods(Warehouse.notEatableGoods[0], false)
                    }
                } else if (a == 1) {
                    if (Warehouse.eatableGoods.isEmpty()) {
                        println("${truck.name} go to the traffic!")
                        traffic.add(truck)
                    } else {
                        while (truck.weight < truck.maxWeight && Warehouse.eatableGoods.isNotEmpty() && Warehouse.eatableGoods[0].weight < (truck.maxWeight - truck.weight))
                            truck.addGoods(Warehouse.eatableGoods[0], true)
                    }
                }
                if (smallTruckCount == 3 || mediumTruckCount == 3 || bigTruckCount == 3) {
                    println("Не съедобные товары на складе:")
                    Warehouse.notEatableGoods.forEach { println(it.goodName) }
                    println("Съедобные товары на складе:")
                    Warehouse.eatableGoods.forEach { println(it.goodName) }
                    println("Машины в очереди:")
                    traffic.forEach { println(it.name) }
                    cancel()
                }
                delay(1000)
            }

        }

        val ch = Channel<Int>()
        launch {
            for (n in 1..5)
                ch.send(n)
        }

        repeat(5) {
            val number = channel.receive()
            println(number)
        }
        println("End")
    }
}

//suspend fun main() = coroutineScope{
//
//    val channel = Channel<Int>()
//    launch {
//        for (n in 1..5) {
//            // отправляем данные через канал
//            channel.send(n)
//        }
//    }
//
//    // получаем данные из канала
//    repeat(5) {
//        val number = channel.receive()
//        println(number)
//    }
//    println("End")
//}