package entity;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import Io.Window;
import collision.AABB;
import collision.Collision;
import render.Animation;
import render.Camera;
import render.Model;
import render.Shader;
import world.World;

public abstract class Entity {

	private static Model model;
	protected EntityState [] states;
	public Transform transform;
	protected AABB boundingBox;
	protected int speed = 10;
	
	protected int useState = 0;
	protected int nextState = -1;

	static float[]  vertices = new float[] { -1f, 1f, 0, 1f, 1f, 0, 1f, -1f, 0, -1f, -1f, 0, };

	static float[] textureCoords = new float[] { 0, 0, 1, 0, 1, 1, 0, 1, };

	static int[] indices = new int[] { 0, 1, 2, 2, 3, 0 };

	public Entity(int maxAnimations, Transform transform) {
		this.states = new EntityState[maxAnimations];
		this.transform = transform;
		boundingBox = new AABB(new Vector2f(transform.pos.x, transform.pos.y), new Vector2f(transform.scale.x/2,transform.scale.y/2));
	}
	
	protected Entity getNew() {
		return this;
	}
		
	protected boolean hasCollision() {
		return false;
	}
	protected void addState(EntityState state) {
		states[state.getID()] = state;
	}
	
	public void useState(int index) {
		if (index != this.useState) {
			resetCurrentState();
		}
		this.useState = index;
	}
	
	protected void setNextState(int index) {
		nextState = index;
	}
	
	protected int getNextState() {
		return nextState;
	}
	
	public void move (Vector2f direction) {
		transform.pos.add(new Vector3f(direction,0));
		boundingBox.getCenter().set(transform.pos.x,transform.pos.y);
	}

	public abstract void update(float delta, Window window, Camera cam, World world);
	
	public abstract Vector2f getMovement(Window window, float delta);
	
	public abstract int getNewState(Vector2f movement, Window window, float delta);
	
	public void collideWithTiles(World world) {
		AABB[] boxes = new AABB[25];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				boxes[i + j * 5] = world.getTileBoundingBox(
						(int) (((transform.pos.x / 2) + 0.5f) - (5 / 2)) + i,
						(int) (((-transform.pos.y / 2) + 0.5f) - (5 / 2)) + j
						);
			}
		}
		AABB box = null;
		for (int i = 0; i < boxes.length; i++) {
			if (boxes[i] != null) {
				if (box == null)
					box = boxes[i];

				Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
				Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
				if (length1.lengthSquared() > length2.lengthSquared()) {
					box = boxes[i];
				}
			}
		}
		if (box != null) {
			Collision data = boundingBox.getCollision(box);
			if (data.isIntersecting()) {
				
				boundingBox.correctPosition(box, data);
				transform.pos.set(boundingBox.getCenter(),0);
			}
			
			for (int i = 0; i < boxes.length; i++) {
				if (boxes[i] != null) {
					if (box == null)
						box = boxes[i];

					Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
					Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
					if (length1.lengthSquared() > length2.lengthSquared()) {
						box = boxes[i];
					}
				}
			}
				data = boundingBox.getCollision(box);
				if (data.isIntersecting()) {
					
					boundingBox.correctPosition(box, data);
					transform.pos.set(boundingBox.getCenter(),0);
				}
		}
	}

	public void render(Shader shader, Camera cam, World world) {
		Matrix4f target = cam.getProjection();
		target.mul(world.getWorldMatrix());
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", transform.getProjection(target));
		getCurrentState().getAnimation().bind(0);
		model.render();
	}
	
	protected EntityState getCurrentState() {
		if (!states[useState].isLocked() && nextState != useState && nextState != -1) {
			useState(nextState);
			nextState = -1;
		}
		return states[useState];
	}
	
	public static void initAsset() {
		model = new Model(vertices, textureCoords, indices);
	}
	
	public static void deleteAsset() {
		model = null;
	}

	public void collideWithEntity(Entity entity) {
		if (!this.hasCollision() || !entity.hasCollision()) {
			return;
		}
		Collision collision = boundingBox.getCollision(entity.boundingBox);
		
		if (collision.isIntersecting()) {
			collision.distance.x /= 2;
			collision.distance.y /= 2;
			
			boundingBox.correctPosition(entity.boundingBox, collision);
			transform.pos.set(boundingBox.getCenter().x,boundingBox.getCenter().y,0);
		
			entity.boundingBox.correctPosition(boundingBox, collision);
			entity.transform.pos.set(entity.boundingBox.getCenter().x,entity.boundingBox.getCenter().y,0);
		}
		
	}
	
	protected void resetCurrentState() {
		states[useState].reset();
	}
}