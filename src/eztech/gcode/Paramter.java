/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eztech.gcode;

/**
 *
 * @author mshowaib
 */
public enum Paramter {

    X(0),
    Y(1),
    Z(2),
    I(0),
    J(1),
    K(2),
    F(0);
    private int index;

    private Paramter(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static String getAllParamters() {
        String parms = "";
        for (Paramter parm : values()) {
            parms += parm.toString();
        }
        return parms;
    }

}
