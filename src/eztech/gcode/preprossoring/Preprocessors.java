/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eztech.gcode.preprossoring;

import eztech.gcode.util.Extenxtion;
import java.util.HashMap;

/**
 *
 * @author yami
 */
public class Preprocessors {

    public static String convert(String filedata, Extenxtion extention) {
        PreProcessor preprocssor = instance.preprocessors.getOrDefault(extention, PreProcessor.getDefault());
        return preprocssor.convert(filedata);
    }
    static Preprocessors instance = new Preprocessors();

    HashMap<Extenxtion, PreProcessor> preprocessors = new HashMap();

    private Preprocessors() {
        preprocessors.put(Extenxtion.TAP, new TapPreProcessor());
        preprocessors.put(Extenxtion.NGC, new TapPreProcessor());
    }
}
