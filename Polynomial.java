public class Polynomial{

    // fields
    double[] coefficients;

    // constructor - no arguments
    public Polynomial(){
        coefficients = new double[1];
        coefficients[0] = 0.0;
    }

    // constructor - 1 argument
    public Polynomial(double[] array){
        coefficients = new double[array.length];
        for (int i = 0; i < coefficients.length; i++){
            coefficients[i] = array[i];
        }
    }

    // methods:
    public Polynomial add(Polynomial p){
        double[] sum;
        int tracker;

        // if the original object is less than or equal to the argument
        if (p.coefficients.length >= this.coefficients.length){
            sum = new double[p.coefficients.length];
            tracker = 0;

            for (int i = 0; i < this.coefficients.length; i++){
                sum[i] = this.coefficients[i] + p.coefficients[i];
                tracker++;
            }

            for (int i = tracker; i < p.coefficients.length; i++){
                sum[i] = p.coefficients[i];
            }
        }
        // if the original object is greater than the argument
        else{
            sum = new double[this.coefficients.length];
            tracker = 0;

            for (int i = 0; i < p.coefficients.length; i++){
                sum[i] = this.coefficients[i] + p.coefficients[i];
                tracker++;
            }

            for (int i = tracker; i < this.coefficients.length; i++){
                sum[i] = this.coefficients[i];
            }
        }

        // creating and returning the new Polynomial object
        Polynomial answer = new Polynomial(sum);
        return answer;
    }

    public double evaluate(double x){
        double total = 0;
        for (int i = 0; i < coefficients.length; i++){
            double current = coefficients[i];
            total += (current*Math.pow(x, i));
        }
        return total;
    }

    public boolean hasRoot(double x){
        return evaluate(x) == 0;
    }

}