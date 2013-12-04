#include <cstdio>
#include <cstring>
#include <ctime>
#include <string>

#include "mqtt_publisher.h"
#include <mosquittopp.h>

using namespace std;

mqtt_publisher::mqtt_publisher(const char *id, const char *host, int port, const char* publishTopic) : mosquittopp(id)
{
	int keepalive = 60;
	published_flag = 0;
	topic = publishTopic;

	/* Connect immediately. This could also be done by calling
	 * mqtt_tempconv->connect(). */
	connect(host, port, keepalive);
};

void mqtt_publisher::on_connect(int rc)
{
	printf("Connected with code %d.\n", rc);

}

void mqtt_publisher::on_publish(int rc)
{
	printf("Sent\n");
	published_flag = 1;
}

void mqtt_publisher::on_message(const struct mosquitto_message *message)
{

}

void mqtt_publisher::on_disconnect(int rc)
{
	printf("Disconnected\n");
}

int mqtt_publisher::get_published_flag()
{
	return published_flag;
}

void mqtt_publisher::set_published_flag()
{
	published_flag = 0;
}
