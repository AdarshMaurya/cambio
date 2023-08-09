package com.softhinkers.cambio.game;

import com.softhinkers.cambio.card.Card;

public class HoldingCards extends Card {

    private CARD_STATUS cardStatus;

    public HoldingCards(Card card, CARD_STATUS cardStatus) {
        super(card.getSuit(), card.getCardFace(), card.getCardFaceValue());
        this.cardStatus = cardStatus;
    }

    public CARD_STATUS getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CARD_STATUS cardStatus) {
        this.cardStatus = cardStatus;
    }

    public enum CARD_STATUS {
        SEEN,
        NOT_SEEN,
        THROWN,
        OTHERS_SEEN_IT
    }

    @Override
    public String toString() {
        return "HoldingCards{" +
                "cardStatus=" + cardStatus +
                ", " +
                super.toString() +
                '}';
    }

}
