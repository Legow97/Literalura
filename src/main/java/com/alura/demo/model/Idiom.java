package com.alura.demo.model;

public enum Idiom {
    SPANISH("es"),
    ENGLISH("en"),
    FRENCH("fr"),
    PORTUGUESE("pt");

    private String idiomGtx;

    Idiom(String idiomGtx) {
        this.idiomGtx = idiomGtx;
    }

    public static Idiom fromString(String text) {
        for (Idiom idiom:Idiom.values()) {
             if(idiom.idiomGtx.equalsIgnoreCase(text)){
                 return idiom;
             }
        }
        throw new IllegalArgumentException("Idioma no encontrado" + text);
    }


}
