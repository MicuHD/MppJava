package aplicatie.utils;


public interface Observer<E> {
    void update(Observable<E> observable);
}