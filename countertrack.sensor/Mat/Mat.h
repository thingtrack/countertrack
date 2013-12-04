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
#include "../MQTT_Publisher/mqtt_publisher.h"

class Mat {
	public:
		Mat();
		int checkActiveZones();
		bool checkGait();
		void getDeviceName();
		void insert(int direction);
		void mqttPublish(int direction);

	private:
		ZoneRegister zoneReg;
		Gait lastGait;
		class mqtt_publisher* publisher;
		char device[10];
		static char* deviceName;
		static int deviceName_callback(void *NotUsed, int argc, char **argv, char **azColName);
		static int insert_callback(void *NotUsed, int argc, char **argv, char **azColName);
		void setDeviceName(char* device);
};

#endif /* MAT_H_ */
