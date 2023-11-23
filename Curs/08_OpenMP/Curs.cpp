#include <iostream>
#include <string>
#include <omp.h>

using namespace std;

int main() {

	printf("\n Start exemplu");

	int nrProcesoare = omp_get_num_procs();
	printf("\n Numarul de procesoare este %d", nrProcesoare);

	int nrThreaduri = omp_get_num_threads();
	int nrMaxThreaduri = omp_get_max_threads();

	printf("\n Nr threaduri in acest moment: %d", nrThreaduri);
	printf("\n Nr maxim threaduri in acest moment: %d", nrMaxThreaduri);

	int idThread = omp_get_thread_num();
	printf("\n Id thread in zona secventiala = %d", idThread);

	if (omp_in_parallel() == 1) {
		printf("\n Rulez in paralel");
	}
	else {
		printf("\n Rulez secvential");
	}


#pragma omp parallel
	{
		idThread = omp_get_thread_num();
		printf("\n Hello de la thread %d", idThread);

		if (idThread == 0) {
			nrThreaduri = omp_get_num_threads();
			nrMaxThreaduri = omp_get_max_threads();

			printf("\n Nr threaduri in acest moment: %d", nrThreaduri);
			printf("\n Nr maxim threaduri in acest moment: %d", nrMaxThreaduri);

			/*printf("\nReturn %d", omp_in_parallel());

			if (omp_in_parallel() == 1) {
				printf("\n *Rulez in paralel*");
			}
			else {
				printf("\n *Rulez secvential*");
			}*/
		}
	}

	printf("\n Final exemplu");
}