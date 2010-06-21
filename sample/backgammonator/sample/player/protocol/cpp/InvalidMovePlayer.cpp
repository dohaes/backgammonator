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

int main() {

	readInput();
	while (status == 0) {
		cout << "24 7 24 1\n";
		readInput();
	}
	return 0;
}
