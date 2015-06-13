/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eztech.gcode;

import eztech.gcode.util.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.*;

/**
 *
 * @author yami
 */
public class GcodeParser {

    private static final String FlOAT_PATTERN = "([-+]?\\d*\\.?\\d*)";
    private static final String CODE_PATTERN = "([GM]\\d{1,2})";
    private static final String PRAMTERS_PATTERN = "([XYZIJ])";
    private static final String PATTERN = String.format("%s( ?%s%s)+", CODE_PATTERN, PRAMTERS_PATTERN, FlOAT_PATTERN);

    public GcodeParser(File file) {
    }

    public static List<Gcode> compile(File file) {
        return compile(Utils.LoadText(file));
    }

    public static List<Gcode> compile(String content) {
        String[] gcodeLines = content.split("\n");
        ArrayList<Gcode> gcodeList = new ArrayList<>();
        for (String gcodeLine : gcodeLines) {
            Gcode gcode = lineToGcode(gcodeLine);
            if (gcode != null) {
                gcodeList.add(gcode);
            }
        }
        return gcodeList;
    }

    public static Gcode lineToGcode(String line) {
        Gcode gcode = null;
        if (isValidGcodeLine(line)) {
            gcode = new Gcode(extractCode(line), extractParamters(line));
        }
        return gcode;
    }

    public static boolean isValidGcodeLine(String line) {
        return Pattern.compile(PATTERN).matcher(line).find();
    }

    public static HashMap<Character, Float> extractParamters(String line) {
        HashMap<Character, Float> params = new HashMap<>();
        Matcher paramKeyMatcher = Pattern.compile(PRAMTERS_PATTERN).matcher(line);
        Matcher floatMatcher = Pattern.compile(PRAMTERS_PATTERN + FlOAT_PATTERN).matcher(line);
        while (paramKeyMatcher.find() && floatMatcher.find()) {
            Character key = paramKeyMatcher.group().charAt(0);
            String valuest = floatMatcher.group().replace(key.toString(), "");
            Float value = Float.parseFloat(valuest);
            params.put(key, value);
        }
        return params;
    }

    static public Code extractCode(String line) {
        Matcher codeMatcher = Pattern.compile(CODE_PATTERN).matcher(line);
        if (codeMatcher.find()) {
            return Code.valueOf(codeMatcher.group());
        } else {
            return null;
        }
    }
}
