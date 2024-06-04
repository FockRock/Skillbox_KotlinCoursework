import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlin.random.Random

fun main() {
    runBlocking {
        val channel = Channel<Truck>()
        val traffic = mutableListOf<Truck>()
        val scope = CoroutineScope(this.coroutineContext)
        val loadTraffic = mutableListOf<Truck>()

//        launch {
//            Depot.flow.collect {
//                traffic.add(it)
//                if(it.isEmpty)
//                    channel.send(it)
//                if (traffic.size == 6) {
//                    cancel()
//                    channel.close()
//                }
//            }
//        }
//        for (a in channel)
//            loadTraffic.add(a)
//        println("List of trucks in line:")
//        loadTraffic.forEach {
//            println(it.name)
//        }
//        println("Done!")

        //Сделать три корутины с выгрузкой, и одну с загрузкой


        val job1 = scope.launch {

            Depot.flow.collect { truck ->
                val a = Random.nextInt(2)
                if (!truck.isEmpty) truck.unloading()
                else if (a == 0) {
                    if (Warehouse.notEatableGoods.isEmpty()) {
                        println("${truck.name} go to the traffic!")
                        traffic.add(truck)
                        channel.send(truck)
                    } else {
                        while (truck.weight < truck.maxWeight && Warehouse.notEatableGoods.isNotEmpty() && Warehouse.notEatableGoods[0].weight < (truck.maxWeight - truck.weight))
                            truck.addGoods(Warehouse.notEatableGoods[0], false)
                    }
                } else if (a == 1) {
                    if (Warehouse.eatableGoods.isEmpty()) {
                        println("${truck.name} go to the traffic!")
                        traffic.add(truck)
                        channel.send(truck)
                    } else {
                        while (truck.weight < truck.maxWeight && Warehouse.eatableGoods.isNotEmpty() && Warehouse.eatableGoods[0].weight < (truck.maxWeight - truck.weight))
                            truck.addGoods(Warehouse.eatableGoods[0], true)
                    }
                }
//                if (smallTruckCount == 3 || mediumTruckCount == 3 || bigTruckCount == 3) {
//                    println("Не съедобные товары на складе:")
//                    Warehouse.notEatableGoods.forEach { println(it.goodName) }
//                    println("Съедобные товары на складе:")
//                    Warehouse.eatableGoods.forEach { println(it.goodName) }
//                    channel.close()
//                    cancel()
//                }
                delay(1000)
            }
        }
        val job2 = launch {
            for (a in channel)
                loadTraffic.add(a)
            println("List of trucks in line:")
            loadTraffic.forEach {
                println(it.name)
            }
            println("Done!")
        }

        val job3 = scope.launch {

            Depot.flow.collect { truck ->
                val a = Random.nextInt(2)
                if (!truck.isEmpty) truck.unloading()
                else if (a == 0) {
                    if (Warehouse.notEatableGoods.isEmpty()) {
                        println("${truck.name} go to the traffic!")
                        traffic.add(truck)
                        channel.send(truck)
                    } else {
                        while (truck.weight < truck.maxWeight && Warehouse.notEatableGoods.isNotEmpty() && Warehouse.notEatableGoods[0].weight < (truck.maxWeight - truck.weight))
                            truck.addGoods(Warehouse.notEatableGoods[0], false)
                    }
                } else if (a == 1) {
                    if (Warehouse.eatableGoods.isEmpty()) {
                        println("${truck.name} go to the traffic!")
                        traffic.add(truck)
                        channel.send(truck)
                    } else {
                        while (truck.weight < truck.maxWeight && Warehouse.eatableGoods.isNotEmpty() && Warehouse.eatableGoods[0].weight < (truck.maxWeight - truck.weight))
                            truck.addGoods(Warehouse.eatableGoods[0], true)
                    }
                }

                delay(1000)
            }
        }
//        for (a in channel)
//            loadTraffic.add(a)
//        println("List of trucks in line:")
//        loadTraffic.forEach {
//            println(it.name)
//        }
//        println("Done!")
    }
}