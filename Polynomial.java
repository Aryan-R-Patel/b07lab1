import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Polynomial{
    // fields
    double[] coefficients;
    int[] exponents;

    // constructor - no arguments
    public Polynomial(){
        coefficients = new double[0];
        exponents = new int[0];
    }

    // constructor - 1 argument
    public Polynomial(File file){
        try{
            Scanner s = new Scanner(file);
            String p = s.nextLine();

            p = p.replace("-", "+-");

            String[] data = p.split("[+]");

            this.coefficients = new double[data.length];
            this.exponents = new int[data.length];

            for (int i = 0; i < data.length; i++){
                String[] inner = data[i].split("[x]");

                double coefficient = Double.parseDouble(inner[0]);
                int exponent = 0;
                if (inner.length == 2){
                    exponent = Integer.parseInt(inner[1]);
                }

                this.coefficients[i] = coefficient;
                this.exponents[i] = exponent;
            }

            s.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // constructor - 2 arguments
    public Polynomial(double[] coefficients, int[] exponents){
        this.coefficients = new double[coefficients.length];
        for (int i = 0; i < coefficients.length; i++){
            if (coefficients[i] != 0.0){
                this.coefficients[i] = coefficients[i];
            }
        }

        this.exponents = new int[exponents.length];
        for (int i = 0; i < exponents.length; i++){
            this.exponents[i] = exponents[i];
        }
    }

    // methods:
    public Polynomial add(Polynomial p){
        double[] coefficients1 = this.coefficients;
        double[] coefficients2 = p.coefficients;
        int[] exponents1 = this.exponents;
        int[] exponents2 = p.exponents;

        double[] temp_coefficients = new double[coefficients1.length+coefficients2.length];
        int[] temp_exponents = new int[exponents1.length+exponents2.length];

        for (int i = 0; i < exponents1.length; i++){
            temp_exponents[i] = exponents1[i];
            temp_coefficients[i] = coefficients1[i];
        }

        // add another array
        int start = exponents1.length;
        for (int i = 0; i < exponents2.length; i++){
            boolean duplicate = false;
            for (int j = 0; j < temp_exponents.length; j++){
                if (temp_exponents[j] == exponents2[i]){
                    temp_coefficients[j] += coefficients2[i];
                    duplicate = true;
                    break;
                }
            }

            if (!duplicate){
                temp_exponents[start] = exponents2[i];
                temp_coefficients[start] = coefficients2[i];
                start++;
            }
        }

        // now simply refine the final answer by checking for 0 coefficients in the existing arrays
        int countNonZero = 0;
        for (int i = 0; i < start; i++){
            if (temp_coefficients[i] != 0){
                countNonZero++;
            }
        }

        double[] final_coefficients = new double[countNonZero];
        int[] final_exponents = new int[countNonZero];

        int tracker = 0;
        for (int i = 0; i < start; i++){
            if (temp_coefficients[i] != 0){
                final_coefficients[tracker] = temp_coefficients[i];
                final_exponents[tracker] = temp_exponents[i];
                tracker++;
            }
        }

        return new Polynomial(final_coefficients, final_exponents);
    }

    public double evaluate(double x){
        double total = 0;
        for (int i = 0; i < this.coefficients.length; i++){
            double coefficient = this.coefficients[i];
            int exponent = this.exponents[i];
            total += (coefficient*Math.pow(x, exponent));
        }
        return total;
    }

    public boolean hasRoot(double x){
        return evaluate(x) == 0;
    }

    public Polynomial multiply(Polynomial p){
        double[] coefficients1 = this.coefficients;
        double[] coefficients2 = p.coefficients; 
        int[] exponents1 = this.exponents;
        int[] exponents2 = p.exponents; 

        double[] temp_coefficients = new double[coefficients1.length*coefficients2.length];
        int[] temp_exponents = new int[exponents1.length*exponents2.length];

        // add to temp_coefficients and temp_exponents
        int tracker = 0;
        for (int i = 0; i < coefficients1.length; i++){
            for (int j = 0; j < coefficients2.length; j++){
                temp_coefficients[tracker] = coefficients1[i]*coefficients2[j];
                temp_exponents[tracker] = exponents1[i]+exponents2[j];
                tracker++;
            }
        }

        // remove the duplicates
        for (int i = 0; i < temp_exponents.length-1; i++){
            for (int j = i+1; j < temp_exponents.length; j++){
                if (temp_exponents[i] == temp_exponents[j]){
                    temp_coefficients[i] += temp_coefficients[j];
                    temp_coefficients[j] = 0;
                }
            }
        }

        // remove zero elements
        int countNonZero = 0;
        for (int i = 0; i < temp_coefficients.length; i++){
            if (temp_coefficients[i] != 0){
                countNonZero++;
            }
        }

        double[] final_coefficients = new double[countNonZero];
        int[] final_exponents = new int[countNonZero];

        int t = 0;
        for (int i = 0; i < temp_coefficients.length; i++){
            if (temp_coefficients[i] != 0){
                final_coefficients[t] = temp_coefficients[i];
                final_exponents[t] = temp_exponents[i];
                t++;
            }
        }

        return new Polynomial(final_coefficients, final_exponents);
    }

    public void saveToFile(String file) throws FileNotFoundException{
        PrintStream ps = new PrintStream(new File(file));
        String polynomial = "";

        for (int i = 0; i < this.coefficients.length; i++){
            if (this.coefficients[i] == 0.0){
                continue;
            }
            if(this.coefficients[i] > 0){
                polynomial += "+";
            }
            // note: if you type case to int then you lose the decimal part
            polynomial += String.valueOf(this.coefficients[i]);
            if (this.exponents[i] == 0){
                continue;
            }
            polynomial += "x";
            polynomial += String.valueOf(this.exponents[i]);
        }

        if (polynomial.length() > 0){
            if (polynomial.charAt(0) == '+'){
                ps.println(polynomial.substring(1));
            }
            else{
                ps.println(polynomial);
            }
        }

        ps.close();
    }
}