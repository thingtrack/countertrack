/*
 * mat.h
 *
 *  Created on: 18/10/2013
 *      Author: miguel
 */

#ifndef MAT_H_
#define MAT_H_

#include "../ZoneRegister/ZoneRegister.h"
#include "../Gait/Gait.h"

class Mat {
	public:
		Mat();
		int checkActiveZones();
		bool checkGait();

	private:
		ZoneRegister zoneReg;
		Gait lastGait;
};

#endif /* MAT_H_ */
