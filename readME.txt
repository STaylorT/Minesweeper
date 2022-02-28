Minesweeper
Developed by : Sean Taylor Thomas
2/22

This program simulates a minesweeper game.
In minesweeper, there is a grid of cells that the player can click.
Each of these cells either represents a mine or it does not. If a player clicks on a cell with a mine, they blow UP.
When a user makes her first click, it will never be a mine. Surrounding cells will be revealed that are not mines. 
When a cell has been uncovered it's either "blank" (no mines are around its 8 neighbors) or contains a number 1-8 representing
the number of mines around the given cell.
The goal is for the player to uncover all non-mine cells without clicking any of the mines and BLOWING UP.

Flags:
The player can right click on a given cell to add a "flag" where the user thinks a mine might be. 
The purpose of the flag is just to let the player visualize where mines are at and disable them from right-clicking the cell.
To remove a flag, the player can right click on the flagged cell.


Options:
The default grid size is 9x9. The player can choose 3 pre-defined modes (easy, medium, hard) or can select her own custom size and mine
percentage.


Improvements to be made:
- Store images in Grid class instead of Cell class so there aren't so many loaded images being unused
- Enhance UI, especially after game loss / win.
