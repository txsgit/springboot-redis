# springboot-redis
spring-boot2.3.1.RELEASE  redis-6.0.5


## 安装redis和sentinel集群

| 服务名称 | 类型 | ip | 端口 |
| --- | --- | --- | --- |
| redis | master | 192.168.31.249 | 6379 |
| redis | slave | 192.168.31.173 | 6379 |
| redis | slave | 192.168.31.57 | 6379 |
| sentinel | - | 192.168.31.249 | 26379 |
| sentinel | - | 192.168.31.173 | 26379 |
| sentinel | - | 192.168.31.57 | 26379 |


1、redis master服务器249的配置文件 redis.conf

```
bind 0.0.0.0   #开启外网访问
protected-mode no  #关闭保护模式
port 6379        #端口
daemonize yes     #守护线程启动
pidfile "/var/run/redis_6379.pid  #pid
loglevel notice   #日志级别
logfile "./redis.log"   #日志路径

```

2、redis 从服务173和57的配置

```
bind 0.0.0.0   #开启外网访问
protected-mode no  #关闭保护模式
port 6379        #端口
daemonize yes     #守护线程启动
pidfile "/var/run/redis_6379.pid  #pid
loglevel notice   #日志级别
logfile "./redis.log"   #日志路径

replicaof 192.168.31.249  6379   #指定主服务

```

3、配置redis哨兵服务sentinel.conf


  在下载的redis压缩包解压后的文件夹下有个sentinel.conf文件,复制该文件到redis的安装目录，最好和redis.conf一个目录
  
  sentinel.conf配置如下  三台sentinel的服务都一样
  ```
  bind 0.0.0.0   #开启外网访问
  protected-mode no  #关闭保护模式
  port 26379         #端口
  daemonize yes     #守护线程
  pidfile "/var/run/redis-sentinel.pid"  #pid
  logfile "/home/txs/redis6/sentinel.log"  #日志文件
  dir "/home/txs/redis6/tmp"   #工作目录
  
  sentinel monitor mymaster 192.168.31.249 6379 2  # 监控的名称是 mymaster  ip和端口是redis主服务器  2代表sentinel服务需要2个以上节点发现redis主服务不可用才切换
  sentinel down-after-milliseconds mymaster 10000  #表示sentinel在10秒内判断是否需要切换
  ```
  4、然后依次启动 redis 和sentinel服务
  ```
  # 启动Redis服务器进程
 ./src/redis-server redis.conf
 ./src/redis-cli
 # 启动哨兵进程
 ./src/redis-sentinel  sentinel.conf
 ./src/redis-cli -p 26379
  ```
  5、查看redis主从服务信息
  ```
  127.0.0.1:6379> info replication
  # Replication
  role:master
  connected_slaves:2
  slave0:ip=192.168.31.173,port=6379,state=online,offset=62206,lag=0
  slave1:ip=192.168.31.57,port=6379,state=online,offset=62206,lag=0
  master_replid:f2094ed330d17cd1180d7fa5d5c4e61032d80edb
  master_replid2:e65073539fea899180b1b23c746a962c17d5f51d
  master_repl_offset:62495
  second_repl_offset:33201
  repl_backlog_active:1
  repl_backlog_size:1048576
  repl_backlog_first_byte_offset:1
  repl_backlog_histlen:62495
  ```
  6、查看从服务器信息
  ```
  127.0.0.1:6379> info replication
  # Replication
  role:slave
  master_host:192.168.31.249
  master_port:6379
  master_link_status:up
  master_last_io_seconds_ago:0
  master_sync_in_progress:0
  slave_repl_offset:42235
  slave_priority:100
  slave_read_only:1
  connected_slaves:0
  master_replid:99e892ef604f7c0fb0bda08ee532b52d9a0ad4c2
  master_replid2:0000000000000000000000000000000000000000
  master_repl_offset:42235
  second_repl_offset:-1
  repl_backlog_active:1
  repl_backlog_size:1048576
  repl_backlog_first_byte_offset:1
  repl_backlog_histlen:42235
  ```
  7、查看sentinel信息
  ```
  127.0.0.1:26379> info sentinel
  # Sentinel
  sentinel_masters:1
  sentinel_tilt:0
  sentinel_running_scripts:0
  sentinel_scripts_queue_length:0
  sentinel_simulate_failure_flags:0
  master0:name=mymaster,status=odown,address=192.168.31.249:6379,slaves=2,sentinels=3
```
  
8、然后启动idea 创建一个spring-boot redis的项目


application.yml配置信息
```
spring:
  redis:
   # host: 192.168.31.249   #如果使用哨兵模式不需要配置
  #  port: 6379            #如果使用哨兵模式不需要配置
    sentinel:
      master: mymaster
      nodes: 192.168.31.249:26379,192.168.31.173:26379,192.168.31.57:26379

#springboot 默认使用lettuce客户端

```
9、然后在RedisConfig.java中配置RedisTemplate，在SpringbootRedisApplicationTests中测试
