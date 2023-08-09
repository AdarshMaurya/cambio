package com.softhinkers.cambio.card;

public enum CardFace {

    ACE("ace"),
    TWO("two"),
    THREE("three"),
    FOUR("four"),
    FIVE("five"),
    SIX("six"),
    SEVEN("seven"),
    EIGHT("eight"),
    NINE("nine"),
    TEN("ten"),
    JACK("jack"),
    QUEEN("queen"),
    KING("king"),
    JOKER("joker");

String cardFaceName;
    CardFace(String cardFaceName) {
        this.cardFaceName = cardFaceName;
    }
}