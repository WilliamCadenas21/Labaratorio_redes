/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab3;

import java.math.BigInteger;

public class Lab3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(codeword("1001"));
        System.out.println(trash(codeword("1001")));
        HomeView v1 = new HomeView();
        v1.setLocationRelativeTo(null);
        v1.setVisible(true);
        
    }
    public static String trash(String s){
        String result;
        System.out.println(new BigInteger(s,2).mod(new BigInteger("1011",2)));
        /*if (!().toString(2).equals("0")){
            System.out.println("error");
        }*/
                    return "";
    }
    private static  String codeword(String binary) {
        String extra = "";
        for (int i = 0; i < "1011".length() - 1; i++) {
            extra += 0;
        }

        BigInteger a = new BigInteger(binary + extra, 2), b = new BigInteger("1011", 2);
        String result = a.mod(b).toString(2);
        for (int i = result.length(); i < "1011".length() - 1; i++) {
            result = '0' + result;
        }
        return binary + result;
    }


}
