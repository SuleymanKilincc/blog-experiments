# Part 1: two of my own early Java programs

Not blind: Claude saw the file names when the repositories were cloned, so class names were not
scored. The original source was not opened until the comparison was finished.

| Program | Source | Size |
|---|---|---|
| Hardware scorer (CPU/GPU pair → score, bottleneck, FPS bands) | [Donanim-ve-Oyun-Benchmark-Hesaplamasi @ e76e274](https://github.com/SuleymanKilincc/Donanim-ve-Oyun-Benchmark-Hesaplamasi/tree/e76e274) | 6 files, 504 lines |
| Console Monopoly | [Java-Monopoly-Game @ e1d12c5](https://github.com/SuleymanKilincc/Java-Monopoly-Game/tree/e1d12c5) | 3 files, 291 lines |

Both were compiled with `javac -g:none --release 17`, obfuscated with ProGuard 7.10.0 (only
`Main.main` kept) and decompiled with CFR 0.152.

## Results

| | Hardware scorer | Monopoly |
|---|---|---|
| Behaviour | 300 / 300 inputs identical ([`scripts/compare_hw.py`](scripts/compare_hw.py)) | 60 / 60 seeded games identical, 36 played to a winner ([`scripts/compare_mono.py`](scripts/compare_mono.py)) |
| Control | different inputs differ | different seeds differ |
| Time (system clock) | about 5 minutes | about 2 minutes |

Monopoly rolls dice, so [`monopoly/harness/SeededRun.java`](monopoly/harness/SeededRun.java)
replaces the program's static `Random` with a seeded one (found by type, not by name) before
`main` runs, giving the original and the rebuild the same dice.

Names: of 64 fields and methods across both programs, 3 came back with the exact original
identifier; nearly all came back with the same meaning (`watDegeri` → `powerWatts`,
`isParcacigisayisi` → `threadCount`, `kiraBedeli` → `rent`). Every field is printed next to a
Turkish label, which is where most of the meaning came from.

## What went wrong on the way

- **The first comparison failed on every case.** While retyping one prompt, Claude had silently
  corrected a typo ("sırasısyla" to "sırasıyla"). Only the byte-level comparison caught it.
  Restoring the typo gave 300/300.
- **CFR's Monopoly output did not compile.** It had mixed up variable types
  (`Iterator iterator = e.nextLine();`), so the rebuild needed reasoning, not just renaming.

## Bugs found in the hardware scorer

Confirmed by running the original, and fixed in
[1db52dd](https://github.com/SuleymanKilincc/Donanim-ve-Oyun-Benchmark-Hesaplamasi/commit/1db52dd):

- None of the 22 Intel CPUs could be selected: input was upper-cased ("i7" → "I7") but the keys
  kept a lower-case "i". All 14 Ryzen models worked.
- "RED DEAD REDEMPTION 2" typed with a plain I was rejected; the key used the Turkish İ.
- The RX 9070 XT was displayed as an RX 9700 XT.
- Cities: Skylines II recommended -32 GB of RAM: `32 - 64` was meant as a range and evaluated as a subtraction.

[`scripts/verify_fix.py`](scripts/verify_fix.py) checks the fix: every listed CPU, GPU and game is
accepted in three spellings (270/270), and on 150 inputs the old version already accepted only the
intended lines change.

## Files

| Path | What it is |
|---|---|
| `hardware/reconstruction/`, `monopoly/reconstruction/` | Claude's rebuilds |
| `monopoly/harness/SeededRun.java` | Gives both builds the same dice |
| `scripts/gen_hw_main.py` | Copies the hardware data tables from the decompile mechanically |
| `scripts/compare_hw.py`, `scripts/compare_mono.py` | Byte-for-byte comparisons |
| `scripts/verify_fix.py` | Checks the bug fixes |

The scripts take the experiment folder as their first argument and expect the layout they were
run in (`build/<program>/orig.jar`, `build/<program>/obf-src/`, `recon/<program>/`).
