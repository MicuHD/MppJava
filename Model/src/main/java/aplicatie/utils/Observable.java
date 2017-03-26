package aplicatie.utils;

public interface Observable<E> {
    void addObserver(Observer<E> o);
    void removeObserver(Observer<E> o);
    public void notifyObservers();
} 