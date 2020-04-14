package com.cnym.HashMap;

/**
 * HashMap的接口 定义一些接口方法
 */
public interface Map<K,V>{
    //值的插入
    V put(K k,V v);
    //值的获取
    V get(K k);
    //获取集合中元素的个数
    int size();

    //内部接口 获取集合中键值对的对象
    interface Node<K,V>{
        //获取key
        K getKey();
        //获取value
        V getValue();
        //设置value
        V setValue(V v);
        //设置next
        Node<K,V> setNext(Node<K,V> node);
        //获取next
        Node<K,V> getNext();
    }
}
