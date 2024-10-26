def sumUpTo(n):
    i = 1
    sum = 0
    while (i < n+1):
        sum = sum+i
        i = i+1
    return sum


#
# Program entry point - main
#
if __name__ == '__main__':
    n = 10
    res = sumUpTo(n)
    print(res, end='\n')
