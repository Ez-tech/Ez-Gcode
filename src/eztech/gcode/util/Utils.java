/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eztech.gcode.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author yami
 */
public class Utils {

    static public String LoadText(File f) {
        try {
            BufferedReader textBuffer = new BufferedReader(new FileReader(f));
            String stemp;
            StringBuilder strBuilder = new StringBuilder();
            stemp = textBuffer.readLine();
            while (stemp != null) {
                strBuilder.append(stemp).append("\n");
                stemp = textBuffer.readLine();
            }
            return strBuilder.toString();
        } catch (IOException ex) {
            System.err.println("File not Found :" + ex);
            return "";
        }
    }

    static public void saveText(File f, String s) {
        try (FileWriter saveFile = new FileWriter(f)) {
            saveFile.append(s + "\n");
            saveFile.close();
        } catch (IOException ex) {
            System.err.println("Error in saving GUI congig :" + ex);
        }
    }

}
