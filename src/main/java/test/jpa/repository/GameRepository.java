package test.jpa.repository;

import test.jpa.model.Game;

public class GameRepository extends Repository<Game> {

    @Override
    public Class<Game> getEntityClass() {
        return Game.class;
    }
}
