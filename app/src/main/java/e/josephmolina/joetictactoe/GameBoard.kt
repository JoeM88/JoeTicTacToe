package e.josephmolina.joetictactoe

class GameBoard {
    val board = Array(3) { arrayOfNulls<String>(3) }
    companion object{
        const val PLAYER = "O"
        const val COMPUTER = "X"
    }

    val availableCell: List<Cell>
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