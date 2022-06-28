import re

c1 = "4fd098298db95b7f1bc205b0a6d8ac15f1f821d72fbfa979d1c2148a24feaafdee8d3108e8ce29c3ce1291"
c2 = "41d9806ec1b55c78258703be87ac9e06edb7369133b1d67ac0960d8632cfb7f2e7974e0ff3c536c1871b"
p1 = "hey let's rob the bank at midnight tonight!"


c1 = [int("0x" + d, base=16) for d in re.split("(..)", c1)[1::2]]
c2 = [int("0x" + d, base=16) for d in re.split("(..)", c2)[1::2]]
p1 = [ord(c) for c in list(p1)]

key = [c ^ p for (c, p) in zip(c1, p1)]

p2 = [chr(c ^ k) for (c, k) in zip(c2, key)]

flag = ''.join(p2)

print(flag)
