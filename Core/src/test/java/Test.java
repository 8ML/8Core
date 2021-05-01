/*
Created by Sander on 4/27/2021
*/

import java.math.BigDecimal;

public class Test {

    public static void main(String[] args) {

        PunishTime one = new PunishTime(PunishTime.TimeUnit.DAYS, 100000L);
        PunishTime two = new PunishTime(one.getDuration());
        System.out.println(two.getTimeLeft() + " " + two.getUnit());

    }

}
