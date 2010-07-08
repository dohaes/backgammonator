count = [None for i in xrange(25)]
possesion = [None for i in xrange(25)]
temp_board = [None for i in xrange(26)]

hits_mine, hits_opponent, bornoff_mine, bornoff_opponent, die1, die2, status = 0, 0, 0, 0, 0, 0, 0

#reads the input
def read_input():
	#read the board
	for i in xrange(1, 25)):
		count[i] = int(raw_input())
		possesion[i] = int(raw_input())

	#read hits and bornoffs
	hits_mine = int(raw_input())
	hits_opponent = int(raw_input())
	bornoff_mine = int(raw_input())
	bornoff_opponent = int(raw_input())
	
	#read the dice
	die1 = int(raw_input())
	die2 = int(raw_input())
	
	#read the status
    status = int(raw_input())

}

if __name__=="__main__":
	#read the input
	read_input()
	while status == 0: 
	    #make the invalid move
		print "24 7 24 1\n"
		read_input()
