package dkeep.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.lang.Object;
import java.util.HashSet;
import java.util.Iterator;

import com.sun.istack.internal.localization.NullLocalizable;
import dkeep.cli.Interaction;
import dkeep.logic.*;
import jdk.nashorn.internal.scripts.JO;

public class CustomizeMap extends JPanel implements MouseListener, ChangeListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static GraphicsBank graphics;
	private static JPanel mapPanel;
	private static JPanel btnsPanel;
    private static JPanel slidersPanel;
	private static JButton btnWall;
	private static JButton btnHero;
	private static JButton btnOgre;
	private static JButton btnDoor;
	private static JButton btnKey;
    private static JButton btnRing;
    private static JButton restore;
	private JButton btnReturn;
	private static JPanel dimensionsPanel;
	private static JLabel lblWidth;
	private static JLabel lblHeight;
	private static JSlider heightSlider;
	private static JSlider widthSlider;
	private JLabel label;
	private JButton applyMapDim;

    private enum Objects {
        WALL, HERO, OGRE, RING,
        KEY, DOOR
    }

    Objects o;

	private char[][] map;
	private int lines, columns;
	private static char elementToAdd;
	private Map mapObject;
	private GameElement objectToAdd;
	private GameState game;

	CustomizeMap() throws IOException {

        graphics =  new GraphicsBank();

        lines = 4;
        columns=4;

		setLayout(new GridLayout(0, 2, 0, 0));

		addMouseListener(this);

		initMapPanel();

		btnsPanel = new JPanel();
		btnsPanel.setLayout(new BorderLayout());
		btnsPanel.setBounds(350,80,180,200);
		add(btnsPanel);

		slidersPanel = new JPanel();
        slidersPanel.setLayout(new BorderLayout());
        slidersPanel.setBounds(350,280,180,110);
        add(slidersPanel);

        initializeButtons();

		initDimensionsPanel();

        graphics = new GraphicsBank();
        graphics.loadGraphics();

		mapObject = new Map(map, false, false);
		game = new GameState(mapObject);
		game.levelup();
		game.setClub(null);
		game.setKey(null);
    }

    private void initMapPanel() {
    	mapPanel = new JPanel();
		add(mapPanel);

		map = new char[10][10];

		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++) {
				map[i][j] = ' ';
			}
		}


		objectToAdd = new dkeep.logic.Object();

		repaint();

	}

	private void initDimensionsPanel() {
		dimensionsPanel = new JPanel();
        slidersPanel.add(dimensionsPanel);
		dimensionsPanel.setLayout(new GridLayout(2, 0, 0, 0));

		lblHeight = new JLabel("Height");
		dimensionsPanel.add(lblHeight);

		heightSlider = new JSlider(4,10,4);
		heightSlider.setSnapToTicks(true);
        heightSlider.setPaintLabels(true);
        heightSlider.setPaintTicks(true);
        heightSlider.setMinorTickSpacing(1);
        heightSlider.setMajorTickSpacing(2);
        heightSlider.setValueIsAdjusting(true);
        dimensionsPanel.add(heightSlider);


		lblWidth = new JLabel("Width");
		dimensionsPanel.add(lblWidth);

		widthSlider = new JSlider(4,10,4);
        widthSlider.setSnapToTicks(true);
        widthSlider.setPaintLabels(true);
        widthSlider.setPaintTicks(true);
        widthSlider.setMinorTickSpacing(1);
        widthSlider.setMajorTickSpacing(2);
        widthSlider.setBounds(heightSlider.getX(), 0, heightSlider.getWidth(), heightSlider.getHeight());
        dimensionsPanel.add(widthSlider);


		label = new JLabel("");
		btnsPanel.add(label);
	}

	public void initializeButtons() {
    	initBtnWall();

    	initBtnHero();

    	initBtnOgre();

    	initBtnDoor();

    	initBtnKey();

    	initBtnRing();

    	initBtnApply();

        initBtnReturn();

        initBtnRestore();
    }

	private void initBtnApply() {
		applyMapDim = new JButton("Apply");
		applyMapDim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			    initMap();
			}
		});
		btnsPanel.add(applyMapDim);
	}

	private boolean saveHero(){
        int coord[] = findCoords('H');
        if(game.getHero() !=null)
            game.getHero().setCoordinates(coord[0], coord[1]);
        else {
            JOptionPane.showMessageDialog(null,"Place Hero!");
            return false;
        }

        return true;
    }

	private boolean save(){
        int coord[] = findCoords('C');

        if(!saveHero()) return false;

        findCoords('C');
        if(game.getClub()!= null) {
            game.getClub().setCoordinates(coord[0], coord[1]);
        }else {
            JOptionPane.showMessageDialog(null,"Place Ring!");
            return false;
        }

        coord = findCoords('k');
        if(game.getKey()!=null) {
            game.getKey().setCoordinates(coord[0], coord[1]);
        }else {
            JOptionPane.showMessageDialog(null,"Place Key!");
            return false;
        }

        if(game.getExitDoor()== null){
            JOptionPane.showMessageDialog(null,"Place Door!");
            return false;
        }
        removeFromMap('H');
        removeFromMap('k');
        removeFromMap('C');

        saveOgres();
       return true;
    }

    private void saveOgres(){
        int coord[] = findCoords('O');

        Iterator<Ogre> it = game.getOgres().iterator();

        if( ! game.getOgres().isEmpty())
            do{
                game.getOgres().iterator().next().setCoordinates(coord[0], coord[1]);
                removeFromMap('O');
                it.next();
            }
            while (it.hasNext())
       ;
    }

	private void initBtnReturn() {
		btnReturn = new JButton("Save");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    if(save()) {
                    DungeonKeep.custom = game;
                    game.getClub().setVisible(true);
                    DungeonKeep.returnMainMenu();
                }
			}
		});
		btnsPanel.add(btnReturn);
	}

	private void initBtnKey() {
		btnKey = new JButton("Key");
		btnKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			   o = Objects.KEY;
			   repaint();
			}
		});
		btnsPanel.add(btnKey);
	}

	private void initBtnDoor() {
		btnDoor = new JButton("Door");
		btnDoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                o = Objects.DOOR;
                repaint();
            }
		});
		btnsPanel.add(btnDoor);
	}

	private void initBtnOgre() {
		btnOgre = new JButton("Ogre");
		btnOgre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                o = Objects.OGRE;
                repaint();
            }
		});
		btnsPanel.add(btnOgre);
	}

	private void initBtnHero() {
		btnHero = new JButton("Hero");
		btnHero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    if(game.getHero()==null) o = Objects.HERO;
                repaint();

            }
		});
		btnsPanel.add(btnHero);
	}


	private void initBtnWall() {
		btnWall = new JButton("Wall");
		btnWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                o = Objects.WALL;
                repaint();

            }

		});
		btnsPanel.setLayout(new GridLayout(0, 2, 0, 0));
		btnsPanel.add(btnWall);

	}


    private void initBtnRing() {
        btnRing = new JButton("Ring");
        btnRing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                o = Objects.RING;
                repaint();

            }

        });
        btnsPanel.add(btnRing);

    }


    private void initBtnRestore() {
        restore = new JButton("Restore");
        restore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                restore();
                game.setClub(null);
                repaint();
            }
        });
        btnsPanel.add(restore);
    }

public void restore(){
    mapObject.removeAllChars();
    mapObject.removeAllChars();
    map = new char[heightSlider.getValue()][widthSlider.getValue()];
    mapObject.setMap(map);
    game = new GameState(mapObject);
    game.setKey(null);
    game.setClub(null);
}


	private void initMap() {
		if(game != null ) {
		    game.removeAllOgres();
            mapObject.removeAllChars();
            game.setGuard(null);
            mapObject.removeAllObjs();
        }


		int height = 10, width = 10;

		height = heightSlider.getValue();
		width = widthSlider.getValue();

	    char[][] m = new char[height][width];

		for(int i = 0; i < m.length; i++){
			for(int j = 0; j < m[i].length; j++) {
			    if(i < map.length && j<map[0].length)
				m[i][j] = map[i][j];
			    else m[i][j] = ' ';
            }
		}

		map = m;

		lines = height;
		columns = width;

		mapObject = new Map(map, false, false);
		game.setMap(mapObject);

		this.repaint();
	}

	@Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
        int width = 30;
        int height = 30;
        int x = 30;
        int y = 80;


        g.drawImage(graphics.getFloor(), x, y, width, height, this);

        if(map==null) return;


        for (int i = 0; i<lines;i++) {
            for (int j = 0; j<columns; j++){
                g.drawImage(graphics.getFloor(), x, y, width, height, this);


                switch (map[i][j]){
                    case 'X':
                        g.drawImage(graphics.getWall(), x, y, width, height, this);
                        break;
                    case 'I':
                        g.drawImage(graphics.getClosedDoor(), x, y, width, height, this);
                        break;
                    case 'S':
                        g.drawImage(graphics.getOpenDoor(), x, y, width, height, this);
                        break;
                    case 'H':
                        g.drawImage(graphics.getHero(), x, y, width, height, this);
                        break;
                    case 'O':
                        g.drawImage(graphics.getOgre(), x, y, width, height, this);
                        break;
                    case 'k':
                        g.drawImage(graphics.getKey(), x, y, width, height, this);
                        break;
                    case '*':
                        g.drawImage(graphics.getPotion(), x, y, width, height, this);
                        break;
                    case 'A':
                        g.drawImage(graphics.getClocked_hero(), x, y, width, height, this);
                        break;
                    case 'C':
                        g.drawImage(graphics.getRing(), x, y, width, height, this);
                        break;
                }
                x+=width;
            }
            x=30;
            y+=height;
        }
    }

	public boolean createKey() {
		elementToAdd = 'k';

		dkeep.logic.Object key;

        if(game.getKey() == null) {
            key = new dkeep.logic.Object();
            key.setChar(elementToAdd);
            mapObject.setKey(true);
            game.setKey(key);
            mapObject.addObj(key);
            return true;
        }
        else JOptionPane.showMessageDialog(null, "Key Already Placed");
        return false;
	}

    public void deleteKey() {
	    mapObject.remObj(game.getKey());
	    game.setKey(null);
    }

	public void createDoor() {
		elementToAdd = 'I';

		dkeep.logic.Object door = new dkeep.logic.Object();
		door.setChar('I');
		game.setExitDoor(door);
		mapObject.addObj(door);
	}

	public void deleteDoor(){
        mapObject.remObj(game.getExitDoor());
        game.setExitDoor(null);
    }

	public void createOgre() {
		elementToAdd = 'O';

		Ogre ogre = new Ogre(elementToAdd);
		game.addOgre(ogre);
		mapObject.addChar(ogre);
	}

	public boolean createHero() {
		elementToAdd = 'H';
        Hero hero ;

        if(game.getHero() == null) {
            hero = new Hero(elementToAdd);
            game.setHero(hero);
            return true;
        }
        else JOptionPane.showMessageDialog(null, "Hero Already Placed");
        return false;
	}

	public void createWall() {
		elementToAdd = 'X';

		dkeep.logic.Object wall = new dkeep.logic.Object();
		wall.setChar('X');
		game.addWall(wall);
		mapObject.addObj(wall);
	}

    public boolean createRing() {
        elementToAdd = 'C';
        dkeep.logic.Club ring;

        if(game.getClub() == null) {
            ring = new dkeep.logic.Club('C');
            ring.setVisible(true);
            game.setClub(ring);
            mapObject.addObj(ring);
            return true;
        }
        else JOptionPane.showMessageDialog(null, "Ring Already Placed");

        return false;
    }

	public int[] getCoord(int X, int Y){
	    int[] c = new int[2];
	    int x, y;

        x = (X-30) / 30;
        y = (Y-80) / 30;

        c[0] = x;
        c[1]= y;
        return c;
    }

	public void mouseClicked(MouseEvent e) {
		int X = e.getX(), Y = e.getY(), x, y;

		if( X < 30 || X > (columns * 30 + 30) || Y < 80 || Y > (lines * 30 + 80) )
			return;

		x = getCoord(X,Y)[0];
        y = getCoord(X,Y)[1];

        if(createGameElement()) {

            objectToAdd.setCoordinates(y, x);

            /*if(map[y][x] == elementToAdd) {
                map[y][x] = ' ';
                deleteGameElement();
            }
            else*/ map[y][x] = elementToAdd;
        }

		repaint();
	}

	public void deleteGameElement(){
        switch(o) {
            case KEY:
                deleteKey();
                break;
            case HERO:
                 createHero();
            case RING:
                createRing();
                break;
            case WALL:
                createWall();
                break;
            case OGRE:
                createOgre();
                break;
            case DOOR:
                deleteDoor();
                break;
        }

    }

	public boolean createGameElement(){
	    boolean r = true;
        switch(o) {
            case KEY:
                return createKey();
            case HERO:
                return createHero();
            case RING:
                return createRing();
            case WALL:
                createWall();
                break;
            case OGRE:
                createOgre();
                break;
            case DOOR:
                createDoor();
                break;
        }
        return r;
    }

	public int[] findCoords(char c){
	    int coord[] = {-1,-1};

        for (int i = 0; i < map.length;i++){
            for(int j = 0; j<map[0].length;j++){
                if(map[i][j] == c) {
                    map[i][j] = ' ';
                    coord[0] = i;
                    coord[1] = j;
                    return coord;
                }
            }
        }
        return coord;
    }

	public void removeFromMap(char c){
	    for (int i = 0; i < map.length;i++){
	        for(int j = 0; j<map[0].length;j++){
	            if(map[i][j] == c)
                    map[i][j] = ' ';
            }
        }
    }

	
	public char[][] getMap() {
		return map;
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

    @Override
    public void stateChanged(ChangeEvent e) {
        initMap();
    }
}
