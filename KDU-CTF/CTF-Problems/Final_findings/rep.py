from tqdm import tqdm
import multiprocessing
import numpy as np

orig = b'\xd1\x85\xc1\xfau( \x94\x9coT\xbc\xd5\xd1\xce\x89L\xb6A\xea|W^;V\xd3b\xb6\xc6\xfd\xf2\x17'

G = {
    'buffer': list(range(0x100)),
    'b7': 0,
    'u5': 1,
    'base': list(orig),
    'u12e': 0,
    'u28': 0,
    'u134': 0,
    'u12f': 0
}

def init():
    G['buffer'] = list(range(0x100))
    G['b7'] = 0
    G['u5'] = 1
    G['base'] = list(orig)
    G['u12e'] = 0
    G['u28'] = 0
    G['u134'] = 0
    G['u12f'] = 0

def f32_outer_shuffle(v):
    f33_shuffle(v)
    G['u5'] += 2
    
def f33_shuffle(v):
    for i in range(v):
        f34_inner_shuffle()

def f34_inner_shuffle():
    G['u12e'] = (G['u12e'] + G['u5']) & 0xff
    v = (G['buffer'][G['u12e']] + G['u28']) & 0xff
    G['u28'] = (G['buffer'][v] + G['u134']) & 0xff
    G['u134'] = G['buffer'][G['u28'] & 0xff] + G['u134'] + G['u12e']
    f39_swap_idx(G['u28'], G['u12e'])
    
        
def f35_sort():
    f36_inner_sort(0x80)

def f36_inner_sort(v):
    for i in range(v,-1,-1):
        f37(i)
        
def f37(v):
    if G['buffer'][v] > G['buffer'][0xff - v]:
        f38(v)
        
def f38(v):
    f39_swap_idx(v, 0xff-v)

def f31_shuffle_sort():
    f32_outer_shuffle(0x200)
    f35_sort()
    f32_outer_shuffle(0x200)
    f35_sort()
    f32_outer_shuffle(0x200)
    G['b7'] = 0

def f30_update_shuffle_keys():
    v = (G['u134'] + G['u12f']) & 0xff
    v2 = (G['buffer'][v] + G['u12e']) & 0xff
    v3 = (G['buffer'][v2] + G['u28']) & 0xff
    G['u12f'] = G['buffer'][v3]
    return G['u12f']
    
def f39_swap_idx(a,b):
    G['buffer'][a], G['buffer'][b] = G['buffer'][b], G['buffer'][a]

def f28(v):
    if G['b7'] == 0x80:
        f31_shuffle_sort()
    
    f39_swap_idx(G['b7'], v + 0x80)
    G['b7'] += 1

def f27(v):
    f28(v & 0xf)
    f28(v >> 4)

def f8_shuffle(ip):
    f27(ip >> 24)
    f27((ip >> 16) & 0xff)
    f27((ip >> 8) & 0xff)
    f27((ip >> 0) & 0xff)

# returns a value
def f29():
    if G['b7'] != 0:
        f31_shuffle_sort()
    f34_inner_shuffle()
    return f30_update_shuffle_keys()
    
    
def f9():
    v = 0xa00 + f29()
    v *= 0x100
    v += f29()
    v *= 0x100
    v += f29()
    return v
    
def f4():
    for i in range(32):
        f29()
    
def run(ip, c1=(b'\x00'*32), c2=(b'\x00'*32)):
    init()
    f8_shuffle(ip)
    i1 = f9()
    G['base'] = [a^b for a,b in zip(G['base'], c1)]
    i2 = f9()
    G['base'] = [a^b for a,b in zip(G['base'], c2)]
    
    out = [x ^ f29() for x in G['base']]
    
    return i1, i2, out

def _run(package):
    return package[0], run(*package)

SZ = 1 << 24

with multiprocessing.Pool() as p:
    q = [(0xa000000 + i,) for i in range(SZ)]

    targets = np.zeros((SZ, 2), dtype=np.uint32)
    buffer = np.zeros((SZ, 32), dtype=np.uint8)

    prog = tqdm(total=SZ)

    for out in p.imap_unordered(_run, q):
        prog.update(1)

        ip, res = out
        a,b,buf = res

        idx = ip - 0xa000000
        targets[idx][0] = a
        targets[idx][1] = b
        buffer[idx] = buf

    prog.close()

    np.save('./targets.npy', targets)
    np.save('./buffer.npy', buffer)
