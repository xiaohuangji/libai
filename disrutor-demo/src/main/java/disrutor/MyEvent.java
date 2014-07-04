package disrutor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by wills on 3/3/14.
 */
public class MyEvent {

    private String value;

    public String getValue() {
        return value;
    }

    public void add(String suffix){value = value+suffix;}

    public void setValue(String value) {
        this.value = value;
    }

    public final static EventFactory<MyEvent> EVENT_FACTORY= new EventFactory<MyEvent>() {
        @Override
        public MyEvent newInstance() {
            return new MyEvent();
        }
    };

}
