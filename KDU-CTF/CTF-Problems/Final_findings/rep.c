
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

unsigned char orig[] = {209, 133, 193, 250, 117, 40, 32, 148, 156, 111, 84, 188, 213, 209, 206, 137, 76, 182, 65, 234, 124, 87, 94, 59, 86, 211, 98, 182, 198, 253, 242, 23};

unsigned char buffer[0x100];
unsigned char base[32];
uint8_t b7 = 0;
uint8_t u5 = 0;
uint8_t u12e = 0;
uint8_t u28 = 0;
uint8_t u134 = 0;
uint8_t u12f = 0;


struct res {
    uint32_t t1;
    uint32_t t2;
    uint8_t out[32];
};

void init() {
    for (int i = 0; i < 0x100; ++i) {
        buffer[i] = i;
    }
    b7 = 0;
    u5 = 1;
    memcpy(base, orig, 32);
    u12e = 0;
    u28 = 0;
    u134 = 0;
    u12f = 0;
}

void f39_swap_idx(int a, int b) {
    uint8_t t = buffer[a];
    buffer[a] = buffer[b];
    buffer[b] = t;
}

void f34_inner_shuffle() {
    u12e = (u12e + u5) & 0xff;
    uint8_t v = (buffer[u12e] + u28) & 0xff;
    u28 = (buffer[v] + u134) & 0xff;
    u134 = buffer[u28] + u134 + u12e;
    f39_swap_idx(u28, u12e);
}

void f38(int v) {
    f39_swap_idx(v, 0xff-v);
}

void f37(int v) {
    if (buffer[v] > buffer[0xff - v]) {
        f38(v);
    }
}

void f36_inner_sort(int v) {
    for (int i = v; i >= 0; --i) {
        f37(i);
    }
}

void f35_sort() {
    f36_inner_sort(0x80);
}

void f33_shuffle(int v) {
    for (int i = 0; i < v; ++i) {
        f34_inner_shuffle();
    }
}

void f32(int v) {
    f33_shuffle(v);
    u5 += 2;
}

void f32_outer_shuffle(int v) {
    f33_shuffle(v);
    u5 += 2;
}

void f31_shuffle_sort() {
    f32_outer_shuffle(0x200);
    f35_sort();
    f32_outer_shuffle(0x200);
    f35_sort();
    f32_outer_shuffle(0x200);
    b7 = 0;
}

uint8_t f30_update_shuffle_keys() {
    uint8_t v = (u134 + u12f) & 0xff;
    uint8_t v2 = (buffer[v] + u12e) & 0xff;
    uint8_t v3 = (buffer[v2] + u28) & 0xff;
    u12f = buffer[v3];
    return u12f;
}

void f28(int v) {
    if (b7 == 0x80) {
        f31_shuffle_sort();
    }
    f39_swap_idx(b7, v + 0x80);
    b7 += 1;
}

void f27(int v) {
    f28(v & 0xf);
    f28(v >> 4);
}

void f8_shuffle(uint32_t ip) {
    f27(ip >> 24);
    f27((ip >> 16) & 0xff);
    f27((ip >> 8) & 0xff);
    f27((ip >> 0) & 0xff);
}

uint8_t f29() {
    if (b7 != 0)
        f31_shuffle_sort();
    f34_inner_shuffle();
    return f30_update_shuffle_keys();
}

uint32_t f9() {
    uint32_t v = 0xa00 + f29();
    v *= 0x100;
    v += f29();
    v *= 0x100;
    v += f29();
    return v;
}

struct res run(uint32_t ip, uint8_t c1[32], uint8_t c2[32]) {
    init();

    struct res result;

    f8_shuffle(ip);
    result.t1 = f9();

    for (int i = 0; i < 32; ++i) {
        base[i] ^= c1[i];
    }

    result.t2 = f9();
    
    for (int i = 0; i < 32; ++i) {
        base[i] ^= c2[i];
    }
    
    for (int i = 0; i < 32; ++i) {
        result.out[i] = (base[i] ^ f29());
    }

    return result;
}

unsigned int SZ = 1 << 24;

int main() {
    FILE *targets = fopen("targets.dat", "wb");
    FILE *buffer = fopen("buffer.dat", "wb");

    uint8_t c1[32] = {0};
    uint8_t c2[32] = {0};

    for (int i = 0; i < SZ; ++i) {
        struct res result = run(0xa000000 + i, c1, c2);

        fwrite(&result.t1, 4, 1, targets);
        fwrite(&result.t2, 4, 1, targets);
        fwrite(&result.out, 32, 1, buffer);

        if (i % (1 << 16) == 0) {
            printf("%d\n", i);
        }
    }

    fclose(targets);
    fclose(buffer);

    printf("done\n");
}
