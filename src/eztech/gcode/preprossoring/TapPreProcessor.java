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
        StringBuilder str = new StringBuilder(code);
        code = code.replaceAll("X", " X");
        code = code.replaceAll("Y", " Y");
        code = code.replaceAll("Z", " Z");
        code = code.replaceAll("I", " I");
        code = code.replaceAll("J", " J");
        code = code.replaceAll("F", " F");
        code = code.replaceAll("S", " S");
        code = code.replaceAll("M", " M");
        String[] codeLines = code.split("\n");
        for (int i = 0; i < codeLines.length; i++) {
            if (codeLines[i].trim().startsWith("G1 ")) {
                codeLines[i] = codeLines[i].trim().replaceFirst("G1 ", "G01 ");
            } else if (codeLines[i].trim().contains("G0 ")) {
                codeLines[i] = codeLines[i].trim().replaceFirst("G0 ", "G00 ");
            } else if (codeLines[i].startsWith("G2 ")) {
                codeLines[i] = codeLines[i].trim().replaceFirst("G2 ", "G02 ");
            } else if (codeLines[i].trim().startsWith("G3 ")) {
                codeLines[i] = codeLines[i].trim().replaceFirst("G3 ", "G03 ");
            }
            if (codeLines[i].trim().startsWith("X") | codeLines[i].trim().startsWith("Y") | codeLines[i].trim().startsWith("Z")) {
                codeLines[i] = "G01" + " " + codeLines[i].trim();
            }
        }
        str = new StringBuilder();
        for (String string : codeLines) {
            str.append(string).append("\n");
        }
        return str.toString().replaceAll("  ", " ");
    }
}
