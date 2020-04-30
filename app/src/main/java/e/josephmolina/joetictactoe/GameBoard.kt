package e.josephmolina.joetictactoe

class GameBoard {
    val board = Array(3) { arrayOfNulls<String>(3) }
    companion object{
        const val PLAYER = "O"
        const val COMPUTER = "X"
    }

    fun placeMove(cell: Cell, player: String) {
        board[cell.row][cell.column] = player
    }
}