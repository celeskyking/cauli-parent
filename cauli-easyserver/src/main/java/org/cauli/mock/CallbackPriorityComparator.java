package org.cauli.mock;


import java.util.Comparator;

/**
 * Created by tianqing.wang on 2014/10/11
 */
public class CallbackPriorityComparator implements Comparator<CallbackMethod> {

    @Override
    public int compare(CallbackMethod o1, CallbackMethod o2) {
        return o1.getIndex()-o2.getIndex();
    }

//    public static void main(String[] args) {
//        CallbackMethod method = new CallbackMethod();
//        method.setIndex(1);
//        CallbackMethod method1 = new CallbackMethod();
//        method1.setIndex(2);
//        Queue<CallbackMethod> callbackMethods = new PriorityQueue<>(10,new CallbackPriorityComparator());
//        callbackMethods.add(method);
//        callbackMethods.add(method1);
//        System.out.println(callbackMethods.poll().getIndex());
//        System.out.println(callbackMethods.poll().getIndex());
//    }
}
