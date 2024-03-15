# SivanCache渐进式缓存框架😀


![SIVANCACHE Logo](https://p16-flow-sign-sg.ciciai.com/ocean-cloud-tos-sg/44b6453b8a67444ba9a4c2f258fa1df0.png~tplv-0es2k971ck-image.png?rk3s=18ea6f23&x-expires=1742024775&x-signature=6MIh%2FpRxBN0EjJsKr0UeS%2BH6tY0%3D)

## 项目介绍😇
Java实现渐进式 kv缓存框架， 为日常开发提供一套简单易用的缓存框架 ，便于后期多级缓存开发。  
截至目前已经开发的功能如下：

● 支持 expire 过期特性，实现了定时删除和惰性删除两种过期键删除策略；

● 内置FIFO、LRU、LRU-2、 LFU、2Q 等多种缓存淘汰算法；

● 自定义删除监听器和慢操作监听器，实现了对删除和慢操作的监听；

● 支持 AOF 持久化机制，实现了对AOF文件的载入与数据还原；

● 自定义渐进式 rehash HashMap，实现了渐进式 rehash 扩容机制。

## 项目设计思路
考虑点主要在数据用何种方式存储，能存储多少数据，多余的数据如何处理以及是否支持持久化等几个点。

- 数据结构
   - 本地缓存最常见的是直接使用 Map 来存储，比如 guava 使用 ConcurrentHashMap，ehcache 也是用了 ConcurrentHashMap，Mybatis 二级缓存使用 HashMap 来存储：
   - 或者复杂的如 redis 一样提供了多种数据类型哈希，列表，集合，有序集合等，底层使用了双端链表，压缩列表，集合，跳跃表等数据结构；

- 存贮上限
   - 因为是本地缓存，内存有上限，所以一般都会指定缓存对象的数量比如 1024，当达到某个上限后需要有某种淘汰策略去删除多余的数据；

- 淘汰策略
   - 配合存贮上限之后使用，常见的比如有FIFO(先进先出)、 LRU(最近最少使用)、LFU(最近最不常用)、SOFT(软引用)、 LRU-K 、2Q 等策略；

- 过期时间
   - 设置过期时间，让缓存数据在指定时间过后自动删除；常见的过期数据删除策略有两种方式：定时删除和惰性删除；
     - 定时删除：设置某个key的过期时间同时，我们创建一个定时器，让定时器在该过期时间到来时，立即执行对其进行删除的操作。
     - 惰性删除：设置该key过期时间后，我们不去管它，当需要该key时，我们在检查其是否过期，如果过期，我们就删掉它，反之返回该key。

- 线程安全
   - 像 redis 是直接使用单线程处理，所以就不存在线程安全问题；而我们现在提供的本地缓存往往是可以多个线程同时访问的，所以线程安全是不容忽视的问题；并且线程安全问题是不应该抛给使用者去保证；
   - 尽量用线程安全的类去存储数据，比如使用 ConcurrentHashMap 代替 HashMap；或者提供相应的同步处理类，比如 Mybatis 提供了 SynchronizedCache：

- 简明的接口
   - 提供常用的 get，put，remove，clear，getSize 方法即可。

- 是否支持持久化
   - 如果我们只是把文件放在内存中，应用重启信息就丢失了。有时候我们希望这些 key/value 信息可以持久化，存储到文件或者 database 中。
   - 持久化的好处是重启之后可以再次加载文件中的数据，这样就起到类似热加载的功效；
   - 像Redis提供了两种不同的持久化方法可以将数据存储在磁盘中，一种是快照RDB，另一种是只追加文件AOF

- 监听器
   - 根据实际工作体验，我们可以添加对删除和慢日志的监听，然后有对应的存储或者报警，这样更加方便问题的分析和快速反馈。

## 联系我们
邮箱：15943999893@163.com
