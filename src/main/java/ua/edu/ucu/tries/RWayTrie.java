package ua.edu.ucu.tries;

import java.util.ArrayList;
import java.util.LinkedList;

public class RWayTrie implements Trie {

    TrieNode root = new TrieNode();

    private TrieNode getWordNode(String word)
    //assistant class to not avoid code duplication
    //attempts to return node which corresponds to such word
    {
        char[] chars = word.toLowerCase().toCharArray();
        int last = chars.length; //index of last + 1
        int i = 0;
        int charIndex;
        TrieNode thisNode = root;
        while (i < last) {
            charIndex = (int) chars[i] - 97; //get array value of current char
            if (thisNode.followers[charIndex] == null) {
                return null; //case when word search failed beforehand
            }
            thisNode = thisNode.followers[charIndex];
            ++i;
        }
        //when all chars had been found and this char is the end of some word
        return thisNode;
    }

    @Override
    public void add(Tuple t) {
        char[] chars = t.term.toLowerCase().toCharArray();
        int last = chars.length; //index of last + 1
        int i = 0;
        int charIndex;
        TrieNode thisNode = root;
        while (i < last) { // when all elements are placed and index out of charArray range
            charIndex = (int) chars[i] - 97; //get array value of current char
            if (thisNode.followers[charIndex] == null)
                thisNode.followers[charIndex] = new TrieNode(); //add new node
            thisNode = thisNode.followers[charIndex];
            ++i;
        }
        thisNode.value = t.weight; // initialize with tuple's length
    }

    @Override
    public boolean contains(String word) {
        TrieNode thisNode = this.getWordNode(word);
        if ((thisNode != null) && (thisNode.value != 0)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String word) {
        TrieNode thisNode = this.getWordNode(word);
        if ((thisNode == null) || (thisNode.value == 0)) {
            return false;
        }
        thisNode.value = 0; // deleting
        return true;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }


    public Iterable<String> wordsWithPrefixSizeK(String s, int k) {
        ArrayList<String> result = new ArrayList<>();
        TrieNode thisNode = this.getWordNode(s);
        if (thisNode == null) { //such prefix exists
            return result; // empty ArrayList because no such words
        }
        if (thisNode.value != 0) result.add(s); // if the prefix IS a word too

        LinkedList<QueueNode> BFS = new LinkedList<>();

        QueueNode first = new QueueNode(thisNode, s); //forming first element

        BFS.add(first);

        StringBuilder prefix;
        StringBuilder newPrefix = new StringBuilder();
        do { //forming BFS sequence and processing it
            int charNumber = 0;
            thisNode = BFS.getFirst().node;
            prefix = BFS.getFirst().prefix;
            while (charNumber < 26) {
                if (thisNode.followers[charNumber] != null) {
                    newPrefix = new StringBuilder(prefix);
                    newPrefix.append((char) (charNumber + 97));

                    BFS.addLast(new QueueNode(thisNode.followers[charNumber], //corresponding node
                            newPrefix)); // node's prefix

                    if (thisNode.followers[charNumber].value != 0) {
                        result.add(newPrefix.toString());
                    }
                }
                ++charNumber;
            }
            BFS.removeFirst();
        } while (newPrefix.length() > s.length() + k || !BFS.isEmpty() );

        return result;
    }



    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        ArrayList<String> result = new ArrayList<>();
        TrieNode thisNode = this.getWordNode(s);
        if (thisNode == null) { //such prefix exists
            return result; // empty ArrayList because no such words
        }
        if (thisNode.value != 0) result.add(s); // if the prefix IS a word too

        LinkedList<QueueNode> BFS = new LinkedList<>();

        QueueNode first = new QueueNode(thisNode, s); //forming first element

        BFS.add(first);

        StringBuilder prefix;
        StringBuilder newPrefix;
        do { //forming BFS sequence and processing it
            int charNumber = 0;
            thisNode = BFS.getFirst().node;
            prefix = BFS.getFirst().prefix;
            while (charNumber < 26) {
                if (thisNode.followers[charNumber] != null) {
                    newPrefix = new StringBuilder(prefix);
                    newPrefix.append((char) (charNumber + 97));

                    BFS.addLast(new QueueNode(thisNode.followers[charNumber], //corresponding node
                            newPrefix)); // node's prefix

                    if (thisNode.followers[charNumber].value != 0) {
                        result.add(newPrefix.toString());
                    }
                }
                ++charNumber;
            }
            BFS.removeFirst();
        } while (!BFS.isEmpty());

        return result;
    }

    @Override
    public int size() {
        Iterable<String> words = this.words();
        int i = 0;
        for (String word : words) { //looks lame, & I know it
            i++;
        }
        return i;
    }

    private class TrieNode {

        TrieNode[] followers; //ptrs to other words
        int value = 0; //default value of not-existing word

        TrieNode() {
            followers = new TrieNode[26];
        }
    }

    private class QueueNode {
        TrieNode node;
        StringBuilder prefix;

        QueueNode(TrieNode correspondingNode, CharSequence pref) {
            this.node = correspondingNode;
            this.prefix = new StringBuilder(pref);
        }
    }

}
