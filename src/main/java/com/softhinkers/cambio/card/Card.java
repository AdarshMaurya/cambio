package com.softhinkers.cambio.card;

public class Card {

    private final Suits suit;

    private final CardFace cardFace;

    private final int cardFaceValue;


    public Card(Suits suit, CardFace cardFace, int cardFaceValue) {
        this.suit = suit;
        this.cardFace = cardFace;
        this.cardFaceValue = cardFaceValue;
    }

    public Suits getSuit() {
        return suit;
    }

    public CardFace getCardFace() {
        return cardFace;
    }

    public int getCardFaceValue() {
        return cardFaceValue;
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit=" + suit +
                ", cardFace=" + cardFace +
                ", cardFaceValue=" + cardFaceValue +
                '}';
    }
}
