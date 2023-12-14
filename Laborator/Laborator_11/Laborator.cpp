#include <iostream>
#include <string>
#include <omp.h>
using namespace std;

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

int solutieParalelaCuAtomic(long start, long final) {
	int numarPrime = 0;

#pragma omp parallel
	for (long valoare = start + omp_get_thread_num();
		valoare < final;
		valoare += omp_get_num_threads()) {
		if (estePrim(valoare))
#pragma omp atomic
			numarPrime += 1;
	}
	return numarPrime;
}

int solutieParalelaCuPrivate(long start, long final) {

	int numarPrime = 0;
	int contorLocal = 0;

#pragma omp parallel firstprivate(contorLocal)
	{
		for (long valoare = start + omp_get_thread_num();
			valoare < final;
			valoare += omp_get_num_threads()) {
			if (estePrim(valoare))
				contorLocal += 1;
		}
#pragma omp atomic
		numarPrime += contorLocal;
	}

	return numarPrime;
}

int solutieParalelaCuPrivateSiLoadBalancing(long start, long final) {

	int numarPrime = 0;
	int contorLocal = 0;

#pragma omp parallel firstprivate(contorLocal)
	{
		double tStart = omp_get_wtime();

		for (long valoare = start + omp_get_thread_num() * 2;
			valoare < final;
			valoare += 2 * omp_get_num_threads()) {
			if (estePrim(valoare))
				contorLocal += 1;
		}
#pragma omp atomic
		numarPrime += contorLocal;

		double tFinal = omp_get_wtime();
		printf("\n Durata thread %d = %f",
			omp_get_thread_num(), (tFinal - tStart));
	}

	return numarPrime;
}

int solutieParalelaCuFor(long start, long final) {
	int numarPrime = 0;

#pragma omp parallel for
	for (long valoare = start; valoare < final; valoare += 1) {
		if (estePrim(valoare))
#pragma omp atomic
			numarPrime += 1;
	}
	return numarPrime;
}

int solutieParalelaCuForScheduleDynamic(long start, long final) {
	int numarPrime = 0;

#pragma omp parallel for schedule(dynamic)
	for (long valoare = start; valoare < final; valoare += 1) {
		if (estePrim(valoare))
#pragma omp atomic
			numarPrime += 1;
	}
	return numarPrime;
}

int solutieParalelaCuForScheduleDynamicCustom(long start, long final) {
	int numarPrime = 0;

#pragma omp parallel for schedule(guided, 3000)
	for (long valoare = start; valoare < final; valoare += 1) {
		if (estePrim(valoare))
#pragma omp atomic
			numarPrime += 1;
	}
	return numarPrime;
}


void benchmark(string mesaj, int (*pf)(long, long), long start, long final) {
	printf("\n Test %s", mesaj.c_str());
	double tStart = omp_get_wtime();
	int rezultat = pf(start, final);
	double tFinal = omp_get_wtime();
	printf("\n Rezultat %d in %f secunde", rezultat, (tFinal - tStart));
}


int mininVector(int* valori, int N) {
	int minim = valori[0];
	for(int i = 0; i < N; i++){
		if (minim > valori[i])
			minim = valori[i];
	}

	return minim;
}

int mininVectorParalel(int* valori, int N) {
	int minim = valori[0];
#pragma omp parallel for
	for (int i = 0; i < N; i++) {
#pragma omp critical
		{
			if (minim > valori[i])
				minim = valori[i];
		}
	}
	return minim;
}

void benchmarkMinim(int (*pf)(int*, int), int* valori, int N) {
	printf("\n Benchmark minim");
	double tStart = omp_get_wtime();
	int minim = pf(valori, N);
	double tFinal = omp_get_wtime();
	printf("\n Minim = %d in %f secunde", minim, tFinal - tStart);
}

int main() {

	const long N = 5e5;
	//benchmark("Solutie secventiala", solutieSecventiala, 1, N);
	//benchmark("Solutie paralela cu atomic", solutieParalelaCuAtomic, 1, N);
	//benchmark("Solutie paralela cu private", solutieParalelaCuPrivate, 1, N);
	
	//benchmark("Solutie paralela cu private si load balancing", solutieParalelaCuPrivateSiLoadBalancing, 1, N);
	//benchmark("Solutie paralela cu for paralel", solutieParalelaCuFor, 1, N);
	//benchmark("Solutie paralela cu for paralel", solutieParalelaCuForScheduleDynamic, 1, N);
	//benchmark("Solutie paralela cu for paralel si dynamic custom", solutieParalelaCuForScheduleDynamicCustom, 1, N);



	//durata solutie paralela mai mare deoarece algoritmul secvential nu este potrivit pentru a fi paralelizat
	const int n = 200000;
	int valori[n];
	for (int i = 0; i < n; i++) {
		valori[i] = n - i;
	}

	benchmarkMinim(mininVector, valori, n);
	benchmarkMinim(mininVectorParalel, valori, n);


}