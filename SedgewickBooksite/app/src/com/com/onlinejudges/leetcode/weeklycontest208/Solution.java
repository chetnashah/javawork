package com.com.onlinejudges.leetcode.weeklycontest208;

public class Solution {
    public int minOperationsMaxProfit(int[] customers, int boardingCost, int runningCost) {
        int custInQueueCount=0;
        int profit=0, operation=0;
        int maxProfit=0,i,minOp=0,boardCustomer=0;
        for(i=0;i<customers.length;i++){
            custInQueueCount+=customers[i];
            boardCustomer+=Math.min(4,custInQueueCount);
            custInQueueCount-=Math.min(4,custInQueueCount);
            profit=boardCustomer*boardingCost - (i+1)*runningCost;
            if(profit>maxProfit){
                System.out.println("Profit:: "+profit+"MaxProfit::"+maxProfit);

                maxProfit=profit;
                minOp=(i+1);
            }

        }
        System.out.println(i);

        while(custInQueueCount>0){
            boardCustomer+=Math.min(4,custInQueueCount);
            custInQueueCount-=Math.min(4,custInQueueCount);
            profit=boardCustomer*boardingCost - (i)*runningCost;
            i++;
            if(profit>maxProfit){
                System.out.println("BoardCust"+boardCustomer+"Profit:: "+profit+"MaxProfit::"+maxProfit);
                maxProfit=profit;
                minOp=i;
            }
        }

        if(maxProfit==0){
            return -1;
        }

        else{
            return minOp;
        }

    }
}
