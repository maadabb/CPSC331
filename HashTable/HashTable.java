//Assignment #3
//TA: Si Zhang
//Professor: Phillip Fong
//Name: Maad Ahmed Abbasi
//UCID: 30127307
//Date: November 20th 2023

package ca.ucalgary.cpsc331.a3;

public class HashTable implements Dictionary {
    private static final int tableSize = 17;
    private static final String DELETED = "deleted";

    private String[] mytable;


    /**
     Constructor
     */
    public HashTable() {
        mytable = new String[tableSize];
    }

    /**
     * Checks if our hashtable is full
     * Check if hashtable is full - O(n)
     * @return true if the hashtable is full otherwise returns false
     */
    @Override
    public boolean full() {
        for (String slot : mytable) {
            if (slot == null || slot.equals(DELETED)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Searches through the hashtable using linear probing in order to find k in the hashtable
     * search for String k - O(n)
     * @param k
     * @return true if k is found otherwise returns false
     */
    @Override
    public boolean member(String k) {
        int hashk = k.hashCode();
        int index = Math.abs(hashk % tableSize);
        int orginalIndex = index;
        do {
            if (mytable[index] != null && mytable[index].equals(k)) {
                return true;
            }
            index = (index + 1) % tableSize;
        } while (index != orginalIndex);
        return false;
    }

    /**
     * Used to insert key k into the hashtable using linear probing
     * insert String k - O(n)
     * @param k
     * @return  true if the key was entered into the hashtable, otherwise returns false
     */
    @Override
    public boolean insert(String k) {
        int i = Math.abs(k.hashCode() % tableSize);
        int start = i;
        int insertPos = -1;
        while (mytable[i] != null) {
            if (mytable[i].equals(k)) {
                return false;
            } else if (mytable[i].equals(DELETED) && insertPos == -1) {
                insertPos = i;
            }
            i = (i + 1) % tableSize;
            if (i == start) {
                break;
            }
        }
        if (insertPos == -1) {
            insertPos = i;
        }
        mytable[insertPos] = k;
        return true;
    }


    /**
     * Used to delete key k from the hashtable using linear probing
     * delete String k - O(n)
     * @param k
     * @return true if the key was deleted otherwise returns false
     */
    @Override
    public boolean delete(String k) {
        int hashk = k.hashCode();
        int index = Math.abs(hashk % tableSize);
        int startindex = index;
        boolean found = false;

        do {
            if (mytable[index] != null && mytable[index].equals(k)) {
                mytable[index] = DELETED;
                found = true;
                break;
                }
                index = (index + 1) % tableSize;
            } while (index != startindex);

        return found;
        }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < tableSize; i++) {
            if (mytable[i] != null) {
                result.append(i).append(":").append(mytable[i].equals(DELETED) ? "deleted" : "\"" + mytable[i] + "\"").append("\n");
            }
        }
        return result.toString();
    }

}