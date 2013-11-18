all:
	g++ -I/usr/local/include -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"src/main.d" -MT"src/main.d" -o "src/main.o" "../src/main.cpp"

	g++ -I/usr/local/include -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"Gait/gait.d" -MT"Gait/gait.d" -o "Gait/gait.o" "../Gait/Gait.cpp"
	
	g++ -I/usr/local/include -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"ZoneRegister/zoneRegister.d" -MT"ZoneRegister/zoneRegister.d" -o "ZoneRegister/zoneRegister.o" "../ZoneRegister/ZoneRegister.cpp"

	g++ -I/usr/local/include -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"Mat/mat.d" -MT"Mat/mat.d" -o "Mat/mat.o" "../Mat/Mat.cpp"

	g++ -I/usr/local/include -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"MQTT_Publisher/mqtt_publisher.d" -MT"MQTT_Publisher/mqtt_publisher.d" -o "MQTT_Publisher/mqtt_publisher.o" "../MQTT_Publisher/mqtt_publisher.cpp"

	g++ -L/usr/local/lib -o "countertrack-sensor"  ./src/main.o ./Mat/mat.o ./Gait/gait.o ./ZoneRegister/zoneRegister.o ./MQTT_Publisher/mqtt_publisher.o   -lmosquittopp -lsqlite3

	
