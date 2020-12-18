/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tb08_sudoku;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gustavo
 */
public class TB08_Sudoku {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Data dados = new Data("C:\\Users\\Gustavo\\Documents\\NetBeansProjects\\TB08_Sudoku\\src\\tb08_sudoku\\instancia.txt");

        try {
            IloCplex model = new IloCplex();

            IloNumVar[][][] S = new IloNumVar[9][9][10];

            //Inciando a matriz resposta:
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    for (int m = 0; m < 10; m++) {
                        S[i][j][m] = model.boolVar();
                    }
                }
            }

            //Aplicando a instância na matriz resposta:
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (dados.matrizP[i][j] != 0) {
                        model.addEq(S[i][j][dados.matrizP[i][j]], 1);
                    }
                }
            }

            //Nenhuma linha pode ter um número repetido 
            for (int i = 0; i < 9; i++) {
                for (int m = 1; m < 10; m++) {
                    IloLinearNumExpr restri = model.linearNumExpr();
                    for (int j = 0; j < 9; j++) {
                        restri.addTerm(1, S[i][j][m]);
                    }
                    model.addEq(restri, 1);
                }
            }

            //Nenhuma coluna pode ser repetida:
            for (int j = 0; j < 9; j++) {
                for (int m = 1; m < 10; m++) {
                    IloLinearNumExpr restri1 = model.linearNumExpr();
                    for (int i = 0; i < 9; i++) {
                        restri1.addTerm(1, S[i][j][m]);
                    }
                    model.addEq(restri1, 1);
                }
            }

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    IloLinearNumExpr restri2 = model.linearNumExpr();
                    for (int m = 1; m < 10; m++) {
                        restri2.addTerm(1, S[i][j][m]);
                    }
                    model.addEq(restri2, 1);
                }
            }


            for (int i = 0; i < 9; i += 3) {
                for (int j = 0; j < 9; j += 3) {
                    for (int m = 1; m < 10; m++) {
                        IloLinearNumExpr restri3 = model.linearNumExpr();
                        for (int i2 = i; i2 < (i+3); i2++) {
                            for (int j2 = j; j2 < (j+3); j2++) {
                                restri3.addTerm(1, S[i2][j2][m]);
                            }
                        }
                        model.addEq(restri3, 1);
                    }
                }
            }

            //Resolvendo o problema..
            if (model.solve()) {

                System.out.println("---------------");
                System.out.println(model.getStatus());
                System.out.println(model.getObjValue());
                System.out.println("---------------");

                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        int valor = 0;
                        for (int m = 1; m < 10; m++) {
                            valor += model.getValue(S[i][j][m]) * m;
                        }
                        System.out.print(valor + " ");
                    }
                    System.out.println();
                }

            } else {
                System.out.println("Não vai dar n.");
            }
        } catch (IloException ex) {
            Logger.getLogger(TB08_Sudoku.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
