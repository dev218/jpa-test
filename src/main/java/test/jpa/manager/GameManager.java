package test.jpa.manager;

import test.jpa.repository.GameRepository;
import test.jpa.repository.Repository;

import javax.inject.Inject;

public class GameManager extends EntityManager {

    @Inject
    GameRepository gameRepository;

    @Override
    protected Repository getRepository() {
        return this.gameRepository;
    }
}
