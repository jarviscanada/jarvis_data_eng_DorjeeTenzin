package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;

public class MyStack {
  private LinkedList<Integer> q1;

  public MyStack() {
    q1 = new LinkedList<>();
  }

  public void push(int x) {
    q1.add(x);
    int sz = q1.size();
    while (sz > 1) {
      q1.add(q1.remove());
      sz--;
    }
  }

  public void pop() {
    q1.remove();
  }

  public int top() {
    return q1.peek();
  }

  public boolean empty() {
    return q1.isEmpty();
  }
}
