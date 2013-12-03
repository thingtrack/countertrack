/*
 * ZoneRegister.cpp
 *
 *  Created on: 21/10/2013
 *      Author: miguel
 */

#include "ZoneRegister.h"
#include <iostream>
#include <ctime>

using namespace std;

ZoneRegister::ZoneRegister() {
	date = time(0);
	counter = 0;
}

void ZoneRegister::setActiveZone(int activeZone) {
	time_t now = time(0);
	if ((now - date) > 1) {
		activeZones.clear();
		counter = 0;
	}
	activeZones.push_back(activeZone);
	counter++;
	date = time(0);
}

void ZoneRegister::reset() {
	activeZones.clear();
	counter = 0;
}

void ZoneRegister::display() {
	cout << "Register: " << endl;
	for (unsigned i=0; i<activeZones.size(); i++)
	    cout << activeZones[i];
	cout << endl;
}

vector<int> ZoneRegister::getActivations() {
	return activeZones;
}

