/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eztech.gcode.util;

/**
 *
 * @author yami
 */
public enum Extenxtion {

    ANC("ArbCad Gcode"),
    TAP("Artcam Tab File");

    String description;

    private Extenxtion(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
