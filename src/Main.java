import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //CONSTANTS
        final int MIN   = 1;            // Se usa para tener contabilizado el mínimo número de bolas.
        final int MAX   = 90;           // Se usa para tener contabilizado el máximo número  de bolas.
        final int ROWS  = 3;            // Se usa para tener contabilizado el número de filas totales del cartón.
        final int POS   = 5;            // Se usa para tener contabilizado el número de posiciones del cartón.
        final int NUMS = ROWS * POS;    // Se usa para tener contabilizado el número totales de números encontrados en un cartón ( filas * posiciones).
        final int FIRSTLINE     = 0;    // Se usa para controlar las bolas acertadas de la posición  1 a  5, o sea la primera fila del cartón.
        final int SECONDLINE    = 5;    // Se usa para controlar las bolas acertadas de la posición  5 a 10, o sea la segunda fila del cartón.
        final int THIRDLINE     = 10;   // Se usa para controlar las bolas acertadas de la posición 10 a 15, o sea la tercera fila del cartón.
        //VARIABLES
        // Integers
        int numbr;                      // Variable que indica el número de cartones a jugar.
        int count = 0;
        int ballsUse = 0;
        // Variable que controla el número de bingos.
        //ARRAYS

        int plays[][];
        int lineWin[][];
        int bingWin[][];
        int ballsSecuence[] = new int[MAX];
        int ballsOut[] = new int[MAX];// en este array se recogen el orden de las 90 bolas que saldrán.
        System.out.println("\n Bienvenido al Bingo clásico \n");
        do {
            System.out.printf(" Insert number of games : ");
            numbr = input.nextInt();            // Insert number of game plays. Número de cartones
            if (numbr<0) {
                System.out.println(" Porfavor vuelva a introducir un valor que no sea negativo");
                System.out.println(" ");
            }
        }while (numbr<0);
        plays = new int[numbr][NUMS];       // and select for two arrays. Cada cartón con sus 15 espacios.
        lineWin = new int[numbr][POS];      // in first dimension the prev Int
        bingWin = new int[numbr][ROWS];
        // Se rellena cada uno de los cartones con números aleatorios de menor a mayor sin que se repitan
        for (int i = 0; i < plays.length; i++) {
            fillSortedArray(plays[i], NUMS, MIN, MAX);
        }
        //  Función para imprimir el cartón o los cartones con los que se juega
        printArray(plays, POS);
        //  Función que simula el sacar las bolas.
        balls(ballsSecuence, MAX);
        //  En este bucle se muestran las bolas que van saliendo, que ya están puestas en el array ballsSecuence
        for (int i = 0; i < ballsSecuence.length; i++) {    // este bucle hace que salgan las 90 bolas, poner || ballsSecuence == false
            if (count != 1) {
                ballsOut[i] = ballsSecuence[i];
                ballsUse=i;
                System.out.println("La bola número " + ((i + 1) + " es : " + ballsSecuence[i]));
                for (int j = 0; j < numbr; j++) {               // Bucle para operar en cada uno de los cartones
                    for (int m = 0; m < NUMS; m++) {            //  Bucle para operar con cada una de las posiciones del cartón en el que nos encontramos
                        if (ballsSecuence[i] == plays[j][m]) {  // Comprobar bola con todas las posiciones del cartón


                            if (m >= THIRDLINE) {               //Las 5 últimas posiciones m hace referencia a la posicion del for anterior.
                                lineWin[j][2]++;                // si la bola pertenece a la tercera linea, el numero de carton J la linea 2 ++
                                System.out.println("Encontrado en el cartón " + (j + 1) + " en la linea 3 "+ "\n" );
                                if (lineWin[j][2] == 5) {       // cuando lo de arriba valga 5 tenemos linea
                                    bingWin[j][0]++;
                                    if (bingWin[j][0] != 3) {
                                        System.out.println("Linea 3 en el cartón " + (j + 1) + "\n");
                                    }
                                }
                            } else if (m >= SECONDLINE && m < THIRDLINE) {       //Las posiciones entre 5 y 10 del cartón
                                lineWin[j][1]++;
                                System.out.println("Encontrado en el cartón " + (j + 1) + "  en la linea 2"+ "\n" );
                                if (lineWin[j][1] == 5) {
                                    bingWin[j][0]++;
                                    if (bingWin[j][0] != 3) {
                                        System.out.println("Linea 2 en el cartón " + (j + 1) + "\n");
                                    }
                                }
                            } else{      //Las primeras 5 posiciones del cartón
                                lineWin[j][0]++;
                                System.out.println("Encontrado en el cartón " + (j + 1) + "  en la linea 1"+ "\n" );
                                if (lineWin[j][0] == 5) {
                                    bingWin[j][0]++;
                                    if (bingWin[j][0] != 3) {
                                        System.out.println("Linea 1 en el cartón " + (j + 1) + "\n");
                                    }
                                }
                            }
                        }   // Fin comprobar bola con todas las posiciones del cartón
                    }
                }
                for (int j = 0; j < numbr; j++) {           //Está aquí para esperar que todos los cartones miren si tienen el número y despues comprueba si tienen todos bingo
                    for (int k = 0; k < ROWS; k++) {
                        if (bingWin[j][k] == ROWS) {
                            if (count!=1) {
                                count++;
                                System.out.println("Bingo"+"\n");
                                System.out.println("");
                            }
                            System.out.println("El cartón ganador es el " + (j + 1) + "\n");

                            for (int l = 0; l < plays[j].length; l++) {
                                System.out.printf("%3d",plays[j][l]);
                                if ((l+1) % POS == 0){
                                    System.out.println();
                                }
                            }
                            System.out.println(" ");
                            System.out.println(" ");
                        }
                    }
                }
            }
        }
        System.out.println(" ");
        System.out.println("Y las bolas que han salido son:\n");      //Esta aqui para que no haya problema con las demás bolas que salen y no son vistas
        for (int l = 0; l <= ballsUse; l++) {
            // if (ballsSecuence[l] != 0)
            //{
            System.out.printf("%3d",ballsSecuence[l] );
            if ((l+1) % (POS+POS) == 0){
                System.out.println();
            }
        }
    }

    // crea un cartón con números ordendos aleatorios de menor a mayor sin repeticiones por cada vez que se llame a esta función
    static void fillSortedArray(int[] plays, int numsCopy, int minCopy, int maxCopy) {
        int arrayCopy[] = new int[numsCopy];
        boolean bol = false;
        do {
            for (int j = 0; j < arrayCopy.length; j++) {                //generate numbers
                arrayCopy[j] = (int) (Math.random() * (maxCopy) + minCopy);
            }
            Arrays.sort(arrayCopy);                                     //order numbers
            for (int k = 1; k < arrayCopy.length; k++) {                    //Check that any number content in array is repeated
                if (arrayCopy[k] == arrayCopy[k - 1]) {
                    bol = false;
                    break;
                } else if (k <= arrayCopy.length) {
                    bol = true;
                }
            }
        } while (!bol);
        for (int c = 0; c < arrayCopy.length; c++) {             // When all numbers content in arrayCopy are different,
            plays[c] = arrayCopy[c];                            // we charge the arraycopy  to our principal array
        }
    }
    /**
     * Función para imprimir el cartón o los cartones con los que se juega
     * @param array es el array plays, que vienen a ser los cartones
     * @param colsCopy: copia de POS
     */
    static void printArray(int array[][], int colsCopy) {
        for (int i = 0; i < array.length; i++) {
            System.out.println("\n Game number " + (i + 1));
            System.out.println();
            for (int j = 0; j < array[i].length; j++) {
                System.out.printf("%3d", array[i][j]);
                if ((j + 1) % colsCopy == 0) { // Esta parte hace que se impriman las casillas del array de 5 en 5; cuando j vale 4, + 1 == 5, 5 % 5 == 0 y entonces salto de línea y así
                    System.out.println();
                }
            }
            System.out.println();
        }
    }
    /**
     *  En esta función se rellena un array que contendrá las bolas que saldrán y su orden. Simula el sacar bola.
     * @param bingoCopy
     * @param MAX
     */
    static void  balls (int[] bingoCopy,final int MAX) {
        int num;
        int maxAux=MAX;
        int ballsSorted[]=new int [MAX];
        int ballsCopy [] = new int [MAX];
        Random  rnd = new Random();
        for (int i = 0; i < ballsSorted.length; i++) {
            ballsSorted[i] = i + 1;
        }
        for (int i = 0; i < ballsCopy.length; i++) {
            // probar con el otro random a ver si da el mismo resultado
            num = rnd.nextInt(maxAux);
            ballsCopy[i] = ballsSorted[num];
            ballsSorted[num] = ballsSorted[maxAux - 1];
            maxAux--;
        }
        for (int i = 0; i < ballsCopy.length; i++) {
            bingoCopy[i]= ballsCopy[i];
        }
    }
}