"""Check the fixed hardware program: every listed model must be selectable however it is typed,
and compared with the original build, only the intended output lines may change."""
import random
import re
import subprocess
import sys

base = sys.argv[1]
src = open(f'{base}/orig/hw/src/Main.java', encoding='utf-8').read()
fixed_cp, orig_cp = f'{base}/build/hw/fixed-classes', f'{base}/build/hw/orig.jar'


def keys(map_name):
    return re.findall(map_name + r'\.put\("(.*?)"', src)


gpus, cpus = keys('kartKutuphanesi'), keys('islemciKutuphanesi')
infos, benches = keys('sistemGereksinimkutuphanesi'), keys('benchmarkKutuphanesi')


def run(cp, lines):
    p = subprocess.run(['java', '-Dstdout.encoding=UTF-8', '-cp', cp, 'Main'],
                       input=('\n'.join(lines) + '\n').encode('utf-8'), capture_output=True, timeout=60)
    return p.stdout.decode('utf-8', 'replace')


def ascii_variant(key):
    return key.replace('İ', 'I')


def accepted(stage_lines, marker):
    return marker in run(fixed_cp, stage_lines)


checks = []
for k in cpus:
    for typed in (k, k.lower(), ascii_variant(k).title()):
        checks.append(('CPU', typed, accepted(['RTX 4070', typed], 'İşlemciniz ')))
for k in gpus:
    checks.append(('GPU', k.lower(), accepted([k.lower()], 'Ekran kartınız ')))
for k in infos:
    for typed in (k, ascii_variant(k).lower()):
        checks.append(('Game info', typed, accepted(['RTX 4070', 'RYZEN 5 7600', typed], 'OYUN BİLGİLERİ')))
for k in benches:
    for typed in (k, ascii_variant(k).lower()):
        checks.append(('Benchmark', typed, accepted(['RTX 4070', 'RYZEN 5 7600', 'FORTNITE', typed], 'Seçilen Oyun : ')))

by_kind = {}
for kind, typed, ok in checks:
    good, total = by_kind.get(kind, (0, 0))
    by_kind[kind] = (good + ok, total + 1)
for kind, (good, total) in by_kind.items():
    print(f'{kind:10s} accepted {good}/{total}')
print('failures:', [(k, t) for k, t, ok in checks if not ok][:5])

out = run(fixed_cp, ['RX 9070 XT', 'RYZEN 5 7600', 'CITIES SKYLINES 2', 'CITIES SKYLINES 2'])
print('RX 9070 XT name line:', [l for l in out.splitlines() if l.startswith('Ekran kartınız')])
print('Cities RAM line:', [l for l in out.splitlines() if "'RAM'" in l])
out = run(fixed_cp, ['RTX 3080', 'RYZEN 5 7600', 'FORTNITE', 'FORTNITE'])
print('RTX 3080 bus line:', [l for l in out.splitlines() if l.startswith('Bellek arayüzü')])
out = run(fixed_cp, ['RTX 4070', 'intel core i9-12900', 'FORTNITE', 'FORTNITE'])
print('i9-12900 name line:', [l for l in out.splitlines() if l.startswith('İşlemciniz')])

# Regression: on inputs the original already accepted, every differing line must be one we meant to change
rng = random.Random(7)
ryzen = [c for c in cpus if c.startswith('RYZEN')]
expected_changes = ('Sorgulamak istediğiniz ekran kartı', 'Sorgulamak istediğniz işlemci', 'Sistem gereksinimlerini',
                    'Ekran kartınız AMD RADEON RX 9070 XT', 'Bellek arayüzü 320', "Önerilen sistem 'RAM' : 32",
                    'Seçtiğiniz oyun : Red Dead Redemption 2')
unexpected, compared = [], 0
for _ in range(150):
    case = [rng.choice(gpus), rng.choice(ryzen), rng.choice(infos), rng.choice(benches)]
    a, b = run(orig_cp, case).splitlines(), run(fixed_cp, case).splitlines()
    compared += 1
    if len(a) != len(b):
        unexpected.append((case, 'line count', len(a), len(b)))
        continue
    for x, y in zip(a, b):
        if x != y and not y.startswith(expected_changes):
            unexpected.append((case, x, y))
print(f'regression: {compared} cases, unexpected differing lines: {len(unexpected)}')
for u in unexpected[:3]:
    print('  ', u)
