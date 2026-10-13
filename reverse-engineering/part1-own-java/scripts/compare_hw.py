"""Run the original and reconstructed hardware programs on the same inputs and diff the output.

Keys are read from the decompiled tables (not from the original source). Each case feeds a
GPU, a CPU, a game for the info lookup and a game for the benchmark, sometimes preceded by
an input the program should reject, so the retry paths run too.
"""
import random
import re
import subprocess
import sys

base = sys.argv[1]
n_cases = int(sys.argv[2])
src = open(f'{base}/build/hw/obf-src/Main.java', encoding='utf-8').read()


def keys(type_letter):
    return [k for k, t in re.findall(r'\.put\("(.*?)", new ([abcd])\(', src) if t == type_letter]


gpus, cpus, infos, benches = keys('a'), keys('c'), keys('d'), keys('b')
rng = random.Random(20261013)

cases = []
# Fixed edge cases first
cases.append(['RTX 4070', 'INTEL CORE i7-13700K', 'RYZEN 7 7800X3D', 'CYBERPUNK 2077', 'CYBERPUNK 2077'])
cases.append(['rtx 4070', 'ryzen 5 7600', 'RED DEAD REDEMPTION 2', 'RED DEAD REDEMPTİON 2', 'RED DEAD REDEMPTİON 2'])
cases.append(['RX 9700 XT', 'RX 9070 XT', 'RYZEN 9 9950X3D', 'FORTNITE', 'The Last Of Us Remake', 'FORTNITE'])
cases.append(['RTX 5090', 'RYZEN 5 5600', 'CITIES SKYLINES 2', 'BEAMNG DRIVE', 'BEAMNG DRIVE'])
cases.append(['RTX 2060'])  # input ends early: both programs must fail the same way
while len(cases) < n_cases:
    case = []
    if rng.random() < 0.2:
        case.append('GTX 9999')
    case.append(rng.choice(gpus))
    if rng.random() < 0.2:
        case.append(rng.choice(['INTEL CORE i5-12600K', 'unknown cpu']))
    case.append(rng.choice(cpus))
    case.append(rng.choice(infos))
    if rng.random() < 0.2:
        case.append('no such game')
    case.append(rng.choice(benches))
    cases.append(case)


def run(classpath, case):
    stdin = ('\n'.join(case) + '\n').encode('utf-8')
    p = subprocess.run(['java', '-Dfile.encoding=UTF-8', '-Dstdout.encoding=UTF-8', '-cp', classpath, 'Main'],
                       input=stdin, capture_output=True, timeout=60)
    err = p.stderr.decode('utf-8', 'replace').strip().splitlines()
    first_err = next((line for line in err if 'Exception' in line), '')
    return p.stdout.decode('utf-8', 'replace'), p.returncode, first_err


orig_cp = f'{base}/build/hw/orig.jar'
recon_cp = f'{base}/build/hw/recon-classes'
same = 0
mismatches = []
for i, case in enumerate(cases):
    a, b = run(orig_cp, case), run(recon_cp, case)
    if a == b:
        same += 1
    else:
        mismatches.append((i, case, a, b))

print(f'cases: {len(cases)}  identical stdout+exit+exception: {same}  mismatches: {len(mismatches)}')
for i, case, a, b in mismatches[:3]:
    print('--- mismatch case', i, case)
    al, bl = a[0].splitlines(), b[0].splitlines()
    for j, (x, y) in enumerate(zip(al, bl)):
        if x != y:
            print('  line', j, 'orig :', x)
            print('  line', j, 'recon:', y)
            break
    print('  exit', a[1], b[1], '| exc', a[2], '|', b[2], '| lines', len(al), len(bl))

# Positive control: the comparison must be able to fail. Feed the original a different case.
a = run(orig_cp, cases[0])
b = run(recon_cp, cases[3])
print('positive control (different inputs differ):', a != b)
