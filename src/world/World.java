package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.lwjgl.glfw.GLFW.*;

import javax.imageio.ImageIO;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import Io.Window;
import collision.AABB;
import entity.*;
import entity.Enemies.*;
import entity.Gui.*;
import entity.Player.*;
import render.Camera;
import render.Shader;

public class World {
	private final int view = 18;
	private byte[] tiles;
	private int width, height, scale;
	private Matrix4f world;
	private AABB[] boundingBoxes;
	private List<String> entityLayer;
	private LinkedHashMap<String, Entity> entities;
	private LinkedHashMap<String, Entity> newEntities;
	private Menu menu = new Menu(GuiStates.MENU_SIZE, new Transform());
	private Controls control = new Controls(1, new Transform());

	private int currentState;

	public World(String world, Camera camera) {
		makeNew(world, camera);
	}

	public void makeNew(String world, Camera camera) {
		try {
			Boss.id.getAndSet(0);
			BufferedImage tileSheet = ImageIO.read(new File("./etc/levels/" + world + "/tiles.png"));
			BufferedImage entitiesSheet = ImageIO.read(new File("./etc/levels/" + world + "/entities.png"));

			width = tileSheet.getWidth();
			height = tileSheet.getHeight();
			scale = 64;
			this.world = new Matrix4f().setTranslation(new Vector3f(0));
			this.world.scale(scale);
			entityLayer = new ArrayList<String>();
			entities = new LinkedHashMap<String, Entity>();
			newEntities = new LinkedHashMap<String, Entity>();
			int[] colorTileSheet = tileSheet.getRGB(0, 0, width, height, null, 0, width);
			int[] colorEntitySheet = entitiesSheet.getRGB(0, 0, width, height, null, 0, width);

			Transform transform;
			tiles = new byte[width * height];
			boundingBoxes = new AABB[width * height];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int red = (colorTileSheet[x + y * width] >> 16) & 0xFF;
					int entityIndex = (colorEntitySheet[x + y * width] >> 16) & 0xFF;
					int entityAlpha = (colorEntitySheet[x + y * width] >> 24) & 0xFF;

					Tile t = null;
					try {
						t = Tile.tiles[red];
					} catch (ArrayIndexOutOfBoundsException e) {
					}

					if (t != null) {
						setTile(t, x, y);
					}

					if (entityAlpha > 0) {
						transform = new Transform();
						transform.pos.x = x * 2;
						transform.pos.y = -y * 2;
						switch (entityIndex) {
						case 1:
							Player player = new Player(transform);
							addEntity(player);
							camera.getPosition().set(0,7000,0);
							break;

						case 2:
							Boss boss = new Boss(transform);
							addEntity(boss);

							Lifebar bar = new Lifebar(1, new Transform(), boss.getID());
							addEntity(bar);
							BorderLifeBar border = new BorderLifeBar(1, new Transform(), boss.getID());
							addEntity(border);
							break;

						default:
							break;
						}
					}
				}
			}
		} catch (IOException e) {

		}
		addEntity(menu);
		addEntity(control);
		currentState = WorldStates.WORLD_STATE_START;

	}

	public void addEntity(Entity entity) {
		entities.put(entity.getName(), entity);
		if (entity.getLayer() == 1) {
			entityLayer.add(entity.getName());
		} else {
			entityLayer.add(entity.getLayer(), entity.getName());
		}
	}

	public void addNewEntity(Entity entity) {
		newEntities.put(entity.getName(), entity);
	}

	public void removeEntityByIndex(int index) {
		String name = entityLayer.get(index);
		entities.remove(name);
		entityLayer.remove(index);
	}

	public Entity getEntityByIndex(int index) {
		return entities.get(entityLayer.get(index));
	}

	public Entity getEntityByName(String name) {
		return entities.get(name);
	}

	public void removeEntityByName(int index) {

	}

	public void correctCamera(Camera cam, Window window) {
		Vector3f pos = cam.getPosition();

		int w = -width * scale * 2;
		int h = height * scale * 2;

		if (pos.x > -(window.getWidth() / 2) + scale)
			pos.x = -window.getWidth() / 2 + scale;
		if (pos.x < w + (window.getWidth() / 2) + scale)
			pos.x = w + (window.getWidth() / 2) + scale;

		if (pos.y < (window.getHeight() / 2) - scale)
			pos.y = (window.getHeight() / 2) - scale;
		if (pos.y > h - (window.getHeight() / 2) - scale)
			pos.y = h - (window.getHeight() / 2) - scale;
	}

	public void render(TileRenderer render, Shader shader, Camera cam, Window window) {
		renderVisibleTiles(render, shader, cam, window);
		renderEntitys(shader, cam);
	}

	private void renderEntitys(Shader shader, Camera cam) {
		for (int i = 0; i < entityLayer.size(); i++) {
			getEntityByIndex(i).render(shader, cam, this);
		}
	}

	private void renderVisibleTiles(TileRenderer render, Shader shader, Camera cam, Window window) {
		int posX = ((int) cam.getPosition().x + (window.getWidth() / 2)) / (scale * 2);
		int posY = ((int) cam.getPosition().y - (window.getHeight() / 2)) / (scale * 2);

		for (int i = 0; i < view; i++) {
			for (int j = 0; j < view; j++) {
				Tile tile = getTile(i - posX, j + posY);
				if (tile != null) {
					render.renderTile(tile, i - posX, -j - posY, shader, world, cam);
				}

			}
		}
	}

	public void update(float delta, Window window, Camera cam) {
		window.getInput().update();

		addNewEntities();
		newEntities = new LinkedHashMap<String, Entity>();

		if (currentState == WorldStates.WORLD_STATE_PAUSE || currentState == WorldStates.WORLD_STATE_GAMEOVER) {
			updateMenu(delta, window, cam);
			control.update(delta, window, cam, this, (Player)getEntityByName("player"));
		}
		if (isInStartScreen()) {
			cam.getPosition().lerp(getEntityByName("player").transform.pos.mul(-this.getScale(), new Vector3f()), 0.005f);
			updateMenu(delta, window, cam);
			control.update(delta, window, cam, this, (Player)getEntityByName("player"));
			updateEntityStates(delta);
		} else if (isRunning()) {
			if(window.getInput().isKeyReleased(GLFW_KEY_ESCAPE)) {
				showPauseMenu();
				this.currentState = WorldStates.WORLD_STATE_PAUSE;
			}
			updateEntities(delta, window, cam);
			doCollision();
		}
		removeEntitys();
	}

	private void addNewEntities() {
		for (Map.Entry<String, Entity> entry : newEntities.entrySet()) {
			addEntity(entry.getValue());
		}
	}

	public boolean isInStartScreen() {
		return getCurrentState() == WorldStates.WORLD_STATE_START;
	}

	private void updateMenu(float delta, Window window, Camera cam) {
		menu.update(delta, window, cam, this, null);
	}
	
	private void showPauseMenu() {
		menu.setState(2);
		menu.hide = false;
	}
	
	public void showDeathMenu() {
		this.currentState = WorldStates.WORLD_STATE_GAMEOVER;
		menu.setState(1);
		menu.hide = false;
	}

	private void updateEntityStates(float delta) {
		for (Map.Entry<String, Entity> entry : entities.entrySet()) {
			entry.getValue().getCurrentState().update(delta, this, entry.getValue().transform);
		}
	}

	private boolean isRunning() {
		return getCurrentState() == WorldStates.WORLD_STATE_RUNNING;
	}

	private void updateEntities(float delta, Window window, Camera cam) {
		for (Map.Entry<String, Entity> entry : entities.entrySet()) {
			entry.getValue().update(delta, window, cam, this, (Player) getEntityByName("player"));
		}
	}

	private void doCollision() {
		for (int i = 0; i < entityLayer.size(); i++) {
			getEntityByIndex(i).collideWithTiles(this);
			for (int j = i + 1; j < entityLayer.size(); j++) {
				getEntityByIndex(i).collideWithEntity(getEntityByIndex(j));
			}
			getEntityByIndex(i).collideWithTiles(this);
		}
	}

	private void removeEntitys() {
		for (int i = 0; i < entityLayer.size(); i++) {
			if (getEntityByIndex(i).toRemove()) {
				removeEntityByIndex(i);
			}
		}
	}

	public void setTile(Tile tile, int x, int y) {
		tiles[x + y * width] = tile.getID();
		if (tile.isSolid()) {
			boundingBoxes[x + y * width] = new AABB(new Vector2f(x * 2, -y * 2),
					new Vector2f(tile.boxScaleX, tile.boxScaleY));
		} else {
			boundingBoxes[x + y * width] = null;
		}
	}

	public Tile getTile(int x, int y) {
		try {
			return Tile.tiles[tiles[x + y * width]];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public AABB getTileBoundingBox(int x, int y) {
		try {
			return boundingBoxes[x + y * width];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public int getScale() {
		return scale;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Matrix4f getWorldMatrix() {
		return world;
	}

	public float getBossHP(int id) {
		return getEntityByName("boss" + id).timeToLive;
	}

	public int getCurrentState() {
		return currentState;
	}
	
	public void setCurrentState(int stae) {
		currentState = stae;
	}

	public void hideMenu() {
		menu.hide = true;
	}

	public void showControls() {
		control.show = true;
	}

	public void hideControls() {
		control.show = false;
		
	}

}
