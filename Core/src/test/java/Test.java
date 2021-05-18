/*
Created by @8ML (https://github.com/8ML) on 4/27/2021
*/

public class Test {

    private static final boolean p = false;

    public static void main(String[] args) {

        String str = "OFF";
        String str_lower = str.toLowerCase();
        str = str_lower.replaceFirst(String.valueOf(str_lower.charAt(0)),
                String.valueOf(str_lower.charAt(0)).toUpperCase());


        System.out.println(str);
    }

}
