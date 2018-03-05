import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Stack;

public class SocialNetwork{
	public static void main(String [] args){
		
		long startTime = System.currentTimeMillis();
		String keyword = "LISTY";
		HashSet<String> dict = createDict();
		HashSet<String> family = new HashSet<String>();

		// Keeps track of what we've seen to ensure no duplicates
		HashSet<String> seenBefore = new HashSet<String>();

		// Keeps track of the words we still have to explore
		Stack<String> q = new Stack<String>();
		
		q.push(keyword);

		System.out.println("Dictionary Size: " + dict.size());

		// While we still have words left to explore,
		while(!(q.empty())){

			// Find the immediate friends of the next word
			String _keyword = q.pop();
			HashSet<String> newFamily = findFriends(_keyword, dict);

			/*
			// Debugging output 
			System.out.println("-----------------------------------------------------------------------------");
			System.out.println("Keyword: "+ _keyword);
			System.out.println("Family: " );
			print(newFamily);
			System.out.println();
			*/

			// If we haven't seen the word before, add it to the family and to the words to be explored
			for(String s : newFamily){
				if(!seenBefore.contains(s)){
					seenBefore.add(s);
					dict.remove(s);
					family.add(s);
					q.push(s);
				}
			}
			
			/*
			// Debugging output
			System.out.println("Family Size: "+ family.size());
			*/
		}

		long endTime = System.currentTimeMillis();
		int timePassed = (int)(endTime - startTime) / 1000;
		System.out.println(family.size());
		/*
		// Debugging Output
		print(family);
		*/
		System.out.println("Time passed: " + timePassed);



	}

	// Create a HashSet of the given dictionary
	public static HashSet<String> createDict(){

		File f = new File("very_small_test_dictionary.txt");
		Scanner s;
		HashSet<String> dict = new HashSet<String>();
		try{
			s = new Scanner(f);
			while(s.hasNext()){
				dict.add(s.nextLine());
			}
			return dict;

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}

		System.out.println("Empty Dictionary");
		return dict;

	}

	// Return the set of words in dict with an edit distance of 1 or less
	public static HashSet<String> findFriends(String keyword, HashSet<String> dict){
		// Friends will be the set of all words in dict with edit distance <= 1 from keyword
		HashSet<String> friends = new HashSet<String>();

		// For each word in the dictionary,
		for(String s : dict){
			// If it's possible for the edit distance to be <= 1
			if(Math.abs(s.length() - keyword.length()) < 2){
				// If the edit distance is <= 1, add it to the set of friends
				if(editDistance(keyword, s) < 2){
					friends.add(s);
				}
			}
		}

		return friends;
	}

	public static int editDistance(String word1, String word2){
		// Sets up the matrix for solving the edit distance with dynamic programming
		int [][] distanceMatrix = new int [word1.length()+1][word2.length()+1];
		/*
		 * Initialize the first row and column = to number of characters
		 * Reason: The maximum edit distane between 2 strings is the length of the longer string
		 * and the first row and column are the edit distane of building the strings from scratch
		 */
		for(int i=1; i<= word1.length(); i++){
			distanceMatrix[i][0] = i; 
		}

		for(int j =1; j<=word2.length(); j++){
			distanceMatrix[0][j] = j;
		}


		int cost;
		//int rowMin;
		for(int i=1; i<= word1.length(); i++){
			//rowMin = word2.length();
			for(int j=1; j<= word2.length(); j++){
				/*
				 * If the i'th character in word1 and the j'th character in word 2 are the same,  
				 * no operation is needed to transform the strings at that index, so cost = 0. 
				 * Otherwise, the cost of substituting a char is 1. 
				 */ 
				if(word1.charAt(i-1) == word2.charAt(j-1)){
					cost = 0;
				}
				else{
					cost = 1;
				}
				distanceMatrix[i][j] = minimum(distanceMatrix[i-1][j] +1, distanceMatrix[i][j-1] +1, distanceMatrix[i-1][j-1] + cost);
			}
		}
		/*
		// Debugging code
		print(distanceMatrix);
		*/
		return distanceMatrix[word1.length()][word2.length()];
	}


	public static int minimum(int a, int b, int c){
		return Math.min(Math.min(a, b), c);
	}

	// Print out a set
	public static void print(HashSet<String> set){
		for(String s: set){
			System.out.println(s);
		}
	}

	// Print out the dynamic programming matrix from editDistance
	public static void print(int [][] arr){
		for(int i = 0; i<arr.length; i++){
			for(int j=0; j<arr[0].length; j++){
				System.out.print(arr[i][j]);
			}
		System.out.println();
		}

		System.out.println();
	}


}