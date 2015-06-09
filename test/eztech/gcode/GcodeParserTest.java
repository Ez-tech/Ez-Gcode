/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eztech.gcode;

import java.util.Map.Entry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yami
 */
public class GcodeParserTest {

    public GcodeParserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testLineToGcode() {
        System.out.println("lineToGcode");
        Gcode expResult = new Gcode(Code.G01);
        expResult.getParamters().put('X', -24f);
        expResult.getParamters().put('Y', 1.2f);
        Gcode result = GcodeParser.lineToGcode("G01 X-24 Y1.2");
        assertEquals(result.getCode(), Code.G01);
        for (Entry<Character, Float> entrySet : result.getParamters().entrySet()) {
            Character key = entrySet.getKey();
            Float value = entrySet.getValue();
            assertEquals(expResult.getParamters().get(key), value);
        }
    }

    @Test
    public void testIsValidGcodeLine() {
        System.out.println("isValidGcodeLine");
        assertEquals(GcodeParser.isValidGcodeLine("G00 X-12"), true);
        assertEquals(GcodeParser.isValidGcodeLine("G01X1.2 Z-0.5"), true);
        assertEquals(GcodeParser.isValidGcodeLine("G02 X-12 Y00 J11 I-17"), true);
        assertEquals(GcodeParser.isValidGcodeLine("G03 Z-12"), true);
    }

}
