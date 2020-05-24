package world;

public class Tile {

	public static Tile tiles [] = new Tile[255];
	public static byte numberOfTiles = 0;
	
	public static final Tile floor = new Tile("floor");
	public static final Tile rock = new Tile("rock").setSolid();
	
	private byte id;
	private boolean solid = false;
	private String texture;
	
	public Tile(String texture) {
		this.id = numberOfTiles;
		numberOfTiles++;
		this.texture = texture;
		if (tiles[id] != null) 
			throw new IllegalStateException("Tile is with id " + id + "is alreay in use");
		tiles[id] = this;
	}
	
	public Tile setSolid() {
		solid = true;
		return this;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public byte getID() {
		return id;
	}

	public String getTexture() {
		return texture;
	}
	
}
