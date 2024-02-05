enum class Goods(var weight: Int, var time: Int, val goodName: String, val isEatable: Boolean = false) {

    APPLE(100, 150, "Apples", true),
    MILK(200, 300, "Milk", true),
    BREAD(100, 500, "Bread", true),
    MEAT(200, 100, "Meat", true),
    TABLE(300, 300, "Table"),
    CHAIR(200, 300, "Chair"),
    COMPUTER(200, 300, "Computer"),
    WARDROBE(600, 500, "Wardrobe"),
    DOOR(400, 400, "Door"),
    LAMP(300, 300, "Lamp"),
    LAPTOP(100, 300, "Laptop"),
    SINK(200, 300, "Sink"),
    ARMCHAIR(100, 300, "Armchair"),
    WINDOW(300, 300, "Window"),
    TV(200, 300, "TV")
}

val edibleProducts = mutableListOf(Goods.APPLE, Goods.MILK, Goods.BREAD, Goods.MEAT)
val notEdibleProducts =
    mutableListOf(Goods.TABLE, Goods.CHAIR, Goods.COMPUTER, Goods.WARDROBE, Goods.DOOR,
        Goods.LAMP, Goods.LAPTOP, Goods.SINK, Goods.ARMCHAIR, Goods.WINDOW, Goods.TV)