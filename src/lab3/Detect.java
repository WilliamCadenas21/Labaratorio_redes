package lab3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 * The class is focus in the task of detect errors in the message
 *
 * @version 1.0
 * @author Willian Garcia
 */
public class Detect {

    String msg, outname, pol;
    File file;

    public Detect(File ff, String out, String pol) {
        file = ff;
        msg = "";
        this.outname = out;
        this.pol = pol;
        try (FileReader f = new FileReader(ff.getAbsolutePath())) {
            BufferedReader b = new BufferedReader(f);
            String temp;
            while ((temp = b.readLine()) != null) {
                msg += temp;
            }
            b.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex);
        }
    }

    public void reception() {
        BufferedReader br = null;
        String datawords="";
        boolean correct=true;
        try {
            br = new BufferedReader(new FileReader(file));
            String codeword;
            String pol= br.readLine();
            System.out.println(pol);
            while ((codeword = br.readLine()) != null) {
                if (!(new BigInteger(codeword,2).mod(new BigInteger(pol,2))).toString(2).equals("0")){
                    correct=false;
                    System.out.println(codeword);
                }
                
                datawords+=codeword.substring(0, codeword.length()-pol.length()+1)+"\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        if(correct){
            System.out.println("Correcto");
        }else{
            System.out.println("incorrecto");
        }
    }

    /**
     *
     */
    public void send() {
        String msg = this.msg, bmsg = "";

        int sz = msg.length() / 16;
        boolean extra = false;
        if (msg.length() % 16 != 0) {
            extra = true;
        }
        int i = 0;
        while (i < sz) {
            i++;
            bmsg += codeword(binBlock(msg.substring(16 * (i - 1), 16 * i))) + "\n";
        }
        if (extra) {
            bmsg += codeword(binBlock(msg.substring(16 * (i - 1), 16 * i))) + "\n";
        }

        String temp[] = file.getAbsolutePath().split(Pattern.quote("\\")), route = "";
        i = 0;
        while (i < temp.length - 1) {
            route += temp[i] + "\\\\";
            i++;
        }
        route += outname;
        try {
            cre_send(route, bmsg);
            JOptionPane.showMessageDialog(null, "Archivo creado");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error en la creacion del archivo");
        }
    }

    private void cre_send(String route, String bin_msg) throws IOException {
        FileWriter fw = null;
        try {
            File archivo = new File(route);
            fw = new FileWriter(archivo, false);
            PrintWriter pw = new PrintWriter(fw);
            fw.write(pol + "\n");
            fw.write(bin_msg);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e.toString());
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error " + e.toString());
            }
        }
    }

    private String codeword(String binary) {
        String extra = "";
        for (int i = 0; i < pol.length() - 1; i++) {
            extra += 0;
        }

        BigInteger a = new BigInteger(binary + extra, 2), b = new BigInteger(pol, 2);
        String result = a.mod(b).toString(2);

        for (int i = result.length(); i < pol.length() - 1; i++) {
            result = '0' + result;
        }
        return binary + result;
    }

    /**
     * get a char and return string with the binary of character.The string has
     * a length of 8
     *
     * @param c This's the Character
     * @return bin this's the binary of c
     */
    private static String char_bin(char c) {
        String bin = Integer.toString((int) c, 2);
        while (bin.length() < 8) {
            bin = '0' + bin;
        }
        return bin;
    }

    /**
     * get a string and return String with the binary block of the string
     *
     * @param msg String of 16 characters or less
     * @return block a string with a binary block
     */
    private static String binBlock(String msg) {
        String block = "";
        for (int i = 0; i < msg.length(); i++) {
            block += char_bin(msg.charAt(i));
        }
        return block;
    }

}
