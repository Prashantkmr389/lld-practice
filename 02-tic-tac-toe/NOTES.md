# 02 — Tic-Tac-Toe (N×N)

started: 2026-09-29   ·   stage reached: 6   ·   time spent: 00:00

---

## 1. Clarify  (10 min)

### Settled spec (interviewer answers)
- Board is N×N, N chosen at game start. Test with 3×3.
- Win = a full line of N marks: any row, any column, or either diagonal.
- Draw = board full with no winner.
- Two players take turns. Human input from console; a bot opponent is also supported.
- Invalid move (cell taken OR outside the board) -> the SAME player is asked again; no turn lost.
- Stage 6 follow-up to expect: "win with K in a row on a 10×10 board". Don't hardcode 3×3.

### In scope (my list)
- N×N tic-tac-toe, tested at 3×3
- Players take turns; humans enter moves on the console; a player can instead be a bot with its own strategy
- A player must pick a cell to fill each turn
- Draw when the board is full
- Win on a complete row, column, or diagonal of the same mark
- Input outside the board -> asked again
- Input is row, then column, entered one at a time (simplest for the player)

### Out of scope (my list)
- Undo
- More than two players
- GUI (console only)
- Score across multiple games

### Open points from review
- Add: a TAKEN cell also re-prompts (not just out-of-board)
- State the v1 bot strategy explicitly (e.g. random empty cell)
- Add K-in-a-row variant to out-of-scope (designed for, not built)

---

## 2. Noun / verb pass  (10 min)

**Candidate classes (nouns)**
Player1, Player2, Board, Player1 Move, Player2 Move, Row, Column, Cell, Game;

**Candidates I killed and why** — this list matters more than the one above.
A noun that has no behaviour and no identity is a field, not a class.
| Killed | Became |
|---|---|
| row | Cell |
| column | Cell |
| Player1 Move | Move |
| Player2 Move | Move |
| Player1 | Player |
| Player2 | Player |



**Behaviours (verbs) → which class owns each**
| Verb | Owner | Why that owner |
|---|---|---|
| Fill | Board | Player can invoke with cell and inputType to fill out the cell|
| Decide | Player | Implemented by the players |
| SwitchTurn | Game | Game will switch turn |
| ValidateMove | Board | Game will decide whether the move was valid or not |
| checkForWin | Board | Game will decide whether the move resulted in Win or not |
| checkForDraw | Board | Game will decide whether the move result in draw or not |
| runTheGame | Game | Game will to run the game or not |

---

## 3. UML  (20 min, on paper)

Diagram lives in `uml/`. Before you draw, decide and write here:

- **Interfaces / abstract classes I will introduce:**
- **Where variation lives** (each of these is a Strategy candidate):
- **Composition vs aggregation calls** — who owns whose lifetime:
- **Enums vs subclasses** — and why:

Patterns I expect to use, and the *pressure* that justifies each:
| Pattern | Justified by which requirement |
|---|---|

---

## 4. Pseudocode  (15 min)

Only the flows that carry logic. Skip constructors and getters.

```
FLOW <name>:
  ...
```

**Invariants that must hold at all times**
- I1
-

---

## 5. Java  (45-60 min)

Code in this folder. `Main` demonstrates the happy path plus one edge case.

Checklist before you say done:
- [ ] no `switch`/`if` chain on a type field where polymorphism would do
- [ ] every collection field is `private final`, exposed as unmodifiable
- [ ] `equals`/`hashCode` on anything used as a map key
- [ ] custom exceptions, not `RuntimeException("...")`
- [ ] no `null` returns for "not found" — `Optional` or an explicit type
- [ ] shared mutable state is either confined or guarded, and you can name which

---

## 6. Review  (15 min)

**SOLID audit** — one concrete sentence each, "N/A" is not an answer
| Principle | Where I honoured it | Where I violated it |
|---|---|---|
| SRP |  |  |
| OCP |  |  |
| LSP |  |  |
| ISP |  |  |
| DIP |  |  |

**Extension test**
> New requirement: ______________________________
> Files I must open to support it: ___
> Files I must *modify* (not just add): ___

**What I got wrong in stage 3 that only showed up in stage 5**

**Reusable lesson (one line, goes in the tracker)**

### Stage 6 outcome (2026-09-29)

**Extension test:** 10×10 board, win with 4 in a row.
- New file: one new `WinningStrategy` implementation (e.g. `KInARowWinningStrategy(int k)`).
- Changed files: `Main.java` only (board size 10, pass the new strategy).
- `Board`, `Game`, `Player`, both move strategies: untouched. Board already N×N from stage 1.
- Principle: Open/Closed — new behaviour by adding a class, not editing existing ones.

**Verified:** compiles clean with -Xlint:all; full game runs to a win; win check passed 9/9 boards
(3×3 row/col/both diagonals, 4×4 "3 in a row is not a win", 5×5 anti-diagonal, 1×1); 30 random games, no hang/crash.

**Still open (polish):** message on invalid/taken cell; catch InputMismatchException for non-numbers;
space in "Player X is winner"; rename WinningStartegy -> WinningStrategy.

**Reusable lesson:** put the rule most likely to change behind an interface on day one — the extension
then costs one new class and one line of wiring.
