# shellcheck disable=SC2164
cd ~/Apps/kafka_2.13-3.6.1

bin/zookeeper-server-start.sh config/zookeeper.properties &
zookeeper_pid=$!

bin/kafka-server-start.sh config/server.properties &
kafka_pid=$!

# Wait for a signal to stop the servers
echo "Press CTRL+C to stop the servers..."

# Trap CTRL+C and kill the Kafka processes
# shellcheck disable=SC2064
trap "kill $zookeeper_pid $kafka_pid; exit" SIGINT

# Wait for the processes to finish
wait