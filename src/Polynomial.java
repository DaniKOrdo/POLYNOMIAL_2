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

    // Constructor a partir d'un string
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

    // Suma el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
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

    // Multiplica el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial mult(Polynomial p2) {
        String pToString = p2.toString();
        float[] pCfs = getCfs(pToString);

        float[] result = new float[(this.cfs.length) * (pCfs.length)];

        for (int i = 0; i < this.cfs.length; i++) {
            for (int j = 0; j < pCfs.length; j++) {
                result[i + j] += (this.cfs[i] * pCfs[j]);
            }
        }

        invertArray(result);
        return new Polynomial(result);
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
    public String toString() {
        String x, result = "";
        float[] cfs = this.cfs;

        for (int i = 0; i < cfs.length; i++) {
            if (cfs[i] != 0) {
                x = ponerX(i);

                // Si no es el último numero de la array
                if (i + 1 < cfs.length) {
                    // Si es un numero positivo
                    if ((int) cfs[i] > 1) {
                        result = " + " + (int) cfs[i] + x + result;
                    } else if (cfs[i] == 1) {
                        result = x + result;
                    } else {
                        if (i == 2 && (cfs[2] == 1 || cfs[2] == -1)) result = " - " + x + result;
                        else result = " - " + ((int) cfs[i] * -1) + x + result;
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