/*
 * mat.cpp
 *
 *  Created on: 18/10/2013
 *      Author: miguel
 */
#include "Mat.h"
#include <iostream>
#include <stdio.h>
#include <cstdlib>

using namespace std;

Mat::Mat () {
	zoneReg = ZoneRegister();
	lastGait = Gait();
}

int Mat::checkActiveZones () {

	int numberOfActiveZones = 0;
	FILE *zoneHandler = NULL;

	const char *zone0File = "/sys/class/gpio/gpio30/value";
	const char *zone1File = "/sys/class/gpio/gpio60/value";
	const char *zone2File = "/sys/class/gpio/gpio31/value";
	const char *zone3File = "/sys/class/gpio/gpio50/value";
	const char *zone4File = "/sys/class/gpio/gpio48/value";
	const char *zone5File = "/sys/class/gpio/gpio51/value";
	const char *zone6File = "/sys/class/gpio/gpio3/value";
	const char *zone7File = "/sys/class/gpio/gpio2/value";
	const char *zone8File = "/sys/class/gpio/gpio49/value";
	const char *zone9File = "/sys/class/gpio/gpio15/value";

	char zoneValue [1];

	if ((zoneHandler = fopen(zone0File,"r+")) != NULL) {
		fread(zoneValue, sizeof(char), 1, zoneHandler);
		int zone0 = zoneValue[0] - '0';
		if (zone0 == 0) {
			zoneReg.setActiveZone(0);
			numberOfActiveZones++;
		}
		fclose(zoneHandler);
	}

	if ((zoneHandler = fopen(zone1File,"r+")) != NULL) {
		fread(zoneValue, sizeof(char), 1, zoneHandler);
		int zone1 = zoneValue[0] - '0';
		if (zone1 == 0) {
			zoneReg.setActiveZone(1);
			numberOfActiveZones++;
		}
		fclose(zoneHandler);
	}

	if ((zoneHandler = fopen(zone2File,"r+")) != NULL) {
		fread(zoneValue, sizeof(char), 1, zoneHandler);
		int zone2 = zoneValue[0] - '0';
		if (zone2 == 0) {
			zoneReg.setActiveZone(2);
			numberOfActiveZones++;
		}
		fclose(zoneHandler);
	}

	if ((zoneHandler = fopen(zone3File,"r+")) != NULL) {
		fread(zoneValue, sizeof(char), 1, zoneHandler);
		int zone3 = zoneValue[0] - '0';
		if (zone3 == 0) {
			zoneReg.setActiveZone(3);
			numberOfActiveZones++;
		}
		fclose(zoneHandler);
	}

	if ((zoneHandler = fopen(zone4File,"r+")) != NULL) {
		fread(zoneValue, sizeof(char), 1, zoneHandler);
		int zone4 = zoneValue[0] - '0';
		if (zone4 == 0) {
			zoneReg.setActiveZone(4);
			numberOfActiveZones++;
		}
		fclose(zoneHandler);
	}

	if ((zoneHandler = fopen(zone5File,"r+")) != NULL) {
		fread(zoneValue, sizeof(char), 1, zoneHandler);
		int zone5 = zoneValue[0] - '0';
		if (zone5 == 0) {
			zoneReg.setActiveZone(5);
			numberOfActiveZones++;
		}
		fclose(zoneHandler);
	}

	if ((zoneHandler = fopen(zone6File,"r+")) != NULL) {
		fread(zoneValue, sizeof(char), 1, zoneHandler);
		int zone6 = zoneValue[0] - '0';
		if (zone6 == 0) {
			zoneReg.setActiveZone(6);
			numberOfActiveZones++;
		}
		fclose(zoneHandler);
	}

	if ((zoneHandler = fopen(zone7File,"r+")) != NULL) {
		fread(zoneValue, sizeof(char), 1, zoneHandler);
		int zone7 = zoneValue[0] - '0';
		if (zone7 == 0) {
			zoneReg.setActiveZone(7);
			numberOfActiveZones++;
		}
		fclose(zoneHandler);
	}

	if ((zoneHandler = fopen(zone8File,"r+")) != NULL) {
		fread(zoneValue, sizeof(char), 1, zoneHandler);
		int zone8 = zoneValue[0] - '0';
		if (zone8 == 0) {
			zoneReg.setActiveZone(8);
			numberOfActiveZones++;
		}
		fclose(zoneHandler);
	}

	if ((zoneHandler = fopen(zone9File,"r+")) != NULL) {
		fread(zoneValue, sizeof(char), 1, zoneHandler);
		int zone9 = zoneValue[0] - '0';
		if (zone9 == 0) {
			zoneReg.setActiveZone(9);
			numberOfActiveZones++;
		}
		fclose(zoneHandler);
	}

	//if (numberOfActiveZones >= 3) zoneReg.display();

	return numberOfActiveZones;

}

bool Mat::checkGait() {

	bool existsGait = false;

	// Se obtiene la secuencia de activación de hilos
	vector<int> activeZones = zoneReg.getActivations();

	for(unsigned i=0; i<activeZones.size(); i++) {
		cout << activeZones[i];
	}
	cout << endl;

	// Se separa la secuencia de activación de la pisada principal de otras posibles pisadas o hilos activos

	vector<int> firstGait = zoneReg.getActivations();
	vector<int> secondaryGait;
	vector<int> gaitSequence;
	bool possibleZones[10];
	int referenceZone;

	int counter1 = 0;
	int counter2 = 0;
	int counterSequence = 0;
	for (int i=0; i<10; i++) {
		possibleZones[i] = true;
	}

	firstGait.push_back(activeZones[0]);
	counter1++;
	gaitSequence.push_back(activeZones[0]);
	counterSequence++;
	referenceZone = activeZones[0];
	possibleZones[referenceZone] = false;
	// MÉTODO 3

	// Se mantiene un hilo como referencia de la secuencia que va siguiendo la pisada
	// Cada vez que se detecta un hilo próximo al hilo de referencia se almacena como
	// parte de la pisada principal

	for (unsigned i=1; i<activeZones.size(); i++) {
		if ((abs(activeZones[i] - referenceZone) <= 2
				&& abs(activeZones[i] - activeZones[0]) <= 3)
				|| !possibleZones[activeZones[i]]) {
			firstGait.push_back(activeZones[i]);
			if (possibleZones[activeZones[i]]) {
				referenceZone = activeZones[i];
				gaitSequence.push_back(activeZones[i]);
				possibleZones[referenceZone] = false;
				counterSequence++;
			}
			counter1++;
		} else {
			secondaryGait.push_back(activeZones[i]);
			counter2++;
		}
	}


	// Se determina cuantas zonas activas hay

	int differentZones = gaitSequence.size();

	//bool possibleDoubleHalfGait = false;

	// Si hay menos de 3 zonas activas en la pisada principal puede que no se haya producido una pisada
	// o que se haya producido partida (pisada con la puntera al inicio y con el talón al final)
	if (differentZones < 3) {
		gaitSequence.push_back(activeZones[activeZones.size() -1]);
		counter2 = 0;
		counterSequence++;
		//possibleDoubleHalfGait = true;
	}

	for (unsigned i=0; i<gaitSequence.size(); i++) {
		cout << gaitSequence[i];
	}
	cout << endl;

	for (unsigned i=0; i<secondaryGait.size(); i++) {
		cout << secondaryGait[i];
	}
	cout << endl;

	Gait first;
	Gait second;
	// ____________________________________________________________________________________________________//

	// Detección del sentido de la pisada

	if (gaitSequence[gaitSequence.size() - 1] > gaitSequence[0]) {
		cout << "Individuo entrando" << endl;
		existsGait = true;
		first = Gait(1, gaitSequence);
		if(!first.sameGait(lastGait)) {
			cout << "Send" << endl;
			lastGait = first;
		}
	} else {
		cout << "Individuo saliendo" << endl;
		existsGait = true;
		first = Gait(0, gaitSequence);
		if(!first.sameGait(lastGait)) {
			cout << "Send" << endl;
			lastGait = first;
		}
	}

	// Se comprueba si hay dos pisadas

	// Para que haya dos pisadas deben haberse activado 3 o más hilos distintos aparte de los de la pisada principal


	if (counter2 > 3) {
		differentZones = 0;
		for (int i=0; i<10; i++) {
			for (int j=0; j<counter2; j++) {
				if (secondaryGait[j] == i) {
					differentZones++;
					break;
				}
			}
		}

		if (differentZones >= 3) {
			if (secondaryGait[counter2 - 1] > secondaryGait[0]) {
				cout << "Segunda pisada entrante" << endl;
				second = Gait(1, secondaryGait);
				if(!second.sameGait(lastGait)) {
					cout << "Send" << endl;
					lastGait = second;
				}
				existsGait = true;
			} else {
				cout << "Segunda pisada saliente" << endl;
				second = Gait(0, secondaryGait);
				if (!second.sameGait(lastGait)) {
					cout << "Send" << endl;
					lastGait = second;
				}
				existsGait = true;
			}
		}
	}
	zoneReg.reset();
	return existsGait;
}

