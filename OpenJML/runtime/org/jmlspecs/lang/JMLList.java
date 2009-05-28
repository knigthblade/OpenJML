package org.jmlspecs.lang;
import org.jmlspecs.annotations.*;

public class JMLList<E> {

    public int _size;
    
    public static class Data {}

    @Pure @NonNull
    public JMLList<E> empty() { return null; }
    
    @Pure
    public int size() { return 0; }
    
//    //@ public normal_behavior
//    //@    ensures size() == \old(size()+1);
//    //@    ensures (\forall int i; i>=0 && i < \old(size()); get(i)N == \old(get(i)));
//    //@    ensures get(size()-1) == item;
//    void add(@Nullable E item);
    
    @Query @NonNull
    public JMLList<E> add(@Nullable E item) { return null; }

    @Nullable @Query
    public E get(int i) { return null; }
}