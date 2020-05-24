package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import Io.Window;
import collision.AABB;
import entity.Boss;
import entity.Entity;
import entity.Player;
import entity.Transform;
import render.Camera;
import render.Shader;

public class World {
	private final int view = 18;
	private byte[] tiles;
	private int width, height, scale;
	private Matrix4f world;
	private AABB[] boundingBoxes;
	private List<Entity> entities;
	private List<Entity> newEntities;

	public World(String world, Camera camera) {
		try {
			BufferedImage tileSheet = ImageIO.read(new File("./etc/levels/" + world + "/tiles.png"));
			BufferedImage entitiesSheet = ImageIO.read(new File("./etc/levels/" + world + "/entities.png"));

			width = tileSheet.getWidth();
			height = tileSheet.getHeight();
			scale = 64;
			this.world = new Matrix4f().setTranslation(new Vector3f(0));
			this.world.scale(scale);
			entities = new ArrayList<Entity>();
			newEntities = new ArrayList<Entity>();
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
						transform.pos.x = x*2;
						transform.pos.y = -y*2;
						switch (entityIndex) {
						case 1:
							Player player  = new Player(transform);
							entities.add(player);
							camera.getPosition().set(transform.pos.mul(-scale,new Vector3f()));
							break;
							
						case 2:
							Boss boss = new Boss(transform);
							entities.add(boss);
							break;

						default:
							break;
						}
					}
				}
			}

			// TODO
		} catch (IOException e) {

		}
		for(int i =0; i< getWidth() ;i++) {
			setTile(Tile.rock, i, 0);
			setTile(Tile.rock, i, getHeight()-1);
		}
		for (int i = 0; i < getHeight(); i++) {
			setTile(Tile.rock, 0, i);
			setTile(Tile.rock, getWidth()-1, i);
			
		}

	}

	public World() {
		width = 64;
		height = 64;
		scale = 128;

		tiles = new byte[width * height];

		world = new Matrix4f().setTranslation(new Vector3f(0));
		world.scale(scale);

		boundingBoxes = new AABB[width * height];
	}

	public void addEntity(Entity entity) {
		newEntities.add(entity);
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

		for (Entity entity : entities) {
			entity.render(shader, cam, this);
		}
	}

	public void update(float delta, Window window, Camera cam) {
		int count = 0;
		for (Entity entity : entities) {
			count ++;
			entity.update(delta, window, cam, this);
		}
		System.out.println(count + " entitys");
		for (Entity entity : newEntities) {
			entities.add(0,entity);
		}
		newEntities = new ArrayList<Entity>();
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).collideWithTiles(this);
			for (int j = i + 1; j < entities.size(); j++) {
				entities.get(i).collideWithEntity(entities.get(j));
			}
			entities.get(i).collideWithTiles(this);
		}

	}

	public void setTile(Tile tile, int x, int y) {
		tiles[x + y * width] = tile.getID();
		if (tile.isSolid()) {
			boundingBoxes[x + y * width] = new AABB(new Vector2f(x * 2, -y * 2), new Vector2f(1, 1));
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

}
