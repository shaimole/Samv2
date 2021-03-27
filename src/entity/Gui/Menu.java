package entity.Gui;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import Io.Window;
import entity.Entity;
import entity.EntityState;
import entity.Transform;
import entity.Player.Player;
import render.Animation;
import render.Camera;
import render.Shader;
import world.World;
import static org.lwjgl.glfw.GLFW.*;

public class Menu extends Entity {
	
	private MenuOption [] options;
	public boolean hide = false;
	private int currentMenu = 0;

	
	private int highlighted = 0;
	
	public static int MENU_START = 0;
	public static int MENU_DEATH = 1;
	public static int MENU_PAUSE = 2;

	public Menu(int maxAnimations, Transform transform) {
		super(maxAnimations, transform);
		this.transform.scale.y = 20f;
		this.transform.scale.x = 20f;
		states = GuiStates.getMenuStates();
		options = GuiStates.getMenuOption();
		
	}
	
	public  void setState(int state) {
		currentMenu = state;
	}
	
	private void moveHighlighted(int move) {
		options[highlighted].setHightlighted(false);
		highlighted += move;
		if (highlighted < 0) {
			highlighted = 0;
		}else if (highlighted > 3) {
			highlighted = 3;
		}
	}

	@Override
	public void update(float delta, Window window, Camera cam, World world, Player player) {
		if (hide) {
			return;
		}

		if (currentMenu > 0) {
			useState(GuiStates.MENU_DIM);
		}else  {
			useState(GuiStates.MENU_BLANK);
		}
		if (currentMenu == MENU_START) {
			options[GuiStates.MENU_NEWGAME].state = GuiStates.MENU_NEWGAME;
		
		}
		if (currentMenu == MENU_DEATH) {
			options[GuiStates.MENU_NEWGAME].state = GuiStates.MENU_RESTART;
		}
		
		if (currentMenu == MENU_PAUSE) {
			options[GuiStates.MENU_NEWGAME].state = GuiStates.MENU_CONTINUE;
		}
		
		transform.pos.x = -cam.getPosition().x/world.getScale();
		transform.pos.y = -cam.getPosition().y/world.getScale();
		
		options[GuiStates.MENU_NEWGAME].transform.pos.x  = this.transform.pos.x;
		options[GuiStates.MENU_NEWGAME].transform.pos.y  = this.transform.pos.y;
		options[GuiStates.MENU_NEWGAME].update(delta, window, cam, world, player);
		
		options[GuiStates.MENU_CONTROLS].transform.pos.x  = this.transform.pos.x;
		options[GuiStates.MENU_CONTROLS].transform.pos.y  = this.transform.pos.y;
		options[GuiStates.MENU_CONTROLS].update(delta, window, cam, world, player);
		
		options[GuiStates.MENU_CREDIT].transform.pos.x  = this.transform.pos.x;
		options[GuiStates.MENU_CREDIT].transform.pos.y  = this.transform.pos.y;
		options[GuiStates.MENU_CREDIT].update(delta, window, cam, world, player);
		
		options[GuiStates.MENU_EXIT].transform.pos.x  = this.transform.pos.x;
		options[GuiStates.MENU_EXIT].transform.pos.y  = this.transform.pos.y;
		options[GuiStates.MENU_EXIT].update(delta, window, cam, world, player);
		
		if(window.getInput().isKeyReleased(GLFW_KEY_DOWN)) {
			moveHighlighted(1);
		}else if (window.getInput().isKeyReleased(GLFW_KEY_UP)) {
			moveHighlighted(-1);
		}
		
		if (window.getInput().isKeyReleased(GLFW_KEY_ENTER)) {
			options[highlighted].action(window,world,cam);
		}
		
		options[highlighted].setHightlighted(true);
	}
	
	@Override
	public void render(Shader shader, Camera cam, World world) {
		if (hide) {
			return;
		}
		Matrix4f target = cam.getProjection();
		target.mul(world.getWorldMatrix());
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", transform.getProjection(target));
		getCurrentState().getAnimation().bind(0);
		model.render();
		for(int i = 0; i< options.length;i++) {
			options[i].render(shader, cam, world);
			if (options[i].isHightlighted()) {
				options[i].pointer.render(shader, cam, world);
			}
		}
	}

	@Override
	public Vector2f getMovement(Window window, float delta) {
		return new Vector2f(0,0);
	}

	@Override
	public int getNewState(Vector2f movement, Window window, float delta) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public String getName() {
		return "menu";
	}

}
