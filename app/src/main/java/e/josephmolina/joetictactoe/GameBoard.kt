package e.josephmolina.joetictactoe

import kotlin.math.max
import kotlin.math.min

class GameBoard {
    val board = Array(3) { arrayOfNulls<String>(3) }
    var computersMove: Cell? = null
    private val availableCells: List<Cell>
        get() {
            val cells = mutableListOf<Cell>()
            for (row in board.indices) {
                for (column in board.indices) {
                    if (board[row][column].isNullOrEmpty()) {
                        cells.add(Cell(row, column))
                    }
                }
            }
            return cells;
        }

    companion object {
        const val PLAYER = "O"
        const val COMPUTER = "X"
    }

    val isGameOver: Boolean
        get() = isComputerWinner() || isPlayerWinner() || isGameATie()

     fun isComputerWinner(): Boolean {
        return checkDiagonals(COMPUTER) || checkRows(COMPUTER) || checkColumns(COMPUTER)
    }

     fun isPlayerWinner(): Boolean {
        return checkDiagonals(PLAYER) || checkRows(PLAYER) || checkColumns(PLAYER)
    }

     fun isGameATie(): Boolean {
        return availableCells.isEmpty()
    }

    private fun checkColumns(player: String): Boolean {
        return (board[0][0] == player && board[1][0] == player && board[2][0] == player ||
                board[0][1] == player && board[1][1] == player && board[2][1] == player ||
                board[0][2] == player && board[1][2] == player && board[2][2] == player)
    }

    private fun checkRows(player: String): Boolean {
        return (board[0][0] == player && board[0][1] == player && board[0][2] == player ||
                board[1][0] == player && board[1][1] == player && board[1][2] == player ||
                board[2][0] == player && board[2][1] == player && board[2][2] == player)
    }

    private fun checkDiagonals(player: String): Boolean {
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player
                || board[0][2] == player && board[1][1] == player && board[2][0] == player)
    }

    fun miniMax(depth: Int, player: String): Int {
        if (isComputerWinner()) return + 1
        if (isPlayerWinner()) return - 1

        if (availableCells.isEmpty()) return 0

        var minimizerMove = Integer.MAX_VALUE
        var maximizerMove = Integer.MIN_VALUE

        for (cell in availableCells.indices) {
            val currentCell = availableCells[cell]
            if (player == COMPUTER) {
                placeMove(currentCell, COMPUTER)
                val currentScore = miniMax(depth + 1, PLAYER)
                maximizerMove = max(currentScore, maximizerMove)

                // Are we back at the top?
                if (currentScore >= 0 && depth == 0) {
                   computersMove = currentCell
                }

                undoMove(currentCell.row, currentCell.column)

            } else if (player == PLAYER) {
                placeMove(currentCell, PLAYER)
                val currentScore = miniMax(depth + 1, COMPUTER)
                minimizerMove = min(currentScore, minimizerMove)
                undoMove(currentCell.row, currentCell.column)
            }
        }
        return if (player == COMPUTER) maximizerMove else minimizerMove
    }

    private fun undoMove(row: Int, column: Int) {
        board[row][column] = ""
    }


    fun placeMove(cell: Cell, player: String) {
        board[cell.row][cell.column] = player
    }
}