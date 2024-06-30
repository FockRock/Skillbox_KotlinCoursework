import kotlinx.coroutines.delay

abstract class Truck(var maxWeight: Int, var name: String, var isEmpty: Boolean = false, var weight: Int = 0) {

    var goodsList = mutableListOf<Goods>()

    suspend fun addGoods(g: Goods, isEatable: Boolean) {
        if (g.weight <= maxWeight - weight) {
            goodsList.add(g)
            delay(g.time.toLong())
            println("${g.goodName} added to $name!")
            weight += g.weight
            println("Total weight $weight")
            if (isEatable) Warehouse.eatableGoods.removeAt(0)
            else Warehouse.notEatableGoods.removeAt(0)
        }
    }

    fun loading(g: Goods) {
        if (g.weight <= maxWeight - weight) {
            goodsList.add(g)
            weight += g.weight
        }
    }

    suspend fun unloading() {
        while (goodsList.isNotEmpty()) {
//            println("${goodsList[0].goodName} unloading from $name!")
            delay(goodsList[0].time.toLong())
            if (goodsList[0].isEatable) Warehouse.eatableGoods.add(goodsList[0])
            else Warehouse.notEatableGoods.add(goodsList[0])
            weight -= goodsList[0].weight
            goodsList.removeAt(0)
//            println("Weight is - $weight")
        }
        println("$name is Empty!")
    }
}

class SmallTruck : Truck(1500, "SmallTruck ${smallTruckCount + 1}")
class MediumTruck : Truck(2500, "MediumTruck ${mediumTruckCount + 1}")
class BigTruck : Truck(3500, "BigTruck ${bigTruckCount + 1}")


var smallTruckCount = 0
var mediumTruckCount = 0
var bigTruckCount = 0