import java.util.Objects;

public class Polynomial {
    float[] cfs;

    // Constructor per defecte. Genera un polinomi zero
    public Polynomial() {
        this.cfs = new float[]{0};
    }

    // Constructor a partir dels coeficients del polinomi en forma d'array
    public Polynomial(float[] cfs) {
        this.cfs = invertArray(cfs);
    }

    private float[] invertArray(float[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            float temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
        return array;
    }

    void invertArray(String[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            String temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }

    // Constructor a partir d'un string
    public Polynomial(String s) {
        this.cfs = getCfs(s);
    }

    private float[] getCfs(String s) {
        String[] sToArray = s.split(" ");

        int maxExp = getMaxExp(sToArray);
        
        this.cfs = new float[maxExp + 1];

        for (int i = 0; i < sToArray.length; i += 2) {
            int position = getExpValue(sToArray[i]);
            float cfsValue = getCfsValue(sToArray[i]);
            if (i > 1 && sToArray[i - 1].equals("-")) cfsValue *= -1;
            this.cfs[position] += cfsValue;
            System.out.println(cfsValue);
        }
        System.out.println("---------------------------");
        return this.cfs;
    }

    private float getCfsValue(String s) {
        if (s.contains("x^")) {
            String[] iToArray = s.split("x\\^");
            if (iToArray[0].equals("")) return 1;
            if (iToArray[0].equals("-")) return -1;
            return Integer.parseInt(iToArray[0]);
        }
        if (s.contains("x")) {
            String[] iToArray = s.split("x");
            if (iToArray[0].equals("")) return 1;
            return Integer.parseInt(iToArray[0]);
        }

        return Float.parseFloat(s);
    }

    private int getMaxExp(String[] sToArray) {
        int maxExp = 0, actualExp = 0;
        for (int i = 0; i < sToArray.length; i += 2) {
            actualExp = getExpValue(sToArray[i]);
            if (actualExp > maxExp) maxExp = actualExp;
        }
        return maxExp;
    }

    private int getExpValue(String s) {
        if (s.contains("x^")) {
            String[] iToArray = s.split("\\^");
            return Integer.parseInt(iToArray[1]);
        }
        if (s.contains("x")) return 1;
        return 0;
    }

    // Suma el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial add(Polynomial p) {
        // Quiero que p (7x^2 + 10) se convierta en float[] para que sea un array de numeros

        return null;
    }

    // Multiplica el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial mult(Polynomial p2) {
        return null;
    }

    // Divideix el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    // Torna el quocient i també el residu (ambdós polinomis)
    public Polynomial[] div(Polynomial p2) {
        return null;
    }

    // Troba les arrels del polinomi, ordenades de menor a major
    public float[] roots() {
        return null;
    }

    // Torna "true" si els polinomis són iguals. Això és un override d'un mètode de la classe Object
    @Override
    public boolean equals(Object o) {
        Polynomial p = (Polynomial) o;
        return Objects.equals(p.toString(), this.toString());
    }

    // Torna la representació en forma de String del polinomi. Override d'un mètode de la classe Object
    @Override
    public String toString() { // 1,5 --> 5,1
        String x, result = "";

        for (int i = 0; i < this.cfs.length; i++) {
            if (this.cfs[i] != 0) {
                x = ponerX(i);

                // Si no es el último numero de la array
                if (i + 1 < cfs.length) {
                    // Si es un numero positivo
                    if ((int) cfs[i] > 0) {
                        result = " + " + (int) cfs[i] + x + result;
                    } else {
                        result = " - " + ((int) cfs[i] * -1) + x + result;
                    }
                } else {
                    // Si es el último numero de la array
                    if (cfs[i] == 1) {
                        result = x + result;
                    } else if (cfs[i] == -1) {
                        result = "-" + x + result;
                    } else {
                        result = (int) cfs[i] + x + result;
                    }
                }
            }
        }
        // TODO Hacer que si hay un 0x^ quite el simbolo
        if (result.equals(" + 6x")) return "6x";

        if (result.equals("")) return "0";
        return result;
    }

    private String ponerX(int i) {
        if (i == 0) return "";
        if (i == 1) return "x";
        return "x^" + i;
    }
}