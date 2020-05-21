package e.josephmolina.joetictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val visualBoardCells = Array(3) { arrayOfNulls<ImageView>(3) }
    var gameBoard = GameBoard()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUiBoard()
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
                        visualBoardCells[row][column]?.setImageResource(R.drawable.circle)
                        visualBoardCells[row][column]?.isEnabled = false
                    }

                    GameBoard.COMPUTER -> {
                        visualBoardCells[row][column]?.setImageResource(R.drawable.cross)
                        visualBoardCells[row][column]?.isEnabled = false
                    }
                    else -> {
                        visualBoardCells[row][column]?.setImageResource(0)
                        visualBoardCells[row][column]?.isEnabled = true
                    }
                }
            }
        }
    }

    private fun setupUiBoard() {
        for (row in visualBoardCells.indices) {
            for (column in visualBoardCells.indices) {
                visualBoardCells[row][column] = ImageView(this)
                setCellDimensions(row, column)
                setCellColor(row, column)
                visualBoardCells[row][column]?.setOnClickListener(CellClickListener(row, column))
                layout_board.addView(visualBoardCells[row][column])
            }
        }
    }

    private fun setCellDimensions(row: Int, column: Int) {
        visualBoardCells[row][column]?.layoutParams = GridLayout.LayoutParams().apply {
            rowSpec = GridLayout.spec(row)
            columnSpec = GridLayout.spec(column)

            width = resources.getInteger(R.integer.cellWidth)
            height = resources.getInteger(R.integer.cellHeight)
            bottomMargin = resources.getInteger(R.integer.cellMargin)
            topMargin = resources.getInteger(R.integer.cellMargin)
            leftMargin = resources.getInteger(R.integer.cellMargin)
            rightMargin = resources.getInteger(R.integer.cellMargin)
        }
    }

    private fun setCellColor(row: Int, column: Int) {
        visualBoardCells[row][column]?.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )
    }

    private fun setGameStatusTitle() {
        when {
            gameBoard.isComputerWinner() -> text_view_result.text = getString(R.string.computer_won)
            gameBoard.isPlayerWinner() -> text_view_result.text = getString(R.string.player_won)
            gameBoard.isGameATie() -> text_view_result.text = getString(R.string.game_tied)
        }
    }

    inner class CellClickListener(private val row: Int, private val col: Int) :
        View.OnClickListener {
        override fun onClick(p0: View?) {
            if (!gameBoard.isGameOver) {
                val cell = Cell(row, col)
                gameBoard.placeMove(cell, GameBoard.PLAYER)

              gameBoard.miniMax(0, GameBoard.COMPUTER)
                gameBoard.computersMove?.let {
                    gameBoard.placeMove(it, GameBoard.COMPUTER)
                }
                mapBoardToUI()
            }
            setGameStatusTitle()
        }
    }
}
