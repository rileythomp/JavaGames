# uttt

A 9x9 Tic-Tac-Toe variant.

When a player makes a move in a cell in a 3x3 board, the other play must make their next move somewhere in the corresponing 3x3 board of the larger board. For example, if player X goes in the bottom right corner cell of the top left corner board, then player O must make their next move move in the bottom right 3x3 board. When a player wins a 3x3 board, any move that would force a player to play in that board now let's them go anywhere. These constraint introduces some interesting strategies. The first player to get a tic-tac-toe on the larger board wins.

![uttt](utttsc.png)
