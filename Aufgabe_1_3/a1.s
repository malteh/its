	.file	"a1.c"
.globl _bufSize
	.section .rdata,"dr"
	.align 4
_bufSize:
	.long	5
LC0:
	.ascii "1st check:\0"
LC1:
	.ascii "OK\0"
LC2:
	.ascii "not OK\0"
LC3:
	.ascii "buf1:{%s}; len: %d; %s\12\0"
LC4:
	.ascii "buf2:{%s}; len: %d; %s\12\0"
LC5:
	.ascii "2nd check:\0"
LC6:
	.ascii "not \0"
LC7:
	.ascii "\0"
	.align 4
LC8:
	.ascii "buf1:{%s}; len: %d; %schanged\12\0"
LC9:
	.ascii "buf2:{%s}; len: %d\12\0"
	.text
	.p2align 4,,15
.globl _exploit
	.def	_exploit;	.scl	2;	.type	32;	.endef
_exploit:
	pushl	%ebp
	movl	%esp, %ebp
	pushl	%edi
	pushl	%esi
	pushl	%ebx
	subl	$28, %esp
	movl	_bufSize, %edx
	movl	%esp, -16(%ebp)
	addl	$30, %edx
	andl	$-16, %edx
	movl	%edx, %eax
	call	__alloca
	leal	31(%esp), %ebx
	movl	%edx, %eax
	andl	$-16, %ebx
	call	__alloca
	movl	$1094795585, (%ebx)
	leal	31(%esp), %esi
	andl	$-16, %esi
	movw	$65, 4(%ebx)
	movl	$1094795585, (%esi)
	movw	$65, 4(%esi)
	movl	$LC0, (%esp)
	call	_puts
	movl	%ebx, %ecx
	.p2align 4,,15
L4:
	movl	(%ecx), %edi
	addl	$4, %ecx
	leal	-16843009(%edi), %edx
	notl	%edi
	andl	%edi, %edx
	movl	%edx, %eax
	andl	$-2139062144, %eax
	je	L4
	andl	$32896, %edx
	jne	L6
	shrl	$16, %eax
	addl	$2, %ecx
L6:
	addb	%al, %al
	movl	$LC1, %edi
	sbbl	$3, %ecx
	subl	%ebx, %ecx
	cmpl	$5, %ecx
	je	L3
	movl	$LC2, %edi
L3:
	movl	%ebx, %ecx
	.p2align 4,,15
L7:
	movl	(%ecx), %eax
	addl	$4, %ecx
	leal	-16843009(%eax), %edx
	notl	%eax
	andl	%eax, %edx
	movl	%edx, %eax
	andl	$-2139062144, %eax
	je	L7
	andl	$32896, %edx
	jne	L9
	shrl	$16, %eax
	addl	$2, %ecx
L9:
	movl	%edi, 12(%esp)
	addb	%al, %al
	sbbl	$3, %ecx
	subl	%ebx, %ecx
	movl	%ecx, 8(%esp)
	movl	%ebx, 4(%esp)
	movl	$LC3, (%esp)
	call	_printf
	movl	%esi, %ecx
	.p2align 4,,15
L12:
	movl	(%ecx), %edi
	addl	$4, %ecx
	leal	-16843009(%edi), %edx
	notl	%edi
	andl	%edi, %edx
	movl	%edx, %eax
	andl	$-2139062144, %eax
	je	L12
	andl	$32896, %edx
	jne	L14
	shrl	$16, %eax
	addl	$2, %ecx
L14:
	addb	%al, %al
	movl	$LC1, %edi
	sbbl	$3, %ecx
	subl	%esi, %ecx
	cmpl	$5, %ecx
	je	L11
	movl	$LC2, %edi
L11:
	movl	%esi, %ecx
	.p2align 4,,15
L15:
	movl	(%ecx), %eax
	addl	$4, %ecx
	leal	-16843009(%eax), %edx
	notl	%eax
	andl	%eax, %edx
	movl	%edx, %eax
	andl	$-2139062144, %eax
	je	L15
	andl	$32896, %edx
	jne	L17
	shrl	$16, %eax
	addl	$2, %ecx
L17:
	movl	%edi, 12(%esp)
	addb	%al, %al
	sbbl	$3, %ecx
	subl	%esi, %ecx
	movl	%ecx, 8(%esp)
	movl	%esi, 4(%esp)
	movl	$LC4, (%esp)
	call	_printf
	movl	8(%ebp), %ecx
	movl	%esi, (%esp)
	movl	%ecx, 4(%esp)
	call	_strcpy
	movl	$LC5, (%esp)
	call	_puts
	movl	%ebx, %ecx
	.p2align 4,,15
L20:
	movl	(%ecx), %edi
	addl	$4, %ecx
	leal	-16843009(%edi), %edx
	notl	%edi
	andl	%edi, %edx
	movl	%edx, %eax
	andl	$-2139062144, %eax
	je	L20
	andl	$32896, %edx
	jne	L22
	shrl	$16, %eax
	addl	$2, %ecx
L22:
	addb	%al, %al
	movl	$LC6, %edi
	sbbl	$3, %ecx
	subl	%ebx, %ecx
	cmpl	$5, %ecx
	je	L19
	movl	$LC7, %edi
L19:
	movl	%ebx, %ecx
	.p2align 4,,15
L23:
	movl	(%ecx), %eax
	addl	$4, %ecx
	leal	-16843009(%eax), %edx
	notl	%eax
	andl	%eax, %edx
	movl	%edx, %eax
	andl	$-2139062144, %eax
	je	L23
	andl	$32896, %edx
	jne	L25
	shrl	$16, %eax
	addl	$2, %ecx
L25:
	movl	%edi, 12(%esp)
	addb	%al, %al
	sbbl	$3, %ecx
	subl	%ebx, %ecx
	movl	%ecx, 8(%esp)
	movl	%ebx, 4(%esp)
	movl	$LC8, (%esp)
	call	_printf
	movl	%esi, %ecx
	.p2align 4,,15
L26:
	movl	(%ecx), %ebx
	addl	$4, %ecx
	leal	-16843009(%ebx), %edx
	notl	%ebx
	andl	%ebx, %edx
	movl	%edx, %eax
	andl	$-2139062144, %eax
	je	L26
	andl	$32896, %edx
	jne	L28
	shrl	$16, %eax
	addl	$2, %ecx
L28:
	movl	%esi, 4(%esp)
	addb	%al, %al
	sbbl	$3, %ecx
	subl	%esi, %ecx
	movl	%ecx, 8(%esp)
	movl	$LC9, (%esp)
	call	_printf
	movl	-16(%ebp), %esp
	leal	-12(%ebp), %esp
	popl	%ebx
	popl	%esi
	popl	%edi
	popl	%ebp
	ret
	.def	___main;	.scl	2;	.type	32;	.endef
	.section .rdata,"dr"
	.align 4
LC10:
	.ascii "#################################\0"
	.align 4
LC11:
	.ascii "### Welcome to assignment 1.3 ###\0"
LC12:
	.ascii "### please enter some chars:\0"
LC13:
	.ascii "### input was: %s\12\0"
LC14:
	.ascii "### length   : %d\12\0"
	.text
	.p2align 4,,15
.globl _main
	.def	_main;	.scl	2;	.type	32;	.endef
_main:
	pushl	%ebp
	movl	$16, %eax
	movl	%esp, %ebp
	pushl	%ebx
	subl	$1044, %esp
	andl	$-16, %esp
	call	__alloca
	leal	-1032(%ebp), %ebx
	call	___main
	movl	$LC10, (%esp)
	call	_puts
	movl	$LC11, (%esp)
	call	_puts
	movl	$LC10, (%esp)
	call	_puts
	movl	$LC12, (%esp)
	call	_printf
	movl	%ebx, (%esp)
	call	_gets
	movl	%ebx, 4(%esp)
	movl	$LC13, (%esp)
	call	_printf
	movl	%ebx, %ecx
	.p2align 4,,15
L45:
	movl	(%ecx), %eax
	addl	$4, %ecx
	leal	-16843009(%eax), %edx
	notl	%eax
	andl	%eax, %edx
	movl	%edx, %eax
	andl	$-2139062144, %eax
	je	L45
	andl	$32896, %edx
	jne	L47
	shrl	$16, %eax
	addl	$2, %ecx
L47:
	movl	$LC14, (%esp)
	addb	%al, %al
	sbbl	$3, %ecx
	subl	%ebx, %ecx
	movl	%ecx, 4(%esp)
	call	_printf
	movl	$LC10, (%esp)
	call	_puts
	movl	%ebx, (%esp)
	call	_exploit
	movl	$LC10, (%esp)
	call	_puts
	movl	-4(%ebp), %ebx
	xorl	%eax, %eax
	leave
	ret
	.def	_puts;	.scl	2;	.type	32;	.endef
	.def	_strcpy;	.scl	2;	.type	32;	.endef
	.def	_gets;	.scl	2;	.type	32;	.endef
	.def	_printf;	.scl	2;	.type	32;	.endef
