/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eztech.gcode.util;

import eztech.gcode.exception.UnsupportedFileFormatException;
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

    public static Extenxtion getExtenion(File file) throws UnsupportedFileFormatException {
        String name = file.getName();
        String ext = name.substring(name.lastIndexOf(".") + 1).toUpperCase();
        try {
            return Extenxtion.valueOf(ext);
        } catch (Exception e) {
            throw new UnsupportedFileFormatException(String.format("File \"%s\" is not Supported", ext));
        }
    }
}
