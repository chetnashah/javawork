package com.com.onlinejudges.leetcode.weeklycontest158;

import java.util.ArrayList;
import java.util.List;

public class QueenAttackKing {
    public static void main(String[] args) {
        int[][] queens = new int[][]{{5,6},{7,7},{2,1},{0,7},{1,6},{5,1},{3,7},{0,3},{4,0},{1,2},{6,3},{5,0},{0,4},{2,2},{1,1},{6,4},{5,4},{0,0},{2,6},{4,5},{5,2},{1,4},{7,5},{2,3},{0,5},{4,2},{1,0},{2,7},{0,1},{4,6},{6,1},{0,6},{4,3},{1,7}};
        int[] king = new int[]{3,4};

        QueenAttackKing kk = new QueenAttackKing();
        kk.queensAttacktheKing(queens,king);
    }
    public List<List<Integer>> queensAttacktheKing(int[][] queens, int[] king) {
        int[][] board = new int[8][8];
        for(int i=0; i< queens.length; i++) {
            int[] queen = queens[i];
            board[queen[0]][queen[1]] = 1; // 1 means queen
        }
        board[king[0]][king[1]] = 2;

        List<List<Integer>> arr = new ArrayList<>();
        System.out.println(checkQueenAttacksKing(board, new int[] {4, 3}, new int[]{3,4}));
        for(int i = 0; i<8; i++){
            for(int j =0; j<8; j++) {
                if (board[i][j] == 1) {
                    if (checkQueenAttacksKing(board, new int[]{i,j}, king)){
                        List<Integer> q = new ArrayList<>();
                        q.add(i); q.add(j);
                        arr.add(q);
                    }
                }
            }
        }
        return arr;
    }

    public boolean checkQueenAttacksKing(int[][] board, int[] queen, int[] king) {
        int qRow = queen[0];
        int qCol = queen[1];
        int kRow = king[0];
        int kCol = king[1];
        int qkRowDiff = Math.abs(qRow - kRow);
        int qkColDiff = Math.abs(qCol - kCol);
        if (qRow != kRow && qCol != kCol && qkRowDiff != qkColDiff) {
            return false;
        }
        boolean midQueen = false;
        if (qRow == kRow) {
            if (qCol > kCol){
                int j = qCol;
                while(j >= 0 && j != kCol){
                    j--;
                    if (board[qRow][j] == 1){
                        return false;
                    }
                }
                return true;
            }
            if (qCol < kCol){
                int j = qCol;
                while(j < 8 && j != kCol){
                    j++;
                    if (board[qRow][j] == 1){
                        return false;
                    }
                }
                return true;
            }
        }
        if (qCol == kCol) {
            if (qRow > kRow) {
                int j = qRow;
                while (j >= 0 && j != kRow) {
                    j--;
                    if (board[j][qCol] == 1) {
                        return false;
                    }
                }
                return true;
            } else if (qRow < kRow) {
                int j = qRow;
                while (j < 8 && j != kRow) {
                    j++;
                    if (board[j][qCol] == 1) {
                        return false;
                    }
                }
                return true;
            }
        }
        if (qkRowDiff == qkColDiff) {
            if (qCol > kCol && qRow > kRow) {
                int j = qCol;
                int k = qRow;
                while (j >=0 && k >=0 && j != kCol && k != kRow) {
                    j--;
                    k--;
                    if (board[k][j] == 1) {
                        return false;
                    }
                }
                return true;
            }
            if (qCol > kCol && qRow < kRow) {
                int j = qCol;
                int k = qRow;
                while (j >=0 && k <8 && j != kCol && k != kRow) {
                    j--;
                    k++;
                    if (board[k][j] == 1) {
                        return false;
                    }
                }
                return true;
            }
            if (qCol < kCol && qRow > kRow) {
                int j = qCol;
                int k = qRow;
                while (j <8 && k >=0 && j != kCol && k != kRow) {
                    j++;
                    k--;
                    if (board[k][j] == 1) {
                        return false;
                    }
                }
                return true;
            }
            if (qCol < kCol && qRow < kRow) {
                int j = qCol;
                int k = qRow;
                while (j <8 && k <8 && j != kCol && k != kRow) {
                    j++;
                    k++;
                    if (board[k][j] == 1) {
                        return false;
                    }
                }
                return true;
            }
        }
        return true;
    }
}
