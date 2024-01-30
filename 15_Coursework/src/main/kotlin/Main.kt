import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.random.Random

fun main() {
    runBlocking {
        val cargoLoadPoint1 = launch {
            println("Truck is coming...")
            Depot.flow.collect { truck ->
                println(truck.name)
                val a = Random.nextInt(2)
                if (!truck.isEmpty) truck.unloading()
                else if (a == 0) {
                    if (Warehouse.notEatableGoods.isEmpty()) println("${truck.name} go away with no goods!")
                    else {
                        while (truck.weight != truck.maxWeight || Warehouse.notEatableGoods.isEmpty()) {
                            truck.addGoods(Warehouse.notEatableGoods[0])
                            Warehouse.notEatableGoods.removeAt(0)
                        }
                    }
                } else if (a == 1) {
                    if (Warehouse.eatableGoods.isEmpty()) println("${truck.name} go away with no goods!")
                    else {
                        while (truck.weight != truck.maxWeight || Warehouse.eatableGoods.isEmpty()) {
                            truck.addGoods(Warehouse.eatableGoods[0])
                            Warehouse.eatableGoods.removeAt(0)
                        }
                    }
                }
                if (smallTruckCount == 2 || mediumTruckCount == 2 || bigTruckCount == 2) {
                    Warehouse.notEatableGoods.forEach { println(it.goodName) }
                    Warehouse.eatableGoods.forEach { println(it.goodName) }
                    cancel()
                }
                delay(5000)
            }
        }
    }
}