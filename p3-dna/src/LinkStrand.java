public class LinkStrand implements IDnaStrand{

    //given instance variables
    private Node myFirst, myLast;
    private long mySize;
    private int myAppends;
    private int myIndex;
    private Node myCurrent;
    private int myLocalIndex;

    //inner class
    private class Node{
        private String info;
        private Node next;

        Node(String x){
            info = x;
        }
        Node(String x, Node node){
            info = x;
            next = node;
        }
    }

    public LinkStrand(){
        this("");
    }

    public LinkStrand(String str) {
        initialize(str);
    }

    @Override
    public long size() {
        // TODO Auto-generated method stub
        return mySize;
    }

    @Override
    public void initialize(String str) {
        // TODO Auto-generated method stub
        
        myFirst = new Node(str);
        myLast = myFirst;
        mySize = str.length();
        myAppends = 0;
        myIndex = 0;
        myCurrent = myFirst;
        myLocalIndex = 0;
    }

    @Override
    public IDnaStrand getInstance(String source) {
        // TODO Auto-generated method stub
        return new LinkStrand(source);
    }

    @Override
    public IDnaStrand append(String dna) {
        // TODO Auto-generated method stub
        Node newNode = new Node(dna);
        myLast.next = newNode;
        myLast = myLast.next;
        mySize = mySize + myLast.info.length();
        myAppends++;
        return this;
    }

    @Override
    public IDnaStrand reverse() {

        LinkStrand revStrand = new LinkStrand();
        Node temp = myFirst;
        while(temp != null){
            StringBuilder tempDNA = new StringBuilder(temp.info);
            String revTempDNA = tempDNA.reverse().toString();
            Node revNode = new Node(revTempDNA);
            revStrand.mySize = revStrand.mySize + revNode.info.length();
            revNode.next = revStrand.myFirst;
            revStrand.myFirst = revNode;
            temp = temp.next;
            }
        return revStrand;
        
    }

    @Override
    public int getAppendCount() {
        // TODO Auto-generated method stub
        return myAppends;
    }

    @Override
    public char charAt(int index) {
        // TODO Auto-generated method stub
        if(index < 0){
            throw new IndexOutOfBoundsException();
        }
        if(index >= this.mySize){
            throw new IndexOutOfBoundsException();
        }
        if(myIndex >= index){
            myIndex = 0;
            myLocalIndex = 0;
            myCurrent = myFirst;
        }
        while(index != myIndex){
            myIndex++;
            myLocalIndex++;

            if(myCurrent.next != null && myLocalIndex >= myCurrent.info.length()){
                myCurrent = myCurrent.next;
                myLocalIndex = 0;
            }
           
        }

        return myCurrent.info.charAt(myLocalIndex);
    }


    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        Node current = myFirst;
        while(current != null){
            builder.append(current.info);
            current = current.next;
        }
        return builder.toString();
    }

    
}
