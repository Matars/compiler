def reverse(arr):
    sz = len(arr)
    toReturn = [0] * sz
    i = 0
    n = 2
    while(i<sz):
        n = arr[sz-i-1]
        toReturn[i] = n
        i = i+1
    return toReturn
def printArray(arr):
    print(arr[0])
    i = 0
    while(i<len(arr)):
        n = arr[i]
        print(" ")
        print(n)
        i = i+1
    print(" ", end='\n')
def findMax(f):
    max = f[0]
    i = 1
    while(i<len(f)):
        p = f[i]
        if(p>max): 
            max = p
        i = i+1
    return max
def selectionSort(arr):
    i = 0
    while(i<len(arr)-1):
        smallest = i
        j = i+1
        while(j<len(arr)):
            if(arr[j]<arr[smallest]): 
                smallest = j
            j = j+1
        smallestNumber = arr[smallest]
        arr[smallest] = arr[i]
        arr[i] = smallestNumber
        i = i+1
#
# Program entry point - main
#
if __name__ == '__main__':
    a = [0] * 10
    i = 0
    n = 2
    while(i<len(a)):
        a[i] = n
        n = 2*n
        i = i+1
    rev = reverse(a)
    printArray(rev)
    f = [9.3,8.8,17.5,1.0,2.1,3.14,4.4]
    print(f[0], end='\n')
    max = findMax(f)
    print(max, end='\n')
    selectionSort(f)
    print(f[len(f)-1], end='\n')
