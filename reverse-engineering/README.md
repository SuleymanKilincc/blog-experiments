# Reverse engineering with an AI assistant

Evidence for the post [AI made reverse engineering cheap. Checking it didn't get cheaper.](https://suleymankilinc.com/blog/ai-made-reverse-engineering-cheap)

The question: if a released Java program is obfuscated, how much of it can an AI assistant
(Claude) rebuild from the decompiled bytecode, and how do you prove the rebuild is right?

| Part | Program | Who wrote it | Blind? | Behaviour | Bugs found |
|---|---|---|---|---|---|
| [1](part1-own-java/) | Hardware scorer (504 lines) and console Monopoly (291 lines) | me, early in my studies | No, file names were seen | 300/300 and 60/60 identical | 4 in the hardware scorer, all confirmed and since fixed |
| [2](part2-blind-test/) | Store management system (13 classes, 1,772 lines) | another AI assistant | Yes | 30/30 hidden tests, 200 random scripts, 150 targeted scenarios identical | 3, all confirmed |

## Pipeline

```
source ──javac -g:none──► jar ──ProGuard 7.10.0──► obfuscated jar ──CFR 0.152──► decompiled Java
                                                                                       │
                                     reconstruction (readable Java) ◄── Claude ────────┘
                                                │
                     run original and reconstruction on the same inputs, compare byte for byte
```

## Tools

`tools/get-tools.ps1` downloads ProGuard 7.10.0 and CFR 0.152 and checks their SHA-256.
The experiments ran on Windows 11 with JDK 25 (`--release 21` for compilation) and Python 3.10.

A string-encryption step was also tried with Skidfuscator 2.0.11. On a program whose behaviour
was already known, its output crashed after the first line and left some strings readable, so it
was not used. Encrypting strings is the obvious next experiment.
