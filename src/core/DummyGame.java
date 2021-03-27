package core;

import static org.lwjgl.opengl.GL11.glViewport;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import Io.Window;
import collision.AABB;
import entity.Entity;
import entity.Transform;
import entity.Player.Player;
import render.Camera;
import render.Model;
import render.Renderer;
import render.Shader;
import render.Texture;
import world.Tile;
import world.TileRenderer;
import world.World;

public class DummyGame implements IGameLogic {

	private Shader shader;

	private Camera cam;
	private TileRenderer tileRenderer;
	private World world;


	private final Renderer renderer;


	public DummyGame() {
		renderer = new Renderer();
	}

	@Override
	public void init(Window window) throws Exception {
		renderer.init();
		tileRenderer = new TileRenderer();
		Entity.initAsset();
		shader = new Shader("shader");
		cam = new Camera(window.getWidth(), window.getHeight());
		world = new World("test",cam);
	}


	@Override
	public void update(double interval, Window window) {
		world.update((float)interval, window, cam);
	}

	@Override
	public void render(Window window) {
		world.correctCamera(cam, window);
		renderer.clear();
		world.render(tileRenderer, shader, cam, window);
	}
}