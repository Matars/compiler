def mult(a, b):
    if (b == 1):
        return a
    else:
        return a+mult(a, b-1)


def div(a, b):
    if (a == b):
        return 1
    elif (b > a):
        return 0
    else:
        return 1+div(a-b, b)


def fib(n):
    if (n == 0):
        return 1
    elif (n == 1):
        return 1
    else:
        return fib(n-2)+fib(n-1)


#
# Program entry point - main
#
if __name__ == '__main__':
    p = 3
    q = 13
    res = mult(p, q)
    print(res, end='\n')
    res = div(q, p)
    print(res, end='\n')
    n = 8
    res = fib(8)
    print(res, end='\n')
