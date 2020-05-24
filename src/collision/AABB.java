package collision;

import org.joml.Vector2f;

import core.Utils;

public class AABB {
	private Vector2f center, halfExtend;

	public AABB(Vector2f center, Vector2f halfExtend) {
		this.center = center;
		this.halfExtend = halfExtend;
	}

	public Collision getCollision(AABB other) {
		Vector2f distance = getAbsoluteDistanceTo(other);
		return new Collision(distance, Utils.isNegative(distance));
	}

	private Vector2f getAbsoluteDistanceTo(AABB other) {
		Vector2f distance = getDistanceCenters(other);
		distance = Utils.makeAbsolute(distance);
		return subHitboxSizesFrom(distance, other);
	}

	private Vector2f getDistanceCenters(AABB other) {
		return other.center.sub(center, new Vector2f());
	}

	private Vector2f subHitboxSizesFrom(Vector2f distance, AABB other) {
		return distance.sub(halfExtend.add(other.halfExtend, new Vector2f()));
	}


	public void correctPosition(AABB other, Collision collision) {
		Vector2f correction = other.center.sub(center, new Vector2f());
		
		if (isCollidingXAxis(collision)) {
			correctSelfX(correction,collision);
		} else {
			correctSelfY(correction,collision);
		}
	}
	
	private boolean isCollidingXAxis(Collision collision) {
		return collision.distance.x > collision.distance.y;
	}
	
	private void correctSelfX(Vector2f correction, Collision collision) {
		if (correction.x > 0) {
			center.add(collision.distance.x, 0);
		} else {
			center.add(-collision.distance.x, 0);
		}
	}
	
	private void correctSelfY(Vector2f correction, Collision collision) {
		if (correction.y > 0) {
			center.add(0, collision.distance.y);
		} else {
			center.add(0, -collision.distance.y);
		}
	}

	public Vector2f getCenter() {
		return center;
	}

	public void setCenter(Vector2f center) {
		this.center = center;
	}

	public Vector2f getHalfExtend() {
		return halfExtend;
	}

	public void setHalfExtend(Vector2f halfExtend) {
		this.halfExtend = halfExtend;
	}
}
