package e.josephmolina.joetictactoe

class GameBoard {
    val board = Array(3) { arrayOfNulls<String>(3) }
    companion object{
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

    val availableCells: List<Cell>
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


    fun placeMove(cell: Cell, player: String) {
        board[cell.row][cell.column] = player
    }
}