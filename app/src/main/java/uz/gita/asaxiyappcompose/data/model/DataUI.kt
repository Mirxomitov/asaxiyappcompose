package uz.gita.asaxiyappcompose.data.model

class DataUI {
    val id : String
    val author: String
    val category: String
    val description: String
    val img: String
    val name: String
    val path: String
    val size: String
    val purchased : Boolean

    constructor(
        id : String = "",
        author: String = "",
        category: String = "",
        description: String = "",
        img: String = "",
        name: String = "",
        path: String = "",
        size: String = "1",
        purchased : Boolean = false
    ) {
        this.id = id
        this.author = author
        this.category = category
        this.description = description
        this.img = img
        this.name = name
        this.path = path
        this.size = size
        this.purchased = purchased
    }

    constructor() {
        this.id = ""
        this.author =""
        this.category =""
        this.description =""
        this.img =""
        this.name =""
        this.path =""
        this.size =""
        this.purchased = false
    }
}

