package com.javanc.enums;

import java.util.HashMap;
import java.util.Map;

public enum CityEnum {
    HA_NOI("Hà Nội"),
    HO_CHI_MINH("Hồ Chí Minh"),
    DA_NANG("Đà nẵng"),
    CAN_THO("Cần Thơ");

   private final String name;
   CityEnum(String name) {
       this.name = name;
   }
   public static Map<String, String> getCity(){
       Map<String, String> result = new HashMap<>();
       for (CityEnum city : CityEnum.values()) {
           result.put(city.toString(), city.name);
       }
       return result;
   }
   public static CityEnum getCityEnum(String city){
       for (CityEnum cityEnum : CityEnum.values()) {
           if(cityEnum.name.equalsIgnoreCase(city)){
               return cityEnum;
           }
       }
       throw new IllegalArgumentException("Invalid city: " + city);
   }
}
