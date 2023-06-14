#include <iostream>
#include <vector>
#include <algorithm>
#include <set>
using namespace std;

int N, M; 
set <string> list;
vector <string> ans;
int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(nullptr);
	cin >> N >> M;
	string name;
	for (int i = 0; i < N; i++) {
		cin >> name;
		list.insert(name);
	}
	for (int i = 0; i < M; i++) {
		cin >> name;
		if (list.erase(name) == 1)
			ans.push_back(name);
	}
	sort(ans.begin(), ans.end());
	cout << ans.size() << "\n";
	for (const auto& str : ans)
		cout << str << "\n";
	return 0;
}