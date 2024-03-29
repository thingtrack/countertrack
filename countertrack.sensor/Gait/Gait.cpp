/*
 * Gait.cpp
 *
 *  Created on: 22/10/2013
 *      Author: miguel
 */
#include "Gait.h"
#include <cstdlib>
#include <stdio.h>

Gait::Gait() {
	direction = 0;
	gaitDate = time(0);
	sequence.push_back(0);
}

Gait::Gait(int currentDirection, vector<int> currentSequence) {
	direction = currentDirection;
	sequence = currentSequence;
	gaitDate = time(0);
}

Gait::Gait(int currentDirection, vector<int> currentSequence,
		time_t currentTime) {
	direction = currentDirection;
	sequence = currentSequence;
	gaitDate = currentTime;
}

bool Gait::sameGait(Gait lastGait) {
	bool sameGait = false;

	if (abs(gaitDate - lastGait.getGaitDate()) == 0) {
		printf("Same time gait\n");
		if (direction == 0 && lastGait.getDirection() == 0
				&& sequence[sequence.size() - 1]
						> lastGait.getSequence()[lastGait.getSequence().size()
								- 1]) {
			sameGait = true;
		} else if (direction == 1 && lastGait.getDirection() == 1
				&& sequence[sequence.size() - 1]
						< lastGait.getSequence()[lastGait.getSequence().size()
								- 1]) {
			sameGait = true;
		}
	}
	return sameGait;
}

