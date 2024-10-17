def flip(b):
    if(b): return False
    else: return True
def select(b, bb, bbb):
    return bbb
#
# Program entry point - main
#
if __name__ == '__main__':
    b = True
    bb = flip(b)
    print(bb, end='\n')
    bbb = select(b,bb,b)
    print(bbb, end='\n')
