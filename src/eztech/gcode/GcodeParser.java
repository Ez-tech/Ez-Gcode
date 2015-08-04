/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eztech.gcode;

import eztech.gcode.exception.InvalidLineCodeException;
import eztech.gcode.preprossoring.Preprocessors;
import eztech.gcode.util.Extenxtion;
import eztech.gcode.exception.*;
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

    public static List<Gcode> compile(File file) throws UnsupportedFileFormatException {
        Extenxtion extention = Utils.getExtenion(file);
        String fileData = Utils.LoadText(file);
        fileData = Preprocessors.convert(fileData, extention);
        return compile(fileData);
    }

    public static List<Gcode> compile(String content) {
        String[] gcodeLines = content.split("\n");
        ArrayList<Gcode> gcodeList = new ArrayList<>();
        ArrayList<String> invalidLines = new ArrayList<>();
        for (String gcodeLine : gcodeLines) {
            try {
                Gcode gcode = lineToGcode(gcodeLine);
                gcodeList.add(gcode);
            } catch (UnsupportedCodeException | InvalidLineCodeException ex) {
                System.err.println(ex.getMessage());
                invalidLines.add(gcodeLine);
            }
        }
        System.err.println("No of uncompiled lines: " + invalidLines.size());
        return gcodeList;
    }

    public static Gcode lineToGcode(String line) throws UnsupportedCodeException, InvalidLineCodeException {
        if (isValidGcodeLine(line)) {
            return new Gcode(extractCode(line), extractParamters(line));
        }
        throw new InvalidLineCodeException(String.format("This Code \"%s\" is not Supported", line));
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

    static public Code extractCode(String line) throws UnsupportedCodeException {
        Matcher codeMatcher = Pattern.compile(CODE_PATTERN).matcher(line);
        if (codeMatcher.find()) {
            try {
                return Code.valueOf(codeMatcher.group());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        throw new UnsupportedCodeException(String.format("This Code \"%s\" is not Supported", line));
    }
}
