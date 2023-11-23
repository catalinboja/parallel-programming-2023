#include <iostream>
#include <string>
#include <omp.h>
#include <thread>
#include <random>
#include <vector>

using namespace std;


void benchmark(string mesaj, int (*pf)(int*, long long), int* v, long long N) {
	printf("\n Test %s", mesaj.c_str());
	double tStart = omp_get_wtime();
	int rezultat = pf(v, N);
	double tFinal = omp_get_wtime();
	printf("\n Rezultat = %d", rezultat);
	printf("\n Durata %f secunde", tFinal - tStart);
}

int* generareVector(long long N) {
	//srand(100);
	int* valori = new int[N];
	for (long long i = 0; i < N; i++) {
		valori[i] = i+1;
	}
	return valori;
}

int solutieSecventialaNumerePare(int* v, long long N) {
	int contor = 0;
	for (long long i = 0; i < N; i++) {
		double temp = v[i] / 3;
		temp = v[i] / 5;
		if (v[i] % 2 == 0) {
			contor += 1;
		}
	}
	return contor;
}

void calculNumerePareCuPas(int* v, long long N, 
			int indexStart, int pas, int& rezultat ) {
	for (long long i = indexStart; i < N; i += pas) {
		double temp = v[i] / 3;
		temp = v[i] / 5;
		if (v[i] % 2 == 0) {
			rezultat += 1;
		}
	}
}

int solutieParalelaCuCacheFalseSharing(int* v, long long N) {
	int nrThreaduri = omp_get_num_procs();
	int* rezultate = new int[nrThreaduri];
	for (int i = 0; i < nrThreaduri; i++) {
		rezultate[i] = 0;
	}
	vector<thread> threaduri;
	for (int i = 0; i < nrThreaduri; i++) {
		threaduri.push_back(
			thread(calculNumerePareCuPas, v, N, i, nrThreaduri, ref(rezultate[i]))
		);
	}
	for (auto& t : threaduri) {
		t.join();
	}
	int rezultatFinal = 0;
	for (int i = 0; i < nrThreaduri; i++) {
		rezultatFinal += rezultate[i];
	}
	return rezultatFinal;
}

int solutieParalelaFaraCacheFalseSharing(int* v, long long N) {
	int nrThreaduri = omp_get_num_procs();
	int* rezultate = new int[nrThreaduri * 1000];
	for (int i = 0; i < nrThreaduri; i++) {
		rezultate[i * 1000] = 0;
	}
	vector<thread> threaduri;
	for (int i = 0; i < nrThreaduri; i++) {
		threaduri.push_back(
			thread(calculNumerePareCuPas, v, N, i, nrThreaduri, ref(rezultate[i*1000]))
		);
	}
	for (auto& t : threaduri) {
		t.join();
	}
	int rezultatFinal = 0;
	for (int i = 0; i < nrThreaduri; i++) {
		rezultatFinal += rezultate[i*1000];
	}
	return rezultatFinal;
}

int main() {

	alignas(1024) int vb;
	alignas(1024) int vb2;

	printf("\n Start generare numere");
	const long long N = 2e9;
	int* valori = generareVector(N);

	printf("\n Start teste");
	benchmark("Solutie secventiala", solutieSecventialaNumerePare, valori, N);
	benchmark("Solutie paralela", solutieParalelaCuCacheFalseSharing, valori, N);
	benchmark("Solutie paralela", solutieParalelaFaraCacheFalseSharing, valori, N);


}
