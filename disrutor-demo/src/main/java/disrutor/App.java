package disrutor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        ExecutorService exec= Executors.newCachedThreadPool();

        Disruptor<MyEvent> disruptor=new Disruptor<MyEvent>(MyEvent.EVENT_FACTORY,8,exec);

        disruptor.handleEventsWithWorkerPool(new Consumer("c1"),new Consumer("c2"));

        Producer producer1=new Producer("p1",disruptor);
        //Producer producer2=new Producer("p2",disruptor);

        //disruptor.publishEvent(producer1);
       // disruptor.publishEvent(producer2);

        disruptor.start();

        exec.submit(producer1);

       // exec.submit(producer2);

//
//        disruptor.shutdown();
//        exec.shutdown();

    }

}
