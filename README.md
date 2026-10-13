# blog-experiments

The evidence behind the posts on [suleymankilinc.com/blog](https://suleymankilinc.com/blog).

When a post says "300 of 300 identical" or "found three bugs", the code, data and scripts that
produced that number are here, so anyone can rerun them and check. Each experiment lives in its
own folder with a README that says what was done, what the results were, and what the numbers
do not show.

| Post | Folder |
|---|---|
| [AI made reverse engineering cheap. Checking it didn't get cheaper.](https://suleymankilinc.com/blog/ai-made-reverse-engineering-cheap) | [`reverse-engineering/part1-own-java/`](reverse-engineering/part1-own-java/) |
| [Part 2: a blind test on a program I had never seen](https://suleymankilinc.com/blog/blind-test-rebuilding-a-program-i-never-saw) | [`reverse-engineering/part2-blind-test/`](reverse-engineering/part2-blind-test/) |

## Conventions

- **Tools are pinned.** Every downloaded tool has a fixed version and a SHA-256 checksum, checked by the download script.
- **Every comparison has a control.** A check that says "identical" is also run on inputs that should differ, to show it can fail.
- **Coverage is reported, not just counts.** "N of N identical" comes with how many inputs reached each important path.
- **Limits are written down.** Each README has a section on what the result does not prove.

Most of the code here was written together with Claude Code, an AI coding assistant; the posts say so too.

## License

MIT, see [LICENSE](LICENSE).
