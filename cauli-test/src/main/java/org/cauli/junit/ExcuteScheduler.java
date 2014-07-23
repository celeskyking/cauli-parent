package org.cauli.junit;

import org.cauli.junit.anno.ThreadRunner;
import org.junit.runners.model.RunnerScheduler;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tianqing.wang on 14-4-3
 */
public class ExcuteScheduler implements RunnerScheduler{

    private Class<?> testClass;

    private ExecutorService executorService;
    private CompletionService<Void> completionService;
    private Queue<Future<Void>> tasks;

    public ExcuteScheduler(Class<?> clazz){
        this.testClass=clazz;
        this.executorService=  Executors.newFixedThreadPool(
                testClass.isAnnotationPresent(ThreadRunner.class) ?testClass.getAnnotation(ThreadRunner.class).threads() : 1,
                new NamedThreadFactory(testClass.getSimpleName()));
        this.completionService = new ExecutorCompletionService<Void>(executorService);
        this.tasks = new LinkedList<Future<Void>>();
    }


    public ExcuteScheduler(int threads){
        this.executorService=  Executors.newFixedThreadPool(threads,new NamedThreadFactory(Thread.currentThread().getName()));
        this.completionService = new ExecutorCompletionService<Void>(executorService);
        this.tasks = new LinkedList<Future<Void>>();
    }
    public void schedule(Runnable childStatement) {
        tasks.offer(completionService.submit(childStatement, null));
    }


    public void finished() {
        try {
            while (!tasks.isEmpty())
                tasks.remove(completionService.take());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            while (!tasks.isEmpty())
                tasks.poll().cancel(true);
            executorService.shutdownNow();
        }
    }



    static final class NamedThreadFactory implements ThreadFactory {
        static final AtomicInteger poolNumber = new AtomicInteger(1);
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final ThreadGroup group;

        NamedThreadFactory(String poolName) {
            group = new ThreadGroup(poolName + "-" + poolNumber.getAndIncrement());
        }


        public Thread newThread(Runnable r) {
            return new Thread(group, r, group.getName() + "-thread-" + threadNumber.getAndIncrement(), 0);
        }
    }
}
