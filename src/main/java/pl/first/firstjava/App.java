package pl.first.firstjava;

/**
 * Klasa główna z metodą main.
 *
 * @author Marcin Kwapisz
 */
public class App {

    public static void main(final String[] args) {
        Greeter greeter = new Greeter();
        System.out.println(greeter.greet(args[0]));
    }
}
//bylem tuuu