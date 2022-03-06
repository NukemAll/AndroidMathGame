package ViewModel

import Model.Field
import Model.MultiplicationGame
import Model.Playfield
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VmMultiplicationGame : ViewModel() {

    var multiplicationGame: MultiplicationGame

    // state variables
    var playfieldState: MutableLiveData<Playfield> = MutableLiveData<Playfield>()
    var multiplicationTarget: MutableLiveData<Int> = MutableLiveData<Int>()
    var missed: MutableLiveData<Int> = MutableLiveData<Int>()


    //
    init {
        multiplicationGame = MultiplicationGame()
    }

    fun StartNewGame() {
        this.GetNewPlayfield()
        this.SetNewMultiplicationTarget()
        this.ResetMissed()
    }

    fun UserSetField(field: Field) {
        var newField: Field = Field(field)

        if (multiplicationGame.userSetFactor == null) {
            newField.pressed = true
            newField.disabled = true
            multiplicationGame.SetUserSetFactor(newField)
            multiplicationGame.playfield.SetField(newField)
        } else {
            var newFieldUserSet: Field = Field(multiplicationGame.userSetFactor!!)

            var resultUser: Int = multiplicationGame.userSetFactor!!.value * field.value
            if (resultUser == multiplicationGame.multiplicationTarget) {
                newField.pressed = false
                newField.disabled = true
                newFieldUserSet.pressed = false
                newFieldUserSet.disabled = true

                multiplicationGame.playfield.SetField(newField)
                multiplicationGame.playfield.SetField(newFieldUserSet)
                this.SetNewMultiplicationTarget()
            } else {
                newField.pressed = false
                newField.disabled = false
                newFieldUserSet.pressed = false
                newFieldUserSet.disabled = false

                multiplicationGame.playfield.SetField(newField)
                multiplicationGame.playfield.SetField(newFieldUserSet)
                multiplicationGame.IncreaseMissed()
            }
            multiplicationGame.SetUserSetFactor(null)
        }

        missed.value = multiplicationGame.missed
        playfieldState.value = multiplicationGame.playfield
    }

    private fun SetNewMultiplicationTarget() {
        multiplicationGame.SetNewMultiplicationTarget()
        // set state
        multiplicationTarget.value = multiplicationGame.multiplicationTarget
    }

    private fun ResetMissed() {
        multiplicationGame.ResetMissed()
        // set state
        missed.value = multiplicationGame.missed
    }

    private fun GetNewPlayfield() {
        for (f in multiplicationGame.playfield.fields) {
            f.value = IntRange(1, 9).random()
            f.pressed = false
            f.disabled = false
        }

        // set state
        playfieldState.value = multiplicationGame.playfield
    }

    private fun SetMissed() {

    }
}