//============================================================================
// Name        : kShop.cpp
// Author      : Héctor Camblor
// Version     : 0.1
// Copyright   : Thingtrack S.L.
// Description : kShop in C++
//============================================================================

#include <iostream>
#include <stdio.h>
#include "../Mat/Mat.h"

using namespace std;

int main() {

	Mat mat;
	bool flag = false;

	while (1) {
		if (mat.checkActiveZones() >= 3) {
			flag = true;
		}

		if (mat.checkActiveZones() == 0 && flag == true) {
			mat.checkGait();
			flag = false;
		}

		sleep(0.2);
	}

	return 0;

}
