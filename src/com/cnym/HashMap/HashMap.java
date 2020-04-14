package com.cnym.HashMap;

public class HashMap<K,V> implements Map<K,V> {

    /**
     * 默认的几个数据成员
     * 默认长度为16
     * 加载因子为0.75
     * Node类型的数组
     * 元素个数
     */
    private static int defaultLength = 16;
    private static double loadFactor = 0.75;
    private Node<K,V>[] tableCell = null;
    private int size = 0;

    @Override
    public V put(K k, V v) {
        //首先判断 tableCell是否为空
        if(tableCell==null){
            //创建默认长度的数组
            tableCell = new Node[defaultLength];
        }

        //当元素数量为总量的0.75倍的时候开始扩容
        if(this.size >= defaultLength*loadFactor){
            resize();
        }

        //获取这对key value的index下标位置
        int index = getIndex(k,defaultLength);
        Map.Node<K,V> node = tableCell[index];
        //要判断是否这个put是修改原有的
        while(node!=null){
            //如果原来有这个k
            if(node.getKey().equals(k)){
                //修改value值
                node.setValue(v);
            }else{
                node = node.getNext();
            }
        }
        //创建Node节点 并将其放在tableCell里的index下标处
        tableCell[index] = new Node<>(k,v,tableCell[index]);
        //元素成功插入
        this.size++;
        return v;
    }

    private int getIndex(K k,int defaultLength){
        //空的key一定放在第一个
        if(k==null){
            return 0;
        }
        //key不为空
        //获取k的哈希值
        int hash = k.hashCode();
        //将哈希值取余并返回 返回的值的范围为 [0,defaultLength-1] 闭区间
        return hash % defaultLength;
    }

    /**
     * 数组扩容
     */
    private void resize(){
        System.out.println("----------开始扩容-----------");
        //重新创建tableCell 逻辑左移一位 数字变成原来的两倍
        Node<K,V>[] newTableCell = new Node[defaultLength<<1];
        Map.Node<K,V> node;
        //遍历原来的tableCell 重新进行散列
        for(int i = 0;i<this.tableCell.length;++i){
            node = this.tableCell[i];
            while(node!=null){
                int index = this.getIndex(node.getKey(),newTableCell.length);
                //获取这一个的下一个
                Map.Node<K,V> next = node.getNext();
                node.setNext(newTableCell[index]);
                newTableCell[index] = (Node<K, V>) node;
                //继续下一个节点
                node = next;
            }
        }
        this.tableCell = newTableCell;
        defaultLength = newTableCell.length;
    }

    @Override
    public V get(K k) {
        //只有tableCell数组不为空 才获取 元素返回 否则返回null
        if(this.tableCell!=null){
            //获取k对应的index
            int index = getIndex(k,defaultLength);
            //获取该元素所在的数组的元素 注意：此时获取的不一定是我们要获取的元素 只是要获取的那个元素所在链表的首部
            Map.Node<K,V> node = this.tableCell[index];
            //当node为null结束循环
            while(node!=null){
                //如果K相同 返回value
                if(node.getKey().equals(k)){
                    return node.getValue();
                }else{
                    node = node.getNext();
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    //内部节点类
    static class Node<K,V> implements Map.Node<K,V>{
        K key;
        V value;
        Map.Node<K,V> next;

        public Node(K k,V v,Map.Node<K,V> next){
            this.key = k;
            this.value = v;
            this.next = next;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V v) {
            //先保存当前的值
            V oldValue = this.value;
            //将当前的值设置成设置的值
            this.value = v;
            //返回之前的值
            return oldValue;
        }

        @Override
        public Map.Node<K, V> setNext(Map.Node<K, V> node) {
            //保存先前的节点
            Map.Node oldNode = this.next;
            //设置下一个节点为传入的节点
            this.next = node;
            //返回先前的节点
            return oldNode;
        }

        @Override
        public Map.Node<K, V> getNext() {
            return this.next;
        }
    }
}
