# Worked example — Stage 2 done properly

Problem used: **Snake & Ladders** (not one you're currently designing, on purpose).
Scope assumed: 2-4 players, one 100-cell board, snakes and ladders, one die,
must land exactly on 100 to win, players alternate turns.

## 1. Candidate nouns — everything the spec mentions

Game, Board, Cell, Player, Token, Dice, Snake, Ladder, Jump, Move, Turn,
Position, Winner, TurnOrder, Rules

## 2. Kill list — the part that matters

| Killed | Why | What it became |
|---|---|---|
| `Snake` | identical behaviour to Ladder: move from A to B. Only the direction differs, and direction is just `end < start`. | folded into `Jump` |
| `Ladder` | same as above | folded into `Jump` |
| `Cell` | 100 objects whose only content is their own index. No behaviour, no identity worth distinguishing. | an `int` position; jumps live in a `Map<Integer,Integer>` on `Board` |
| `Position` | an int with no behaviour | a field on `Player` |
| `Move` | it happens, it isn't a thing — no identity, no state that outlives the turn | the return of `Dice.roll()` plus `Board.destinationFrom(pos, roll)` |
| `Winner` | a role a Player temporarily occupies, not a separate type | a `Player` reference inside `GameResult` |
| `TurnOrder` | no identity, no behaviour beyond rotation | a `Deque<Player>` inside the game |
| `Token` | in THIS game each player has exactly one, with no piece-specific behaviour | collapsed into `Player` |

Note the last row. `Token` **would survive in Ludo**, where one player owns four
pieces that each have their own position and state. Killing a candidate is a
scope-dependent decision, not a universal truth — which is why you write down
the reason, not just the verdict.

## 3. Survivors

`Board` · `Player` · `Dice` · `Jump` · `Game` · `GameResult`

Six classes. Compare with the fifteen candidates.

## 4. Verb -> owner

| Verb | Owner | Why that owner |
|---|---|---|
| roll | `Dice` | owns the randomness; injecting a fake Dice is the only way to test the game deterministically |
| translate a position + roll into a destination | `Board` | the only object that knows where the jumps are |
| move to a position | `Player` | owns its own position — nothing else is allowed to write it |
| decide whose turn it is | `Game` | owns the turn queue |
| run one turn | `Game` | orchestrates dice -> board -> player in order |
| decide whether the game is over | `Game` | **Strategy candidate** — becomes `WinCondition` the moment "first to 100" might change |
| decide what happens on an overshoot | `Game` | **Strategy candidate** — "must land exactly" is one house rule among several |
| report what happened | an observer / renderer, outside all of the above | so console printing is never welded into the rules |

## 5. The three verbs everyone conflates

"The player rolls and moves" sounds like one action by one object. It is three:
**Dice** rolls, **Board** translates, **Player** moves. If one class does all
three, that class is your god class and you built it in stage 2, not stage 5.
