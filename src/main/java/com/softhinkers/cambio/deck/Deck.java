package com.softhinkers.cambio.deck;

import com.softhinkers.cambio.card.Card;
import com.softhinkers.cambio.card.CardFace;
import com.softhinkers.cambio.card.Suits;
import com.softhinkers.cambio.generic.GlobalConstant;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> cards = new ArrayList<>();

    public Deck(String gameDeckName) {
        if (gameDeckName.equals(GlobalConstant.CAMBIO)) {
            this.cards.addAll(createCambioCardDeck());
        }
        suffleDeckCards();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    private ArrayList<Card> createCambioCardDeck() {
        ArrayList<Suits> suitsArrayList = new ArrayList<>();
        suitsArrayList.add(Suits.SPADE);
        suitsArrayList.add(Suits.CLUB);
        suitsArrayList.add(Suits.DIAMOND);
        suitsArrayList.add(Suits.HEART);

        ArrayList<CardFace> cardFacesList = new ArrayList<>();
        cardFacesList.add(CardFace.ACE);
        cardFacesList.add(CardFace.TWO);
        cardFacesList.add(CardFace.THREE);
        cardFacesList.add(CardFace.FOUR);
        cardFacesList.add(CardFace.FIVE);
        cardFacesList.add(CardFace.SIX);
        cardFacesList.add(CardFace.SEVEN);
        cardFacesList.add(CardFace.EIGHT);
        cardFacesList.add(CardFace.NINE);
        cardFacesList.add(CardFace.TEN);
        cardFacesList.add(CardFace.JACK);
        cardFacesList.add(CardFace.QUEEN);
        cardFacesList.add(CardFace.KING);

        ArrayList<Card> cardDeckList = new ArrayList<>();
        Card card = null;

        for (Suits suite : suitsArrayList) {
            for (CardFace cardFace : cardFacesList) {
                switch (cardFace) {
                    case ACE:
                        card = new Card(suite, CardFace.ACE, 1);
                        break;
                    case TWO:
                        card = new Card(suite, CardFace.TWO, 2);
                        break;
                    case THREE:
                        card = new Card(suite, CardFace.THREE, 3);
                        break;
                    case FOUR:
                        card = new Card(suite, CardFace.FOUR, 4);
                        break;
                    case FIVE:
                        card = new Card(suite, CardFace.FIVE, 5);
                        break;
                    case SIX:
                        card = new Card(suite, CardFace.SIX, 6);
                        break;
                    case SEVEN:
                        card = new Card(suite, CardFace.SEVEN, 7);
                        break;
                    case EIGHT:
                        card = new Card(suite, CardFace.EIGHT, 8);
                        break;
                    case NINE:
                        card = new Card(suite, CardFace.NINE, 9);
                        break;
                    case TEN:
                        card = new Card(suite, CardFace.TEN, 10);
                        break;
                    case JACK:
                        card = new Card(suite, CardFace.JACK, 10);
                        break;
                    case QUEEN:
                        card = new Card(suite, CardFace.QUEEN, 10);
                        break;
                    case KING:
                        if (suite.equals(Suits.CLUB) || suite.equals(Suits.SPADE)) {
                            card = new Card(suite, CardFace.KING, 10);
                        } else {
                            card = new Card(suite, CardFace.KING, -1);
                        }
                    default: //todo throw exception
                }
                cardDeckList.add(card);
            }
        }
        cardDeckList.add(new Card(Suits.NOTHING, CardFace.JOKER, 0));
        cardDeckList.add(new Card(Suits.NOTHING, CardFace.JOKER, 0));

        return cardDeckList;
    }

    private void suffleDeckCards() {
        Collections.shuffle(cards);
    }
}
