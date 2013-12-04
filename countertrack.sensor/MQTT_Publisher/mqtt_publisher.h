#ifndef TEMPERATURE_CONVERSION_H
#define TEMPERATURE_CONVERSION_H

#include <mosquittopp.h>

class mqtt_publisher : public mosqpp::mosquittopp
{
	public:
		mqtt_publisher(const char *id, const char *host, int port, const char* publishTopic);
		~mqtt_publisher();

		void on_connect(int rc);
		void on_publish(int rc);
		void on_message(const struct mosquitto_message *message);
		void on_disconnect(int rc);
		int get_published_flag();
		void set_published_flag();

	private:
		int published_flag;
		const char* topic;
		const char* data;
};

#endif
