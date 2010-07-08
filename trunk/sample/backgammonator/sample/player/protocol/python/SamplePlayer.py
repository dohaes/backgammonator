count = [None for i in xrange(25)]
possesion = [None for i in xrange(25)]
temp_board = [None for i in xrange(26)]

hits_mine, hits_opponent, bornoff_mine, bornoff_opponent, die1, die2, status = 0, 0, 0, 0, 0, 0, 0

#reads the input
def read_input():
	#read the board
	for i in xrange(1, 25):
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

# makes the move on the internal board representation 
def do_move(point, die):
	temp_board[point] = temp_board[point] - 1
	end = point - die
	if end < 0:
		end = 0

	if temp_board[end] == -1:
		temp_board[end] = 1
	else:
		temp_board[end] = temp_board[end] + 1

#find the move on the internal representation of the board
def find_move(die):
	if temp_board[25] > 0:
		if temp_board[25 - die] >= -1:
			do_move(25, die)
			print 25, die
			return True
	else:
	    #visit the elements in reversed order from index 24 to 1
		for i in xrange(-24, 0):
			index = - i - die
			if index < 0:
				index = 0
				
			if temp_board[-i] > 0 and temp_board[index] >= -1:
				do_move(i, die)
				print -i, die
				return True
				
	return False

def make_move():
	temp_board[0] = bornoff_mine;
	temp_board[25] = hits_mine;
	
	for i in xrange(1, 25):
		if possesion[i] == 0:
			temp_board[i] = count[i]
		else:
			temp_board[i] = -count[i]

	if find_move(die1) == False:
		if find_move(die2) == False:
			print 0, die2
		print " "
		if find_move(die1) == False:
			print 0, die1
	else:
		print " "
		if find_move(die2) == False:
			print 0, die2

	if die1 == die2:
		print " "
		if find_move(die1) == False:
			print 0, die1
		print " "
		if find_move(die1) == False:
			print 0, die1
			
	print "\n"
	

if __name__=="__main__":
	read_input()
	# while the game is not over
	# read the input and make the move
	while status == 0:
		make_move()
		read_input()

