/*
 * Gait.h
 *
 *  Created on: 22/10/2013
 *      Author: miguel
 */
#include <vector>
#include <ctime>

using namespace std;

#ifndef GAIT_H_
#define GAIT_H_

class Gait {
public:
	Gait();
	Gait(int currentDirection, vector<int> currentSequence);
	bool sameGait(Gait lastGait);

	int getDirection() const {
		return direction;
	}

	time_t getGaitDate() const {
		return gaitDate;
	}

	vector<int> getSequence() const {
		return sequence;
	}
private:
	int direction;
	vector<int> sequence;
	time_t gaitDate;
};


#endif /* GAIT_H_ */
