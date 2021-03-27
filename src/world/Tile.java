package world;

public class Tile {

	public static Tile tiles [] = new Tile[255];
	public static byte numberOfTiles = 0;
	
	public static final Tile floor = new Tile("floor");
	public static final Tile rock = new Tile("rock").setSolid().setBoxScaleY(0.5f).setBoxScaleX(0.5f);
	public static final Tile floorBroken = new Tile("floorBroken");
	public static final Tile boundBottom = new Tile("boundBottom").setSolid();
	public static final Tile boundingTop = new Tile("boundingTop").setSolid();
	public static final Tile boundLeft = new Tile("boundLeft").setSolid();
	public static final Tile boundRIght = new Tile("boundRIght").setSolid();
	public static final Tile boundWrapLeftDown = new Tile("boundWrapLeftDown").setSolid();
	public static final Tile boundWrapLeftUp = new Tile("boundWrapLeftUp").setSolid();
	public static final Tile boundWrapRightDown = new Tile("boundWrapRightDown").setSolid();
	public static final Tile boundWrapRightUp = new Tile("boundWrapRightUp").setSolid();
	public static final Tile wallDeco1 = new Tile("wallDeco1").setSolid().setBoxScaleY(0.5f).setBoxScaleX(0.5f);;
	public static final Tile wallDeco2 = new Tile("wallDeco2").setSolid().setBoxScaleY(0.5f).setBoxScaleX(0.5f);;
	public static final Tile wallTop = new Tile("wallTop").setBoxScaleY(0.4f).setSolid();
	public static final Tile VOID = new Tile("void").setSolid();
	public static final Tile floorcarpetL = new Tile("floorcarpetL");
	public static final Tile floorcarpetLR = new Tile("floorcarpetLR");
	public static final Tile pit = new Tile("pit").setSolid();
	public static final Tile rock2 = new Tile("rock2").setSolid();
	public static final Tile rock3 = new Tile("rock3").setSolid();
	public static final Tile barrels = new Tile("barrels").setSolid();
	public static final Tile floorcarpetLM = new Tile("floorcarpetLM");

	private byte id;
	private boolean solid = false;
	private String texture;
	
	public  float boxScaleX = 1.0f;
	public  float boxScaleY = 1.0f;
	
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
	
	public Tile setBoxScaleX(float scale ) {
		boxScaleX = scale;
		return this;
	}
	
	public Tile setBoxScaleY(float scale ) {
		boxScaleY = scale;
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
