"""Targeted scenarios for paths the random scripts rarely reach: loyalty bonus (6th order onward),
coupons hitting their minimum or usage limit, quantity discounts, tier changes, bad numbers and
unknown tiers. Compares original vs reconstruction and reports how often each path was hit."""
import collections
import random
import subprocess
import sys

blind = sys.argv[1]
n = int(sys.argv[2])
rng = random.Random(99)


def scenario():
    tier = rng.choice(['REGULAR', 'SILVER', 'GOLD', 'PLATINUM'])
    lines = [f'ADD_CATEGORY ELEK Elektronik {rng.choice([0, 7.5, 12, 30])}',
             f'ADD_CATEGORY GIDA Gida {rng.choice([0, 3])}',
             f'ADD_PRODUCT P1 Telefon ELEK {rng.uniform(50, 900):.2f} 200',
             f'ADD_PRODUCT P2 Ekmek GIDA {rng.uniform(1, 40):.2f} 500',
             f'ADD_CUSTOMER C1 Ayse {tier}',
             f'ADD_COUPON K1 {rng.choice([5, 10, 12.5])} {rng.choice([0, 100, 1000, 5000])}',
             f'ADD_COUPON_LIMITED L1 {rng.choice([5, 20])} {rng.choice([0, 200])} {rng.choice([1, 2, 3])}']
    for k in range(1, rng.randint(6, 10)):
        o = f'O{k}'
        lines.append(f'CREATE_ORDER {o} C1')
        lines.append(f'ADD_ITEM {o} P1 {rng.choice([1, 2, 4, 5, 7])}')
        if rng.random() < 0.6:
            lines.append(f'ADD_ITEM {o} P2 {rng.choice([1, 5, 12])}')
        if rng.random() < 0.5:
            lines.append(f'APPLY_COUPON {o} {rng.choice(["K1", "L1"])}')
        if rng.random() < 0.15:
            lines.append(f'UPGRADE_TIER C1 {rng.choice(["SILVER", "GOLD", "platinum", "WOOD"])}')
        if rng.random() < 0.1:
            lines.append(f'CANCEL_ORDER {o}')
        lines.append(f'FINALIZE_ORDER {o}')
        if rng.random() < 0.5:
            lines.append(f'ORDER_DETAIL {o}')
    lines += ['CUSTOMER_REPORT C1', 'LIST_ORDERS', 'LIST_COUPONS', 'STOCK_REPORT', 'LIST_CUSTOMERS',
              'ADD_PRODUCT P3 X ELEK 1,5 3', 'RESTOCK P1 abc', 'ADD_COUPON K2 0 10']
    return lines


def run(cmd, lines):
    p = subprocess.run(cmd, input=('\n'.join(lines) + '\n').encode('utf-8'), capture_output=True, timeout=120)
    return p.stdout, p.returncode


orig = ['java', '-Dstdout.encoding=UTF-8', '-jar', f'{blind}/obf.jar']
recon = ['java', '-Dstdout.encoding=UTF-8', '-cp', f'{blind}/recon-classes', 'StoreApp']
paths = {'loyalty bonus applied': 'Sadakat bonusu (%', 'coupon discount applied': 'Kupon indirimi (%',
         'coupon rejected at finalize': 'geçersiz (min tutar', 'tier discount applied': 'Müşteri katmanı (%',
         'quantity discount (5+ units)': '|    5.0% |', 'bad number': 'Geçersiz sayı formatı',
         'unknown tier': 'Bilinmeyen müşteri katmanı', 'loyalty active in report': 'AKTIF (%2 ek indirim)'}
hits = collections.Counter()
same = 0
for _ in range(n):
    s = scenario()
    a, b = run(orig, s), run(recon, s)
    same += a == b
    text = a[0].decode('utf-8')
    for name, marker in paths.items():
        hits[name] += marker in text
print(f'targeted scenarios: {n}  identical: {same}')
for name in paths:
    print(f'  scenarios that reached "{name}": {hits[name]}')
