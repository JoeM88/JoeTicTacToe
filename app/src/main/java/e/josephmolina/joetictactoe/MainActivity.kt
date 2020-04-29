package e.josephmolina.joetictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    private val boardCells = Array(3) { arrayOfNulls<ImageView>(3) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadBoard()
    }

    private fun loadBoard() {
        for (row in boardCells.indices) {
            for (column in boardCells.indices) {
                boardCells[row][column] = ImageView(this)
                setCellDimensions(row, column)
                setCellColor(row, column)
                layout_board.addView(boardCells[row][column])
            }
        }
    }

    private fun setCellDimensions(row: Int, column: Int) {
        boardCells[row][column]?.layoutParams = GridLayout.LayoutParams().apply {
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
        boardCells[row][column]?.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )
    }
}
