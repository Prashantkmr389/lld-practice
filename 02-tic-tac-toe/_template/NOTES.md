# <Problem name>

started: <date>   ·   stage reached: <1-6>   ·   time spent: <hh:mm>

---

## 1. Clarify  (10 min)

**Clarifying questions I would ask the interviewer**
1. 
2.
3.

**In scope (functional requirements)**
- should for N*N tic & toe, test it out for 3*3
- Each player will take turn and will be asked input from console but player can also play against bot which needs it's own strategy
- One player must have to choose the cell that needs to be filled. 
- Game will end in draw when the board is full
- Game will end when either wins by having same input in row or column or any diagonal
- player give input outside the cell it will be asked again,
- Player will be asked to give input in row and col in one by one because it is simple.



**Explicitly out of scope** — say this out loud in an interview, it earns points
- Player Can't undo a move
- Three player can't play
- It is console only, No Gui
- Only one game, no score keeping across several games

**Actors**
| Actor | What they do |
|---|---|
|  |  |

**Scale / concurrency assumptions**
- single JVM? multi-threaded? how many concurrent actors?

---

## 2. Noun / verb pass  (10 min)

**Candidate classes (nouns)**

**Candidates I killed and why** — this list matters more than the one above.
A noun that has no behaviour and no identity is a field, not a class.
| Killed | Became |
|---|---|
|  |  |

**Behaviours (verbs) → which class owns each**
| Verb | Owner | Why that owner |
|---|---|---|

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
