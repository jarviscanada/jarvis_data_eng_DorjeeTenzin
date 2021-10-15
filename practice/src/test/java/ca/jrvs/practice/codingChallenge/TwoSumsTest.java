package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TwoSumsTest {
  private int[] nums;
  private int target;

  @Before
  public void setUp() {
    nums = new int[]{2,7,11,15};
    target = 9;
  }
  @Test
  public void hash() {
    int[] expected = new int[]{0,2,1,7};

    int[] result = TwoSums.hash(nums, target);

    assert expected.length == result.length;
    for (int i = 0; i < expected.length; i++ ) {
      assert result[i] == expected[i];
    }
  }

  @Test
  public void bruteForce() {
    int[] expected = new int[]{0,2,1,7};

    int[] result = TwoSums.bruteForce(nums, target);

    assert expected.length == result.length;
    for (int i = 0; i < expected.length; i++) {
      assert result[i] == expected[i];
    }
  }
}