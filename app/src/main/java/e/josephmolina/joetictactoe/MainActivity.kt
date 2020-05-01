package e.josephmolina.joetictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val uiBoardCells = Array(3) { arrayOfNulls<ImageView>(3) }
    var gameBoard = GameBoard()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadUIBoard()
        setRestartButton()
    }

    private fun setRestartButton() {
        button_restart.setOnClickListener{
            gameBoard = GameBoard()
            text_view_result.text = ""
            mapBoardToUI()
        }
    }

    private fun mapBoardToUI() {
        for (row in gameBoard.board.indices) {
            for (column in gameBoard.board.indices) {
                when (gameBoard.board[row][column]) {
                    GameBoard.PLAYER -> {
                        uiBoardCells[row][column]?.setImageResource(R.drawable.circle)
                        uiBoardCells[row][column]?.isEnabled = false
                    }

                    GameBoard.COMPUTER -> {
                        uiBoardCells[row][column]?.setImageResource(R.drawable.cross)
                        uiBoardCells[row][column]?.isEnabled = false
                    }
                    else -> {
                        uiBoardCells[row][column]?.setImageResource(0)
                        uiBoardCells[row][column]?.isEnabled = true
                    }
                }
            }
        }
    }

    private fun loadUIBoard() {
        for (row in uiBoardCells.indices) {
            for (column in uiBoardCells.indices) {
                uiBoardCells[row][column] = ImageView(this)
                setCellDimensions(row, column)
                setCellColor(row, column)
                uiBoardCells[row][column]?.setOnClickListener(CellClickListener(row, column))
                layout_board.addView(uiBoardCells[row][column])
            }
        }
    }

    private fun setCellDimensions(row: Int, column: Int) {
        uiBoardCells[row][column]?.layoutParams = GridLayout.LayoutParams().apply {
            rowSpec = GridLayout.spec(row)
            columnSpec = GridLayout.spec(column)
            width = 300
            height = 300
            bottomMargin = 5
            topMargin = 5
            leftMargin = 5
            rightMargin = 5
        }
    }

    private fun setCellColor(row: Int, column: Int) {
        uiBoardCells[row][column]?.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )
    }

    inner class CellClickListener(private val row: Int, private val col: Int): View.OnClickListener{
        override fun onClick(p0: View?) {
            val cell = Cell(row, col)
            gameBoard.placeMove(cell, GameBoard.PLAYER)

            if (gameBoard.availableCell.isNotEmpty()) {
                val computerCell = gameBoard.availableCell[Random.nextInt(0, gameBoard.availableCell.size)]
                gameBoard.placeMove(computerCell, GameBoard.COMPUTER)
            }
            mapBoardToUI()
        }
    }
}
