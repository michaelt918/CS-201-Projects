import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashListAutocomplete implements Autocompletor {

    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;

    public HashListAutocomplete(String[] terms, double[] weights) {
        
        if (terms == null || weights == null) {
			throw new NullPointerException("One or more arguments null");
		}

		if (terms.length != weights.length) {
			throw new IllegalArgumentException("terms and weights are not the same length");
		}
		initialize(terms,weights);
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        // TODO Auto-generated method stub
        if(prefix.length() > MAX_PREFIX){
            prefix = prefix.substring(0, MAX_PREFIX);
        }
        List<Term> list = new ArrayList<>();
        if(myMap.containsKey(prefix)){
            List<Term> pValues = myMap.get(prefix);
            list = pValues.subList(0, Math.min(pValues.size(), k));
        }
        return list;
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        // TODO Auto-generated method stub
        myMap = new HashMap<String, List<Term>>();
        for(int i = 0; i < terms.length; i++){
            String kWord = terms[i];
            Term term = new Term(kWord, weights[i]);
            mySize += BYTES_PER_CHAR * kWord.length() + BYTES_PER_DOUBLE;
            for(int j = 0; j <= Math.min(MAX_PREFIX, kWord.length()); j++){
                String key = kWord.substring(0, j);
                if(!myMap.containsKey(key)) {
                    mySize += BYTES_PER_CHAR * j;
                }
                myMap.putIfAbsent(key, new ArrayList<Term>());
                myMap.get(key).add(term);
            }
        }
        for(String s : myMap.keySet()){
            Collections.sort(myMap.get(s), Comparator.comparing(Term::getWeight).reversed());
        }
    }

    @Override
    public int sizeInBytes() {
        // TODO Auto-generated method stub
        return mySize;

    }
    
}

