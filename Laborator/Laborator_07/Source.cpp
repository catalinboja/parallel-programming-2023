#include <iostream>
#include <string>
#include <thread>
#include <vector>
#include <mutex>

using namespace std;

//definire mutex global
mutex mutexGlobal;

void hello() {
	printf("\n Hello !");
}
void helloCuId(int id) {
	printf("\n Hello de la thread-ul %d!", id);
}

void suma(int a, int b, int& rezultat) {
	rezultat = a + b;
}

void increment(int& contor, int nrIteratii) {
	for (int i = 0; i < nrIteratii; i++) {
		//se distruge automat la iesire din context - for
		//lock_guard<mutex> lock(mutexGlobal);
		mutexGlobal.lock();
		contor += 1;
		mutexGlobal.unlock();
	}
}

void incrementCuMutexLocal(int& contor, int nrIteratii, mutex& semafor) {
	for (int i = 0; i < nrIteratii; i++) {
		semafor.lock();
		contor += 1;
		semafor.unlock();
	}
}

void incrementCuAtomic(atomic<int>& contor, int nrIteratii) {
	for (int i = 0; i < nrIteratii; i++) {
		contor += 1;
	}
}

int main() {
	thread t1(hello);
	thread t2(helloCuId, 2);
	thread t3(helloCuId, 3);
	int rez = 0;
	thread t4(suma, 2, 3, ref(rez));

	t1.join();
	t2.join();
	t3.join();
	t4.join();

	printf("\n Rezultat suma: %d", rez);

	//vector de thread-uri
	vector<thread> threaduri;
	for (int i = 0; i < 4; i++) {
		threaduri.push_back(thread(helloCuId, 100 + i));
	}

	for (thread &t : threaduri) {
		t.join();
	}

	//test race condition
	int contor = 0;
	thread tContor1(increment,ref(contor), 100000);
	thread tContor2(increment, ref(contor), 100000);

	tContor1.join();
	tContor2.join();

	printf("\n Valoare contor = %d", contor);


	//test atomic int
	atomic<int> contorAtomic = 0;
	thread tContorAtomic1(incrementCuAtomic, 
		ref(contorAtomic), 100000);
	thread tContorAtomic2(incrementCuAtomic, 
		ref(contorAtomic), 100000);

	tContorAtomic1.join();
	tContorAtomic2.join();

	printf("\n Valoare contor = %d", contorAtomic.load());

	printf("\n The end");
}
