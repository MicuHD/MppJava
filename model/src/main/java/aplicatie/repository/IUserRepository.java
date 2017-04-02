package aplicatie.repository;

/**
 * Created by Micu on 4/2/2017.
 */
public interface IUserRepository<ID,T>  extends IRepository<ID,T>{
    T login(T user);
}
