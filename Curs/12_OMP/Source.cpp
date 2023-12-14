#include <iostream>
#include <string>
#include <omp.h>
using namespace std;

//suma elemente vector
int suma(int* valori, int N) {
	int s = 0;
	for (int i = 0; i < N; i++)
	{
		s += valori[i];
		int temp = valori[i] / 3;
	}

	return s;
}

int sumaParalela(int* valori, int N) {
	int s = 0;

#pragma omp parallel for reduction(+: s)
	for (int i = 0; i < N; i++)
	{
		s+= valori[i];
		int temp = valori[i] / 3;
	}

	return s;
}

bool estePrim(long numar) {
	bool prim = true;
	for (long i = 2; i <= numar / 2; i++) {
		if (numar % i == 0)
		{
			prim = false;
			break;
		}
	}
	return prim;
}

int solutieSecventiala(long start, long final) {
	int numarPrime = 0;
	for (long valoare = start; valoare < final; valoare += 1) {
		if (estePrim(valoare))
			numarPrime += 1;
	}
	return numarPrime;
}

int solutieParalela(long start, long final) {
	int numarPrime = 0;

#pragma omp parallel for reduction(+: numarPrime) schedule(guided)
	for (long valoare = start; valoare < final; valoare += 1) {
		if (estePrim(valoare))
			numarPrime += 1;
	}
	return numarPrime;
}

void benchmark(string test, int (*pf)(int*, int), int* valori, int N) {
	printf("\n Test pentru %s", test.c_str());
	double tStart = omp_get_wtime();
	int rezultat = pf(valori, N);
	double tFinal = omp_get_wtime();
	printf("\n Rezultat %d in %f secunde", rezultat, tFinal - tStart);
}


int main() {
	//const int N = 1e9;
	//int* valori = new int[N];
	//for (int i = 0; i < N; i++) {
	//	valori[i] = 1;
	//}

	//benchmark("solutie secventiala", suma, valori, N);
	//benchmark("solutie paralela", sumaParalela, valori, N);


	//barrier si nowait
#pragma omp parallel
	{
		printf("\n Thread %d a pornit ", omp_get_thread_num());

#pragma omp for nowait
		for (int i = 0; i < 50; i++) {
			printf("\n ** Thread %d prelucreaza iteratia %d",
				omp_get_thread_num(), i);
		}//bariera implicita fara nowait

#pragma omp single
		{
			printf("\n ########### Thread %d prelucreaza zona single",
				omp_get_thread_num());
		}

		printf("\n ************ Thread %d a iesit din for ", omp_get_thread_num());
	}


#pragma omp parallel sections
	{
#pragma omp section
		{
			printf("\n $$$$$$$$ Thread %d prelucreaza sectiunea #1",
				omp_get_thread_num());
			for (int i = 0; i < 10000; i++) {
				int temp = 10 / 3;
			}
		}
#pragma omp section
		{
			printf("\n $$$$$$$$ Thread %d prelucreaza sectiunea #2",
				omp_get_thread_num());
		}
#pragma omp section
		{
			printf("\n $$$$$$$$ Thread %d prelucreaza sectiunea #3",
				omp_get_thread_num());
		}
	}
}