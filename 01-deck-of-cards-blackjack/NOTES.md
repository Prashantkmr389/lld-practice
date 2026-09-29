# 01 — Deck of Cards & Blackjack

started: 2026-09-09   ·   stage reached: 4   ·   time spent: 00:00

> **Prompt as given:** Design a card game system. Build a reusable standard
> 52-card deck, then implement Blackjack on top of it. Single JVM, console
> output, 1 dealer + 1..N human players at one table.
>
> **Stage 1 timer: 10 minutes.** Write your clarifying questions below before
> reading anything else. Do not open the deck of cards Wikipedia page.

---

## 1. Clarify  (10 min)

**Clarifying questions I would ask the interviewer**
1.
2.
3. 

**In scope (functional requirements)**
- FR1
- FR2

**Explicitly out of scope** — say this out loud in an interview, it earns points
- persistence / DB
- auth
-

**Actors**
| Actor | What they do |
|---|---|
|  |  |

**Scale / concurrency assumptions**
- single JVM? multi-threaded? how many concurrent actors?


### Settled spec (interviewer-given — do not re-litigate)

- One dealer, 2-6 players, count fixed when the table is created.
- Card/deck layer must be **game-agnostic**. Blackjack is the only game implemented,
  but adding Poker later must not require editing Card or Deck.
- Number cards = face value, J/Q/K = 10, Ace = 1 or 11, whichever favours the hand.
- Two cards each; dealer's second card is face down until all players finish.
- Player turn: HIT or STAND, repeatedly, until STAND or bust (> 21).
- Dealer: must hit at 16 or less, must stand at 17 or more. Not configurable.
- Outcomes: bust loses; otherwise higher total wins; **equal total is a PUSH**;
  "blackjack" = Ace + 10-value on the FIRST TWO CARDS only, and beats a 21 made
  from three or more cards.
- Multiple rounds at the same table.
- No persistence, no networking, no split / double down / insurance / surrender.
- Players cannot join or leave mid-session.

### Open decisions I must make and justify (not the interviewer's to answer)

1. Money: none at all (outcome is just WIN / LOSE / PUSH / BLACKJACK), or balances
   with a 3:2 blackjack payout? Cannot have "unlimited money" AND a payout ratio —
   pick one. Chosen: ______
2. Shoe size and reshuffle policy: how many 52-card decks, and does it reshuffle
   every round or at a penetration threshold? Chosen: ______
3. Is the dealer modelled as a kind of player, or as its own type? (Stage 3 decides
   this, not stage 1 — the requirement is only that the dealer has no free choices.)
4. Who computes a hand's total? Decided at stage 1: an external scorer (option c),
   because scoring is a game rule and a hand can hold more than two cards.
   Open sub-question: does that stay true if Hand is itself game-specific?

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


### Settled design (diagram v4, uml/image copy 2.png)

- `Card` — rank, suit (enums). No value. Game-agnostic.
- `Deck` — owns `List<Card>`; `Card drawCard()` is the ONLY way a card leaves it.
- `Hand` — owns `List<Card>`; `addCard(Card)`. No scoring.
- `Scorer` — `int getTotal(Hand)`. All Blackjack value rules live here.
- `Person` (abstract) — `Hand hand`, `TurnDecider decider`. `Player`, `Dealer` extend it.
- `TurnDecider` (interface) — `Action decide(Hand hand)`  <-- signature must be exactly this
    - `ConsolePlayer` — reads HIT/STAND from stdin
    - `DealerRule(int standsOn)` — HIT while score < standsOn
- `Action` enum — HIT, STAND
- `State` enum — WIN, LOOSE, BLACKJACK, PUSH, NOT_YET_DECIDE
- `Round` — `Map<Person, State>`; results live here, not on Person
- `Game` — owns deck, participants, scorer, current round. Orchestrates only.

---

## 4. Pseudocode  (15 min)

Only the flows that carry logic. Skip constructors and getters.

```
FLOW playRound:
   List<People> peoples;
  Map<People, State> peopleAndTheirState;
  cardDistribution(List<Cards> cards){
      card1 = cards[0], card2 = cards[1];
      i = 1;
      for(People people : peoples){
      
         people.h = {
            {card1, card2}
         }
         card1 = card[++i];
         card2 = card[++i];
      }

      People dealer;
      for(People p : peoples){
         if(people is Dealer)continue;
         canPlay = true;
         while(canPlay){
            Action a = p.decide(people.hand)()
            if(a ==Action.HIT){
               p.hand.addCard(cards.drawCard());
               score = scorer(p.hand)
               if(score > 21){
                  peopleAndTheirState[p] = state.LOOSE;
                  canPlay = false;
               }
               else if(score == 21){
                  peopleAndTheirState[p] = state.BLACKJACK;
                  canPlay = false;
               }
               else{

                  canPlay = true;
               }
            }
            else{
               continue;
            }
            
         }
      }

      // for dealer turn
      dealer.decide()

      

      for(People p : peoples){
         if(scorer.getTotal(p.hand) <  scorer.getTotal(dealer.hand)){
            peopleAndtheirState[p] = STATE.LOOSE;
         }
         else{
            peopleAndtheirState[p] = STATE.WIN;
         }
      }

  }

```


### Review of pseudocode v1 — bugs found (fix these in v2)

1. STAND does `continue` -> loop condition still true -> infinite loop. Must set canPlay=false / break.
2. `score == 21` marked BLACKJACK. Wrong: blackjack = Ace + 10-value on the FIRST TWO cards only.
   Check it right after the deal, not after a hit.
3. Dealer never plays: `dealer.decide()` called once, result ignored; `dealer` never assigned.
4. Settle: loops over the dealer too; overwrites busted players' LOOSE; no PUSH; no dealer-bust case.
5. Deal reads cards[i] by index instead of `deck.drawCard()`; runs past the end on the last player.
6. Parameter typed `List<Cards>` but used as a Deck.
7. `score(hand)` not written at all — this is the one with the real logic.

### v2 — remaining, in order (one at a time)

- [ ] FLOW score(hand) -> int        (handles any number of aces)
- [ ] FLOW playTurn(person, deck)     (fix bugs 1, 3)
- [ ] FLOW settle(players, dealer)    (fix bug 4)
- [ ] FLOW playRound                  (deal via drawCard, check blackjack, turns, dealer, settle)
- [ ] 2-3 invariants

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
