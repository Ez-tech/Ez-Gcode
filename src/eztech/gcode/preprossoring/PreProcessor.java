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
public interface PreProcessor {

    default String convert(String code) {
        return code;
    }
    static PreProcessor defaultPreProcessor = new PreProcessor() {
    };

    static PreProcessor getDefault() {
        return defaultPreProcessor;
    }
}
