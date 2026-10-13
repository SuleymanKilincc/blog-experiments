# Part 2: blind test

A program I had never seen was written by another AI assistant (not Claude), compiled,
obfuscated, and handed to Claude as a single `obf.jar`. Claude rebuilt readable source from the
decompiled bytecode alone, and the rebuild was compared with the original on tests Claude never saw.

## Protocol

| Step | Done by | What Claude could see |
|---|---|---|
| Ask another AI for a Java 17+ console program (800–2000 lines, deterministic, stdin to stdout) and 30 test inputs | Süleyman | nothing |
| Run [`scripts/prepare-blind.ps1`](scripts/prepare-blind.ps1): compile with `-g:none`, rename every class, field and method with ProGuard (entry class included), keep source, original jar and mapping private | Süleyman | only `obf.jar` |
| Decompile with CFR, rebuild readable Java, test it against `obf.jar` with self-generated inputs | Claude | `obf.jar` and its decompiled form ([`decompiled/`](decompiled/)) |
| Run the 30 hidden tests on original and rebuild with [`scripts/compare-hidden-tests.ps1`](scripts/compare-hidden-tests.ps1) | Süleyman | the printed count only |
| Open the mapping and the source, score names, translate comments | Claude, after the tests | everything |

The private folder and the source folder were not opened before the hidden tests had run; the
session transcript records every file Claude read.

## Results

**Behaviour**

| Inputs | Identical | Notes |
|---|---|---|
| 30 hidden tests written by the program's author | **30 / 30** | stdout and exit code |
| 200 random command scripts (39,820 commands) | 200 / 200 | [`scripts/compare_blind.py`](scripts/compare_blind.py); a control run with different scripts differs |
| 150 targeted scenarios | 150 / 150 | [`scripts/compare_blind_targeted.py`](scripts/compare_blind_targeted.py) |

The random scripts never reached the loyalty bonus (it needs one customer to finish five orders
first), so the targeted scenarios were added. Paths reached in those 150: loyalty bonus 35,
coupon rejected at finalize 80, coupon applied 100, tier discount 129, quantity discount 131,
unknown tier 32, malformed number 150.

**Names** ([`names.md`](names.md), scored by [`scripts/name_score.py`](scripts/name_score.py) against [`artifacts/mapping.txt`](artifacts/mapping.txt))

- Classes: 10 of 13 got exactly the original name. 6 of those 10 appear verbatim in a `toString`
  format string (`"Category{id='%s'..."`), so they were given away by the program's own text.
  The other 3 differ in wording only: `CommandInterpreter`/`CommandProcessor`,
  `DiscountEngine`/`PricingEngine`, `Main`/`StoreApp`.
- Fields and methods: of 181, 62 exact (34%), 90 the same once `get`/`is`/`set` and case are
  ignored (50%). Only 12 of the 181 appear anywhere in the program's strings.

**String literals:** all 204 string literals in the rebuild match the decompiled program exactly
([`scripts/literal_check.py`](scripts/literal_check.py)). The 3 extra literals on the decompiled
side are enum constant names that CFR prints as strings.

**Time:** about 7 minutes of Claude's working time from opening `obf.jar` to a compiling,
matching rebuild, by the system clock.

## Bugs found by reading the bytecode

Each was confirmed by running the original program.

1. **`SET_BULK_DISCOUNT` is stored and listed but never priced.** `Category`'s own documentation
   promises "an extra 5% on orders of 500+ TL", but `DiscountEngine` never reads the setting.
   ```
   ADD_CATEGORY ELEK Elektronik 0
   ADD_PRODUCT P1 Laptop ELEK 1000 10
   ADD_CUSTOMER C1 Ali REGULAR
   CREATE_ORDER O1 C1
   ADD_ITEM O1 P1 2
   SET_BULK_DISCOUNT ELEK 100 20
   FINALIZE_ORDER O1        →  Ödenecek: 2000.00   (no discount)
   ```
2. **A used-up coupon is reported as "minimum amount not met".** `Coupon.isValid` returns false for
   both reasons and `Store.finalizeOrder` always blames the minimum. With a one-use coupon
   (minimum 50) on a second order of 300: `ERROR: Kupon TEK geçersiz (min tutar: 50.00, mevcut tutar karşılanmıyor)`.
3. **An open order's detail shows a zero total.** `ReportGenerator.orderDetail` prints the price
   summary for open orders that have items, although its own comment says the summary is "only
   meaningful for finalized or cancelled orders"; the totals are only computed at finalization,
   so it prints `ÖDENECEK TOPLAM: 0.00`.

**The author's own tests missed bug 1.** The test inputs carry expected results as comments
(`# Beklenen: ... = 950.00`). [`scripts/check_expectations.py`](scripts/check_expectations.py)
checks them: all 18 expected amounts appear in the output (a control program that prints nothing
misses all 18). The only test that sets a bulk discount, `test30`, writes no expectation for it.

## What this does not show

- **The program describes itself.** Its output strings and `toString` formats name most concepts,
  which made recovering meaning much easier. Commercial software often encrypts or strips strings.
- **The decompiler was not perfect.** CFR printed both enum constructors as "possible parameter
  corruption"; they were rewritten from the logic.
- **One program, one reverse engineer.** It says nothing about larger or adversarially protected code.
- **The time is Claude's**, measured by the system clock, not a person's.

## Files

| Path | What it is |
|---|---|
| `original/src/` | The program as the other AI wrote it. Comments were translated from Turkish to English afterwards; recompiled with `-g:none`, all 13 class files are byte-identical to the original build, so only comments changed. Program output stays in Turkish. |
| `tests/` | The 30 hidden test inputs, unchanged |
| `artifacts/obf.jar` | The exact obfuscated jar Claude worked from |
| `artifacts/mapping.txt` | ProGuard's name mapping (private until the tests had run) |
| `decompiled/` | CFR 0.152 output of `obf.jar` |
| `reconstruction/` | Claude's rebuild; entry point `StoreApp` |
| `names.md` | Every class, field and method name, original vs rebuild |
| `scripts/` | Preparation, comparison, scoring and checking scripts |

## Rerun

```powershell
..\tools\get-tools.ps1
javac -encoding UTF-8 --release 21 -d build\recon reconstruction\*.java
scripts\prepare-blind.ps1 -Source original\src -Out build\out -Private build\private -Tests tests
scripts\compare-hidden-tests.ps1 -Tests tests -Reconstruction build\recon -Private build\private
python scripts\check_expectations.py tests artifacts\obf.jar
```
