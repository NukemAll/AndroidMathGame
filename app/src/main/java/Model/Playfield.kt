package Model

class Playfield(sizeX: Int, sizeY: Int) {

    var playfieldX: Int = -1
    var playfieldY: Int = -1
    var playFieldSize: Int = -1
    var fields: ArrayList<Field> = ArrayList<Field>()

    init {
        this.playfieldX = sizeX
        this.playfieldY = sizeY
        this.playFieldSize = sizeX * sizeY
    }

    fun SetField(field: Field) {
        for (f in fields) {
            if (f.index == field.index) {
                f.value = field.value
                f.pressed = field.pressed
                f.disabled = field.disabled
            }
        }
    }

}