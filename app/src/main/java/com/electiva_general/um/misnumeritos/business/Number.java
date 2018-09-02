package com.electiva_general.um.misnumeritos.business;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public final class Number {

    public static final byte NUMBERS_LENGTH = 4;

    // Número de 4 dígitos: x1 x2 x3 x4
    private int x1;
    private int x2;
    private int x3;
    private int x4;


    // a) Generación del número de 4 dígitos distintos [1023:9876]
    public static Number Generate()
    {
        Number numberToGuess = new Number();
        Random randomNumber = new Random(System.currentTimeMillis());

        // Lista con los números enteros [0:9]
        ArrayList<Integer> digits = new ArrayList<Integer>(
                Arrays.asList(0,1,2,3,4,5,6,7,8,9));


        // Asignar los digitos 2,3 y 4. El dígito 1 se asigna último, dependiendo de los demás dígitos

        // El método Random.nextInt(value) devuelve un número entero aleatorio [0:value)
        // Tomar como value el ArrayList.size().
        // El número aleatorio devuelto será nuestro index

        // En x2 el entero de la posición digits[index]
        numberToGuess.x2 = digits.get(randomNumber.nextInt(digits.size()));
        // Remover el entero escogido de digits para que no se pueda repetir. Se decrementa en uno el size()
        digits.remove(numberToGuess.x2);

        // En x3 el entero de la posición digits[index]
        numberToGuess.x3 = digits.get(randomNumber.nextInt(digits.size()));
        // Remover el entero escogido de digits para que no se pueda repetir. Se decrementa en uno el size()
        digits.remove(numberToGuess.x3);

        // En x4 el entero de la posición digits[index]
        numberToGuess.x4 = digits.get(randomNumber.nextInt(digits.size()));
        // Remover el entero escogido de digits para que no se pueda repetir. Se decrementa en uno el size()
        digits.remove(numberToGuess.x4);

        // Asignación del dígito 1.
        // No puede ser 0xxx
        if (digits.get(0) == 0)
            digits.remove(0);

        // En x1 el entero de la posición digits[index]
        numberToGuess.x1 = digits.get(randomNumber.nextInt(digits.size()));

        return numberToGuess;
    }

    private Number(){
    }

    public Number(final Integer x1,final Integer x2,final Integer x3, final Integer x4){
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getX3() {
        return x3;
    }

    public int getX4() {
        return x4;
    }

    @Override
    public String toString() {
        return  "" + x1 + "" + x2 + "" + x3 + "" + x4;
    }

}
