#include <iostream>
#include <string>
#include <omp.h>

using namespace std;

int main() {

	//control numar threaduri

	//1. Implicit nr thread-uri  = nr core-uri
	//2. Valoare din variabila de mediu OMP_NUM_THREADS
	//3. 

#pragma omp parallel 
	{
		//2. Valoare din variabila de mediu OMP_NUM_THREADS

#pragma omp master
		printf("\n Numarul de thread-uri este %d",
			omp_get_num_threads());
	}

	//3 prin omp_set_num_threads
	omp_set_num_threads(6);

#pragma omp parallel
#pragma omp master
	printf("\n Numarul de thread-uri este %d",
		omp_get_num_threads());

	//4 prin clauza num_threads
#pragma omp parallel num_threads(16)
#pragma omp master
	printf("\n Numarul de thread-uri este %d",
		omp_get_num_threads());

	//test critical sau atomic
	

	for (int j = 0; j < 10; j++) {

		int contor = 0;

#pragma omp parallel shared(contor)
		for (int i = 0; i < 1000; i++) {
#pragma omp critical
			contor += 1;
		}

		printf("\n Valoare contor = %d", contor);
	}

	//test barrier

#pragma omp parallel
	{
		printf("\n Hello ! Eu sunt thread %d", omp_get_thread_num());
		for (int i = 0; i < 100; i++) {
			float test = 3 / 2;
		}
#pragma omp barrier
		printf("\n Bye ! Eu sunt thread %d", omp_get_thread_num());
	}


	//test paralelizare bucla for
	//suma elemente vector

	int valori[10000];
	for (int i = 0; i < 10000; i++) {
		valori[i] = i + 1;
	}


	int suma = 0;
#pragma omp parallel for schedule(dynamic, 10)
	for (int i = 0; i < 10000; i++) {
		printf("\n Thread %d proceseaza iteratia %d",
			omp_get_thread_num(), i);
#pragma omp critical
		suma += valori[i];
	}

	printf("\n Suma este: %d", suma);


}

