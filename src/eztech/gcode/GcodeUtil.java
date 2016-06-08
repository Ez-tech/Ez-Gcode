/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eztech.gcode;

import eztech.gcode.util.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author yami
 */
public class GcodeUtil {

    public static List<Gcode> repeatAutoFill(float maxX, float maxY, float spacing, List<Gcode> gcodeList) {
        float[] max, min;
        max = getMaximum(gcodeList);
        min = getMinmum(gcodeList);
        float width = max[0] - min[0] + spacing;
        float height = max[1] - min[1] + spacing;
        return repeat(maxX, maxY,
                (int) (maxX / width),
                (int) (maxY / height), gcodeList);
    }

    public static List<Gcode> repeat(float maxX, float maxY, int noRepeatX, int noRepeatY, List<Gcode> gcodelist) {
        float[] refrenceCoord = new float[3];
        float dx = maxX / noRepeatX;
        float dy = maxY / noRepeatY;
        List<Gcode> allGcodelist = new ArrayList<>();
        int c = 0;
        for (int j = 0; j < noRepeatY; j++) {
            for (int i = 0; i < noRepeatX; i++) {
                refrenceCoord[0] = dx * i;
                refrenceCoord[1] = dy * j;
                List<Gcode> translatedGcodelist = copy(gcodelist);
                translate(refrenceCoord, translatedGcodelist);
                allGcodelist.addAll(translatedGcodelist);
            }
        }
        return allGcodelist;
    }

    public static void rotate(float[] rotationPoints, float angle, List<Gcode> gcodeList) {
        float s = (float) Math.sin(angle);
        float c = (float) Math.cos(angle);
        float[] currentPoint = new float[2];
        gcodeList.stream().forEach((Gcode gcode) -> {
            currentPoint[0] = gcode.getParamters().getOrDefault('X', currentPoint[0]);
            currentPoint[1] = gcode.getParamters().getOrDefault('Y', currentPoint[1]);
            float x = currentPoint[0] - rotationPoints[0];
            float y = currentPoint[1] - rotationPoints[0];
            float xnew = x * c - y * s;
            float ynew = x * s + y * c;
            xnew += rotationPoints[0];
            ynew += rotationPoints[1];
            gcode.getParamters().put('X', xnew);
            gcode.getParamters().put('Y', ynew);
        });
    }

    public static void translate(float[] translate, List<Gcode> gcodeList) {
        gcodeList.stream().forEach((Gcode gcode) -> {
            gcode.getParamters().entrySet().stream().forEach((paramter) -> {
                if ("XYZ".contains(paramter.getKey().toString())) {
                    int index = Paramter.valueOf(paramter.getKey().toString()).getIndex();
                    paramter.setValue(paramter.getValue() + translate[index]);
                }
            });
        });
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
        Arrays.fill(max, 0, max.length, Float.NaN);
        for (Gcode gcode : gcodeList) {
            gcode.getParamters().entrySet().stream().forEach((paramter) -> {
                int index = Paramter.valueOf(paramter.getKey().toString()).getIndex();
                if (max[index] < paramter.getValue() || Float.isNaN(max[index])) {
                    max[index] = paramter.getValue();
                }
            });
        }
        return max;
    }

    public static float[] getMinmum(List<Gcode> gcodeList) {
        float[] min = new float[3];
        Arrays.fill(min, 0, min.length, Float.NaN);
        for (Gcode gcode : gcodeList) {
            gcode.getParamters().entrySet().stream().forEach((paramter) -> {
                int index = Paramter.valueOf(paramter.getKey().toString()).getIndex();
                if (min[index] > paramter.getValue() || Float.isNaN(min[index])) {
                    min[index] = paramter.getValue();
                }
            });
        }
        return min;
    }

    public static float[] getCenter(List<Gcode> gcodeList) {
        float[] min = getMinmum(gcodeList);
        float[] max = getMaximum(gcodeList);
        float[] center = new float[min.length];
        for (int i = 0; i < center.length; i++) {
            center[i] = (min[i] + max[i]) / 2;
        }
        return center;
    }

    public static List<Gcode> copy(List<Gcode> gcodeList) {
        return gcodeList.stream().map(Gcode::clone).collect(Collectors.toList());
    }
}
