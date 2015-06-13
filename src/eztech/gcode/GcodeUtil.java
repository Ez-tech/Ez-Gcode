/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eztech.gcode;

import eztech.gcode.util.Utils;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author yami
 */
public class GcodeUtil {

    enum Paramter {

        X(0),
        Y(1),
        Z(2),
        I(0),
        J(1);
        private int index;

        private Paramter(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

    }

    public static void scale(float[] ratios, List<Gcode> gcodeList) {
        gcodeList.stream().forEach((Gcode gcode) -> {
            gcode.getParamters().entrySet().stream().forEach((paramter) -> {
                int index = Paramter.valueOf(paramter.getKey().toString()).getIndex();
                paramter.setValue(paramter.getValue() * ratios[index]);
            });
        });
    }

    public static void saveGcodeFile(List<Gcode> gcodeList, File file) {
        StringBuilder str = new StringBuilder();
        gcodeList.stream().forEach((gcode) -> {
            str.append(gcode).append("\n");
        });
        Utils.saveText(file, str.toString());
    }

    public static float[] getMaximum(List<Gcode> gcodeList) {
        float[] max = new float[3];
        Arrays.fill(max, 0, max.length, Float.MIN_VALUE);
        for (Gcode gcode : gcodeList) {
            gcode.getParamters().entrySet().stream().forEach((paramter) -> {
                int index = Paramter.valueOf(paramter.getKey().toString()).getIndex();
                if (max[index] < paramter.getValue()) {
                    max[index] = paramter.getValue();
                }
            });
        }
        return max;
    }

    public static float[] getMinmum(List<Gcode> gcodeList) {
        float[] min = new float[3];
        Arrays.fill(min, 0, min.length, Float.MAX_VALUE);
        for (Gcode gcode : gcodeList) {
            gcode.getParamters().entrySet().stream().forEach((paramter) -> {
                int index = Paramter.valueOf(paramter.getKey().toString()).getIndex();
                if (min[index] > paramter.getValue()) {
                    min[index] = paramter.getValue();
                }
            });
        }
        return min;
    }
}
