package com.softhinkers.cambio.game;

import com.softhinkers.cambio.card.Card;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

public class Player {

    private final String name;
    private final String uniqueID;

    private ArrayList<HoldingCards> playerHoldingCards = new ArrayList<>();

    private boolean gameFinished;

    public Player(String name) {
        this.name = name;
        this.gameFinished = false;
        this.uniqueID = new StringBuilder()
                .append(UUID.randomUUID().toString())
                .append("-")
                .append(UUID.randomUUID().toString())
                .append("-")
                .append(UUID.randomUUID().toString())
                .append("-")
                .append(UUID.randomUUID().toString())
                .toString();
    }


    public void holdingCard(Card card) {
        HoldingCards holdingCard = new HoldingCards(card, HoldingCards.CARD_STATUS.NOT_SEEN);
        playerHoldingCards.add(holdingCard);
    }

    public String getName() {
        return name;
    }

    public ArrayList<HoldingCards> getPlayerHoldingCards() {
        return playerHoldingCards;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean finishGame) {
        this.gameFinished = finishGame;
    }

    public void swapCardFromHoldingCards(HoldingCards card, int holdingCardIndexToBeSwapped) {
        playerHoldingCards.remove(holdingCardIndexToBeSwapped);
        playerHoldingCards.add(holdingCardIndexToBeSwapped, card);
    }

    public String getUniqueID() {
        return uniqueID;
    }
}
