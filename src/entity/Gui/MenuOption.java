package entity.Gui;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import java.awt.Desktop;
import java.io.File;

import org.joml.Vector2f;

import Io.Window;
import entity.Entity;
import entity.Transform;
import entity.Player.Player;
import render.Camera;
import world.World;
import world.WorldStates;

public class MenuOption extends Entity {

	MenuPointer pointer;

	private boolean hightlighted;

	private int pos;

	public int state;

	public MenuOption setPos(int pos) {
		this.pos = pos;
		return this;
	}

	public MenuOption(int maxAnimations, Transform transform, int useState) {
		super(maxAnimations, transform);
		this.pointer = new MenuPointer(0, transform);
		states = GuiStates.getMenuOptionOptions();
		this.state = useState;
		useState(useState);
	}

	@Override
	public void update(float delta, Window window, Camera cam, World world, Player player) {
		useState(state);
		this.transform.scale.x = 3;
		this.transform.scale.y = 2.25f;
		this.transform.pos.y += 3f - (float) pos * 2;
		pointer.transform = this.transform;
		pointer.update(delta, window, cam, world, player);

	}

	@Override
	public Vector2f getMovement(Window window, float delta) {
		return new Vector2f(0, 0);
	}

	@Override
	public int getNewState(Vector2f movement, Window window, float delta) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		return null;
	}

	public boolean isHightlighted() {
		return hightlighted;
	}

	public void setHightlighted(boolean hightlighted) {
		this.hightlighted = hightlighted;
	}

	public void action(Window window, World world, Camera cam) {
		if (this.state == GuiStates.MENU_EXIT) {
			glfwSetWindowShouldClose(window.getHandle(), true);
		}

		if (this.state == GuiStates.MENU_RESTART) {
			world.makeNew("test", cam);
			world.hideMenu();
			world.hideControls();
			world.setCurrentState(WorldStates.WORLD_STATE_RUNNING);

		}

		if (this.state == GuiStates.MENU_NEWGAME) {
			world.setCurrentState(WorldStates.WORLD_STATE_RUNNING);
			world.hideControls();
			world.hideMenu();
		}

		if (this.state == GuiStates.MENU_CONTINUE) {
			world.setCurrentState(WorldStates.WORLD_STATE_RUNNING);
			world.hideControls();
			world.hideMenu();
		}

		if (this.state == GuiStates.MENU_CREDIT) {
			{
				try {
					File file = new File("./etc/credit.txt");
					if (!Desktop.isDesktopSupported())// check if Desktop is supported by Platform or not
					{
						System.out.println("not supported");
						return;
					}
					Desktop desktop = Desktop.getDesktop();
					if (file.exists())
						desktop.open(file); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (this.state == GuiStates.MENU_CONTROLS) {
				world.showControls();
			}

		}

	}
}
