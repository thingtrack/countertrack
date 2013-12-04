/*
 * ZoneRegister.h
 *
 *  Created on: 21/10/2013
 *      Author: miguel
 */

#ifndef ZONEREGISTER_H_
#define ZONEREGISTER_H_

#include <vector>
#include <ctime>

using namespace std;

class ZoneRegister
{
public:
	ZoneRegister();
	void setActiveZone(int zone);
	void reset();
	void display();
	vector<int> getActivations();

private:
	vector<int> activeZones;
	time_t date;
	int counter;
};



#endif /* ZONEREGISTER_H_ */
