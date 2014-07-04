package disrutor;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * Created by wills on 3/3/14.
 */
public class Producer implements EventTranslator<MyEvent>, Runnable {

    private Disruptor disruptor;

    private String name;

    public Producer(String name,Disruptor disruptor) {
        this.name=name;
        this.disruptor = disruptor;
    }

    @Override
    public void translateTo(MyEvent myEvent, long sequence) {
        //myEvent.setValue(name+"-"+sequence+"|");
        myEvent.setValue(sequence+"");
        System.out.println(name+"==>"+myEvent.getValue());
    }

    @Override
    public void run() {
        while(true){
            disruptor.publishEvent(this);
            //System.out.println("size--"+disruptor.getCursor());
            try{
                 Thread.sleep(1000);
            }catch(Exception e){
                System.out.println(e);
            }
        }

    }
}
