package com.softhinkers.cambio.main;

import com.softhinkers.cambio.card.Card;
import com.softhinkers.cambio.card.CardFace;
import com.softhinkers.cambio.card.Suits;
import com.softhinkers.cambio.deck.Deck;
import com.softhinkers.cambio.game.HoldingCards;
import com.softhinkers.cambio.game.Player;
import com.softhinkers.cambio.generic.GlobalConstant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static ArrayList<Card> suffledCards = new ArrayList<>();
    public static ArrayList<Card> thrownCardsList = new ArrayList<>();
    public static ArrayList<Player> playerList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Deck cambioDeck = new Deck(GlobalConstant.CAMBIO);
        suffledCards = cambioDeck.getCards();
//        for (Card card : suffledCards) {
//            System.out.println(card.getCardFace() + " " + card.getSuit());
//        }

        // distribute cards between players
        Player swati = new Player("Swati");
        Player adarsh = new Player("Adarsh");
        Player anuj = new Player("Anuj");
        Player maruti = new Player("Maruti");


        playerList.add(swati);
        playerList.add(adarsh);
        playerList.add(anuj);
        playerList.add(maruti);

        // Distribute 4 cards among each players
        for (Player player : playerList) {
            for (int i = 0; i < 4; i++) {
                Card card = suffledCards.get(i);
                player.holdingCard(card);
                suffledCards.remove(i);
            }
        }

//        for (Player p : playerList) {
//            System.out.println("Playing Card Size: " + p.getPlayerHoldingCards().size());
//            System.out.println(p.getName());
//            for (HoldingCards card : p.getPlayerHoldingCards()) {
//                System.out.println(card.getCardFace() + " " + card.getSuit() + " " + card.getCardFaceValue() +" "+ card.getCardStatus());
//            }
//            System.out.println("===============");
//        }

//        System.out.println("SuffledCard Size: " + suffledCards.size());

        //Wait for last 2 cards to been seen & put down on table by all the players
//        ExecutorService threadPool = Executors.newFixedThreadPool(playerList.size());
//        for (Player player : playerList) {
//            threadPool.submit(new Runnable() {
//                @Override
//                public void run() {
//                    String playerName = player.getName();
//                    HoldingCards card3 = player.getPlayerHoldingCards().get(2);
//                    HoldingCards card4 = player.getPlayerHoldingCards().get(3);
//                    System.out.println(playerName + ": " + card3.getSuit() + " " + card3.getCardFace() + " " + card3.getCardStatus());
//                    System.out.println(playerName + ": " + card4.getSuit() + " " + card4.getCardFace() + " " + card4.getCardStatus());
//
//                    Scanner scanner = new Scanner(System.in);
//                    System.out.println(playerName + " - Press enter key to change card seen status");
//                    scanner.nextLine();
//                    System.out.println(playerName + "- Your last two card status updated to seen");
//                    HoldingCards cardSeen3 = player.getPlayerHoldingCards().get(2);
//                    HoldingCards cardSeen4 = player.getPlayerHoldingCards().get(3);
//                    cardSeen3.setCardStatus(HoldingCards.CARD_STATUS.SEEN);
//                    cardSeen4.setCardStatus(HoldingCards.CARD_STATUS.SEEN);
//
//
//                    System.out.println(playerName + ": " + cardSeen3.getSuit() + " " + cardSeen3.getCardFace() + " " + cardSeen3.getCardStatus());
//                    System.out.println(playerName + ": " + cardSeen4.getSuit() + " " + cardSeen4.getCardFace() + " " + cardSeen4.getCardStatus());
//
//                    System.out.println("================");
//                }
//            });
//        }
//        threadPool.shutdown();
//        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

        //randomly select one player to pick the card from the suffled deck
        int rand = new Random().nextInt(playerList.size());
        Player startingPlayer = playerList.get(rand);

        //sort direction of players - relative to the randomly selected starting player
        ArrayList<Player> sortedPlayer = sortPlayers("CLOCKWISE", playerList, startingPlayer);
        playerList.clear();
        playerList.addAll(sortedPlayer);

        boolean CAMBIO_SAID_FINISH_GAME = false;
        int ROUND_OF_GAME = 4;
        boolean LAST_ROUND = false;
        do {
            for (Player player : playerList) {
                if (LAST_ROUND) {
                    // if any player has called cambio, continue for others.
                    continue;
                }
//                System.out.println(player.getName() + " holds card: " + player.getPlayerHoldingCards().size());
//                for (HoldingCards holdingCard : player.getPlayerHoldingCards()) {
//                    System.out.println(holdingCard.toString());
//                }
                if (!player.isGameFinished()) {
                    System.out.println(player.getName() + ": Say CAMBIO? Type yes or no");
                    Scanner scanner = new Scanner(System.in);
                    String playerChoice = scanner.nextLine();
                    switch (playerChoice.toUpperCase()) {
                        case "YES": {
                            CAMBIO_SAID_FINISH_GAME = true;
                            player.setGameFinished(true);
                            LAST_ROUND = true;
                            continue;
                        }
                        case "NO": {
                            player.setGameFinished(false);
                            break;
                        }
                    }
                    if (!player.isGameFinished()) {
                        //player picks card from suffledDeck
                        Card pickedCard = suffledCards.remove(0);
                        HoldingCards card = new HoldingCards(pickedCard, HoldingCards.CARD_STATUS.SEEN);
                        System.out.println(player.getName() + ": You picked Card: ");
                        System.out.println(card.toString());

                        switch (card.getCardFace()) {
                            case JOKER:
                            case TWO:
                            case THREE:
                            case FOUR:
                            case FIVE:
                            case SIX:
                            case ACE: {
                                choiceAvailableToPlayerForCard(player, card);
                                break;
                            }
                            case KING: {
                                if (Suits.DIAMOND.equals(card.getSuit()) || Suits.HEART.equals(card.getSuit())) {
                                    choiceAvailableToPlayerForCard(player, card);
                                    break;
                                } else {
                                    choiceAvailableToPlayerForSpecialCard(player, card);
                                    break;
                                }
                            }
                            case SEVEN:
                            case EIGHT:
                            case NINE:
                            case TEN:
                            case QUEEN:
                            case JACK: {
                                choiceAvailableToPlayerForSpecialCard(player, card);
                                break;
                            }
                        }
//                        System.out.println("===THROWN_CARD===");
//                        System.out.println(thrownCardsList);
//                        System.out.println("===HOLDING_CARD===");
//                        for (HoldingCards holdingCard : player.getPlayerHoldingCards()) {
//                            System.out.println(holdingCard.toString());
//                        }
                    }
                }
            }
            // Last Round of Cambio being played
            if (LAST_ROUND) {
                ArrayList<Player> lastRoundPlayers = new ArrayList<>(playerList);
                Player playerGameFinished = lastRoundPlayers.stream().filter(Player::isGameFinished).collect(Collectors.toList()).get(0);
                ArrayList<Player> playerSortedInClockWiseList = sortPlayers("CLOCKWISE", lastRoundPlayers, playerGameFinished);
                playerSortedInClockWiseList.removeIf(Player::isGameFinished);
//                System.out.println("LAST ROUND: Player List " + playerSortedInClockWiseList.size());
                for (Player player : playerSortedInClockWiseList) {
                    if (!player.isGameFinished()) {
                        //player picks card from suffledDeck
                        Card pickedCard = suffledCards.remove(0);
                        HoldingCards card = new HoldingCards(pickedCard, HoldingCards.CARD_STATUS.SEEN);
                        System.out.println(player.getName() + ": You picked Card: ");
                        System.out.println(card.toString());

                        switch (card.getCardFace()) {

                            case JOKER:
                            case TWO:
                            case THREE:
                            case FOUR:
                            case FIVE:
                            case SIX:
                            case ACE: {
                                choiceAvailableToPlayerForCard(player, card);
                                break;
                            }
                            case KING: {
                                if (Suits.DIAMOND.equals(card.getSuit()) || Suits.HEART.equals(card.getSuit())) {
                                    choiceAvailableToPlayerForCard(player, card);
                                    break;
                                } else {
                                    choiceAvailableToPlayerForSpecialCard(player, card);
                                    break;
                                }
                            }
                            case SEVEN:
                            case EIGHT:
                            case NINE:
                            case TEN:
                            case QUEEN:
                            case JACK: {
                                choiceAvailableToPlayerForSpecialCard(player, card);
                                break;
                            }
                        }
//                        System.out.println("===THROWN_CARD===");
//                        System.out.println(thrownCardsList);
//                        System.out.println("===HOLDING_CARD===");
//                        for (HoldingCards holdingCard : player.getPlayerHoldingCards()) {
//                            System.out.println(holdingCard.toString());
//                        }
                    }
                }
            }

            System.out.println("============");

            if (CAMBIO_SAID_FINISH_GAME) ROUND_OF_GAME = 0;
            else ROUND_OF_GAME--;

            if (ROUND_OF_GAME == 0) {
                // Declare winner
                ArrayList<String> playerHoldingCardValueList = new ArrayList<>();
                for (Player player : playerList) {
                    int playerHoldingCardValue = 0;
                    ArrayList<HoldingCards> holdingCardsList = player.getPlayerHoldingCards();
                    for (HoldingCards card : holdingCardsList) {
                        playerHoldingCardValue = playerHoldingCardValue + card.getCardFaceValue();
                    }
                    playerHoldingCardValueList.add(playerHoldingCardValue + ":" + player.getName());
                    System.out.println(player.getName() + ": Your score is: " + playerHoldingCardValue);
                }
                Collections.sort(playerHoldingCardValueList);
                String[] winner = playerHoldingCardValueList.get(0).split(":");
                System.out.println();
                //TODO: declare winner when scores of player is same
                System.out.println("Winner is: " + winner[1] + " Score: " + winner[0]);

            }

        } while (ROUND_OF_GAME > 0);

    }

    private static void choiceAvailableToPlayerForSpecialCard(Player player, HoldingCards holdingCard) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(player.getName() + ": " +
                "\n Press: 1 To Show The Card" +
                "\n Press: 2 To Swap The Card With Your Holding Card" +
                "\n Press: 3 To Execute Valid Card Rule");
        int playerChoice = scanner.nextInt();
        scanner.nextLine();
        switch (playerChoice) {
            case 1: {
                addThrownCardToList(holdingCard);
                break;
            }
            case 2: {
                // Swap this card with any of the holding card
                swapThePlayersCardWithHoldingCard(player, holdingCard, scanner);
                break;
            }
            case 3: {
                // Execute Valid Card Rule.
                addThrownCardToList(holdingCard);
                executeValidCardRule(player, holdingCard, scanner);
                break;
            }
            default:
                // TODO
        }
    }

    private static void executeValidCardRule(Player player, HoldingCards card, Scanner scanner) {
        CardFace face = card.getCardFace();
        switch (face) {
            case SEVEN:
            case EIGHT: {
                // see your own cards
                System.out.println(player.getName() + ": Enter 1 2 3 4 to view your card: ");
                int cardNumber = scanner.nextInt();
                player.getPlayerHoldingCards().get(cardNumber - 1).setCardStatus(HoldingCards.CARD_STATUS.SEEN);
                System.out.println(player.getName() + " card is: " + player.getPlayerHoldingCards().get(cardNumber - 1));
                break;
            }
            case NINE:
            case TEN: {
                // see other's card
                System.out.println(player.getName() + ": Enter player's name to see their card: ");
                String playerNameToSeeCard = scanner.nextLine();
                // make sure player does not enter/select his own name
                if (!playerNameToSeeCard.equals(player.getName())) {
                    for (Player opponentPlayer : playerList) {
                        if (playerNameToSeeCard.equals(opponentPlayer.getName())) {
                            System.out.println(player.getName() + ": Enter 1 2 3 4 to view card of " + playerNameToSeeCard + ": ");
                            int cardNumber = scanner.nextInt();
                            opponentPlayer.getPlayerHoldingCards().get(cardNumber - 1).setCardStatus(HoldingCards.CARD_STATUS.OTHERS_SEEN_IT);
                            System.out.println(opponentPlayer.getName() + " card is: " + opponentPlayer.getPlayerHoldingCards().get(cardNumber - 1));
                            break;
                        }
                    }
                }
                break;
            }
            case JACK:
            case QUEEN: {
                // blind swap
                System.out.println(player.getName() + ": Enter player's name to blind swap their card: ");
                String playerNameToSwapCard = scanner.nextLine();
                // make sure player does not enter/select his own name
                if (!playerNameToSwapCard.equals(player.getName())) {
                    for (Player opponentPlayer : playerList) {
                        if (playerNameToSwapCard.equals(opponentPlayer.getName())) {
                            System.out.println(player.getName() + ": Enter your card number (1 2 3 4) to be swapped: ");
                            int playerCardNumber = scanner.nextInt();
                            System.out.println(opponentPlayer.getName() + ": Enter opponent card number(1 2 3 4) to be swapped with: ");
                            int opponentCardNumber = scanner.nextInt();
                            // get player's card to be swapped from opponent card.
                            HoldingCards playerCard = player.getPlayerHoldingCards().get(playerCardNumber - 1);
                            // remove player's card to be swapped with opponent card.
                            player.getPlayerHoldingCards().remove(playerCardNumber - 1);
                            // get opponent's card to be swapped with
                            HoldingCards opponentCard = opponentPlayer.getPlayerHoldingCards().get(opponentCardNumber - 1);
                            // removed opponent's card from their holding cards
                            opponentPlayer.getPlayerHoldingCards().remove(opponentCardNumber - 1);
                            // change opponentCard status as NOT_SEEN
                            opponentCard.setCardStatus(HoldingCards.CARD_STATUS.NOT_SEEN);
                            // add opponent cards to player's holding card.
                            player.getPlayerHoldingCards().add(playerCardNumber - 1, opponentCard);

                            // add player's card to the opponent's holding cards
                            playerCard.setCardStatus(HoldingCards.CARD_STATUS.NOT_SEEN);
                            opponentPlayer.getPlayerHoldingCards().add(opponentCardNumber - 1, playerCard);
                        }
                    }
                }
                break;
            }
            case KING: {
                // see and swap if you player wants to
                System.out.println(player.getName() + ": Enter player's name to  see & swap their card\n");
                String playerNameToSwapCard = scanner.nextLine();
                // make sure player does not enter/select his own name
                if (!playerNameToSwapCard.equals(player.getName())) {
                    for (Player opponentPlayer : playerList) {
                        if (playerNameToSwapCard.equals(opponentPlayer.getName())) {
                            System.out.println(player.getName() + ": Enter your card number (1 2 3 4) which you can see & swap.\n");
                            int playerCardNumber = scanner.nextInt();
                            System.out.println(opponentPlayer.getName() + ": Enter opponent card number(1 2 3 4) which you can see & swap.\n");
                            int opponentCardNumber = scanner.nextInt();

                            // see your card
                            HoldingCards playerCard = player.getPlayerHoldingCards().get(playerCardNumber - 1);
                            playerCard.setCardStatus(HoldingCards.CARD_STATUS.SEEN);

                            // see opponent's card
                            HoldingCards opponentCard = opponentPlayer.getPlayerHoldingCards().get(opponentCardNumber - 1);
                            System.out.println(opponentCard.toString());
                            opponentCard.setCardStatus(HoldingCards.CARD_STATUS.OTHERS_SEEN_IT);

                            System.out.println(player.getName() + " Do you want to swap the card? Enter Yes or No \n");
                            String consentOfPlayer = scanner.nextLine();
                            String choice = consentOfPlayer.toUpperCase();
                            switch (choice) {
                                case "NO": {

                                    break;
                                }
                                case "YES": {
                                    // swap the card with opponent's card
                                    player.getPlayerHoldingCards().remove(playerCardNumber - 1);
                                    opponentPlayer.getPlayerHoldingCards().remove(opponentCardNumber - 1);
                                    opponentCard.setCardStatus(HoldingCards.CARD_STATUS.SEEN);
                                    playerCard.setCardStatus(HoldingCards.CARD_STATUS.OTHERS_SEEN_IT);
                                    player.getPlayerHoldingCards().add(playerCardNumber - 1, opponentCard);
                                    opponentPlayer.getPlayerHoldingCards().add(opponentCardNumber - 1, playerCard);
                                    break;
                                }
                            }

                        }
                    }
                }
                break;
            }

        }
    }

    private static void choiceAvailableToPlayerForCard(Player player, HoldingCards card) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(player.getName() + ": " +
                "\n Press: 1 To Show The Card" +
                "\n Press: 2 To Swap The Card With Your Holding Card");
        int playerChoice = scanner.nextInt();
        scanner.nextLine();
        switch (playerChoice) {
            case 1: {
                addThrownCardToList(card);
                break;
            }
            case 2: {
                swapThePlayersCardWithHoldingCard(player, card, scanner);
                break;
            }
            default:
                // TODO
        }
    }

    private static ArrayList<Player> sortPlayers(String direction, ArrayList<Player> players, Player startingPlayer) {
        int from = -1;
        int to = players.size();
        for (Player p : players) {
            from++;
            if (p.equals(startingPlayer)) {
                break;
            }
        }

        //clockwise
        if ("CLOCKWISE".equalsIgnoreCase(direction)) {
            ArrayList<Player> clocwiseSortedPlayer = new ArrayList<>();
            clocwiseSortedPlayer.addAll(players.subList(from, to));
            clocwiseSortedPlayer.addAll(players.subList(0, from));
            return clocwiseSortedPlayer;
        } else {
            //anticlockwise
            ArrayList<Player> antiClockwiseSortedPlayers = new ArrayList<>();
            for (int left = from; left >= 0; left--) {
                antiClockwiseSortedPlayers.add(players.get(left));
            }
            for (int right = from + 1; right < players.size(); right++) {
                antiClockwiseSortedPlayers.add(players.get(right));
            }
            return antiClockwiseSortedPlayers;
        }
    }

    private static void addThrownCardToList(HoldingCards card) {
        // Show the card
        card.setCardStatus(HoldingCards.CARD_STATUS.THROWN);
        thrownCardsList.add(card);
    }

    private static void swapThePlayersCardWithHoldingCard(Player player, HoldingCards card, Scanner scanner) {
        // Swap this card with any of the holding card
        System.out.println(player.getName() + ": Enter holding card 1 2 3 4,  you want to swap your card: ");
        int cardNumber = scanner.nextInt();
        thrownCardsList.add(new HoldingCards(player.getPlayerHoldingCards().get(cardNumber - 1), HoldingCards.CARD_STATUS.THROWN));
        player.swapCardFromHoldingCards(card, cardNumber - 1);

    }
}
