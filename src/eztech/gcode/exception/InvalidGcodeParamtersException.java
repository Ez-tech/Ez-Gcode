/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eztech.gcode.exception;

/**
 *
 * @author yami
 */
public class InvalidGcodeParamtersException extends Exception{

    public InvalidGcodeParamtersException(String format) {
        super(format);
    }
    
}
