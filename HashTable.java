package sample;

public class HashTable {
    String [] table;
    int size, collisions, longestChain;

    HashTable(){
        size = 1000;
        table = new String[size];
        longestChain = 0;
    }
    HashTable(int n){
        table = new String[n];
        size = n;
        longestChain = 0;
    }
    void insert(String word){
        boolean inserted, hasCollided, isChain;
        int address, numChain;
        address = hash(word);
        hasCollided = false;
        numChain = 0;
        isChain = true;
        do {
            if (table[address] == null) {
                table[address] = word;
                inserted = true;
            }else{
                if((hash(table[address]) == hash(word)) && isChain){
                    numChain++;
                }else isChain = false;
                address++;
                hasCollided = true;
                inserted = false;
            }
            if(address >= table.length-1) address = 0;
        }while(!inserted);
        if(numChain > longestChain) longestChain = numChain;
        if(hasCollided)collisions++;
    }
    boolean contains(String word){
        int address, wrapAround;
        for(int i = 0; i < word.length(); i++){
            if(!Character.isAlphabetic(word.charAt(i))){
                word = word.replace(word.charAt(i) + "", "");
            }
        }
        if(word.length() <= 1) return true;
        address = hash(word);
        wrapAround = address;
        do{
            if(table[address] != null && table[address].equals(word))return true;
            address++;
            if (address == table.length - 1) address = 0;
        }while(address != wrapAround);
        return false;
    }
    int count(){
        int totalCount;
        totalCount = 0;
        for(int i = 0; i < table.length; i++){
            if(table[i] != null) totalCount++;
        }
        return totalCount;
    }
    int hash(String word){
        int factor, exponent, charValue, sum, address;
        exponent = 3;
        factor = 31;
        sum = 0;

        for(int i = 0; i < word.length(); i++){
            charValue = getCharValue(word.charAt(i));
            sum += charValue*(int)Math.pow(factor,exponent);
            exponent--;
            if (exponent == -1) exponent = 3;
        }
        address = sum%table.length;
        return address;
    }
    int getCharValue(char letter){
        int results;
        results = (int)letter;
        if (results >=97 && results <= 122){
            return results -96;
        }else return 0;
    }
}
