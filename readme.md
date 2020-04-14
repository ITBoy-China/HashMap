# 面试经典，深层次剖析HashMap﻿

> 面试当中经常被问到HashMap是怎样的实现的，加载因子为什么是0.75，或者HashMap的初始长度是多少，今天我们手撸一个HashMap，从中深层次理解。（采取数组+链表的方式实现HashMap）

[toc]

### 面试中常问的HashMap问题

+ **谈一下HashMap的特性**

1. HashMap存储键值对实现快速存取，允许为null。key值不可重复，若key值重复则覆盖。

2. 非同步，线程不安全。

3. 底层是hash表，不保证有序(比如插入的顺序)

+ **如果HashMap的大小超过了负载因子(load factor)定义的容量，怎么办？**

​        超过阙值会进行扩容操作，概括的讲就是扩容后的数组大小是原数组的2倍，将原来的元素重新hashing放入到新的散列表中去。

+ **Hashmap怎么解决hash冲突？**

​        将所有哈希地址为i的元素构成一个称为同义词链的单链表，并将单链表的头指针存在哈希表的第i个单元中，因而查找、插入和删除主要在同义词链中进行。链地址法适用于经常进行插入和删除的情况。

### HashMap的数据结构

​        在 JDK1.7里， HashMap是采用 “链表散列”的方式，也就是数组加上链表的结合体，示意图如下：

![1586854723313496.jpg](http://www.image.naosial.com/2020/04/14/1586854723313496.jpg?imageView2/0/q/100|watermark/2/text/aHR0cHM6Ly9jaGVuZ25hbmJsb2cubmFvc2lhbC5jb20=/font/Y29uc29sYXM=/fontsize/240/fill/I0ZGRkZGRg==/dissolve/50/gravity/SouthEast/dx/10/dy/10)

​        在 HashMap 里，散列数组的默认长度为 **16** ，加载因子为 **0.75**，加载因子的作用就是当散列数组的长度快要不够用的时候 （一般是散列数组的长度乘以加载因子），那么此时就进行扩容，扩容的长度为当前散列数组长度的 2 倍。在进行扩容操作的时候是先 new 一个长度为原来长度2倍的数组，然后再讲原来的散列数组里的元素取出来再次进行散列。

​        如果不是这样做的就要出问题，因为我们在使用默认长度进行散列计算的是用的默认长度从而算得数据在散列数组里的下标。但是现在我们将数组容量扩大了，如果这个时候不重新进行散列，那么通过 key 而得到的数据的下标就不是正确的下标，这个时候我们就会拿不到数据或者拿到错误的数据。这也就是为什么每次扩容之后要进行重新散列的过程。

​        当然，这样做出现了 Hash 冲突的时候我们应该怎么解决？当现在插入的元素所算得的下标在散列数组中已经被占据了，这个时候应该怎么办？

​        当出现这种情况，在 JDK1.7 里采取的是链表的方式，当出现哈希冲突的时候，我们把 put 的这个元素的 next 指针域指向与我们冲突的元素，然后再把我们 put 的元素放到冲突的元素的位置里，这样在散列表的单元里存放的是这个哈希值对应元素的链表头，而通过每一个节点的 next 域即可遍历下一个节点。这样也就解决了哈希冲突。

### 值得一提的 hashCode() 方法

​        在 Java 里每一个对象都会有一个 hasCode方法，原因是在 Java 的 Object 类里有 hasCode() 方法，我们在 idea 里用快捷键 **CTRL+SHIFT+ALT+N** 搜索 Object 类并查看源码。

![158685698799997.jpg](http://www.image.naosial.com/2020/04/14/158685698799997.jpg?imageView2/0/q/100|watermark/2/text/aHR0cHM6Ly9jaGVuZ25hbmJsb2cubmFvc2lhbC5jb20=/font/Y29uc29sYXM=/fontsize/240/fill/I0ZGRkZGRg==/dissolve/50/gravity/SouthEast/dx/10/dy/10)

​        这里的 hashCode 方法是调用的本地接口也就是 C 语言的一些接口编写的，这个方法是干什么的呢？在 JVM 里，每当 new 一个对象的时候就会为该对象产生一个地址，而不同的对象的地址是不同的，所以利用地址以及一些算法计算而得到一个哈希值并将其返回。所以在我们的 HashMap 里采用的就是获取每一个 key 的 hash 值，然后进行散列，这样保证了 key 的唯一性。

### 代码如下

**Map.java**

![1586857749876.jpg](http://www.image.naosial.com/2020/04/14/1586857749876.jpg?imageView2/0/q/100|watermark/2/text/aHR0cHM6Ly9jaGVuZ25hbmJsb2cubmFvc2lhbC5jb20=/font/Y29uc29sYXM=/fontsize/240/fill/I0ZGRkZGRg==/dissolve/50/gravity/SouthEast/dx/10/dy/10)

**HashMap.java**

![1586857749878.jpg](http://www.image.naosial.com/2020/04/14/1586857749878.jpg?imageView2/0/q/100|watermark/2/text/aHR0cHM6Ly9jaGVuZ25hbmJsb2cubmFvc2lhbC5jb20=/font/Y29uc29sYXM=/fontsize/240/fill/I0ZGRkZGRg==/dissolve/50/gravity/SouthEast/dx/10/dy/10)

**Main.java**

![1586858364506.jpg](http://www.image.naosial.com/2020/04/14/1586858364506.jpg?imageView2/0/q/100|watermark/2/text/aHR0cHM6Ly9jaGVuZ25hbmJsb2cubmFvc2lhbC5jb20=/font/Y29uc29sYXM=/fontsize/240/fill/I0ZGRkZGRg==/dissolve/50/gravity/SouthEast/dx/10/dy/10)

我们可以打断点进行调试，发现在 HashMap 里是按照我们所想进行存储的

![1586858497809221.jpg](http://www.image.naosial.com/2020/04/14/1586858497809221.jpg?imageView2/0/q/100|watermark/2/text/aHR0cHM6Ly9jaGVuZ25hbmJsb2cubmFvc2lhbC5jb20=/font/Y29uc29sYXM=/fontsize/240/fill/I0ZGRkZGRg==/dissolve/50/gravity/SouthEast/dx/10/dy/10)

![1586858535819875.jpg](http://www.image.naosial.com/2020/04/14/1586858535819875.jpg?imageView2/0/q/100|watermark/2/text/aHR0cHM6Ly9jaGVuZ25hbmJsb2cubmFvc2lhbC5jb20=/font/Y29uc29sYXM=/fontsize/240/fill/I0ZGRkZGRg==/dissolve/50/gravity/SouthEast/dx/10/dy/10)

[CSDN博客链接](https://blog.csdn.net/qq_40401866/article/details/105517931)