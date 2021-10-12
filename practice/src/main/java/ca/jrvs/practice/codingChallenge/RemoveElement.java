package ca.jrvs.practice.codingChallenge;
/**
https://www.notion.so/jarvisdev/Remove-Element-2e8955cf0aff4200ad1d9bab7f3a571a
**/
 public class RemoveElement {
  /**
   * Big-O: O(n): iterate through the array of length n
   *
   * @param nums input int array to remove an special value from it
   * @param val  value that should be removed from the input array
   * @return the array with the val removed
   */
  public static int removeElement(int[] nums, int val) {

    int i = 0;
    for (int j: nums) {
      if (j != val) {
        nums[i] = j;
        i++;
      }
    }
    return i;
  }
}
