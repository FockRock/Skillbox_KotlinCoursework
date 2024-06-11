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
                if (!truck.isEmpty) truck.unloading() else loadTruck(truck)
                delay(1000)

                if (smallTruckCount == 3 || mediumTruckCount == 3 || bigTruckCount == 3) {
                    println("Не съедобные товары на складе:")
                    Warehouse.notEatableGoods.forEach { println(it.goodName) }
                    println("Съедобные товары на складе:")
                    Warehouse.eatableGoods.forEach { println(it.goodName) }
                    channel.close()
                    cancel()
                }
            }
        }

//        val unloadPoint2 = scope.launch {
//
//            Depot.flow.collect { truck ->
//                if (!truck.isEmpty) truck.unloading() else loadFun(truck)
//                delay(1000)
//
//                if (smallTruckCount == 3 || mediumTruckCount == 3 || bigTruckCount == 3) {
//                    println("Не съедобные товары на складе:")
//                    Warehouse.notEatableGoods.forEach { println(it.goodName) }
//                    println("Съедобные товары на складе:")
//                    Warehouse.eatableGoods.forEach { println(it.goodName) }
//                    channel.close()
//                    cancel()
//                }
//            }
//        }
//
//        val unloadPoint3 = scope.launch {
//
//            Depot.flow.collect { truck ->
//                if (!truck.isEmpty) truck.unloading() else loadFun(truck)
//                delay(1000)
//
//                if (smallTruckCount == 3 || mediumTruckCount == 3 || bigTruckCount == 3) {
//                    println("Не съедобные товары на складе:")
//                    Warehouse.notEatableGoods.forEach { println(it.goodName) }
//                    println("Съедобные товары на складе:")
//                    Warehouse.eatableGoods.forEach { println(it.goodName) }
//                    channel.close()
//                    cancel()
//                }
//            }
//        }

        val loadPoint = launch {
            for (a in channel)
                loadTraffic.add(a)
            println("List of trucks in line:")
            loadTraffic.forEach {
                println(it.name)
            }
            println("Done!")
        }
    }
}


val channel = Channel<Truck>()
val traffic = mutableListOf<Truck>()
suspend fun loadTruck(t: Truck){
    val a = Random.nextInt(2)
    if (a == 0) {
        if (Warehouse.notEatableGoods.isEmpty()) {
            println("${t.name} go to the traffic!")
            traffic.add(t)
            channel.send(t)
        } else {
            while (t.weight < t.maxWeight && Warehouse.notEatableGoods.isNotEmpty() && Warehouse.notEatableGoods[0].weight < (t.maxWeight - t.weight))
                t.addGoods(Warehouse.notEatableGoods[0], false)
        }
    } else if (a == 1) {
        if (Warehouse.eatableGoods.isEmpty()) {
            println("${t.name} go to the traffic!")
            traffic.add(t)
            channel.send(t)
        } else {
            while (t.weight < t.maxWeight && Warehouse.eatableGoods.isNotEmpty() && Warehouse.eatableGoods[0].weight < (t.maxWeight - t.weight))
                t.addGoods(Warehouse.eatableGoods[0], true)
        }
    }
}