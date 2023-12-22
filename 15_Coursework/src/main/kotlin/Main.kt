import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

var smallTruckCount = 0
var mediumTruckCount = 0
var bigTruckCount = 0

fun main() {
    runBlocking {
        val cargoLoadPoint1 = launch {
            println("Началась погрузка...")
            Depot.flow.collect {
                println(it.name)
                it.addGoods(apple)
                it.addGoods(table)
                cancel()
            }
        }
    }
}

object Depot {
    val flow = flow {
        while (currentCoroutineContext().isActive) {
            val b: Truck
            when (Random.nextInt(1, 4)) {
                1 -> {
                    b = SmallTruck()
                    smallTruckCount++
                }
                2 -> {
                    b = MediumTruck()
                    mediumTruckCount++
                }
                else -> {
                    b = BigTruck()
//                    b.goodsList.add(notEdibleProducts[Random.nextInt(0,10)])
                    bigTruckCount++
                }
            }
            delay(1000)
            emit(b)
        }
    }
}

abstract class Truck(var maxWeight: Int, var name: String, var isEmpty: Boolean = false, var weight: Int = 0){

    var goodsList = mutableListOf<Goods>()

    fun addGoods (g: Goods) {
        goodsList.add(g)
        println("${g.name} added to $name!")
        weight += g.weight
        println("Total weight $weight")
    }
    fun unloading () {
        while (goodsList.isNotEmpty()) {
            println("${goodsList[0]} unloading!")
            goodsList.removeAt(0)
        }
        println("$name is Empty!")
    }
}

class SmallTruck : Truck(1500, "SmallTruck ${smallTruckCount + 1}")
class MediumTruck : Truck(2500, "MediumTruck ${mediumTruckCount + 1}")
class BigTruck : Truck(3500, "BigTruck ${bigTruckCount + 1}")

class Goods(var weight: Int, var time: Int, val name: String)

val apple = Goods(100, 1500, "Apples")
val milk = Goods(200, 3000,"Milk")
val bread = Goods(100, 500,"Bread")
val meat = Goods(200, 1000,"Meat")
val table = Goods(300, 3000,"Table")
val chair = Goods(200, 3000,"Chair")
val computer = Goods(100, 3000,"Computer")
val wardrobe = Goods(600, 5000,"Wardrobe")
val door = Goods(400, 4000,"Door")
val lamp = Goods(300, 3000,"Lamp")
val laptop = Goods(100, 3000,"Laptop")
val sink = Goods(100, 3000,"Sink")
val armchair = Goods(100, 3000,"Armchair")
val window = Goods(100, 3000,"Window")
val tv = Goods(100, 3000,"TV")

val edibleProducts = mutableListOf(apple,milk,bread,meat)
val notEdibleProducts = mutableListOf(table,chair,computer,wardrobe,door,lamp,laptop,sink,armchair,window,tv)