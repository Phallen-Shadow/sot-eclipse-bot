package SoTEclipse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Eclipse {

    public Eclipse(){}
    private static final long offset_0 = TimeUnit.MINUTES.toMillis(591);
    private static final long offset_1 = TimeUnit.MINUTES.toMillis(24);
    private static final long offset_2 = TimeUnit.MINUTES.toMillis(33);
    private static final long offset_3 = TimeUnit.MINUTES.toMillis(24);

    // Original Sheet
    // https://docs.google.com/spreadsheets/d/16_PB9alwNZt7CowvU5MowXe7l__RAYNgO0hDYzZ-HqY/edit?gid=2028923469#gid=2028923469



    public static Long getNextEclipseCycleStartMilli() {
        long now = new Date().getTime();
        long offset = offset_0 + offset_1 + offset_2 + offset_3 + 7;
        long diff = now % offset;
        long eclipse = (now - diff);

        return eclipse + offset_0;
    }

    public static Long getNextEclipseCycleStartMilli(long offsetMillis) {
        return getNextEclipseCycleStartMilli() + offsetMillis;
    }

    public static Long getNextEclipseCycleStartRounded(long offsetMillis) {
        long e = getNextEclipseCycleStartMilli();
        return ((e - (e % 60000)) + offsetMillis) / 1000;
    }

    public static List<Long> getNextEclipse(int cycles) {
        List<Long> list = new ArrayList<>();
        long now = new Date().getTime();
        long offset = offset_0 + offset_1 + offset_2 + offset_3 + 7;
        long diff = now % offset;
        long eclipse = (now - diff);
        for (int i = 0; i < cycles; i++) {
            eclipse += offset_0;
            list.add(eclipse);
            eclipse += offset_1;
            list.add(eclipse);
            eclipse += offset_2;
            list.add(eclipse);
            eclipse += offset_3;
            list.add(eclipse);
        }
        return list;
    }
}
