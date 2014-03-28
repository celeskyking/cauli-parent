package org.cauli.junit;



import com.google.common.collect.Lists;
import org.cauli.db.DBCore;
import org.cauli.db.DbManager;
import org.cauli.db.annotation.DB;
import org.cauli.db.annotation.MySQL;
import org.cauli.instrument.ClassPool;
import org.cauli.junit.anno.ThreadRunner;
import org.cauli.junit.statement.InterceptorStatement;
import org.cauli.junit.anno.CauliRule;
import org.cauli.junit.anno.Listener;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 王天庆
 * */
public class JUnitBaseRunner extends Feeder{
    public JUnitBaseRunner(final Class<?> klass)
            throws InitializationError {
        super(klass);
        initDB();
        setScheduler(new RunnerScheduler() {
            ExecutorService executorService = Executors.newFixedThreadPool(
                    klass.isAnnotationPresent(ThreadRunner.class) ?
                            klass.getAnnotation(ThreadRunner.class).threads() :1,
                    new NamedThreadFactory(klass.getSimpleName()));
            CompletionService<Void> completionService = new ExecutorCompletionService<Void>(executorService);
            Queue<Future<Void>> tasks = new LinkedList<Future<Void>>();

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

        });
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


    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        InterceptorStatement statement = new InterceptorStatement(method, test);
        return statement;
    }


    public List<TestRule> getRules()  {
        List<TestRule> testRules = Lists.newArrayList();
        Set<Class<?>> classes = ClassPool.getClassPool();
        for(Class<?> clazz :classes){
            if(clazz.isAnnotationPresent(CauliRule.class)||clazz.isAnnotationPresent(Listener.class)){
                try {
                    testRules.add((TestRule) clazz.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return testRules;
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            filter(new CauliFilter());
        } catch (NoTestsRemainException e) {
            e.printStackTrace();
        }
        super.run(notifier);
    }

    @Override
    protected List<TestRule> getTestRules(Object target) {
        List<TestRule> result = getTestClass().getAnnotatedMethodValues(target,
                Rule.class, TestRule.class);

        result.addAll(getTestClass().getAnnotatedFieldValues(target,
                Rule.class, TestRule.class));
        result.addAll(getRules());
        return result;
    }

    public void initDB(){
        Set<Class<?>> classes=ClassPool.getClassPool();
        for(Class<?> clazz:classes){
            if(clazz.isAnnotationPresent(DB.class)||clazz.isAnnotationPresent(MySQL.class)){
                if(clazz.isAnnotationPresent(DB.class)){
                    DB db = clazz.getAnnotation(DB.class);
                    String username = db.username();
                    String password = db.password();
                    String url = db.url();
                    String id = db.id();
                    String driver = db.driver();
                    DBCore dbCore = new DBCore(username,password,url,driver);
                    DbManager.register(id,dbCore);
                }else if(clazz.isAnnotationPresent(MySQL.class)){
                    MySQL db = clazz.getAnnotation(MySQL.class);
                    String username = db.username();
                    String password = db.password();
                    String url = db.url();
                    String alias = db.id();
                    DBCore core = DBCore.mysql(username,password,url);
                    DbManager.register(alias,core);
                }
            }
        }
    }


}
