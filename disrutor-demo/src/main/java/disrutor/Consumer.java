package disrutor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * Created by wills on 3/3/14.
 */
public class Consumer implements WorkHandler<MyEvent> ,EventHandler<MyEvent>{

    private String name;

    public Consumer(String name) {
        this.name = name;
    }

    @Override
    public void onEvent(MyEvent myEvent) throws Exception {
        Thread.sleep(3000);
        //myEvent.add(name+"-"+l+"|");
        System.out.println(name+"==>"+myEvent.getValue());
    }

    @Override
    public void onEvent(MyEvent myEvent, long l, boolean b) throws Exception {
        Thread.sleep(3000);
        //myEvent.add(name+"-"+l+"|");
        System.out.println(name+"==>"+myEvent.getValue());

    }
}
