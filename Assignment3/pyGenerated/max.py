def maxOfTwo(a, b):
    if(a>b):
        return a
    else:
        return b
def maxOfThree(a, b, c):
    if(a>b):
        if(a>c):
            return a
        elif(c>a):
            return c
        else:
            return c
    else:
        if(b>c):
            return b
        elif(c>b):
            return c
        else:
            return c
#
# Program entry point - main
#
if __name__ == '__main__':
    p = 3
    q = 7
    res = maxOfTwo(p,q)
    print(res, end='\n')
    print(res, end='\n')
    res = maxOfThree(p,q,9)
    print(res, end='\n')
