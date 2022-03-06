package Model

import androidx.lifecycle.MutableLiveData

class MultiplicationGame {

    val playfield: Playfield = Playfield(6, 6)

    var multiplicationTarget: Int = 0
    var userSetFactor: Field? = null
    var missed: Int = 0

    init {
        for (i in 0 until (playfield.playFieldSize)) {
            playfield.fields.add(
                Field(
                    i,
                    IntRange(1, 9).random(),
                    false,
                    true,
                )
            )
        }


    }

    fun ResetMissed() {
        this.missed = 0
    }

    fun IncreaseMissed() {
        this.missed++
    }

    fun SetNewMultiplicationTarget() {
        var f1 = GetFactor(GetRemainingFields(), null)
        var f2 = GetFactor(GetRemainingFields(), f1)

        if (f1 != null && f2 != null) {
            this.multiplicationTarget = f1.value * f2.value
        } else {
            this.multiplicationTarget = 0
        }
    }

    fun GetRemainingFields(): ArrayList<Field> {
        var remainingFields: ArrayList<Field> = ArrayList<Field>()

        for (f in playfield.fields) {
            if (f.disabled == false) {
                remainingFields.add(f)
            }
        }

        return remainingFields
    }

    fun SetUserSetFactor(field: Field?) {
        this.userSetFactor = field
    }

    private fun GetFactor(remainingFields: ArrayList<Field>, f1: Field?): Field? {
        if (remainingFields.size > 1) {
            if (f1 != null) {
                var newRemainingFields =
                    remainingFields.filter { it.index != f1.index } as ArrayList<Field>
                if(newRemainingFields.size == 1) {
                    return newRemainingFields[0]
                }
                return newRemainingFields[IntRange(0, newRemainingFields.size - 1).random()]
            }
            return remainingFields[IntRange(0, remainingFields.size - 1).random()]
        }
        return null
    }
}