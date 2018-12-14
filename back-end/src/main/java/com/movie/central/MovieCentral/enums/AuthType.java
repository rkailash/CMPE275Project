package com.movie.central.MovieCentral.enums;

public enum AuthType {

    LOCAL("local"),
    OAUTH("oauth");

    private final String type;

    AuthType(final String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static AuthType getByName(String type){
        for(AuthType authType : values()){
            if(authType.getType().equals(type)){
                return authType;
            }
        }

        throw new IllegalArgumentException(type + " is not a valid authType");
    }
}
