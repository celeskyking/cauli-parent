package org.cauli.unit;

import java.util.concurrent.*;

/**
 * Created by tianqing.wang on 2014/6/13
 */
public class ThreadTest {

    public static void main(String[] args) {
        PriorityBlockingQueue<String> tasks = new PriorityBlockingQueue<String>();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        CompletionService completionService = new ExecutorCompletionService(executor) ;
    }





    public class TaskEntity implements Comparable<TaskEntity>{

        int level=0;

        @Override
        public int compareTo(TaskEntity o) {
            return 0;
        }


    }

}
