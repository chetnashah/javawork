package com.com.onlinejudges.leetcode;

import java.util.PriorityQueue;

public class DungeonEscape {

    // Node class representing each cell with time, x, y.
    static class Node implements Comparable<Node> {
        long time;
        int x;
        int y;

        Node(long time, int x, int y) {
            this.time = time;
            this.x = x;
            this.y = y;
        }

        // Comparison based on time for PriorityQueue ordering
        @Override
        public int compareTo(Node other) {
            return Long.compare(this.time, other.time);
        }
    }

    public static long minimumTime(int[][] moveTime) {
        int n = moveTime.length;
        int m = moveTime[0].length;

        // Directions: up, down, left, right
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        // Initialize arrival times to infinity
        long[][] arrival = new long[n][m];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                arrival[i][j] = Long.MAX_VALUE;
            }
        }
        arrival[0][0] = 0;

        // PriorityQueue to process nodes based on earliest arrival time
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(0, 0, 0));

        while(!pq.isEmpty()) {
            Node current = pq.poll();
            long currentTime = current.time;
            int x = current.x;
            int y = current.y;

            // If we've reached the destination, return the time
            if(x == n - 1 && y == m - 1) {
                return currentTime;
            }

            // If we've already found a better way to this cell, skip
            if(currentTime > arrival[x][y]) {
                continue;
            }

            // Explore all adjacent cells
            for(int dir = 0; dir < 4; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];

                // Check boundaries
                if(nx >= 0 && nx < n && ny >= 0 && ny < m) {
                    // Calculate departure time
                    // To move to (nx, ny), departure time is max(currentTime, moveTime[x][y], moveTime[nx][ny})
                    long departureTime = Math.max(currentTime, Math.max(moveTime[x][y], moveTime[nx][ny]));

                    // Arrival time at the next cell
                    long arrivalTime = departureTime + 1;

                    // If this path is better, update and enqueue
                    if(arrivalTime < arrival[nx][ny]) {
                        arrival[nx][ny] = arrivalTime;
                        pq.offer(new Node(arrivalTime, nx, ny));
                    }
                }
            }
        }

        // If destination is unreachable (shouldn't happen per constraints)
        return -1;
    }

    // Example usage and test cases
    public static void main(String[] args) {
        // Test Case 1
        int[][] moveTime1 = {{0, 1}, {2, 3}};
        System.out.println(minimumTime(moveTime1)); // Expected Output: 6

        // Test Case 2
        int[][] moveTime2 = {
                {0, 0, 0},
                {0, 0, 0}
        };
        System.out.println(minimumTime(moveTime2)); // Expected Output: 3

        // Test Case 3
        int[][] moveTime3 = {
                {0, 1},
                {1, 2}
        };
        System.out.println(minimumTime(moveTime3)); // Expected Output: 3
    }
}
