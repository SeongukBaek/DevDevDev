#include <iostream>
#include <algorithm>
#include <vector>;
using namespace std;

int find_num(int n);

int N, M; 
vector <int> arr;
int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(nullptr);
	cin >> N;
	int n;
	for (int i = 0; i < N; i++) {
		cin >> n;
		arr.push_back(n);
	}
	sort(arr.begin(), arr.end());

	cin >> M;
	for (int i = 0; i < M; i++) {
		cin >> n;
		cout << find_num(n) << "\n";
	}

	return 0;
}

int find_num(int n) {
	int low = 0 , high = N - 1, ret = 0;

	while (low <= high) {
		int mid = (low + high) / 2;
		if (arr[mid] < n)
			low = mid + 1;
		else if (arr[mid] > n)
			high = mid - 1;
		else if (arr[mid] == n) {
			ret = 1;
			break;
		}
	}
	return ret;
}