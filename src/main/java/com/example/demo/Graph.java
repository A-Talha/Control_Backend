package com.example.demo;

import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

@Component
public class Graph {

    /**Talha Puts his arrays here and functions down **/
    /**Tefa uses them and gives us the c*/
    private enum visit {NOT_VISITED, IN_STACK, VISITED};
    private ArrayList<StringBuilder> arrayList = new ArrayList(); // Loops names
    private ArrayList<Integer> arryList = new ArrayList(); // Loops gains
    private HashMap<String, Integer> loops = new HashMap();
    private HashMap<String, Integer> loopsCheck = new HashMap();
    private int n;
    private visit[] visited = new visit[1000];
    private int[][] graph = new int[1000][1000];
    private boolean[][] relations;
    private ArrayList<ArrayList<Integer>> nonTouchingC = new ArrayList<>();
    public Graph(int[][] g)
    {
        n = g.length;
        for(int i = 0; i < 1000; ++i) {
            for(int j = 0; j < 1000; ++j) {
                graph[i][j] = 0;
            }
        }
        for(int i = 1; i <= n; i++)
        {
            for(int j = 1; j <= n; j++)
            {
                graph[i][j]=g[i-1][j-1];
            }
        }
    }
    public ArrayList<ArrayList<Integer>> solve()
    {
        findCycles();
        findNontouching();
        findCombinations();
        /*Call Your functions here ya regala**/
        ArrayList<ArrayList<Integer>> aykalam = new ArrayList<>();
        return aykalam;
    }
    private void findCombinations(){
        for(int i = 1; i < 1<<arrayList.size(); i++)
        {
            ArrayList<Integer> in = new ArrayList<>();
            for(int j = 0; j < arrayList.size(); j++)
            {
                if(((1<<j)&i)!=0)
                    in.add(j+1);
            }
            boolean nonTouching = true;
            for(int x : in)
            {
                for(int y : in)
                {
                    if(x==y) continue;
                    nonTouching &= !relations[x][y];
                }
            }
            if(nonTouching)
                nonTouchingC.add(in);
        }
        for(int i = 0; i < nonTouchingC.size(); i++)
        {
            for(int j = 0; j < nonTouchingC.get(i).size(); j++)
            {
                System.out.print(nonTouchingC.get(i).get(j) + " ");
            }
            System.out.println();
        }

    }
    private void findNontouching()
    {
        int[][] bit_map = new int[arrayList.size()+1][n+1];
        relations = new boolean[arrayList.size()+1][arrayList.size()+1];
        int it = 1;
        for(StringBuilder iter: arrayList)
        {
            for(int i = 0; i < iter.length(); i++)
            {
                bit_map[it][(iter.charAt(i)-'0')] = 1;
            }
            it++;
        }
        /**
         * 0000
         * 0100
         * 0101
         * 0110*/
        for(int i = 1; i <= arrayList.size(); i++)
        {
            for(int j = i+1; j <= arrayList.size(); j++)
            {
                int x = 0;
                for(int k = 1; k <= n; k++)
                {
                    x |= (bit_map[i][k]&bit_map[j][k]);
                }
                if(x!=0) // found intersection
                {
                    relations[i][j] = true;
                    relations[j][i] = true;
                }
            }
        }
        for(int i = 1; i <= arrayList.size(); i++)
        {
            for(int j = 1; j <= arrayList.size(); j++)
            {
                System.out.print(relations[i][j] + " ");
            }
            System.out.println();
        }
    }
    private void findCycles() {
        int i;
        for(i = 0; i < 1000; ++i) {
            this.visited[i] = visit.NOT_VISITED;
        }
        for(i = 1; i <= n; ++i) {
            Stack<Integer> stack = new Stack();
            stack.push(i);
            this.visited[i] = visit.IN_STACK;
            this.processDFSTree(stack, n, 1);
        }
        for(i = 0; i < arrayList.size(); ++i) {
            PrintStream var10000 = System.out;
            Object var10001 = arrayList.get(i);
            var10000.println(var10001 + " " + arryList.get(i));
        }

    }
    private String sortString(String inputString) {
        char[] tempArray = inputString.toCharArray();
        Arrays.sort(tempArray);
        return new String(tempArray);
    }
    private void processDFSTree(Stack<Integer> stack, int n, int gain) {
        int curr = (Integer)stack.peek();

        for(int j = 1; j <= n; ++j) {
            if (this.graph[curr][j] != 0) {
                if (this.visited[j] == visit.IN_STACK) {
                    stack.push(j);
                    this.recordStack(stack, gain*this.graph[curr][j]);
                } else if (this.visited[curr] != visit.VISITED) {
                    stack.push(j);
                    this.visited[j] = visit.IN_STACK;
                    this.processDFSTree(stack, n, gain * this.graph[curr][j]);
                    stack.pop();
                }
            }
        }
        this.visited[curr] = visit.VISITED;
    }

    private void recordStack(Stack<Integer> stack, int gain) {
        String path = new String();
        Stack<Integer> stack1 = new Stack();
        int x = (Integer)stack.pop();
        path = path + x;
        while((Integer)stack.peek() != x) {
            int y = (Integer)stack.pop();
            path = path + y;
            stack1.push(y);
        }
        String buffer = sortString(path);
        path = path + x;
        if (!loops.containsKey(buffer)) {
            loops.put(buffer, gain);
            StringBuilder a = new StringBuilder();
            a.append(path);
            a.reverse();
            arrayList.add(a);
            arryList.add(gain);
        }
        while(!stack1.empty()) {
            stack.push((Integer)stack1.pop());
        }
    }

}
