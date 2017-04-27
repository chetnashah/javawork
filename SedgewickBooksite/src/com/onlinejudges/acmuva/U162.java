package com.onlinejudges.acmuva;

import java.util.*;

/**
 * Created by jayshah on 18/4/17.
 */
public class U162 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Stack<String> dealerCards = new Stack<>();
        Stack<String> nonDealerCards = new Stack<>();
        Stack<String>[] players = new Stack[2];
        players[0] = dealerCards;
        players[1] = nonDealerCards;
        int numFaceCards = 4 * 4;
        List<String> allWords = new ArrayList<>();
        // scan start
        for(int i=0;i<4;i++) {
            String line = sc.nextLine();
            String[] words = line.split(" ");
            allWords.addAll(Arrays.asList(words));
        }
        for(int i=0;i<26;i++){
            nonDealerCards.push(allWords.get(2*i));
            dealerCards.push(allWords.get(2*i+1));
        }
        // stacks setup end

        // solve start
        Stack<String> playStack = new Stack<>();
        int winner = 0;
        while(players[0].size() > 0 && players[1].size() > 0){
            String dealerCard = dealerCards.peek();
            String nonDealerCard = nonDealerCards.peek();
            if(isFaceCard(dealerCard)) {
                System.out.println("dealer got face card " + dealerCard);
            } else if(isFaceCard(nonDealerCard)) {
                System.out.println("non dealer got face card " + nonDealerCard);
            } else {
                dealerCards.pop();
                nonDealerCards.pop();
            }
        }
        // solve end
    }


    private static boolean isFaceCard(String dealerCard) {
        char cardNum = dealerCard.charAt(1);
        if(cardNum == 'J' || cardNum == 'Q' || cardNum == 'K' || cardNum == 'A') {
            return true;
        }
        return false;
    }
}
