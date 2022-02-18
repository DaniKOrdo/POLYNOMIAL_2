public class Polynomial {
    // Atributo del polinomio
    float[] cfs;

    // Constructor por defecto. Genera un polinomio cero
    public Polynomial() {
        this.cfs = new float[]{0};
    }

    // Constructor a partir de los coeficientes del polinomio en forma de array
    public Polynomial(float[] cfs) {
        if (cfs.length > 1 && cfs[0] == 0) cfs = removeZeros(cfs);
        this.cfs = invertArray(cfs);
    }


    private float[] removeZeros(float[] cfs) {
        // Buscamos la posición del primer número que no sea 0
        int count = 0;

        for (int i = 0; i < cfs.length; i++) {
            if (cfs[i] != 0) break;
            count++;
        }

        // Copiamos los números a partir del contador en adelante
        float[] newCfs = new float[cfs.length - count];

        for (int i = 0; i < newCfs.length; i++) {
            newCfs[i] = cfs[i + count];
        }

        return newCfs;
    }

    // Función/método para invertir un array de floats
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

        // Los números impares de sToArray son los valores y los pares los símbolos (+ -)
        for (int i = 0; i < sToArray.length; i += 2) {
            int expValue = getExpValue(sToArray[i]);
            float cfsValue = getCfsValue(sToArray[i]);

            // Si tiene un - delante del número el valor se convierte en negativo
            if (i > 1 && sToArray[i - 1].equals("-")) cfsValue *= -1;
            cfs[expValue] += cfsValue;
        }

        // En caso de que el array tenga ceros a la izquierda innecesarios se quitan
        invertArray(cfs);
        if (cfs.length > 1 && cfs[0] == 0) cfs = removeZeros(cfs);
        return invertArray(cfs);
    }

    private float getCfsValue(String s) {
        // Se mira si s contiene x^ o x, y se devuelve el valor
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
                if (iToArray[0].equals("-")) return -1;
                return Integer.parseInt(iToArray[0]);
            }
            return 1;
        }

        // Si llega hasta aquí significa que no es un monomio de ningún grado y se devuelve el valor
        return Float.parseFloat(s);
    }

    // Función para buscar el máximo exponente
    private int getMaxExp(String[] sToArray) {
        int maxExp = 0, actualExp;
        for (int i = 0; i < sToArray.length; i += 2) {
            actualExp = getExpValue(sToArray[i]);
            if (actualExp > maxExp) maxExp = actualExp;
        }
        return maxExp;
    }

    // Función para buscar el valor del exponente
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

        // Se obtiene el mayor exponente para crear el array result
        int maxExpP = pCfs.length - 1;
        int maxExpThis = this.cfs.length - 1;

        int maxExpAll = Math.max(maxExpP, maxExpThis);

        float[] result = new float[maxExpAll + 1];

        // Se mira si el la longitud (i) de result es menor que la de los 2 cfs y se suma el valor a result
        for (int i = 0; i < result.length; i++) {
            if (i < pCfs.length) result[i] += pCfs[i];
            if (i < this.cfs.length) result[i] += this.cfs[i];
        }

        // En caso de que el array tenga ceros a la izquierda innecesarios se quitan
        invertArray(result);
        if (result.length > 1 && result[0] == 0) result = removeZeros(result);
        return new Polynomial(result);
    }

    // Multiplica el polinomio con otro. No modifica el polinomio actual (this). Genera uno nuevo
    public Polynomial mult(Polynomial p2) {
        float[] result = new float[(this.cfs.length) * (p2.cfs.length)];

        // Se multiplica el valor de (i) de con todos los valores de (j) de p2
        for (int i = 0; i < this.cfs.length; i++) {
            for (int j = 0; j < p2.cfs.length; j++) {
                result[i + j] += (this.cfs[i] * p2.cfs[j]);
            }
        }

        // En caso de que el array tenga ceros a la izquierda innecesarios se quitan
        invertArray(result);
        if (result.length > 1 && result[0] == 0) result = removeZeros(result);
        return new Polynomial(result);
    }

    // Divide el polinomio con otro. No modifica el polinomio actual (this). Genera uno nuevo
    // Vuelve el cociente y también el residuo (ambos polinomios)
    public Polynomial[] div(Polynomial p2) {
        float[] dividendo = invertArray(this.cfs);
        float[] divisor = invertArray(p2.cfs);
        float[] cociente = new float[dividendo.length - (divisor.length - 1)];
        float[] resto = new float[divisor.length];

        // Se divide el dividendo (i) entre el primer divisor para tener el cociente
        // El resto es la resta del dividendo correspondiente con el divisor * el primer cociente
        // Se actualiza el dividendo mediante el resto
        for (int i = 0; i < cociente.length; i++) {
            cociente[i] = dividendo[i] / divisor[0];
            for (int j = 0; j < resto.length; j++) {
                resto[j] = dividendo[j + i] - (divisor[j] * cociente[i]);
                dividendo[i + j] = resto[j];
            }
        }

        Polynomial[] res = new Polynomial[2];
        res[0] = new Polynomial(cociente);
        res[1] = new Polynomial(resto);
        return res;
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
        float[] cfs = this.cfs;

        String x, result = "";

        for (int i = 0; i < cfs.length; i++) {
            if (cfs[i] != 0) {
                x = putLetterX(i);

                // Si no es el último numero de la array
                if (i + 1 < cfs.length) {
                    // Números positivos
                    if (cfs[i] > 1) {
                        result = " + " + (int) cfs[i] + x + result;
                    } else if (cfs[i] == 1) {
                        result = " + " + x + result;
                    }
                    // Números negativos
                    else if (i > 0 && cfs[i] == -1) {
                        result = " - " + x + result;
                    } else {
                        result = " - " + ((int) cfs[i] * -1) + x + result;
                    }
                } else {
                    // Si es el último número de la array
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

        if (result.equals("")) return "0";
        return result;
    }

    // Función para devolver el exponente
    private String putLetterX(int i) {
        if (i == 0) return "";
        if (i == 1) return "x";
        return "x^" + i;
    }
}