"""Generate random command scripts for the store program and compare the obfuscated original
with the reconstruction, byte for byte. Usage: compare_blind.py <blind dir> <n scripts>"""
import random
import subprocess
import sys

blind = sys.argv[1]
n_scripts = int(sys.argv[2])
rng = random.Random(4242)

TIERS = ['REGULAR', 'SILVER', 'GOLD', 'PLATINUM', 'gold', 'Diamond']


def num(lo, hi, decimals=2):
    r = rng.random()
    if r < 0.03:
        return 'abc'
    if r < 0.06:
        return '-5'
    return f'{rng.uniform(lo, hi):.{decimals}f}'


def script(length):
    cats, prods, custs, orders, coups = ['ELEK', 'GIDA'], [], [], [], []
    lines = ['# test', 'ADD_CATEGORY ELEK Elektronik 10', 'ADD_CATEGORY GIDA Gida 0']
    for _ in range(length):
        r = rng.random()
        pick = lambda xs, fallback: rng.choice(xs) if xs and rng.random() < 0.9 else fallback
        if r < 0.05:
            c = rng.choice(['ELEK', 'GIDA', 'KIT', 'OYUN'])
            lines.append(f'ADD_CATEGORY {c} {c.title()} {num(0, 30, 1)}')
            cats.append(c)
        elif r < 0.08:
            lines.append(f'SET_BULK_DISCOUNT {pick(cats, "YOK")} {num(0, 500)} {num(0, 20, 1)}')
        elif r < 0.16:
            p = f'P{rng.randint(1, 12)}'
            lines.append(f'ADD_PRODUCT {p} Urun{p} {pick(cats, "YOK")} {num(1, 300)} {rng.randint(0, 30)}')
            prods.append(p)
        elif r < 0.19:
            lines.append(f'RESTOCK {pick(prods, "P99")} {rng.choice([5, 10, 0, -2])}')
        elif r < 0.22:
            lines.append(f'UPDATE_PRICE {pick(prods, "P99")} {num(1, 300)}')
        elif r < 0.28:
            c = f'C{rng.randint(1, 6)}'
            lines.append(f'ADD_CUSTOMER {c} Musteri{c} {rng.choice(TIERS)}')
            custs.append(c)
        elif r < 0.30:
            lines.append(f'UPGRADE_TIER {pick(custs, "C99")} {rng.choice(TIERS)}')
        elif r < 0.33:
            k = f'K{rng.randint(1, 5)}'
            lines.append(f'ADD_COUPON {k} {num(1, 30, 1)} {num(0, 400)}')
            coups.append(k)
        elif r < 0.36:
            k = f'L{rng.randint(1, 4)}'
            lines.append(f'ADD_COUPON_LIMITED {k} {num(1, 30, 1)} {num(0, 200)} {rng.choice([1, 2, 0])}')
            coups.append(k)
        elif r < 0.46:
            o = f'O{rng.randint(1, 25)}'
            lines.append(f'CREATE_ORDER {o} {pick(custs, "C99")}')
            orders.append(o)
        elif r < 0.62:
            lines.append(f'ADD_ITEM {pick(orders, "O99")} {pick(prods, "P99")} {rng.choice([1, 2, 3, 5, 6, 10, 0])}')
        elif r < 0.67:
            lines.append(f'APPLY_COUPON {pick(orders, "O99")} {pick(coups, "K99")}')
        elif r < 0.78:
            lines.append(f'FINALIZE_ORDER {pick(orders, "O99")}')
        elif r < 0.81:
            lines.append(f'CANCEL_ORDER {pick(orders, "O99")}')
        elif r < 0.93:
            cmd = rng.choice(['LIST_CATEGORIES', 'LIST_PRODUCTS', 'LIST_CUSTOMERS', 'LIST_ORDERS', 'LIST_COUPONS',
                              'STOCK_REPORT', f'ORDER_DETAIL {pick(orders, "O99")}', f'CUSTOMER_REPORT {pick(custs, "C99")}'])
            lines.append(cmd.lower() if rng.random() < 0.1 else cmd)
        else:
            lines.append(rng.choice(['ADD_ITEM O1', 'FOO bar', '', '   ', 'ADD_PRODUCT P1 X ELEK 1,5 3',
                                     'FINALIZE_ORDER', 'ADD_CUSTOMER C1 Ali']))
    return lines


def run(cmd, lines):
    p = subprocess.run(cmd, input=('\n'.join(lines) + '\n').encode('utf-8'), capture_output=True, timeout=120)
    return p.stdout, p.returncode


orig = ['java', '-jar', f'{blind}/obf.jar']
recon = ['java', '-cp', f'{blind}/recon-classes', 'StoreApp']
same, mismatches, total_lines = 0, [], 0
for i in range(n_scripts):
    lines = script(rng.choice([30, 120, 400]))
    total_lines += len(lines)
    a, b = run(orig, lines), run(recon, lines)
    if a == b:
        same += 1
    else:
        mismatches.append((i, lines, a, b))
print(f'scripts: {n_scripts}  commands: {total_lines}  identical: {same}  mismatches: {len(mismatches)}')
for i, lines, a, b in mismatches[:2]:
    al, bl = a[0].decode('utf-8', 'replace').splitlines(), b[0].decode('utf-8', 'replace').splitlines()
    for j, (x, y) in enumerate(zip(al, bl)):
        if x != y:
            print(f'script {i} line {j}\n  orig : {x}\n  recon: {y}')
            break
    else:
        print(f'script {i}: line counts {len(al)} vs {len(bl)}, exit {a[1]} vs {b[1]}')

# Positive control: two different scripts must not compare equal
x, y = script(50), script(50)
print('positive control (different scripts differ):', run(orig, x) != run(recon, y))
