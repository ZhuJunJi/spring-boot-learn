# spring-boot-learn
## 项目环境搭建（所有配置都存放在nacos配置中心）
### 1、本地下载启动nacos
### 2、登录nacos创建namespace dev test pro
### 3、在各个namespace下创建相应的配置
    Data ID:application
    Group:DEFAULT_GROUP
    配置格式：yaml
    配置内容如下，服务器上已经启动好了kafka zookeeper mysql redis
    ```
    spring:
      profiles: dev
      kafka:
        # 以逗号分隔的地址列表，用于建立与Kafka集群的初始连接(kafka 默认的端口号为9092)
        bootstrap-servers: xx.xx.xx.xx:9092
        producer:
          # 发生错误后，消息重发的次数。
          retries: 0
          #当有多个消息需要被发送到同一个分区时生产者会把它们放在同一个批次里。该参数指定了一个批次可以使用的内存大小，按照字节数计算。
          batch-size: 16384
          # 设置生产者内存缓冲区的大小。
          buffer-memory: 33554432
          # 键的序列化方式
          key-serializer: org.apache.kafka.common.serialization.StringSerializer
          # 值的序列化方式
          value-serializer: org.apache.kafka.common.serialization.StringSerializer
          # acks:0 ： 生产者在成功写入消息之前不会等待任何来自服务器的响应。
          # acks:1 ： 只要集群的首领节点收到消息，生产者就会收到一个来自服务器成功响应。
          # acks:all ：只有当所有参与复制的节点全部收到消息时，生产者才会收到一个来自服务器的成功响应。
          acks: 1
        consumer:
          #在spring boot 2:X 版本 auto-commit-interval（自动提交的时间间隔）采用的是值的类型为Duration ，Duration 是
          #jdk 1:8 版本之后引入的类,在其源码中我们可以看到对于其字符串的表达需要符合一定的规范，即数字+单位，如下的写法1s ，
          #1:5s， 0s， 0:001S ，1h， 2d 在yaml 中都是有效的。如果传入无效的字符串，则spring boot 在启动阶段解析配置文件
          #的时候就会抛出异常。
          # 自动提交的时间间隔 在spring boot 2:X 版本中这里采用的是值的类型为Duration 需要符合特定的格式，如1S,1M,2H,5D
          auto-commit-interval: 1S
          # 该属性指定了消费者在读取一个没有偏移量的分区或者偏移量无效的情况下该作何处理：
          # latest（默认值）在偏移量无效的情况下，消费者将从最新的记录开始读取数据（在消费者启动之后生成的记录）
          # earliest ：在偏移量无效的情况下，消费者将从起始位置读取分区的记录
          auto-offset-reset: earliest
          # 是否自动提交偏移量，默认值是true,为了避免出现重复数据和数据丢失，可以把它设置为false,然后手动提交偏移量
          enable-auto-commit: false
          # 键的反序列化方式
          key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
          # 值的反序列化方式
          value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
          group-id: "spring-boot-kafka"
          fetch-max-wait: 1S
        listener:
          # 在侦听器容器中运行的线程数。
          concurrency: 1
          # 轮询消费超时
          poll-timeout: 1S
      redis:
        # Redis数据库索引（默认为0）
        database: 0
        # Redis服务器地址
        host: xx.xx.xx.xx
        # Redis服务器连接端口
        port: 6379
        # Redis服务器连接密码（默认为空）
        password: password
        # 连接超时时间（毫秒）
        timeout: 1S
        jedis:
          pool:
            # 连接池最大连接数（使用负值表示没有限制）
            max-active: 5
            # 连接池最大阻塞等待时间（使用负值表示没有限制）
            max-wait: -1S
            # 连接池中的最大空闲连接
            max-idle: 5
            # 连接池中的最小空闲连接
            min-idle: 1
      datasource:
        master:
          jdbc-url: jdbc:mysql://xx.xx.xx.xx:3306/test
          username: username
          password: "password"
          driver-class-name: com.mysql.cj.jdbc.Driver
        # 只读账户
        slave1:
          jdbc-url: jdbc:mysql://xx.xx.xx.xx:3307/test
          username: username
          password: "password"
          driver-class-name: com.mysql.cj.jdbc.Driver
        # 只读账户
        slave2:
          jdbc-url: jdbc:mysql://xx.xx.xx.xx:3308/test
          username: username
          password: "password"
          driver-class-name: com.mysql.cj.jdbc.Driver
    ```
### 4、项目启动 --spring.profiles.active 指定环境配置
```
java -jar spring-boot-learn-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

# Docker
```
# 查看所有容器IP
docker inspect -f='{{.Name}} {{.NetworkSettings.IPAddress}} {{.HostConfig.PortBindings}}' $(docker ps -aq)
# 进入启动中的容器
docker exec -ti <容器名> /bin/bash
# 查看容器日志
docker logs -t -f --tail 10 <容器名>
# 复制容器中的文件
docker cp <容器ID>:<要复制的容器中的文件路径> <宿主机目标路径>
# 复制主机文件到容器
docker cp <宿主机目标路径> <容器ID>:<要复制的容器中的文件路径>
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
./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group group-name

# kafka设置主题partitions
/bin/kafka-topics.sh --zookeeper  192.168.137.100:2181 --alter --partitions 4 --topic spring.boot.kafka.newGroup
```
# MySQL
```
docker run -p 3306:3306 --name mysql-master -v /home/mysql/master/conf.d:/etc/mysql/conf.d -v /home/mysql/master/logs:/logs -v /home/mysql/master/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=<password> -d --privileged=true docker.io/mysql:5.7.26

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
CREATE USER 'user'@'192.168.137.%' IDENTIFIED BY 'password';
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
master_user = 'user',
master_password = 'password',
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
