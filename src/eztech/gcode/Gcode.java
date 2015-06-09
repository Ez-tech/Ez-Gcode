/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eztech.gcode;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yami
 */
public class Gcode {

    public static final String PROP_PARAMTERS = "paramters";
    public static final String PROP_CODE = "code";

    private Code code;
    private HashMap<Character, Float> paramters;

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public Gcode(Code code) {
        this(code, new HashMap<>());
    }

    public Gcode(Code code, HashMap<Character, Float> paramters) {
        this.code = code;
        this.paramters = paramters;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public HashMap<Character, Float> getParamters() {
        return paramters;
    }

    public void setParamters(HashMap<Character, Float> paramters) {
        HashMap<Character, Float> oldParamters = this.paramters;
        this.paramters = paramters;
        propertyChangeSupport.firePropertyChange(PROP_PARAMTERS, oldParamters, paramters);
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        Code oldCode = this.code;
        this.code = code;
        propertyChangeSupport.firePropertyChange(PROP_CODE, oldCode, code);
    }

    @Override
    public String toString() {
        String prams = "";
        for (Map.Entry<Character, Float> entrySet : paramters.entrySet()) {
            prams += " " + entrySet.getKey() + entrySet.getValue();
        }
        return code + prams;
    }

}
