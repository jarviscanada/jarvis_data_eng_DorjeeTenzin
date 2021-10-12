package ca.jrvs.practice.codingChallenge;

import static java.util.Arrays.sort;

import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RemoveElementTest {

  @Test
  public void removeElement() {
    int[] nums = new int[]{3, 6, 2, 7, 5, 7};
    int val = 7;
    int[] expected = new int[]{3, 6, 2, 5};

    int k = RemoveElement.removeElement(nums, val);

    assert k == expected.length;
    for (int i = 0; i < k; i++) {
      assert nums[i] == expected[i];
    }
  }
}