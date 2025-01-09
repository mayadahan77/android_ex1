package com.example.tictactoe

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
//import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.widget.TextView


class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonIds = listOf(
            R.id.btnTicTac1,
            R.id.btnTicTac2,
            R.id.btnTicTac3,
            R.id.btnTicTac4,
            R.id.btnTicTac5,
            R.id.btnTicTac6,
            R.id.btnTicTac7,
            R.id.btnTicTac8,
            R.id.btnTicTac9
        )

        buttonIds.forEach { id ->
            val button = findViewById<Button>(id)
            button.setOnClickListener {
                btnTicTac(button)
            }
        }

    }


    protected fun btnTicTac(view: View){
        val btnSelected = view as Button
        var cellID= 0
        when(btnSelected.id){
            R.id.btnTicTac1 -> cellID=1
            R.id.btnTicTac2 -> cellID=2
            R.id.btnTicTac3 -> cellID=3
            R.id.btnTicTac4 -> cellID=4
            R.id.btnTicTac5 -> cellID=5
            R.id.btnTicTac6 -> cellID=6
            R.id.btnTicTac7 -> cellID=7
            R.id.btnTicTac8 -> cellID=8
            R.id.btnTicTac9 -> cellID=9
        }
        //   Toast.makeText(this, "ID: "+cellID, Toast.LENGTH_SHORT).show()
        GameLogic(cellID, btnSelected)
    }
    var Player1= ArrayList<Int>()
    var Player2= ArrayList<Int>()
    var ActivePlayer=1
    var allMoves = 0  // Track all moves made

    fun GameLogic(cellId:Int, btnSelected: Button){
        val textView = findViewById<TextView>(R.id.textView) // Find the TextView by ID

        if (ActivePlayer == 1){
            btnSelected.text="X"
            btnSelected.setTextColor(Color.RED)
            Player1.add(cellId)
            allMoves++
            ActivePlayer=2
            textView.text = getString(R.string.o_turn)
        }else {
            btnSelected.text="0"
            btnSelected.setTextColor(Color.BLUE)
            Player2.add(cellId)
            allMoves++
            ActivePlayer=1
            textView.text = getString(R.string.x_turn)
        }
        btnSelected.isEnabled= false
        FindWinner()
    }

    fun FindWinner() {
        val winningCombinations = listOf(
            listOf(1, 2, 3), // Row 1
            listOf(4, 5, 6), // Row 2
            listOf(7, 8, 9), // Row 3
            listOf(1, 4, 7), // Column 1
            listOf(2, 5, 8), // Column 2
            listOf(3, 6, 9), // Column 3
            listOf(1, 5, 9), // Diagonal 1
            listOf(3, 5, 7)  // Diagonal 2
        )

        var winner = -1

        for (combination in winningCombinations) {
            if (Player1.containsAll(combination)) {
                winner = 1
                break
            }
            if (Player2.containsAll(combination)) {
                winner = 2
                break
            }
        }
        if (winner != -1) {
            if (winner == 1) {
                showWinnerDialog("X is the Winner")
            } else {
                showWinnerDialog("O is the Winner")
            }
        } else if (allMoves == 9) {  // Check for a tie (all moves made and no winner)
            showTieDialog()
        }
    }

    fun showWinnerDialog(message: String) {
        AlertDialog.Builder(this@MainActivity).setTitle("Winner")
            .setMessage("$message\n\nWould you like to play again?")
            .setPositiveButton("Play Again") { dialog, which ->
                startActivity(Intent(this, MainActivity::class.java))
            }
            .create().show()
    }

    fun showTieDialog() {
        AlertDialog.Builder(this@MainActivity).setTitle("Tie")
            .setMessage("It's a tie! No more moves available.\n\nWould you like to play again?")
            .setPositiveButton("Play Again") { dialog, which ->
                startActivity(Intent(this, MainActivity::class.java))
            }
            .create().show()
    }
}