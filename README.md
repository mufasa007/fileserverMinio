# fileserverMinio



## 1，功能概述

​	开发一套文件管理服务实现图片文档等小文件上传、下载、更新、预览功能！

​	主要目的还是要替代阿里云的oss服务，实现免费的博客图片查阅服务！（主要是因为我之前使用的阿里云oss服务开始收费了！！！）

​	需要我的一个mufasa.site域名可以使用一下了



## 2，构架描述

![image-20220225232247171](https://mufasa-blog-images.oss-cn-beijing.aliyuncs.com/imgimage-20220225232247171.png)



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

### 6.1，minio启动报错（已解决）

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



### 6.2，mybatis 转义符（已解决）

mapper文件格式为 xml

<、>符号是特殊的保留符号，必须通过转译才能使用

```json
<![CDATA[符号未知]]>
样例：
<![CDATA[<]]>   <![CDATA[>]]>
```



### 6.3，获取请求者的真实ip（未解决）

```java
    public String getIpAddr(HttpServletRequest request) {
        String ip = null;
        // 处理代理情况
        ip = request.getHeader("x-forwarded-for");
        if (!StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (!StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                InetAddress inet = null;// 根据网卡取本机配置的IP
                try {
                    inet = InetAddress.getLocalHost();//idea-PC/192.168.212.144
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();//192.168.212.144
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割,多级代理的时候会得到多个以,分割的ip，
        //这时候第一个是真实的客户端ip
        if (ip != null && ip.length() > 15) { // "***.***.***.***".length()
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
```

但是，使用frp网络代理依然没办法直接获取到真实ip地址





### 6.4，jmeter聚合报告导出时乱码的解决（已解决）

先使用记事本打开后，选择编码格式后，得新保存既可

使用编码器打开后选择另存为，将编码从原来的UTF-8改变成ANSI格式。

再次使用excel打开就可以正常显示中文了



### 6.5，预览md文件格式中文乱码（待）



### 6.6，视频播放功能（待）



### 6.7，swagger-ui 3.0.0无法显示（待）

该问题其实可以跳过去，但是又想研究研究



### 6.8，frp代理本地127.0.0.1的端口进行tcp连接被拒绝（已解决）

frpc中的本地ip不能直接配置为127.0.0.1，需要使用真实ip（例如window中的ipconfig、linux中的ip addr查看）



### 6.9，systemLog日志记录请求url、参数等（已解决）



### 6.10，spring.profiles.active不生效（已解决）

target中的classes/config目录中没有正常生成配置文件

![image-20220226002619102](https://mufasa-blog-images.oss-cn-beijing.aliyuncs.com/imgimage-20220226002619102.png)



### 6.11，WARN  s.d.s.w.readers.parameter.ParameterDataTypeReader（已解决）

日志警告：

```json
2022-02-26 00:25:07.914 [main] WARN  s.d.s.w.readers.parameter.ParameterDataTypeReader - Trying to infer dataType java.util.List<java.lang.String>
```

原因（未使用[spring](https://so.csdn.net/so/search?q=spring&spm=1001.2101.3001.7020)规定的基本类型接受前端传送来的数据）：

```
    @ApiOperation(value = "批量删除")
    @GetMapping("/deleteBatchByGet")
    @ResponseBody
    public BaseResponse deleteBatchByGet(List<String> fileCodeList) {
       fileService.deleteBatch(fileCodeList);
        return success("批量删除");
    }
```

修改为List[]  fileCodeList即可消除警告



## 7，性能调优

### 7.1，V1.0.0版本 2022.02.20 

更新说明：

- 项目基础实体类、工具类、web相关代码编写
- 集成springBoot、minio、postgres、redis、swagger

输入文件code——查询PG数据库——调用minio——返回

| Label    | # 样本 | 平均值 | 最小值 | 最大值 | 标准偏差 | 异常 % | 吞吐量   | 接收 KB/sec | 发送 KB/sec | 平均字节数 |
| -------- | ------ | ------ | ------ | ------ | -------- | ------ | -------- | ----------- | ----------- | ---------- |
| HTTP请求 | 1000   | 4432   | 260    | 5993   | 1066.56  | 0.00%  | 143.4103 | 47810.67    | 27.17       | 341385     |
| 总体     | 1000   | 4432   | 260    | 5993   | 1066.56  | 0.00%  | 143.4103 | 47810.67    | 27.17       | 341385     |

并发：1000

接收：46.68MB/sec

平均响应时间：4.432s

可以优化的点：

- 查询code的逻辑：使用热点数据缓存redis+caffeine
- 异步操作：除核心操作以外，其他操作异步执行



### 7.2，V1.0.1版本

更新说明：

- 使用md5码实现union filesystem
- 集成redis+caffeine热点数据缓存
- 集成阿里云oss实现文件的上传下载



## 8，参考链接

该项目的github仓库地址：https://github.com/mufasa007/fileserverMinio.git



1，[jmeter聚合报告导出时乱码的解决](https://blog.csdn.net/weixin_33816300/article/details/93395751)

2，[阿里云OSS 官网api文档](https://help.aliyun.com/document_detail/32009.html)
