def mult(a, n, b):
    return a*b
def max(a, b):
    if(a>b): return a
    else: return b
#
# Program entry point - main
#
if __name__ == '__main__':
    f = 2.34
    ff = 2.0
    ffff = 4.0
    fff = mult(f,5,ff)
    print(fff, end='\n')
    fff = max(f,ff)
    print(fff, end='\n')
