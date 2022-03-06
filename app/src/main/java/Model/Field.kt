package Model

class Field constructor(
    index: Int,
    value: Int,
    pressed: Boolean,
    disabled: Boolean,
) {

    var index: Int = -1
    var value: Int = -1
    var pressed: Boolean = false
    var disabled: Boolean = false

    init {
        this.index = index
        this.value = value
        this.pressed = pressed
        this.disabled = disabled
    }

    constructor(field: Field) : this(
        field.index,
        field.value,
        field.pressed,
        field.disabled,
    ) {
    }
}