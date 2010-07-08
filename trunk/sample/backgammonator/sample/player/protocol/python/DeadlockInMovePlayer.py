import time
import thread
from threading import *

cond1=Condition()
cond2=Condition()

def deadlock(cond1_, cond2_):
    cond1_.acquire()
    time.sleep(1)
    # both threads should end up locked here
    cond2_.acquire()
    cond2_.release()
    cond1_.release()
	
if __name__=="__main__":
    thread.start_new_thread(deadlock,(cond1, cond2))
    deadlock(cond2, cond1)
	