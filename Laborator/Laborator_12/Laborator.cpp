#include <iostream>
#include <fstream>
#include <string>
#include <omp.h>
#include "sha1.h"

using namespace std;

int main() {

	//cum folosesc sha1

	string parola = "1234";
	string hashSha1 = sha1(parola);

	printf("\n SHA1 pentru 1234 este %s", hashSha1.c_str());
	cout << endl << " SHA1 pentru 1234 este " << hashSha1;

	//cum citest din fisier text
	ifstream fisier("10-million-password-list-top-1000000.txt");
	if (!fisier.is_open()) {
		printf("\n **** Fisier inexistent ****");
	}
	else {

		string password;
		fisier >> password;
		cout << endl << password;

		fisier >> password;
		cout << endl << password;

		if (fisier.eof()) {
			cout << endl << "Am ajuns la final de fisier";
		}

		fisier.close();
	}


}
