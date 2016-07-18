#log-extension
基于分布式系统消息链式调用



> 郑重声明：该文档由本人收集了大量文档进行编写，转载必须注明出处。项目分享的代码不能应用于商用代码，仅供参考学习。涉及法律问题，保留追责的权利。


# 概述


随着系统的增长，业务系统越来越复杂。监控对于系统来说越来越重要。没有人可以保证业务的流畅性以及业务完整性,最直接有效手段就是监控。

上述的问题，引入了一个新的概念就是分布式部署。随着业务系统越来越复杂，分布式的部署再所难免，监控不仅仅是一个系统的事情，现在变成跨业务、跨集群、跨进程、跨线程的监控。以电商云为例，目前我们的系统就有20多个，每个系统部署3个节点来看，一共60个节点。每个系统直接而且调用或者被调用的复杂的关系。为了保证业务在出错的第一时间我们开发者可以清楚分析问题所在，这些就是我们今天讨论的问题。

---

## 日志收集

### 离线收集

业界常用的手段就是，让业务系统产生日志(Log4j, Log4j2, Logback)，然后使用Flume进行采集，把日志存入到大数据分析中(Hadoop)进行离线分析。

### 实时收集

实时收集，可以基于Log4j框架的Appender策略，自己写一个Appender把日志实时存储到远程服务器上。可以借助于缓冲中间件Kafka,后续分析应用从Kafka中读取日志信息。

## 日志分析

对于日志文件，一般都是非结构化数据或者说整理后近似于半结构化数据,可以把数据索引到lucene类似的中间件中进行业务分析。

---

### 实时分析

实时分析一般基于流式分析，像apache storm这种实时流分析。

### 离线分析

离线分析方式比较多，看数据量大小，如果数据量不大，可以使用awk就可以做到，如果数据量较大可以使用hadoop，elastic-search(基于lucence)索引后进行分析查询。


# 解决方案

## ELK

> https://www.elastic.co/products/elasticsearch

- logstash

日志收集器，这个可以使用其他替代，logstash的优点就是本身以插件方式编写，如果不满足需求可以自定义插件（ruby）,可以跨平台，log4j有直接的appender这样的插件。

- elastic-search

该中间件基于lucene，非常适合分析半结构化数据，优点支持完全分布式，性能非常好，支持rest，跨语言能力很好，完善的文档。

- kibana

该中间件可以搜索展示elasticsearch中的日志索引数据，大部分功能可以满足简单的搜索。缺点就是自定义比较复杂。


## CAT

> https://github.com/dianping/cat

由大众点评公司编写并且开源的参考实现。功能非常强大，延迟低，占用资源少等优点，目前已被多家互联网公司采纳。缺点就是自定义改造难度大，代码复杂，侵入代码，需要埋点。

我在业余时间写了参考，并且成功搭建了该中间件。大致由客户端的agent和服务端server构成，把分析完的数据存入到mysql中。

> https://github.com/Percy0601/training-cat

## EagleEye(淘宝鹰眼)

目前没有开源，基于hsf做的，淘宝的hsf没有开源，但是可以免费使用。具体使用方式，大家可以去阿里云上找到，这里不再介绍。

> 首先预览一下：

![image](http://git.oschina.net/Percy0601/blog-image/raw/master/logx/logx002.png)

淘宝的鹰眼是如何实现的呢？

### 参考实现

#### 调用链

同一次请求的所有相关调用的情况，在淘宝EagleEye里称作调用链。同一个时刻某一台服务器并行发起的网络调用有很多，怎么识别这个调用是属于哪个调用链的呢？可以在各个发起网络调用的中间件上下手。

HSF/Dubbo/JSF -> Filter -> Attachment(隐式传参)

#### 埋点

在前端请求到达服务器时，应用容器在执行实际业务处理之前，会先执行 EagleEye 的埋点逻辑（类似Filter的机制），埋点逻辑为这个前端请求分配一个全局唯一的调用链ID。这个ID在EagleEye里面被称为 TraceId，埋点逻辑把TraceId放在一个调用上下文对象里面，而调用上下文对象会存储在ThreadLocal里面。调用上下文里还有一个ID非常重要，在 EagleEye里面被称作RpcId。RpcId用于区分同一个调用链下的多个网络调用的发生顺序和嵌套层次关系。对于前端收到请求，生成的 RpcId 固定都是0。

  当这个前端执行业务处理需要发起RPC调用时，淘宝的RPC调用客户端 HSF 会首先从当前线程 ThreadLocal 上面获取之前 EagleEye 设置的调用上下文。然后，把 RpcId 递增一个序号。在 EagleEye 里使用多级序号来表示RpcId，比如前端刚接到请求之后的 RpcId 是0，那么 它第一次调用RPC服务A时，会把RpcId改成0.1。之后，调用上下文会作为附件随这次请求一起发送到远程的 HSF 服务器。

  HSF 服务端收到这个请求之后，会从请求附件里取出调用上下文,并放到当前线程ThreadLocal上面。如果服务A在处理时，需要调用另一个服务，这个时候它会重复之前提到的操作，唯一的差别就是RpcId 会先改成0.1.1再传过去。服务A的逻辑全部处理完毕之后，HSF在返回响应对象之前，会把这次调用情况以及TraceId、RpcId都打印到它的访问日志之中，同时，会从ThreadLocal清理掉调用上下文。
  
>  如图展示了一个浏览器请求可能触发的系统间调用。
  
  ![image](http://git.oschina.net/Percy0601/blog-image/raw/master/logx/logx005.jpg)
  
  
####   ThreadLocal

上述图描述了EagleEye在一个非常简单的分布式调用场景里做的事情，就是为每次调用分配TraceId、RpcId，放在ThreadLocal的调用上下文上面，调用结束的时候，把TraceId、RpcId打印到访问日志。类似的其他网络调用中间件的调用过程也都比较类似，这里不再赘述了。访问日志里面，一般会记录调用时间、远端IP地址、结果状态码、调用耗时之类，也会记录与这次调用类型相关的一些信息，如URL、服 务名、消息topic等。很多调用场景会比上面说的完全同步的调用更为复杂，比如会遇到异步、单向、广播、并发、批处理等等，这时候需要妥善处理好ThreadLocal上的调用上下文，避免调用上下文混乱和无法正确释放。另外，采用多级序号的RpcId设计方案会比单级序号递增更容易准确还原当时的调用情况。

最后，EagleEye 分析系统把调用链相关的所有访问日志都收集上来，按 TraceId 汇总在一起之后，就可以准确还原调用当时的情况了。

![image](http://git.oschina.net/Percy0601/blog-image/raw/master/logx/logx006.jpg)
> 这个是淘宝线上的一个典型的调用链

如图所示，就是采集自淘宝线上环境的某一条实际调用链。调用链通过树形展现了调用情况。调用链可以清晰地看到当前请求的调用情况，帮助问题定 位。如上图，mtop应用发生错误时，在调用链上可以直接看出这是因为第四层的一个(tair@1)请求导致网络超时，使最上层页面出现超时问题。这种调用链，可以在EagleEye系统监测到包含异常的访问日志后，把当前的错误与整个调用链关联起来。问题排查人员在发现入口错误量上涨或耗时上升时，通过EagleEye查找出这种包含错误的调用链采样，提高故障定位速度。

如果对同一个前端入口的多条调用链做汇总统计，也就是说，把这个入口URL下面的所有调用按照调用链的树形结构全部叠加在一起，就可以得到一个新的树结构（如图所示）。这就是入口下面的所有依赖的调用路径情况。

![image](http://git.oschina.net/Percy0601/blog-image/raw/master/logx/logx007.jpg)

> 上图对某个入口的调用链做统计之后得到的依赖分析


这种分析能力对于复杂的分布式环境的调用关系梳理尤为重要。传统的调用统计日志是按固定时间窗口预先做了统计的日志，上面缺少了链路细节导致没办法对超过两层以上的调用情况进行分析。例如，后端数据库就无法评估数据库访问是来源于最上层的哪些入口；每个前端系统也无法清楚确定当前入口由于双十一活动流量翻倍，会对后端哪些系统造成多大的压力，需要分别准备多少机器。有了EagleEye的数据，这些问题就迎刃而解了。

> 下图展示了数据流转过程。

![image](http://git.oschina.net/Percy0601/blog-image/raw/master/logx/logx008.png)




#### 采样

调用链数据在容量规划和稳定性方面的分析

---



## ZipKin

> https://github.com/openzipkin/zipkin

由twitter出品，目前已经有了开源版本，支持的语言多。

> 预览一下

![image](http://git.oschina.net/Percy0601/blog-image/raw/master/logx/logx001.gif)


## GoogleDapper

> http://bigbully.github.io/Dapper-translation/
> http://www.tuicool.com/articles/F3YNrm

谷歌没开源，但是给了文档的设计规范，可以参考。


## CAL

由eBay出品，目前没开源。


## Hydra

京东商城引入了阿里开源的服务治理中间件Dubbo，所以它的分布式跟踪Hydra基于Dubbo就能做到对业务系统几乎无侵入了。

> 预览：

![image](http://git.oschina.net/Percy0601/blog-image/raw/master/logx/logx003.png)

> 领域模型：

![image](http://git.oschina.net/Percy0601/blog-image/raw/master/logx/logx009.png)

> 部署模型：

![image](http://git.oschina.net/Percy0601/blog-image/raw/master/logx/logx010.jpg)



## logx(log-extension)

这个我下述要分析的内容，由我个人独自开发完成。其中看过一些开源代码，诸如CAT，但是我的实现方式跟CAT还是有很大不同的。这个章节不多说，下述分析。

# 自定义参考实现

## 设计指标

- 不侵入

> 由于目前业务复杂，如果侵入代码，不仅仅是工作量问题，而且容易出错，实施这个工作就很难。只有在不侵入代码或者很少污染之前的业务代码才有可能进行实施。

- 策略灵活

> 策略灵活就是说可以灵活的配置那部分可以监控，那部分不需要监控。灵活决定所收集数据的范围和粒度。

- 近实时

> 响应要快，不能做到之前那种方式反复查询几个项目的日志文件才能找到错误的问题。在出错的第一时间就可以知道问题错误的原因和位置。

- 可视化

> 可视化，要做到不用看日志通过可视化进行筛选。

- 轻量级

> 轻量，客户端的收集器（Agent）不能对业务系统造成资源过度消耗。占用内存和CPU要足够轻量。


## 重要概念

### 隐式传参

我们想把整个调用链能够在不侵入代码方式，那么必须可以在调用系统直接传递。不能污染正常参数，还好每个RPC的中间件都有隐式传参的概念特性。

### 过滤器

不污染代码，就必须把代码植入到过滤器中。这样可以不污染业务代码。Dubbo, JSF, HSF都支持该特性。其实服务治理的核心特性都是通过过滤器来实现的。

### ThreadLocal

把每次调用的变量加入到当前线程进行记录隔离，那么这个过程使用ThreadLocal再合适不过了。

### AOP

可以采用切面方式详细记录每个堆栈信息。AOP就是这么任性。

### Message

我们把每个调用过程传递使用消息方式，Message记录调用的当前调用Id,可以使用Java自带的UUID产生。把当前ID加入到ThreadLocal中。

messageId = parentMessageId = rootMessageId

对于根消息这三个ID相同，调用的下次消息的parentMessageId为上次消息的Id。设计的大致思路就是这样。其他都是细节问题。

## 运行时序列图

### Web

```
sequenceDiagram

Broswer->>Web: 浏览器发起

Web->>Controller: 该控制器被切面拦截

Controller->>Agent: 记录调用关系（MessageId/ParentId）


```
上述图阐述了消息发起过程，由Agent的Interceptor拦截器所拦截，产生RootId, MessageId, ParentId,对于根消息来说，三者相同。处理完调用链的消息后发送到Server端，对于发送来说，我做了一个抽象，既可以使用Log4j的Appender策略机制也可以使用dubbo/jsf进行发送。当然这个过程是灵活的，你也可以先把消息存放到本地中，使用flume/logstash等中间件进行收集。

### Center


```
sequenceDiagram
WebController->>CenterFilter: 处理消息关系

CenterFilter->>Agent: 发送消息


```



### Server


```
sequenceDiagram
Server->>MessageHandler:解析消息

MessageHandler->>ElasticSearch: 解析后加工处理存储到ES中
```



### Console


![image](http://git.oschina.net/Percy0601/blog-image/raw/master/logx/logx011.png)

展现消息


## 消息传递详情



```
gantt
dateFormat YYYY-MM-DD
section Web
Message(m_01, p_01, r_01): 2016-07-18, 10d

section Center(A)
Message(m_02, p_01, r_01): 2016-07-19, 10d

section Center(B)
Message(m_03, p_01, r_01): 2016-07-19, 10d

```

以当前消息链来讲，他们的根消息RootMessageId:01，这样我们就可以分析出只要根消息相同的就是同一个消息。很容易分析出整个调用链关系来。


## 概览使用

我们来概览一下如何使用：该中间件基于Spring/JSF/Dubbo，如果是其他的中间件，请独立编写。


### 客服端

![image](http://git.oschina.net/Percy0601/blog-image/raw/master/logx/logx013.png)

### 服务端


![image](http://git.oschina.net/Percy0601/blog-image/raw/master/logx/logx013.png)

## 核心代码


### Filter

#### ConsumerFilter


```
String rootMessageId = MDC.get(Constants.MESSAGE_ROOT_ID);
String parentMessageId = MDC.get(Constants.MESSAGE_PARENT_ID);
		
if (StringUtils.isEmpty(rootMessageId)) {
	rootMessageId = UUID.randomUUID().toString();
	parentMessageId = rootMessageId;
}

RpcContext context = RpcContext.getContext();
context.setAttachment(Constants.MESSAGE_ROOT_ID,rootMessageId);
context.setAttachment(Constants.MESSAGE_PARENT_ID,parentMessageId);

return invoker.invoke(invocation);
```
MDC就是log4j的ThreadLocal中的工具，当然我们可以自己写一个工具，非常简单了。我们先从MDC中取出根消息Id,以及ParentID，如果不存在就创建一个，当然一般是存在的，因为有Consumer发起消息调用。把这两个消息Id加入到中间件的Context中，隐式传参传入到Center(Provider)接收方进行处理。


#### ProviderFilter


```
RpcContext context = RpcContext.getContext();
String rootMessageId = context.getAttachment(Constants.MESSAGE_ROOT_ID);
String currentRootMessageId = context.getAttachment(Constants.MESSAGE_PARENT_ID);
if (StringUtils.isEmpty(rootMessageId)) {
	rootMessageId = UUID.randomUUID().toString();
	currentRootMessageId = rootMessageId;
}
MDC.put(Constants.MESSAGE_ROOT_ID, rootMessageId);
MDC.put(Constants.MESSAGE_CURRENT_ROOT_ID,currentRootMessageId);
MDC.put(Constants.MESSAGE_PARENT_ID, currentRootMessageId);
return invoker.invoke(invocation);
```

提供方先把Context中的隐式传来的参数取出来，加入到MDC(ThreadLocal)当中，当前线程的其他位置就可以方便的把rootId和parentId取到了。

### LogExtensionInterceptor

这里代码太长不再一一叙述

---

## 总结

总的来说，核心代码就几百行，想实现一个链式调用关系的分析不太复杂，读者可以仔细想一下我说的几点。隐式传参，ThreadLocal, AOP, MessageId, ParentId, RootId, 消息的关系就出来了。把消息存储到方便查询的中间件中，例如ElasticSearch，嗯嗯，基本就这些。


> 参考代码位置：https://github.com/Percy0601/log-extension
> 原文地址：http://note.youdao.com/yws/public/redirect/share?id=061946ab9185ee0c9bd32419ea281dcc&type=false
