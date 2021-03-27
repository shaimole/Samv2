package entity.Gui;

import entity.EntityState;
import entity.Transform;
import render.Animation;

public class GuiStates {

	public static final int BAR_DEFAULT = 0;
	public static final int BAR_SIZE = 1;
	
	public static EntityState [] getBarStates() {
		EntityState[] states = new EntityState[BAR_SIZE];
		states[0] = new EntityState(BAR_DEFAULT, new Animation(1, 1, "bars/hp").dontLoop());	
		return states;
	}
	
	public static final int BORDER_DEFAULT = 0;
	public static final int BORDER_SIZE = 1;

	public static EntityState[] getBorderStates() {
		EntityState[] states = new EntityState[BORDER_SIZE];
		states[0] = new EntityState(BORDER_DEFAULT, new Animation(1, 1, "bars/hp Border").dontLoop());	
		return states;
	}
	
	public static final int SCREEN_START = 0;
	public static final int SCREEN_RESTART = 1;
	public static final int SCREEN_BLANK = 1;
	public static final int SCREEN_SIZE = 3;
	
	
	
	public static final int MENU_BLANK = 0;
	public static final int MENU_DIM = 1;
	public static final int MENU_SIZE = 2;

	public static EntityState[] getScreenStates() {
		EntityState[] states = new EntityState[SCREEN_SIZE];
		states[0] = new EntityState(SCREEN_START, new Animation(1, 1, "screen/start").dontLoop());
		states[1] = new EntityState(SCREEN_RESTART, new Animation(1, 1, "screen/restart").dontLoop());
		states[2] = new EntityState(SCREEN_BLANK, new Animation(1, 1, "screen/blank").dontLoop());
		return states;
	}
	
	public static EntityState[] getMenuStates() {
		EntityState[] states = new EntityState[MENU_SIZE];
		states[MENU_BLANK] = new EntityState(MENU_BLANK, new Animation(1, 1, "screen/blank").dontLoop());
		states[MENU_DIM] = new EntityState(MENU_DIM, new Animation(1, 1, "screen/dim").dontLoop());
		return states;
	}

	
	public static final int MENU_NEWGAME = 0;
	public static final int MENU_CONTROLS = 1;
	public static final int MENU_CONTINUE = 4;
	public static final int MENU_CREDIT = 2;
	public static final int MENU_EXIT = 3;
	public static final int MENU_RESTART =5;
	public static final int MENU_OPIONS_SIZE = 6;
	
	public static EntityState[] getMenuOptionOptions() {
		EntityState[] options = new EntityState[MENU_OPIONS_SIZE];
		options[MENU_NEWGAME] = new EntityState(MENU_NEWGAME, new Animation(0,1, 1, "screen/start").dontLoop());
		options[MENU_CONTROLS] = new EntityState(MENU_CONTROLS, new Animation(4,5, 1, "screen/start").dontLoop());
		options[MENU_CONTINUE] = new EntityState(MENU_CONTINUE, new Animation(1,2, 1, "screen/start").dontLoop());
		options[MENU_EXIT] = new EntityState(MENU_EXIT, new Animation(2,3, 1, "screen/start").dontLoop());
		options[MENU_CREDIT] = new EntityState(MENU_CREDIT, new Animation(3,4, 1, "screen/start").dontLoop());
		options[MENU_RESTART] = new EntityState(MENU_RESTART, new Animation(5,6, 1, "screen/start").dontLoop());
		return options;
	}
	
	public static MenuOption[] getMenuOption() {
		MenuOption [] options = new MenuOption[4];
		options[MENU_NEWGAME] = new MenuOption(MENU_OPIONS_SIZE, new Transform(),MENU_NEWGAME).setPos(0);
		options[MENU_CONTROLS] = new MenuOption(MENU_OPIONS_SIZE, new Transform(),MENU_CONTROLS).setPos(1);
		options[MENU_CREDIT] = new MenuOption(MENU_OPIONS_SIZE, new Transform(),MENU_CREDIT).setPos(2);
		options[MENU_EXIT] = new MenuOption(MENU_OPIONS_SIZE, new Transform(),MENU_EXIT).setPos(3);
		return options;
	}
	
	public static final int POINTER = 0;
	public static final int POINTER_SIZE = 1;
	
	public static EntityState[] getMenuPointerOptions() {
		EntityState[] options = new EntityState[POINTER_SIZE];
		options[POINTER] = new EntityState(POINTER, new Animation(0,1, 1, "screen/pointer").dontLoop());
		return options;
	}

	public static EntityState[] getControlStates() {
		EntityState[] options = new EntityState[POINTER_SIZE];
		options[0] = new EntityState(1, new Animation(0,1, 1, "screen/controls").dontLoop());
		return options;
	}
	
}
