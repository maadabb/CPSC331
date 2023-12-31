package ca.ucalgary.cpsc331.a3;

public interface Dictionary {
    // full(): returns true iff Dictionary is full.
    boolean full();

    // member(k): returns true iff key k is a member
    // of the Dictionary.
    boolean member(String k);

    // insert(k): If key k is not a member of the
    // Dictionary and the latter is not yet full,
    // then the method inserts k and returns true.
    // If key k is not a member of the Dictionary,
    // but the latter is already full, then the
    // method raises a RuntimeException. Otherwise,
    // the method returns false.
    boolean insert(String k);

    // delete(k): If key k is a member of the
    // Dictionary, then the method deletes k and
    // returns true. Otherwise, the method returns
    // false.
    boolean delete(String k);
}

