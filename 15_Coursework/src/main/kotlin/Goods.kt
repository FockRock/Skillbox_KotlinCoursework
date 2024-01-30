enum class Goods(var weight: Int, var time: Int, val goodName: String, val isEatable: Boolean = false) {

    APPLE(100, 15, "Apples", true),
    MILK(200, 30, "Milk", true),
    BREAD(100, 5, "Bread", true),
    MEAT(200, 10, "Meat", true),
    TABLE(300, 30, "Table"),
    CHAIR(200, 30, "Chair"),
    COMPUTER(200, 30, "Computer"),
    WARDROBE(600, 50, "Wardrobe"),
    DOOR(400, 40, "Door"),
    LAMP(300, 30, "Lamp"),
    LAPTOP(100, 30, "Laptop"),
    SINK(200, 30, "Sink"),
    ARMCHAIR(100, 30, "Armchair"),
    WINDOW(300, 30, "Window"),
    TV(200, 30, "TV")
}

val edibleProducts = mutableListOf(Goods.APPLE, Goods.MILK, Goods.BREAD, Goods.MEAT)
val notEdibleProducts =
    mutableListOf(Goods.TABLE, Goods.CHAIR, Goods.COMPUTER, Goods.WARDROBE, Goods.DOOR,
        Goods.LAMP, Goods.LAPTOP, Goods.SINK, Goods.ARMCHAIR, Goods.WINDOW, Goods.TV)