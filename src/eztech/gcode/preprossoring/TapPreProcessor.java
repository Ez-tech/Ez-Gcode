/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eztech.gcode.preprossoring;

/**
 *
 * @author yami
 */
public class TapPreProcessor implements PreProcessor {

    @Override
    public String convert(String code) {
        String[] codeLines = code.split("\n");
        for (int i = 0; i < codeLines.length; i++) {
            if (codeLines[i].trim().startsWith("X") | codeLines[i].trim().startsWith("Y") | codeLines[i].trim().startsWith("Z")) {
                codeLines[i] = "G01" + " " + codeLines[i].trim();
            }
        }
        StringBuilder str = new StringBuilder();
        for (String string : codeLines) {
            str.append(string).append("\n");
        }
        return str.toString();
    }
}
