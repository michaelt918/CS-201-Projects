import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class HashMarkov implements MarkovInterface{
    
    //instance variables
    protected String[] myWords;		// Training text split into array of words 
	protected Random myRandom;		// Random number generator
	protected int myOrder;			// Length of WordGrams used
    HashMap<WordGram, List<String>> map;

    //constuctor
    public HashMarkov(int order){
		myOrder = order;
		myRandom = new Random();
        map = new HashMap<>();
	}

    
	public void setTraining(String text){
		myWords = text.split("\\s+");
        if(map.size() != 0)
            map.clear();
        for(int i = 0; i < myWords.length - myOrder; i++){
            List<String> list = new ArrayList<>();
            WordGram wGram = new WordGram(myWords, i, myOrder);
            if(! map.containsKey(wGram))
                map.put(wGram, list);
            map.get(wGram).add(myWords[i + myOrder]);
        }
	}

    public List<String> getFollows(WordGram wgram){
        List<String> ret = new ArrayList<>();
        if(map.containsKey(wgram))
            ret = map.get(wgram);
        return ret;
    }

    public String getRandomText(int length){
		ArrayList<String> randomWords = new ArrayList<>(length);
		int index = myRandom.nextInt(myWords.length - myOrder + 1);
		WordGram current = new WordGram(myWords,index,myOrder);
		randomWords.add(current.toString());

		for(int k=0; k < length-myOrder; k += 1) {
			String nextWord = getNext(current);
			randomWords.add(nextWord);
			current = current.shiftAdd(nextWord);
		}
		return String.join(" ", randomWords);
	}

    private String getNext(WordGram wgram) {
		List<String> follows = getFollows(wgram);
		if (follows.size() == 0) {
			int randomIndex = myRandom.nextInt(myWords.length);
			follows.add(myWords[randomIndex]);
		}
		int randomIndex = myRandom.nextInt(follows.size());
		return follows.get(randomIndex);
	}

    public int getOrder(){
        return myOrder;
    }

    public void setSeed(long seed){
        myRandom.setSeed(seed);
    }
}
