package core;

import Io.Window;

public interface IGameLogic {

    void init(Window window) throws Exception;

    void update(double interval, Window window);

    void render(Window window);
}
