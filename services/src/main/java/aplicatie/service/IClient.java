package aplicatie.service;

import aplicatie.domain.Spectacol;

/**
 * Created by Micu on 4/2/2017.
 */
public interface IClient {
    void SoldTickets(Spectacol spec) throws ShowException;
}
