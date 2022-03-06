package com.nukemall.androidmathgame

import Model.Field
import Model.Playfield
import ViewModel.VmMultiplicationGame
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    lateinit var vmMultiplicationGame: VmMultiplicationGame

    lateinit var layoutPlayfield: TableLayout
    lateinit var buttonFields: ArrayList<Button>
    lateinit var textViewResult: TextView
    lateinit var textViewMissed: TextView

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        this.SetStateObserver()

        this.InitGame()

    }

    private fun SetStateObserver() {
        // create ViewModel
        vmMultiplicationGame = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(VmMultiplicationGame::class.java)

        vmMultiplicationGame.playfieldState.observe(this) { t: Playfield ->
            this.renderPlayfield(t)
        }

        vmMultiplicationGame.multiplicationTarget.observe(this) { t: Int ->
            this.renderMultiplicationTarget(t)
        }

        vmMultiplicationGame.missed.observe(this) { t: Int ->
            this.renderMissed(t)
        }

    }

    private fun InitGame() {
        //
        layoutPlayfield = findViewById<TableLayout>(R.id.TableLayout)

        var bStartNewGame = findViewById<Button>(R.id.buttonStartNewGame)
        bStartNewGame?.setOnClickListener { v -> vmMultiplicationGame.StartNewGame() }

        textViewResult = findViewById<TextView>(R.id.TextViewResult)
        textViewResult.setText(vmMultiplicationGame.multiplicationGame.multiplicationTarget.toString())

        textViewMissed = findViewById(R.id.missed)
        textViewMissed.setText(vmMultiplicationGame.multiplicationGame.missed.toString())
        //
        this.generatePlayfield()

        this.renderPlayfield(vmMultiplicationGame.multiplicationGame.playfield)
    }

    private fun renderPlayfield(t: Playfield) {
        for (i in 0 until vmMultiplicationGame.multiplicationGame.playfield.playFieldSize) {
            buttonFields[i].setText(t.fields[i].value.toString())
            if (t.fields[i].pressed == true) {
                buttonFields[i].backgroundTintList = ContextCompat.getColorStateList(
                    this, R.color.teal_700
                )
            } else {
                buttonFields[i].backgroundTintList = ContextCompat.getColorStateList(
                    this, androidx.appcompat.R.color.button_material_dark
                )
            }

            buttonFields[i].isEnabled = !t.fields[i].disabled
        }

        if(vmMultiplicationGame.multiplicationGame.GetRemainingFields().size == 0) {
            Toast.makeText(this, "Spielende", Toast.LENGTH_SHORT).show()
        }

    }

    private fun renderMultiplicationTarget(t: Int) {
        textViewResult.setText(t.toString())
    }

    private fun renderMissed(t: Int) {
        textViewMissed.setText(t.toString())
    }

    private fun generatePlayfield() {
        this.generateButtonsFromDataModel()

        for (row in 0 until vmMultiplicationGame.multiplicationGame.playfield.playfieldY) {
            val currentRow = TableRow(this)
            for (column in 0 until vmMultiplicationGame.multiplicationGame.playfield.playfieldX) {
                val currentButton =
                    buttonFields[column + row * vmMultiplicationGame.multiplicationGame.playfield.playfieldX] //Button(this)
                currentRow.addView(currentButton)
            }
            layoutPlayfield.addView(currentRow)
        }
    }

    private fun generateButtonsFromDataModel() {
        buttonFields =
            ArrayList<Button>(vmMultiplicationGame.multiplicationGame.playfield.playFieldSize)
        for (field in vmMultiplicationGame.multiplicationGame.playfield.fields) {
            val currentButton = Button(this)
            currentButton.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1F
            )
            currentButton.setOnClickListener { v: View ->
                vmMultiplicationGame.UserSetField(field)
            }
            buttonFields.add(currentButton)
        }
    }
}