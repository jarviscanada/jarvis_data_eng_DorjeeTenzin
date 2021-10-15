package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

public class TwoSums {

  /**
   * Complexity: O(n), traverse the list in worse case scenario n times plus hash lookup. O(n) + O(1)
   * @param nums
   * @param target
   * @return int array with the indexes and values format[index1, value1, index2, value2]
   */
  public static int [] hash (int[] nums, int target){
    Map<Integer, Integer> dict = new HashMap<>();
    for(int i = 0; i < nums.length; i++) {
      int diff = target - nums[i];
      if (dict.containsKey(diff)){
        return new int[] {dict.get(diff), diff, i, nums[i]};
      }
      dict.put(nums[i], i);
    }
    return null;
  }

  /**
   * Complexity: O(n^2), traverse through two loops at length n. O(n) * O(n)
   * @param nums
   * @param target
   * @return int array with the indexes and values format[index1, value1, index2, value2]
   */
  public static int [] bruteForce (int[] nums, int target){
    for(int i = 0; i < nums.length; i++){
      for(int j = i + 1; j < nums.length; j++) {
        if (nums[i] == target - nums[j]){
          return new int[] {i, nums[i], j, nums[j]};
        }
      }
    }
    return null;
  }
}

