# LLD Practice — Java

Rule of the house: **no code before the diagram.** Every problem runs the same
six stages, in order, timed. If you skip a stage you did not practise LLD, you
practised typing.

| # | Stage | Budget | Artifact you produce |
|---|-------|--------|----------------------|
| 1 | Clarify | 10 min | `NOTES.md` §1 — functional reqs, out-of-scope, actors |
| 2 | Noun/verb pass | 10 min | `NOTES.md` §2 — candidate classes, killed candidates |
| 3 | UML class diagram | 20 min | photo/export in `uml/` — by hand, correct arrow notation |
| 4 | Pseudocode | 15 min | `NOTES.md` §4 — core flows only, no getters |
| 5 | Java | 45-60 min | `*.java` in the problem folder + a `Main` demo |
| 6 | Review | 15 min | `NOTES.md` §6 — SOLID audit + the extension test |

## Folder convention

```
lld-practice/
  NN-problem-name/
    NOTES.md      copy of _template/NOTES.md
    uml/          your diagram (photo, .drawio, .svg — anything)
    *.java        default package, one type per file
    Main.java     runnable demo of the happy path + one edge case
```

No Maven, no Gradle. Default package. Keeps the friction at zero.

## Compile and run

```sh
./run.sh 01-tic-tac-toe
```

## The extension test (stage 6)

After the code compiles, pick a requirement you did **not** design for and ask:
how many existing files must I open to add it? Zero or one means your
abstractions held. Four means you have a god class wearing a costume.
Write the answer down in §6. That number, tracked over 40 problems, is the
only real measure of whether this practice is working.
