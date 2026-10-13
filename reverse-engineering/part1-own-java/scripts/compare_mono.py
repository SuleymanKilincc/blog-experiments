"""Play the original and reconstructed Monopoly with the same dice seed and the same keystrokes.

Each game gets a seed and a stream of input lines drawn from Enter, buy, pass and stray keys,
long enough that most games finish. Output, including any exception at the end, is compared.
"""
import os
import random
import subprocess
import sys

base = sys.argv[1]
n_games = int(sys.argv[2])
rng = random.Random(1307)
tmp = f'{base}/build/mono/stdin.txt'


def play(classpath, seed, lines):
    with open(tmp, 'w', encoding='utf-8', newline='\n') as f:
        f.write('\n'.join(lines) + '\n')
    p = subprocess.run(['java', '-Dstdout.encoding=UTF-8', '-cp', f'{base}/harness', 'SeededRun', classpath, str(seed), tmp],
                       capture_output=True, timeout=120)
    return p.stdout.decode('utf-8', 'replace') + p.stderr.decode('utf-8', 'replace')


orig = f'{base}/build/mono/orig.jar'
recon = f'{base}/build/mono/recon-classes'
same, finished, mismatches = 0, 0, []
for g in range(n_games):
    seed = rng.randrange(1 << 40)
    buy_bias = rng.choice([0.2, 0.5, 0.9])
    lines = ['x'] * rng.randint(0, 2) + ['']  # sometimes a wrong key before the start roll
    for _ in range(rng.choice([40, 400, 4000])):
        r = rng.random()
        lines.append('b' if r < buy_bias else ('B' if r < buy_bias + 0.05 else rng.choice(['p', '', 'x'])))
    a, b = play(orig, seed, lines), play(recon, seed, lines)
    finished += 'KAZANAN' in a
    if a == b:
        same += 1
    else:
        mismatches.append((g, a, b))

print(f'games: {n_games}  identical: {same}  mismatches: {len(mismatches)}  games that reached a winner: {finished}')
for g, a, b in mismatches[:2]:
    for j, (x, y) in enumerate(zip(a.splitlines(), b.splitlines())):
        if x != y:
            print(f'game {g} line {j}\n  orig : {x}\n  recon: {y}')
            break

# Positive control: different seeds must give different games
print('positive control (different seeds differ):', play(orig, 1, [''] + ['b'] * 50) != play(orig, 2, [''] + ['b'] * 50))
