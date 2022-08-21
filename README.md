# League-Ranking-Calculator
Application that will calculate the ranking table for a league.

## Rules
In this league, a draw (tie) is worth 1 point and a win is worth 3 points. A loss is worth 0 points.
If two or more teams have the same number of points, they should have the same rank and be
printed in alphabetical order (as in the tie for 3rd place in the sample data).

## Sample Input
Lions 3, Snakes 3<br>
Tarantulas 1, FC Awesome 0<br>
Lions 1, FC Awesome 1<br>
Tarantulas 3, Snakes 1<br>
Lions 4, Grouches 0<br>

## Expected Output
1. Tarantulas, 6 pts
2. Lions, 5 pts
3. FC Awesome, 1 pt
3. Snakes, 1 pt
5. Grouches, 0 pts

## Command Line Parametes
-i input file<br>
-o output file<br>