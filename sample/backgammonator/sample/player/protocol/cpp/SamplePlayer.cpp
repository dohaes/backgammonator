#include<iostream>

using namespace std;

int count[25], possesion[25], temp_board[26];
int hits_mine, hits_opponent, bornoff_mine, bornoff_opponent, die1, die2, status;

void readInput() {
	for (int i = 1; i < 25; i++) {
		cin >> count[i];
		cin >> possesion[i];
	}

	cin >> hits_mine >> hits_opponent >> bornoff_mine >> bornoff_opponent;
	cin >> die1 >> die2;
	cin >> status;
}

void doMove(int point, int die) {
	temp_board[point]--;
	int end = point - die;
	if (end < 0) {
		end = 0;
	}
	if (temp_board[end] == -1) {
		temp_board[end] = 1;
	} else {
		temp_board[end]++;
	}
}

bool findMove(int die) {
	if (temp_board[25] > 0) {
		if (temp_board[25 - die] >= -1) {
			doMove(25, die);
			cout << "25 " << die;
			return true;
		}
	} else {
		for (int i = 24; i >= 1; i--) {
			if (temp_board[i] > 0 && temp_board[(i - die < 0) ? 0 : i - die]
					>= -1) {
				doMove(i, die);
				cout << i << " " << die;
				return true;
			}
		}
	}
	return false;
}

void makeMove() {
	temp_board[0] = bornoff_mine;
	temp_board[25] = hits_mine;
	for (int i = 1; i < 25; i++) {
		temp_board[i] = count[i] * (possesion[i] == 0 ? 1 : -1);
	}
	if (!findMove(die1)) {
		if (!findMove(die2)) {
			cout << "0 " << die2;
		}
		cout << " ";
		if (!findMove(die1)) {
			cout << "0 " << die1;
		}
	} else {
		cout << " ";
		if (!findMove(die2)) {
			cout << "0 " << die2;
		}
	}

	if (die1 == die2) {
		cout << " ";
		if (!findMove(die1)) {
			cout << "0 " << die1;
		}
		cout << " ";
		if (!findMove(die1)) {
			cout << "0 " << die1;
		}
	}
	cout << "\n";
}

int main() {

	readInput();
	while (status == 0) {
		makeMove();
		readInput();
	}
	return 0;
}
