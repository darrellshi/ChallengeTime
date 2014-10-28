import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class challengeTime {
	public static void main(String[] args) throws FileNotFoundException {
		// input the name list into ArrayList<String> wordlist
		File file = new File("randomlist.txt");
		Scanner fileScanner = new Scanner(file);
		ArrayList<String> wordlist = new ArrayList<String>();
		while(fileScanner.hasNext())
			wordlist.add(fileScanner.next());
		
		ArrayList<String> friends = new ArrayList<String>();
		Scanner s = new Scanner(System.in);
		System.out.println("Please give the word you are trying to find social nectwork with:");
		String word = s.nextLine();

		findSocialNetwork(word, friends, wordlist);
		checkForItself(word, friends);

		if (friends.isEmpty())
			System.out.printf("\"%s\" doesn't have any friends :(\n", word);
		else {
			System.out.printf("Here's the list of \"%s\"'s social network: \n", word);
			for (int i = 0; i < friends.size(); i++) {
				System.out.println(friends.get(i));
			}
		}
	}

	// check if two words are friends
	public static boolean areFriends (String word1, String word2) {
		return checkMissing(word1, word2) || checkMissing(word2, word1) || checkReplacing(word1, word2);
	}

	// find all the friends of "word"
	public static void findFriends (String word, ArrayList<String> friends, ArrayList<String> wordlist) {
		for (int i = 0; i < wordlist.size(); i++) {
			String candidate = wordlist.get(i);
			if (areFriends(word, candidate) && !friends.contains(candidate)) {
				friends.add(candidate);
			}
		}
	}

	// find "word"'s friends and its friends' friends
	public static void findSocialNetwork(String word, ArrayList<String> friends, ArrayList<String> wordlist) {
		findFriends(word, friends, wordlist);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < friends.size(); j++)
				findFriends(friends.get(j), friends, wordlist);
		}
	}

	// check if adding one letter to "check" can get "base"
	public static boolean checkMissing (String base, String check) {
		if (base.length() != (check.length() + 1))
			return false;
		int right = -1;
		int left = -1;
		int i, j;
		for (i = 0, j = 0; i < base.length() && j < check.length(); i++, j++) {
			if(base.charAt(i) != check.charAt(i)) {
				right = i;
				break;
			}
			if (j == (check.length() - 1))
				return true;
		}
		for (i = (base.length() - 1), j = (check.length() - 1); i > -1 && j > -1; i--, j--) {
			if (base.charAt(i) != check.charAt(j)) {
				left = i;
				break;
			}
			if (j == 0)
				return true;
		}

		if (left == right && left != -1)
			return true;
		else
			return false;
	}

	// check if replacing one letter of "check" can get "base"
	public static boolean checkReplacing(String base, String check) {
		if (base.length() != check.length())
			return false;

		int right = -1;
		int left = -1;
		for (int i = 0; i < base.length(); i++) {
			if(base.charAt(i) != check.charAt(i)) {
				right = i;
				break;
			}
		}
		for (int i = (base.length() - 1); i > -1; i--) {
			if (base.charAt(i) != check.charAt(i)) {
				left = i;
				break;
			}
		}
		if (right == left && right != -1)
			return true;
		else
			return false;
	}

	// check if "word"'s friends list has itself in it, and delete it if it does
	public static void checkForItself(String word, ArrayList<String> friends) {
		if (friends.contains(word))
			friends.remove(word);
	}
}
