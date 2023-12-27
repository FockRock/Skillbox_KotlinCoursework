import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

fun main() {
    val channel: Channel<Goods>
    runBlocking {
        val cargoLoadPoint1 = launch {
            println("Truck is coming...")
            Depot.flow.collect {
                if (it.goodsList.isNotEmpty()) it.unloading()
                else {
                    val i = Random.nextInt(0, 4)
                    while (it.weight < it.maxWeight) {
                        if (i == 0) it.addGoods(edibleProducts[Random.nextInt(0, 4)])
                        else it.addGoods(notEdibleProducts[Random.nextInt(0, 11)])
                    }
                }
                cancel()
            }
        }
    }
}

var smallTruckCount = 0
var mediumTruckCount = 0
var bigTruckCount = 0

object Depot {
    val flow = flow {
        while (currentCoroutineContext().isActive) {
            val b: Truck
            when (Random.nextInt(1, 16)) {
                1 -> {
                    b = SmallTruck()
                    b.isEmpty = true
                    smallTruckCount++
                    println("${b.name} is empty and ready to load!")
                }
                2 -> {
                    b = MediumTruck()
                    b.isEmpty = true
                    mediumTruckCount++
                    println("${b.name} is empty and ready to load!")
                }
                3 -> {
                    b = BigTruck()
                    b.isEmpty = true
                    bigTruckCount++
                    println("${b.name} is empty and ready to load!")
                }
                4, 5, 6 -> {
                    b = SmallTruck()
                    smallTruckCount++
                    while (b.weight < b.maxWeight) {
                        b.loading(notEdibleProducts[Random.nextInt(0, 11)])
                    }
                    println("${b.name} is ready to unload!")
                }
                7, 8, 9 -> {
                    b = MediumTruck()
                    mediumTruckCount++
                    while (b.weight < b.maxWeight) {
                        b.loading(notEdibleProducts[Random.nextInt(0, 11)])
                    }
                    println("${b.name} is ready to unload!")
                }
                10, 11, 12 -> {
                    b = BigTruck()
                    bigTruckCount++
                    while (b.weight < b.maxWeight) {
                        b.loading(notEdibleProducts[Random.nextInt(0, 11)])
                    }
                    println("${b.name} is ready to unload!")
                }
                13 -> {
                    b = SmallTruck()
                    smallTruckCount++
                    while (b.weight < b.maxWeight) {
                        b.loading(edibleProducts[Random.nextInt(0, 4)])
                    }
                    println("${b.name} is ready to unload!")
                }
                14 -> {
                    b = MediumTruck()
                    mediumTruckCount++
                    while (b.weight < b.maxWeight) {
                        b.loading(edibleProducts[Random.nextInt(0, 4)])
                    }
                    println("${b.name} is ready to unload!")
                }
                else -> {
                    b = BigTruck()
                    bigTruckCount++
                    while (b.weight < b.maxWeight) {
                        b.loading(edibleProducts[Random.nextInt(0, 4)])
                    }
                    println("${b.name} is ready to unload!")
                }
            }
            delay(1000)
            emit(b)
        }
    }
}

object Warehouse {
    val notEatableGoods = mutableListOf<Goods>()
    val eatableGoods = mutableListOf<Goods>()
}

abstract class Truck(var maxWeight: Int, var name: String, var isEmpty: Boolean = false, var weight: Int = 0) {

    var goodsList = mutableListOf<Goods>()

    suspend fun addGoods(g: Goods) {
        if (g.weight <= maxWeight - weight) {
            goodsList.add(g)
            delay(g.time.toLong())
            println("${g.name} added to $name!")
            weight += g.weight
            println("Total weight $weight")
        } else println("${g.name} cannot fit!")
    }

    fun loading(g: Goods) {
        if (g.weight <= maxWeight - weight) {
            goodsList.add(g)
            weight += g.weight

        }
    }

    suspend fun unloading() {
        while (goodsList.isNotEmpty()) {
            println("${goodsList[0].name} unloading from $name!")
            delay(goodsList[0].time.toLong())
            if (goodsList[0].isEatable) Warehouse.eatableGoods.add(goodsList[0])
            else Warehouse.notEatableGoods.add(goodsList[0])
            goodsList.removeAt(0)
        }
        println("$name is Empty!")
    }
}

class SmallTruck : Truck(1500, "SmallTruck ${smallTruckCount + 1}")
class MediumTruck : Truck(2500, "MediumTruck ${mediumTruckCount + 1}")
class BigTruck : Truck(3500, "BigTruck ${bigTruckCount + 1}")

class Goods(var weight: Int, var time: Int, val name: String,val isEatable: Boolean = false)

val apple = Goods(100, 15, "Apples", true)
val milk = Goods(200, 30, "Milk", true)
val bread = Goods(100, 5, "Bread", true)
val meat = Goods(200, 10, "Meat", true)
val table = Goods(300, 30, "Table")
val chair = Goods(200, 30, "Chair")
val computer = Goods(200, 30, "Computer")
val wardrobe = Goods(600, 50, "Wardrobe")
val door = Goods(400, 40, "Door")
val lamp = Goods(300, 30, "Lamp")
val laptop = Goods(100, 30, "Laptop")
val sink = Goods(200, 30, "Sink")
val armchair = Goods(100, 30, "Armchair")
val window = Goods(300, 30, "Window")
val tv = Goods(200, 30, "TV")

val edibleProducts = mutableListOf(apple, milk, bread, meat)
val notEdibleProducts =
    mutableListOf(table, chair, computer, wardrobe, door, lamp, laptop, sink, armchair, window, tv)