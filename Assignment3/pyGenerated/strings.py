def countA(str):
    n = -5
    i = 0
    while(i<len(str)):
        c = str[i]
        if(c=='A'): n = n+1
        i = i+1
    return n
def replace(str, oldChar, newChar):
    i = 0
    while(i<len(str)):
        c = str[i]
        if(c==oldChar): str[i] = newChar
        i = i+1
    return str
def printArray(arr):
    print(arr[0])
    i = 1
    while(i<len(arr)):
        c = arr[i]
        print(" ")
        print(c)
        i = i+1
    print(" ", end='\n')
def containsXandY(str):
    if(contains(str,'X')): 
        if(contains(str,'Y')): return True
    return False
def contains(str, ch):
    i = 0
    while(i<len(str)):
        p = str[i]
        if(p==ch): return True
        i = i+1
    return False
#
# Program entry point - main
#
if __name__ == '__main__':
    s = "All students got the highest grade A!"
    res = countA(s)
    print(res, end='\n')
    charArr = ['a','b','c','d','e','f']
    charArr = replace(charArr,'b','B')
    printArray(charArr)
    xy = "Xerxes said Yes!"
    ok = containsXandY(xy)
    print(ok, end='\n')
