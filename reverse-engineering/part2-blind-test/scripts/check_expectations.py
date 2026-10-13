"""The test inputs carry their author's expectations as comments ("# Beklenen: ... 950.00").
The tests never check them; this script does. For every expected amount (a number with two
decimals on an expectation line), it reports whether that amount appears in the program's
output for the same test. Usage: check_expectations.py <tests dir> <program jar>"""
import pathlib
import re
import subprocess
import sys

tests_dir, jar = pathlib.Path(sys.argv[1]), sys.argv[2]
EXPECT = re.compile(r'^#\s*(Beklenen|Expected)\b', re.IGNORECASE)
NUMBER = re.compile(r'(?<![\w.])\d+(?:\.\d+)?(?![\w.])')


def result_amount(line):
    """The amount a line expects: the first number after the last '=', or the number in
    "grand total: X". Prose expectations ("2 orders, spend shown") return None.
    The amount is formatted the way the program prints money (two decimals)."""
    if '=' in line:
        numbers = NUMBER.findall(line.rsplit('=', 1)[1])
    else:
        m = re.search(r'grand total\s*:\s*(\d+(?:\.\d+)?)', line, re.IGNORECASE)
        numbers = [m.group(1)] if m else []
    return f'{float(numbers[0]):.2f}' if numbers else None


checked = missing = 0
for test in sorted(tests_dir.glob('*.txt')):
    lines = test.read_text(encoding='utf-8').splitlines()
    expected = [(n, l, result_amount(l)) for n, l in enumerate(lines, 1) if EXPECT.match(l)]
    expected = [(n, l, a) for n, l, a in expected if a]
    if not expected:
        continue
    out = subprocess.run(['java', '-Dstdout.encoding=UTF-8', '-jar', jar], input=test.read_bytes(),
                         capture_output=True, timeout=60).stdout.decode('utf-8')
    for n, line, amount in expected:
        checked += 1
        if amount not in out:
            missing += 1
            print(f'{test.name}:{n}: expected {amount} not in output  |  {line.strip()}')
print(f'expected amounts checked: {checked}, not found in the output: {missing}')
