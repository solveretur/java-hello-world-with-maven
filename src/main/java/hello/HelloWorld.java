package hello;

import org.joda.time.LocalTime;

public class HelloWorld {
    public static void main(String[] args) {
        final LocalTime currentTime = new LocalTime();
        System.out.println("The current local time is: " + currentTime);

        final String hello = "Hey Hi Hello !";
        final Greeter greeter = new Greeter();
        System.out.println(greeter.sayMessage(hello));
    }
}
