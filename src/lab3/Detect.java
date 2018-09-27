package lab3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
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
                if (!module(codeword).equals("000"))correct=false;
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
            String route=file.getAbsolutePath().replace(".crc", ".txt");
            try {
                cre_file(route,datawords);
            } catch (IOException ex) {
                Logger.getLogger(Detect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("incorrecto");
        }
    }
    
    /**
     * this function return the module
     * of binary % pol
     * @param binary binary number
     */
    private String module(String binary) {
        String poly=this.pol,mod=binary.substring(0, poly.length());
        int indx=poly.length();
        while(indx<binary.length()){
            if(mod.charAt(0)=='1'){
                String temp="";
                for (int i = 0; i <poly.length() ; i++) {
                    if(mod.charAt(i)!=poly.charAt(i))temp+='1';
                    else temp+='0';
                }
                mod=temp;
            }
            mod=mod.substring(1, mod.length())+binary.charAt(indx);
            indx++;
        }
        return mod.substring(1);
    }

    /**
     *this function creates the file
     * which contains the codewords
     * in first place calculate the numbers of 
     * blocks and verify if there is a block
     * without 16 characters, after we create the zeros who 
     * will be add to the dataword and we transform the 
     * block to binary,and create all the codewords
     * finally we create the crc file.
     */
    public void send() {
        String codewords = this.msg, bmsg = "";
        int sz = codewords.length() / 16;
        boolean extra = false;
        if (codewords.length() % 16 != 0) {
            extra = true;
        }
        String zeros = "";
        for (int i = 0; i < pol.length() - 1; i++) {
            zeros += 0;
        }
        String dataword="";
        int i = 0;
        while (i < sz) {
            i++;
            dataword=binBlock(codewords.substring(16 * (i - 1), 16 * i));
            bmsg += dataword+module(dataword+zeros) + "\n";
        }
        if (extra) {
            dataword=binBlock(codewords.substring(16 * (i - 1), 16 * i));
            bmsg += dataword+module(dataword+zeros) + "\n";
        }
       String route=getroute()+outname+".crc";
       try {
            cre_file(route, bmsg);
            JOptionPane.showMessageDialog(null, "Archivo creado");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error en la creacion del archivo");
        }
    }
    /**
     
     */
    private String getroute(){
     String temp[] = file.getAbsolutePath().split(Pattern.quote("\\")), route = "";
        int i = 0;
        while (i < temp.length - 1) {
            route += temp[i] + "\\\\";
            i++;
        }
        return route;
    }
    /**
     * get the path and the message 
     * to create a file
     * @param route path
     * @param bin_msg message
     */
    private void cre_file(String route, String bin_msg) throws IOException {
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
