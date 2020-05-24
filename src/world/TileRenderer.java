package world;

import java.util.HashMap;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import render.Camera;
import render.Model;
import render.Shader;
import render.Texture;

public class TileRenderer {

	private HashMap<String, Texture> tileTextures;
	private Model model;

	float[] vertices = new float[] { -1f, 1f, 0, 1f, 1f, 0, 1f, -1f, 0, -1f, -1f, 0, };

	float[] textureCoords = new float[] { 0, 0, 1, 0, 1, 1, 0, 1, };

	int[] indices = new int[] { 0, 1, 2, 2, 3, 0 };

	public TileRenderer() {
		tileTextures = new HashMap<String, Texture>();
		model = new Model(vertices, textureCoords, indices);

		for (int i = 0; i < Tile.tiles.length; i++) {
			if (Tile.tiles[i] == null)
				continue;
			String texture = Tile.tiles[i].getTexture();
			if (!tileTextures.containsKey(texture)) {
				tileTextures.put(texture, new Texture("/tiles/" + texture + ".png"));
			}
		}
	}

	public void renderTile(Tile tile, int x, int y, Shader shader, Matrix4f world, Camera cam) {
		shader.bind();
		String texKey = tile.getTexture();
		if (tileTextures.containsKey(texKey))
			tileTextures.get(texKey).bind(0);

		Matrix4f tilePos = new Matrix4f().translate(new Vector3f(x*2, y*2, 0));
		Matrix4f target = new Matrix4f();

		cam.getProjection().mul(world, target);
		target.mul(tilePos);

		shader.setUniform("sampler", 0);
		shader.setUniform("projection", target);
		model.render();

	}

}
