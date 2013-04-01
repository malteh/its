CC = gcc
CFLAGS = -O3
a1: a1.c
	$(CC) $(CFLAGS) -o a1 a1.c
a1-asm: a1.c
	$(CC) $(CFLAGS) -S -o a1.s a1.c
printWordSize: printWordSize.c
	$(CC) -o printWordSize printWordSize.c
