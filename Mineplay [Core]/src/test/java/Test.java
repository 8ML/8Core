/*
Created by Sander on 4/27/2021
*/

import java.math.BigDecimal;

public class Test {

    public static void main(String[] args) {

        convert(22333333333453L);

    }

    public static void convert(long duration) {

        double dur = Long.valueOf(duration).doubleValue();
        double days = (dur / (20 * 60 * 60 * 24));

        System.out.println(days);

    }

}
