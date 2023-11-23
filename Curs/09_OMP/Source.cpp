#include <iostream>
#include <string>
#include <omp.h>

int main() {

	printf("\n Numar procesoare = %d", omp_get_num_procs());
	printf("\n Numar thread-uri = %d", omp_get_num_threads());
	printf("\n Id thread = %d", omp_get_thread_num());

	//se va executa in paralel doar prima instructiune dupa pragma
#pragma omp parallel
	printf("\n Hello ");
	printf("\n Bye ");


	int nrThreaduri = 4;
#pragma omp parallel num_threads(nrThreaduri)
	{
		printf("\n Salut de la thread %d", omp_get_thread_num());
	}


	//test numar maxim de thread-uri pe care il pot obtine de la sistem
	nrThreaduri = 50000;
#pragma omp parallel num_threads(nrThreaduri)
	{
		if (omp_get_thread_num() == 0) {
			printf("\n Numar real de thread-uri este %d", 
				omp_get_num_threads());
		}
	}

	//test control executie paralela
	bool avemMemorie = true;
#pragma omp parallel num_threads(6) if(avemMemorie)
	{
		printf("\n Bloc executat in paralel");
	}

	//test vizibilitate variabile in zona paralela
	int sharedVariable = 100;
	int privateVariable = 10;
	int firstPrivateVariable = 60;

#pragma omp parallel default(none)
	{
		int sharedVariable = 10;
		sharedVariable += 10;
		//privateVariable += 1;
		//firstPrivateVariable += 1;
	}

	printf("\n Variabila shared = %d", sharedVariable);
	printf("\n variabila private = %d", privateVariable);
	printf("\n Variabila firstPrivate = %d", firstPrivateVariable);


#pragma omp parallel shared(sharedVariable) \
	private(privateVariable) firstprivate(firstPrivateVariable)
	{
		privateVariable = 0;

		sharedVariable += 1;
		privateVariable += 1;
		firstPrivateVariable += 1;

		if (omp_get_thread_num() == 0) {
			printf("\n variabila private in parallel = %d", privateVariable);
			printf("\n Variabila firstPrivate in parallel = %d", firstPrivateVariable);
		}
	}

	printf("\n Variabila shared = %d", sharedVariable);
	printf("\n variabila private = %d", privateVariable);
	printf("\n Variabila firstPrivate = %d", firstPrivateVariable);


	//adunam elementele unui vector pe mai multe thread-uri
	int* valori = new int[100];
	for (int i = 0; i < 100; i++) {
		valori[i] = i + 1;
	}


	int suma = 0;
	int sumaPartiala = 0;

	omp_lock_t mutex;
	omp_init_lock(&mutex);

#pragma omp parallel shared(suma) firstprivate(sumaPartiala)
	{
		for (int i = omp_get_thread_num(); i < 100; i += omp_get_num_threads() ) {
			sumaPartiala += valori[i];
		}
		omp_set_lock(&mutex);
		suma += sumaPartiala;
		omp_unset_lock(&mutex);
	}

	printf("\n Suma este %d", suma);

	suma = 0;
#pragma omp parallel shared(suma) firstprivate(sumaPartiala)
	{

#pragma omp single
		{
			printf("\nSecventa de test");
		}

		for (int i = omp_get_thread_num(); i < 100; i += omp_get_num_threads()) {
			sumaPartiala += valori[i];
		}
#pragma omp atomic
			suma += sumaPartiala;
	}

	//sau 
//#pragma omp critical
//	{
//		suma += sumaPartiala;
//	}
	printf("\n Suma este %d", suma);

}