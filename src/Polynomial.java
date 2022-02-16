import java.util.Objects;

public class Polynomial {
    float[] cfs; // esto atributos polynomio

    // Constructor por defecto. Genera un polinomio cero
    public Polynomial() {
        this.cfs = new float[]{0};
    }

    // Constructor a partir de los coeficientes del polinomio en forma de array
    public Polynomial(float[] cfs) {
        // TODO: asegurarnos que no hay ceros no significaati
        this.cfs = invertArray(cfs);
    }

    // Función o método para invertir un array de floats
    private float[] invertArray(float[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            float temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
        return array;
    }

    // Constructor a partir de un string
    public Polynomial(String s) {
        this.cfs = getCfs(s);
    }

    private float[] getCfs(String s) {
        String[] sToArray = s.split(" ");

        int maxExp = getMaxExp(sToArray);

        float[] cfs = new float[maxExp + 1];

        for (int i = 0; i < sToArray.length; i += 2) {
            int position = getExpValue(sToArray[i]);
            float cfsValue = getCfsValue(sToArray[i]);
            if (i > 1 && sToArray[i - 1].equals("-")) cfsValue *= -1;
            cfs[position] += cfsValue;
        }
        return cfs;
    }

    private float getCfsValue(String s) {
        if (s.contains("x^")) {
            String[] iToArray = s.split("x\\^");
            if (iToArray[0].equals("")) return 1;
            if (iToArray[0].equals("-")) return -1;
            return Integer.parseInt(iToArray[0]);
        }
        if (s.contains("x")) {
            if (s.length() > 1) {
                String[] iToArray = s.split("x");
                if (iToArray[0].equals("")) return 1;
                return Integer.parseInt(iToArray[0]);
            }
            return 1;
        }

        return Float.parseFloat(s);
    }

    private int getMaxExp(String[] sToArray) {
        int maxExp = 0, actualExp;
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

    // Suma el polinomio con otro. No modifica el polinomio actual (this). Genera uno nuevo
    public Polynomial add(Polynomial p) {
        String pToString = p.toString();
        float[] pCfs = getCfs(pToString);

        int maxExpP = pCfs.length - 1;
        int maxExpThis = this.cfs.length - 1;

        int maxExpAll = Math.max(maxExpP, maxExpThis);

        float[] result = new float[maxExpAll + 1];

        for (int i = 0; i < result.length; i++) {
            if (i < pCfs.length) result[i] += pCfs[i];
            if (i < this.cfs.length) result[i] += this.cfs[i];
        }

        invertArray(result);
        return new Polynomial(result);
    }

    // Multiplica el polinomio con otro. No modifica el polinomio actual (this). Genera uno nuevo
    public Polynomial mult(Polynomial p2) {
        float[] result = new float[(this.cfs.length) * (p2.cfs.length)];

        for (int i = 0; i < this.cfs.length; i++) {
            for (int j = 0; j < p2.cfs.length; j++) {
                result[i + j] += (this.cfs[i] * p2.cfs[j]);
            }
        }

        invertArray(result);
        return new Polynomial(result);
    }

    // Divide el polinomio con otro. No modifica el polinomio actual (this). Genera uno nuevo
    // Vuelve el cociente y también el residuo (ambos polinomios)
    public Polynomial[] div(Polynomial p2) { //8.0, 0.0, -6.0, 0.0, 1.0 entre -1, 1 coeficiente 1, 1, -5, -5 resto 3
        float[] dividendo = this.cfs;
        float[] divisor = p2.cfs;
        float[] cociente = new float[this.cfs.length + p2.cfs.length];




        System.out.println("\nDividedo:");
        for (int i = 0; i < dividendo.length; i++) {
            System.out.print(dividendo[i] + ", ");
        }
        System.out.println("\nDivisor:");
        for (int i = 0; i < divisor.length; i++) {
            System.out.print(divisor[i] + ", ");
        }
        return null;
    }

    // Encuentra las raíces del polinomio, ordenadas de menor a mayor
    public float[] roots() {
        return null;
    }

    // Vuelve "true" si los polinomios son iguales. Esto es un override de un método de la clase Object
    @Override
    public boolean equals(Object o) {
        Polynomial p = (Polynomial) o;
        Polynomial p2 = this;

        return p.toString().equals(p2.toString());
    }

    // Vuelve la representación en forma de String del polinomio. Override de un método de la clase Object
    @Override
    public String toString() {
        String x, result = "";
        float[] cfs = this.cfs;
System.out.println("-------");
        for (int i = 0; i < this.cfs.length; i++) {
            System.out.print(this.cfs[i] + ", ");
        }

        for (int i = 0; i < cfs.length; i++) {
            if (cfs[i] != 0) {
                x = ponerX(i);

                // Si no es el último numero de la array
                if (i + 1 < cfs.length) {
                    // Si es un numero positivo
                    if ((int) cfs[i] > 1) {
                        result = " + " + (int) cfs[i] + x + result;
                    } else if (cfs[i] == 1 && cfs[i + 1] == 1) {
                        result = " + " + x + result;
                    } else if (cfs[i] == 1) {
                        result = x + result;
                    } else if (i == 2 && cfs[2] == -1) {
                        result = " - " + x + result;
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

        if (result.length() > 1 && (result.charAt(1) == '+' || result.charAt(1) == '-'))
            result = resultWithNo0(result);

        if (result.equals("")) return "0";
        return result;
    }

    private String resultWithNo0(String result) {
        char splitResult = result.charAt(1);
        String finalResult = result;

        int position = result.length();

        if (splitResult == '+') finalResult = result.substring(3, position);
        if (splitResult == '-') finalResult = "-" + result.substring(3, position);

        return finalResult;
    }

    private String ponerX(int i) {
        if (i == 0) return "";
        if (i == 1) return "x";
        return "x^" + i;
    }
}