# SivanCache渐进式缓存框架

## 项目设计思路
1. 数据结构
● 本地缓存最常见的就是使用Map，比如guava使用 CourrentHashMap 来实现，Caffeine也是基于guava实现的，Mybatis的二级缓存使用的是 HashMap 来进行存储；
● 像Redis内部就提供了很多种数据类型：String、List、HashSet等，底层就使用了很多种数据结构：SDS、跳表、HashMap等。
2. 数据淘汰
● 如果说我们单纯的只是使用HashMap、CourrentHashMap这种数据结构肯定是不够的，因为我们不能让缓存无限的增长，需要去淘汰数据；
3. 缓存淘汰策略
● 最开始我去研究了Caffeine的缓存淘汰策略，使用的是W-TinyLFU，但是它不能自定义缓存淘汰策略，用着感觉不爽，所以打算自己写一些常用的缓存淘汰策略。可以自行进行配置。
