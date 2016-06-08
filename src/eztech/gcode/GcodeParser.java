/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eztech.gcode;

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

    private static final String FlOAT_PATTERN = "([-+]?\\d+(\\.\\d*)?)";
    private static final String CODE_PATTERN = "([GM]\\d{1,2})";
    private static final String PRAMTERS_PATTERN = "([" + Paramter.getAllParamters() + "])";
    private static final String PATTERN = String.format("%s( *%s *%s)*", CODE_PATTERN, PRAMTERS_PATTERN, FlOAT_PATTERN);
    private static final String[] COMMENTS_PATTERNS = {"%[^\\n]*", "\\([^)]*\\)"};

    public static List<Gcode> compile(File file) throws UnsupportedFileFormatException {
        Extenxtion extention = Utils.getExtenion(file);
        String fileData = Utils.LoadText(file);
        fileData = Preprocessors.convert(fileData, extention);
        return compile(fileData);
    }

    public static List<Gcode> compile(String content) {
        for (String comment : COMMENTS_PATTERNS) {
            content = content.replaceAll(comment, "");
        }
        String[] gcodeLines = content.split("\n");
        ArrayList<Gcode> gcodeList = new ArrayList<>();
        ArrayList<String> invalidLines = new ArrayList<>();
        for (String gcodeLine : gcodeLines) {
            gcodeLine = gcodeLine.trim();
            if (gcodeLine.length() != 0) {
                try {
                    gcodeList.add(lineToGcode(gcodeLine));
                } catch (UnsupportedCodeException | InvalidGcodeParamtersException | InvalidLineCodeException ex) {
                    System.err.println(ex.getMessage());
                    invalidLines.add(gcodeLine);
                }
            }
        }
        System.err.println("No of uncompiled lines: " + invalidLines.size());
        return gcodeList;
    }

    public static Gcode lineToGcode(String line) throws UnsupportedCodeException, InvalidLineCodeException, InvalidGcodeParamtersException {
        if (isValidGcodeLine(line)) {
            return new Gcode(extractCode(line), extractParamters(line));
        }
        throw new InvalidLineCodeException(line);
    }

    public static boolean isValidGcodeLine(String line) {
        return line.matches(PATTERN);
    }

    public static HashMap<Character, Float> extractParamters(String line) throws InvalidGcodeParamtersException {
        HashMap<Character, Float> params = new HashMap<>();
        Matcher paramKeyMatcher = Pattern.compile(PRAMTERS_PATTERN).matcher(line);
        Matcher floatMatcher = Pattern.compile(PRAMTERS_PATTERN + FlOAT_PATTERN).matcher(line);
        while (paramKeyMatcher.find() && floatMatcher.find()) {
            try {
                Character key = paramKeyMatcher.group().charAt(0);
                String valuest = floatMatcher.group().replace(key.toString(), "");
                Float value = Float.parseFloat(valuest);
                params.put(key, value);
            } catch (Exception e) {
                throw new InvalidGcodeParamtersException(line);
            }
        }
        return params;
    }

    static public Code extractCode(String line) throws UnsupportedCodeException {
        Matcher codeMatcher = Pattern.compile(CODE_PATTERN).matcher(line);
        if (codeMatcher.find()) {
            try {
                return Code.getCodeFromString(codeMatcher.group());
            } catch (Exception e) {
                System.err.println(e.getMessage());;
            }
        }
        throw new UnsupportedCodeException(String.format("This Code \"%s\" is not Supported", line));
    }
}
