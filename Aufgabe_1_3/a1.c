#include<stdio.h>

void exploit(char *InputString);
const bufSize = 5;

int main(void)
{
	char inputString[1024];
	printf("#################################\n");
	printf("### Welcome to assignment 1.3 ###\n");
	printf("#################################\n");
	printf("### please enter some chars:");
	gets(inputString);
	printf("### input was: %s\n", inputString);
	printf("### length   : %d\n", strlen(inputString));
	printf("#################################\n");
	exploit(inputString);
	printf("#################################\n");
	return 0;
}

void exploit(char *InputString) {
	char buf1[bufSize];
	char buf2[bufSize];
	
	strcpy (buf1,"AAAAA");		/* Initialisierung mit Konstante */
	strcpy (buf2,"AAAAA");		/* Initialisierung mit Konstante */
	
	// prüfen, ob alles OK ist:
	printf("1st check:\n");
	printf("buf1:{%s}; len: %d; %s\n",buf1, strlen(buf1), (strlen(buf1)==bufSize)?"OK":"not OK");
	printf("buf2:{%s}; len: %d; %s\n",buf2, strlen(buf2), (strlen(buf2)==bufSize)?"OK":"not OK");
	
	// kopieren InputString -> buf2
	strcpy (buf2,InputString);
	
	// Test nach dem Kopieren:
	printf("2nd check:\n");
	printf("buf1:{%s}; len: %d; size %schanged\n",buf1, strlen(buf1), (strlen(buf1)==bufSize)?"not ":"");
	printf("buf2:{%s}; len: %d\n",buf2, strlen(buf2));
}
