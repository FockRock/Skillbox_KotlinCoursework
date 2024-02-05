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
                        while (truck.weight < truck.maxWeight && Warehouse.notEatableGoods.isNotEmpty())
                            truck.addGoods(Warehouse.notEatableGoods[0],false)
                    }
                } else if (a == 1) {
                    if (Warehouse.eatableGoods.isEmpty()) println("${truck.name} go away with no goods!")
                    else {
                        while (truck.weight < truck.maxWeight && Warehouse.eatableGoods.isNotEmpty())
                            truck.addGoods(Warehouse.eatableGoods[0],true)
                    }
                }
                if (smallTruckCount == 3 || mediumTruckCount == 3 || bigTruckCount == 3) {
                    println("Не съедобные товары на складе:")
                    Warehouse.notEatableGoods.forEach { println(it.goodName) }
                    println("Съедобные товары на складе:")
                    Warehouse.eatableGoods.forEach { println(it.goodName) }
                    cancel()
                }
                delay(5000)
            }
        }
    }
}