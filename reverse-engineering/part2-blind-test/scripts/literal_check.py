"""Compare every string literal in the decompiled source with the reconstruction.

A literal present in one set but not the other is either a decompiler artefact (enum names,
synthetic code) or a retyping mistake, and each one is listed so it can be judged."""
import collections
import pathlib
import re
import sys

LIT = re.compile(r'"((?:[^"\\]|\\.)*)"')


def literals(folder):
    found = collections.Counter()
    for path in pathlib.Path(folder).glob('*.java'):
        text = path.read_text(encoding='utf-8')
        text = re.sub(r'//[^\n]*', '', text)          # line comments may contain quotes
        text = re.sub(r'/\*.*?\*/', '', text, flags=re.S)
        for lit in LIT.findall(text):
            # \uXXXX escapes and the characters they stand for are the same string to javac
            found[re.sub(r'\\u([0-9a-fA-F]{4})', lambda m: chr(int(m.group(1), 16)), lit)] += 1
    return found


obf, recon = literals(sys.argv[1]), literals(sys.argv[2])
only_obf = sorted(set(obf) - set(recon))
only_recon = sorted(set(recon) - set(obf))
print(f'distinct literals: decompiled={len(obf)} reconstruction={len(recon)}')
print('only in decompiled   :', only_obf)
print('only in reconstruction:', only_recon)
