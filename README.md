# spring-boot-learn
# Docker
```
# 查看所有容器IP
docker inspect -f='{{.Name}} {{.NetworkSettings.IPAddress}} {{.HostConfig.PortBindings}}' $(docker ps -aq)
```
# 环境配置Redis、Zookeeper、Kafka、MySQL
# Redis
```
docker run -d -p 6379:6379 --name redis --privileged=true -v /home/redis/redis.conf:/etc/redis/redis.conf -v /home/redis/data:/data docker.io/redis redis-server /etc/redis/redis.conf --appendonly yes
```
# Zookeeper
```
docker run -d -p 2181:2181 -p 2888:2888 -p 3888:3888 --name zookeeper -v /home/zookeeper/data:/data -v /home/zookeeper/datalog:/datalog --privileged=true docker.io/zookeeper
```
# Kafka
```
# KAFKA_ZOOKEEPER_CONNECT KAFKA_ADVERTISED_LISTENERS=PLAINTEXT IP为宿主机器IP
docker run  -d --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=192.168.137.100:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.137.100:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -t wurstmeister/kafka
```
# MySQL
```
docker run -p 3306:3306 --name mysql-master -v /home/mysql/master/conf.d:/etc/mysql/conf.d -v /home/mysql/master/logs:/logs -v /home/mysql/master/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -d --privileged=true docker.io/mysql:5.7.26

# Master
# 1、MySQL配置
[mysql]
# 设置mysql客户端默认字符集
default-character-set=utf8mb4
[mysqld]
# 允许最大连接数
max_connections=200
# 服务端使用的字符集默认为8比特编码的latin1字符集
character-set-server=utf8mb4
# 同一局域网内注意要唯一
server-id=100
# 开启二进制功能，可以随便取
log-bin=mysql-bin

# 2、创建用于数据同步的账户
CREATE USER 'pig'@'192.168.137.%' IDENTIFIED BY '123456';
GRANT REPLICATION SLAVE ON *.* TO 'pig'@'192.168.137.%';
FLUSH PRIVILEGES;

# 3、查询 master_log_file master_log_pos
show MASTER status;

# Slave
# 1、MySQL配置
[mysql]
# 设置mysql客户端默认字符集
default-character-set=utf8mb4
[mysqld]
# 允许最大连接数
max_connections=200
# 服务端使用的字符集默认为8比特编码的latin1字符集
character-set-server=utf8mb4
# 设置server_id,注意要唯一
server-id=101
# 开启二进制日志功能，以备Slave作为其它Slave的Master时使用
log-bin=mysql-slave-bin
# relay_log配置中继日志
relay_log=edu-mysql-relay-bin

# 2、从库配置
change MASTER TO master_host = '172.17.0.2',
master_user = 'pig',
master_password = '123456',
master_port = 3306,
master_log_file = 'mysql-bin.000001',
master_log_pos = 971,
master_connect_retry = 30;

# 3、重启Slave
STOP SLAVE;
set GLOBAL SQL_SLAVE_SKIP_COUNTER=1;
START SLAVE;

# 4、查看同步状态
show SLAVE status;
```
