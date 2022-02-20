# fileserverMinio



## 1，功能概述

​	开发一套文件管理服务实现图片文档等小文件上传、下载、更新、预览功能！

​	主要目的还是要替代阿里云的oss服务，实现免费的博客图片查阅服务！（主要是因为我之前使用的阿里云oss服务开始收费了！！！）

​	需要我的一个mufasa.site域名可以使用一下了



## 2，构架描述

使用技术：

springBoot、minio、postgres、redis、caffeine、swagger



### 2.1，上传文件：

​	上传文件包括更新文件操作，上传文件的【操作频率相对较低】！

​	先在数据库中查询一下，是否已存在该文件，如果存在且一致，则新增索引（不重复上传文件浪费内存）！



### 2.2，下载文件：

​	下载文件包括预览操作（只有特定格式的文件格式才支持预览操作），下载文件的操作频率相对较高需要考虑高并发与防止攻击的情况！

​	外部请求进入系统：

​	①先保存ip信息，校验是否是非法攻击；

​	②查询Redis中是否存在该数据，如果存在则直接下载并阅览值+1，退出；否则，进入pg库查询，查询出结果则直接下载并将该数据（小于5mb）刷新至Redis；

​	③Redis使用caffeine高性能缓存进行热点数据操作！



## 3，实现难点







## 4，web接口说明





## 5，细节



### 5.1，flyway

​	base是数据库表修改目录、patch是数据修改目录



## 6，遇到问题解决

### 6.1，minio启动报错

```json
***************************
APPLICATION FAILED TO START
***************************

Description:

An attempt was made to call a method that does not exist. The attempt was made from the following location:

    io.minio.S3Base.<clinit>(S3Base.java:105)

The following method did not exist:

    okhttp3.RequestBody.create([BLokhttp3/MediaType;)Lokhttp3/RequestBody;

The method's class, okhttp3.RequestBody, is available from the following locations:

    jar:file:/D:/apache-maven-3.6.3/repo/com/squareup/okhttp3/okhttp/3.14.9/okhttp-3.14.9.jar!/okhttp3/RequestBody.class

It was loaded from the following location:

    file:/D:/apache-maven-3.6.3/repo/com/squareup/okhttp3/okhttp/3.14.9/okhttp-3.14.9.jar
```

minio使用的最新版，但是其依赖的okhttp版本不是最新的，重新引用依赖即可解决

```xml
        <!-- minio文件 -->
        <dependency>
            <groupId>io.minio</groupId>
            <artifactId>minio</artifactId>
            <version>8.3.6</version>
        </dependency>
        <dependency>
            <groupId>com.squareup.okhttp3</groupId>
            <artifactId>okhttp</artifactId>
            <version>4.9.3</version>
        </dependency>
```



### 6.2，mybatis 转义符

mapper文件格式为 xml

<、>符号是特殊的保留符号，必须通过转译才能使用

```json
<![CDATA[符号未知]]>
样例：
<![CDATA[<]]>   <![CDATA[>]]>
```



### 6.3，获取请求者的真实ip



### 6.4，jmeter聚合报告导出时乱码的解决

先使用记事本打开后，选择编码格式后，得新保存既可

![img](D:\3_project\github\fileserverMinio\README.assets\835259-20160315094330490-893529549.png)

使用编码器打开后选择另存为，将编码从原来的UTF-8改变成ANSI格式。

再次使用excel打开就可以正常显示中文了



## 7，性能调优

### 7.1，2022.02.20 V1.0.0版本

输入文件code——查询PG数据库——调用minio——返回

| Label    | # 样本 | 平均值 | 最小值 | 最大值 | 标准偏差 | 异常 % | 吞吐量   | 接收 KB/sec | 发送 KB/sec | 平均字节数 |
| -------- | ------ | ------ | ------ | ------ | -------- | ------ | -------- | ----------- | ----------- | ---------- |
| HTTP请求 | 10200  | 23341  | 13     | 42882  | 12293.32 | 6.28%  | 104.7798 | 32415.18    | 18.61       | 316789.5   |
| 总体     | 10200  | 23341  | 13     | 42882  | 12293.32 | 6.28%  | 104.7798 | 32415.18    | 18.61       | 316789.5   |

可以优化的点：

- 查询code的逻辑：使用热点数据缓存redis+caffeine
- 异步操作：除核心操作以外，其他操作异步执行



### 7.2，2022.02.20 V1.0.0 CU1版本



## 8，参考链接



1，[jmeter聚合报告导出时乱码的解决](https://blog.csdn.net/weixin_33816300/article/details/93395751)
