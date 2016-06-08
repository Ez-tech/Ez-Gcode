/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eztech.gcode;

/**
 *
 * @author yami
 */
public enum Code {
    G00,
    G01,
    G02,
    G03,
    G28,//Go Origin
    G90,
    G91,
    M0,
    M6;

    public Gcode getDefaultGcode() {
        return new Gcode(this);
    }

    public static Code getCodeFromString(String s) {
        int codeNo = Integer.parseInt(s.substring(1));
        String code = String.format("%s%02d", s.charAt(0), codeNo);
        return valueOf(code);
    }

}
