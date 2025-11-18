package com.javanc.enums;

import org.hibernate.engine.jdbc.Size;

import java.util.HashMap;
import java.util.Map;

public enum SizeEnum {
    S ("S"),
    M ("M"),
    L ("L"),
    XL ("XL"),
    XXL ("XXL");

    private final String size;

    SizeEnum(String size) {
        this.size = size;
    }

    public static Map<String, String> getSize(){
        Map<String, String> result = new HashMap<>();
        for(SizeEnum s : SizeEnum.values()){
            result.put(s.toString(), s.name());
        }
        return result;
    }
    public static SizeEnum getSizeEnum(String size){
        for(SizeEnum s : SizeEnum.values()){
            if (s.size.equalsIgnoreCase(size)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid size " + size);
    }
}
