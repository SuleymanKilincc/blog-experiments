"""List comment lines that still contain Turkish letters, ignoring the program's own strings."""
import pathlib
import re
import sys

TURKISH = re.compile('[çğıöşüÇĞİÖŞÜ]')
STRING = re.compile(r'"(?:[^"\\]|\\.)*"')
bad = []
for path in sorted(pathlib.Path(sys.argv[1]).glob('*.java')):
    for number, line in enumerate(path.read_text(encoding='utf-8').split('\n'), 1):
        code = STRING.sub('""', line)
        is_comment = re.match(r'^\s*(//|\*|/\*\*)', code) or '//' in code
        if is_comment and TURKISH.search(code):
            bad.append(f'{path.name}:{number}: {line.strip()}')
print('comment lines with Turkish letters:', len(bad))
print('\n'.join(bad[:10]))
