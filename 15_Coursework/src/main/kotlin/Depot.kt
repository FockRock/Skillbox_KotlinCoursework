import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlin.random.Random

object Depot {
    val flow = flow {
        while (currentCoroutineContext().isActive) {
            val truck: Truck
            when (Random.nextInt(1, 25)) {
                1,17,18,19,20 -> {
                    truck = SmallTruck()
                    truck.isEmpty = true
                    smallTruckCount++
                    println("${truck.name} is empty and ready to load!")
                }
                2,21,22 -> {
                    truck = MediumTruck()
                    truck.isEmpty = true
                    mediumTruckCount++
                    println("${truck.name} is empty and ready to load!")
                }
                3,23,24 -> {
                    truck = BigTruck()
                    truck.isEmpty = true
                    bigTruckCount++
                    println("${truck.name} is empty and ready to load!")
                }
                4, 5, 6 -> {
                    truck = SmallTruck()
                    smallTruckCount++
                    while (truck.weight < truck.maxWeight) {
                        truck.loading(notEdibleProducts[Random.nextInt(0, 11)])
                    }
                    println("${truck.name} is ready to unload!")
                }
                7, 8, 9 -> {
                    truck = MediumTruck()
                    mediumTruckCount++
                    while (truck.weight < truck.maxWeight) {
                        truck.loading(notEdibleProducts[Random.nextInt(0, 11)])
                    }
                    println("${truck.name} is ready to unload!")
                }
                10, 11, 12 -> {
                    truck = BigTruck()
                    bigTruckCount++
                    while (truck.weight < truck.maxWeight) {
                        truck.loading(notEdibleProducts[Random.nextInt(0, 11)])
                    }
                    println("${truck.name} is ready to unload!")
                }
                13 -> {
                    truck = SmallTruck()
                    smallTruckCount++
                    while (truck.weight < truck.maxWeight) {
                        truck.loading(edibleProducts[Random.nextInt(0, 4)])
                    }
                    println("${truck.name} is ready to unload!")
                }
                14 -> {
                    truck = MediumTruck()
                    mediumTruckCount++
                    while (truck.weight < truck.maxWeight) {
                        truck.loading(edibleProducts[Random.nextInt(0, 4)])
                    }
                    println("${truck.name} is ready to unload!")
                }
                else -> {
                    truck = BigTruck()
                    bigTruckCount++
                    while (truck.weight < truck.maxWeight) {
                        truck.loading(edibleProducts[Random.nextInt(0, 4)])
                    }
                    println("${truck.name} is ready to unload!")
                }
            }
            delay(1000)
            emit(truck)
        }
    }
}