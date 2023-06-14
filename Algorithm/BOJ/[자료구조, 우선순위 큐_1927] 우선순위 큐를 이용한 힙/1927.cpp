#include <iostream>
#include <vector>
#include <queue>
using namespace std;

priority_queue <int, vector<int>, greater<int>> arr;
int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL); cout.tie(NULL);
	int N = 0;
	cin >> N;

	int n = 0;
	for (int i = 0; i < N; i++) {
		cin >> n;
		if (n == 0) {
			if (arr.size() == 0)
				cout << 0 << "\n";
			else {
				cout << arr.top() << "\n";
				arr.pop();
			}
		}
		else
			arr.push(n);
	}
	return 0;
}