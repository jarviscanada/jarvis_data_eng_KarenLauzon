package codingChallenge;

public class TwoSum {
    public static void main(String[] args) {
        int [] nums = new int[]{2,7,11,15};
        int target =9;
        int [] output = twoSum(nums, target);
        System.out.println("nums = [2,7,11,15], target = "+target+", output: [" + output[0] +"," +output[1] +"]");

        nums = new int[]{3,2,4};
        target =6;
        output = twoSum(nums, target);
        System.out.println("nums = [3,2,4], target = "+target+", output: [" + output[0] +"," +output[1] +"]");

        nums = new int[]{3,3};
        target =6;
        output = twoSum(nums, target);
        System.out.println("nums = [3,3], target = "+target+", output: [" + output[0] +"," +output[1] +"]");

    }
    public static int[] twoSum(int[] nums, int target){
        int[] output = new int[0];

        for (int first =0; first<nums.length; first++){
            for (int second =0; second<nums.length; second++){
                if(first != second){
                    if(nums[first] + nums[second] == target){
                        output= new int[]{second, first};
                    }
                }
            }
        }

        return output;
    }
}
