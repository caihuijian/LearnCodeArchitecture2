package com.example.d08singleton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by hjcai on 2021/8/4.
 */
public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void jump(View view) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * 单线程错误实例
     * 问题产生原因：
     *     public boolean remove(Object o) {
     *         if (o == null) {
     *             for (int index = 0; index < size; index++)
     *                 if (elementData[index] == null) {
     *                     fastRemove(index);
     *                     return true;
     *                 }
     *         } else {
     *             for (int index = 0; index < size; index++)
     *                 if (o.equals(elementData[index])) {
     *                     fastRemove(index);
     *                     return true;
     *                 }
     *         }
     *         return false;
     *     }
     *
     *     private void fastRemove(int index) {
     *         modCount++;//这里是关键
     *         int numMoved = size - index - 1;
     *         if (numMoved > 0)
     *             System.arraycopy(elementData, index+1, elementData, index,
     *                              numMoved);
     *         elementData[--size] = null; // clear to let GC do its work
     *     }
     * 我们再看看ArrayList中Iterator的next方法
     *         public E next() {
     *             if (modCount != expectedModCount)//关键
     *                 throw new ConcurrentModificationException();
     *             int i = cursor;
     *             if (i >= limit)
     *                 throw new NoSuchElementException();
     *             Object[] elementData = ArrayList.this.elementData;
     *             if (i >= elementData.length)
     *                 throw new ConcurrentModificationException();
     *             cursor = i + 1;
     *             return (E) elementData[lastRet = i];
     *         }
     * 问题的根本原因是modCount != expectedModCount
     * 那么如何解决呢
     */
    public void test1(View view) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer integer = iterator.next();
            if (integer == 2) {
                list.remove(integer);
            }
        }
    }

    /**
     * 单线程解决方案1
     * 单线程中使用iterator.remove解决问题
     * 使得modCount != expectedModCount不成立
     * 注意看ArrayList的Iterator的remove方法
     *         public void remove() {
     *             if (lastRet < 0)
     *                 throw new IllegalStateException();
     *             if (modCount != expectedModCount)
     *                 throw new ConcurrentModificationException();
     *
     *             try {
     *                 ArrayList.this.remove(lastRet);
     *                 cursor = lastRet;
     *                 lastRet = -1;
     *                 expectedModCount = modCount;//关键
     *                 limit--;
     *             } catch (IndexOutOfBoundsException ex) {
     *                 throw new ConcurrentModificationException();
     *             }
     *         }
     */
    public void test2(View view) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer integer = iterator.next();
            if (integer == 2) {
                iterator.remove();
            }
        }
        Toast.makeText(LoginActivity.this, "list " + list, Toast.LENGTH_SHORT).show();
    }

    /**
     * 单线程解决方案2
     * 绕开modCount != expectedModCount CopyOnWriteArrayList中的Iterator没有使用这种判断
     */
    public void test3(View view) {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer integer = iterator.next();
            if (integer == 2) {
                list.remove(integer);
            }
        }
        Toast.makeText(LoginActivity.this, "list " + list, Toast.LENGTH_SHORT).show();
    }

    /**
     * 单线程解决方案3
     * 再次绕开modCount != expectedModCount 增强的for循环和Iterator会出现这种问题 但普通循环不会 注意-- 不要出现数组越界
     */
    public void test4(View view) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i) == 2) {
                list.remove(list.get(i));
                i--;
                size--;
            }
        }
        Toast.makeText(LoginActivity.this, "list " + list, Toast.LENGTH_SHORT).show();
    }

    /**
     * 多线程下的错误示例
     * 多线程仍然会有问题 这是因为list.iterator每次都会创建一个新的iterator
     * 在线程2remove一个元素后 线程1的迭代器仍然维持原样 又会导致modCount != expectedModCount
     */
    public void test5(View view) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        Thread thread1 = new Thread() {
            public void run() {
                Iterator<Integer> iterator = list.iterator();
                while (iterator.hasNext()) {
                    Integer integer = iterator.next();
                    System.out.println(integer);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread2 = new Thread() {
            public void run() {
                Iterator<Integer> iterator = list.iterator();
                while (iterator.hasNext()) {
                    Integer integer = iterator.next();
                    if (integer == 2)
                        iterator.remove();
                }
            }
        };
        thread1.start();
        thread2.start();
    }

    /**
     * 多线程解决方案1
     * 通过锁住list（当然应该也可以锁住迭代器）来规避该问题
     */
    public void test6(View view) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        Iterator<Integer> iterator = list.iterator();
        Thread thread1 = new Thread() {
            public void run() {
                synchronized (list) {
                    while (iterator.hasNext()) {
                        Integer integer = iterator.next();
                        System.out.println(integer);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        Thread thread2 = new Thread() {
            public void run() {
                synchronized (list) {
                    while (iterator.hasNext()) {
                        Integer integer = iterator.next();
                        if (integer == 2)
                            iterator.remove();
                    }
                }
            }
        };
        thread1.start();
        thread2.start();
    }

    /**
     * 多线程解决方案2
     * 通过CopyOnWriteArrayList保证同步并且不发生异常
     */
    public void test7(View view) {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        Thread thread1 = new Thread() {
            public void run() {
                Iterator<Integer> iterator = list.iterator();
                while (iterator.hasNext()) {
                    Integer integer = iterator.next();
                    System.out.println(integer);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread2 = new Thread() {
            public void run() {
                Iterator<Integer> iterator = list.iterator();
                while (iterator.hasNext()) {
                    Integer integer = iterator.next();
                    if (integer == 2) {
                        // 注意CopyOnWriteArrayList的迭代器不支持remove add操作 这里直接操作list
                        list.remove(integer);
                    }
                }
            }
        };
        thread1.start();
        thread2.start();
    }
}
