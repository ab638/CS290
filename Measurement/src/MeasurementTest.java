// File: MeasurementTests.java
// Holds the Measurement Class Tests Ran using Junit
//
// ­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­
// Class: CS290                    Instructor: Dr. Don Roberts
// Assignment: Homework 1          Date assigned:  Jan. 18, 2019
// Programmer: Austin Brummett     Date completed: Jan. 26, 2019

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MeasurementTest {
    @Test
    public void TestConstructor(){
        Measurement m = new Measurement(5, "in");
        assertEquals("5.0in",m.toString());
        Measurement n = new Measurement();
        assertEquals("0.0", n.toString());
    }

    @Test
    public void TestAddSub(){
        Measurement y = new Measurement(5, "in");
        Measurement z = new Measurement(4.5, "in");
        Measurement x;
        x = y.add(z);
        assertEquals("9.5in", x.toString());
        x = y.sub(z);
        assertEquals("0.5in", x.toString());
    }

    @Test
    public void TestMultDiv(){
        // test a easily wrong case
        Measurement a = new Measurement(3, "ft");
        Measurement b = new Measurement(4, "lb");
        Measurement c = new Measurement(9.8, "m");
        Measurement s = new Measurement(1, "s");
        Measurement d = new Measurement(10, "s");
        Measurement y = new Measurement(5, "in");
        Measurement z = new Measurement(4, "in");
        Measurement x;

        // simple multiplication and division
        x = y.mult(z);
        assertEquals("20.0in^2", x.toString());
        x = y.div(z);
        assertEquals("1.25", x.toString());

        // multi unit multiplication
        x = a.mult(b);
        assertEquals("12.0ftlb", x.toString());

        // Multi unit division
        x = c.div(s); // 9.8m/s
        x = x.div(s); // 9.8m/s^2
        assertEquals("9.8m/s^2", x.toString());

        // multiplication and division
        x = c.div(s); // 9.8m/s
        x = x.div(s); // 9.8m/s^2
        x = x.mult(d); // 98.0m/s
        x = x.mult(b); // 392mlb/s
        assertEquals("392.0lbm/s", x.toString());

        // big exponent
        a = a.mult(a);
        a = a.mult(a);
        a = a.mult(a);
        a = a.mult(a);
        a = a.mult(a);
        a = a.mult(a);
        a = a.mult(a);
        assertEquals("1.1790184577738583E61ft^128", a.toString());

        // big exponent / medium exponent
        Measurement p = new Measurement(2, "ft");
        p = p.mult(p);
        p = p.mult(p);
        p = p.mult(p);
        p = p.mult(p);
        p = p.mult(p);

        a = a.div(p);
        assertEquals("2.7451162640328014E51ft^96", a.toString());

        // medium exponent / big exponent... repeatedly
        p = p.div(a);
        p = p.div(a);
        p = p.div(a);
        p = p.div(a);
        p = p.div(a);
        p = p.div(a);
        assertEquals("1.0036803093069938E-299/ft^544", p.toString());


        // starting with a /s
        Measurement n = new Measurement(4,"/s");
        Measurement o = new Measurement(5, "ft");
        assertEquals("4.0/s", n.toString());
        n = n.mult(o);
        assertEquals("20.0ft/s", n.toString());
        n = n.div(o);
        assertEquals("4.0/s", n.toString());


        Measurement h = new Measurement(5, "ft").div(new Measurement(1, "m"));
        assertEquals("5.0ft/m", h.toString());

    }

    @Test
    public void BadAddSub(){
        // test a easily wrong case
        Measurement a = new Measurement(5, "m");
        Measurement b = new Measurement(4.5, "in");

        // passes if exception thrown
        assertThrows(ArithmeticException.class, () -> {
            Measurement c = new Measurement();
            c = a.add(b);
        });
        assertThrows(ArithmeticException.class, () -> {
            Measurement c = new Measurement();
            c = a.sub(b);
        });
    }
}
