/*
Created by @8ML (https://github.com/8ML) on 4/27/2021
*/

import club.mineplay.core.utils.ScoreBoard;

import java.math.BigDecimal;
import java.util.Date;

public class Test {

    private static final boolean p = false;

    public static void main(String[] args) {

        String[] frames = ScoreBoard.animateString("Hello");
        for (String frame : frames) {
            System.out.println(frame);
        }

    }

}
