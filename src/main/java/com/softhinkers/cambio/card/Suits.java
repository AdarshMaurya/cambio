package com.softhinkers.cambio.card;

public enum Suits {

    DIAMOND("diamond"),
    HEART("heart"),
    CLUB("club"),
    SPADE("spade"),
    NOTHING("nothing");

    String suiteName;

    Suits(String suiteName) {
        this.suiteName = suiteName;
    }
}
