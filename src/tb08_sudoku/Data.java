/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tb08_sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Gustavo
 */
public class Data {
    public int[][] matrizP = new int[9][9];
    public int n = 9, m = 9;

    public Data(String path) throws FileNotFoundException, IOException{
        BufferedReader scan = new BufferedReader(new FileReader(path));
        String partes = scan.readLine();
        String[] mano = null;

        for (; partes != null;) {
            mano = partes.split(",");
            System.out.println(mano[0]);
            int linha = Integer.parseInt(mano[0]);
            int column = Integer.parseInt(mano[1]);
            int valor = Integer.parseInt(mano[2]);
            this.matrizP[linha][column] = valor;
            partes = scan.readLine();
        }
    }
}