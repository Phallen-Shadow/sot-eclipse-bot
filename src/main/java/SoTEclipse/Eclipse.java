package SoTEclipse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Eclipse {

    public Eclipse(){}
    private final long offset_0 = TimeUnit.MINUTES.toMillis(591);
    private final long offset_1 = TimeUnit.MINUTES.toMillis(24);
    private final long offset_2 = TimeUnit.MINUTES.toMillis(33);
    private final long offset_3 = TimeUnit.MINUTES.toMillis(24);

    private long current = 0;

    public List<Long> getNextEclipse(){
        List<Long> list = new ArrayList<>();
        long start = 1703636400000L + TimeUnit.MINUTES.toMillis(60 + 21);
        long now = new Date().getTime();
        if(current == 0) current = start;
        do {
            current += offset_0 + offset_1 + offset_2 + offset_3;
        } while(now > current + offset_0 + offset_1 + offset_2 + offset_3);

        current += offset_0;
        list.add(current);
        current += offset_1;
        list.add(current);
        current += offset_2;
        list.add(current);
        current += offset_3;
        list.add(current);

        return list;
    }

    public List<Long> getNextEclipse(int count){
        List<Long> list = new ArrayList<>();
        long start = 1703636400000L + TimeUnit.MINUTES.toMillis(60 + 21);
        long now = new Date().getTime();
        if(current == 0) current = start;
        do {
            current += offset_0 + offset_1 + offset_2 + offset_3;
        } while(now > current + offset_0 + offset_1 + offset_2 + offset_3);

        for(int i = 0; i < count; i++){
            current += offset_0;
            list.add(current);
            current += offset_1;
            list.add(current);
            current += offset_2;
            list.add(current);
            current += offset_3;
            list.add(current);
        }

        return list;
    }
}
