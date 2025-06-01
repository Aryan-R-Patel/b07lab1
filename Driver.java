import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Driver {
    public static void main(String [] args) throws FileNotFoundException {
        // test - contructor 1
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double[] c1 = {6, 5};
        int[] e1 = {0, 3};
        // test - contructor 2
        Polynomial p1 = new Polynomial(c1, e1);
        double [] c2 = {-2, -9};
        int[] e2 = {1, 4};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);  // add
        System.out.println("s(0.1) = " + s.evaluate(0.1));  // evaluate
        if(s.hasRoot(1))  // hasRoot
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        // multiply
        double[] multCoefficient1 = {-2, -3, 4}; 
        int[] multExponent1 = {0, 1, 2}; 
        Polynomial m1 = new Polynomial(multCoefficient1, multExponent1);
        double[] multCoefficient2 = {-1, 5};
        int[] multExponent2 = {0, 2};
        Polynomial m2 = new Polynomial(multCoefficient2, multExponent2);

        Polynomial m3 = m1.multiply(m2);

        // save to file
        m3.saveToFile("testOutput.txt");

        // test - contructor 3
        File file = new File("testOutput.txt");
        Polynomial fileP = new Polynomial(file);
        System.out.println("Coefficients: " + Arrays.toString(fileP.coefficients));
        System.out.println("Exponents: " + Arrays.toString(fileP.exponents));
    }
}