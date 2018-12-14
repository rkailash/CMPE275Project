package com.movie.central.MovieCentral.enums;

public enum SubscriptionType {

    PAY_PER_VIEW("pay_per_view"),
    SUBSCRIPTION("subscription");

    final String type;

    SubscriptionType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

//    public static SubscriptionType getByName(String type){
//        for(SubscriptionType st : SubscriptionType.values()){
//            if(st.getType().equals(type))
//                return st;
//        }
//        throw new IllegalArgumentException(type + " is not a valid subscription type");
//    }
}
