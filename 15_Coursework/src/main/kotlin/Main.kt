import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlin.random.Random

fun main() {
    runBlocking {
        val scope = CoroutineScope(this.coroutineContext)
        val loadTraffic = mutableListOf<Truck>()

        val unloadPoint1 = scope.launch {
            Depot.flow.collect { truck ->
                val name = "Unload point 1"
                println("Go to the $name")
                if (!truck.isEmpty) truck.unloading() else {
                    println("${truck.name} go to the traffic!")
//                    traffic.add(truck)
                    channel.send(truck)
                }
                delay(1000)
                if (check()) cancel()
            }
        }

        val loadPoint = launch {
            for (a in channel)
                loadTraffic.add(a)
            println("List of trucks in line:")
            loadTraffic.forEach {
                if (Warehouse.notEatableGoods.isNotEmpty()) {
                    while (it.weight < it.maxWeight && Warehouse.notEatableGoods.isNotEmpty() && Warehouse.notEatableGoods[0].weight < (it.maxWeight - it.weight))
                        it.addGoods(Warehouse.notEatableGoods[0], false)
                } else if (Warehouse.eatableGoods.isNotEmpty()) {
                    while (it.weight < it.maxWeight && Warehouse.eatableGoods.isNotEmpty() && Warehouse.eatableGoods[0].weight < (it.maxWeight - it.weight))
                        it.addGoods(Warehouse.eatableGoods[0], true)
                } else println("${it.name} - ${it.goodsList}")
            }
            println("Done!")
        }
    }
}


val channel = Channel<Truck>()

suspend fun loadTruck(t: Truck, n: String) {
    val a = Random.nextInt(2)
    if (a == 0) {
        if (Warehouse.notEatableGoods.isEmpty()) {
            println("Go from the $n ${t.name} go to the traffic!")
            channel.send(t)
        } else {
            while (t.weight < t.maxWeight && Warehouse.notEatableGoods.isNotEmpty() && Warehouse.notEatableGoods[0].weight < (t.maxWeight - t.weight))
                t.addGoods(Warehouse.notEatableGoods[0], false)
        }
    } else if (a == 1) {
        if (Warehouse.eatableGoods.isEmpty()) {
            println("Go from the $n ${t.name} go to the traffic!")
            channel.send(t)
        } else {
            while (t.weight < t.maxWeight && Warehouse.eatableGoods.isNotEmpty() && Warehouse.eatableGoods[0].weight < (t.maxWeight - t.weight))
                t.addGoods(Warehouse.eatableGoods[0], true)
        }
    }
}

fun check(): Boolean {
    val a = if (smallTruckCount == 3 || mediumTruckCount == 3 || bigTruckCount == 3) {
        println("Не съедобные товары на складе:")
        Warehouse.notEatableGoods.forEach { println(it.goodName) }
        println("Съедобные товары на складе:")
        Warehouse.eatableGoods.forEach { println(it.goodName) }
        channel.close()
        true
    } else false
    return a
}