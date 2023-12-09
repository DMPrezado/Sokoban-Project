package pt.iscte.poo.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
// import java.awt.event.MouseEvent;
// import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.utils.Point2D;

/**
 * @author POO 2018
 * <p>
 * ImageMatrixGUI, manages and displays in its main window a grid of
 * square images (implementations of ImageTile) of the same size. The
 * default image-size is 48x48 and the default grid-size is 10x10.
 * <p>
 * This class also provides a status-bar (also composed of square images
 * of the same size) that will be displayed above the grid.
 * <p>
 * This class is observable and will send an update message everytime a
 * key is pressed.
 * <p>
 * The images that ImageTiles refer to MUST be in a folder called
 * "images" directly under the project folder.
 * <p>
 * ImageTile is required to provide the name of the image (e.g.
 * "XxxX") and its position (in tile coordinates, with 0,0 in the top
 * left corner and increasing to the right in the horizontal axis and
 * downwards in the vertical axis). ImageMatrixGUI will look for an
 * image with that name in the "images" folder (e.g. "XxxX.png") and draw
 * that image in the window.
 * <p>
 * ImageMatrixGUI implements the Singleton pattern.
 */

/**
 * @author lmmn
 */

//Changed to local Observer-Observed pattern 27-Set-2018
public class ImageMatrixGUI extends Observed {

    private static ImageMatrixGUI INSTANCE;

    private final String IMAGE_DIR = "images";
    private final int LABEL_HEIGHT = 20;
    private final int SQUARE_SIZE;

    private  int n_squares_width = 3;
    private  int n_squares_height = 3;

    private JFrame frame;
    private JPanel panel;
    private JLabel info;

    private Map<String, ImageIcon> imageDB = new HashMap<String, ImageIcon>();

    private List<ImageTile> images = new ArrayList<ImageTile>();

    // private Point2D lastMouseCoordinate;
    private boolean mouseClicked;

    private int lastKeyPressed;
    private boolean keyPressed;
    private boolean windowClosed = false; // Added 25-oct-2022

    private int maxLevel;

    private ImageMatrixGUI() {
        SQUARE_SIZE = 50;
        init();
    }

    /**
     * @return Access to the Singleton instance of ImageMatrixGUI
     */
    public static ImageMatrixGUI getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ImageMatrixGUI();
        return INSTANCE;
    }

    /**
     * Setter for the name of the frame
     *
     * @param name Name of application (will be displayed as a frame title in the
     *             top left corner)
     */

    public void setName(final String name) {
        frame.setTitle(name); // Corrected 2-Mar-2016
    }

    private void init() {
        frame = new JFrame();
        panel = new DisplayWindow();
        info = new JLabel();

        panel.setPreferredSize(new Dimension(n_squares_width * SQUARE_SIZE, n_squares_height * SQUARE_SIZE));
        info.setPreferredSize(new Dimension(n_squares_width * SQUARE_SIZE, LABEL_HEIGHT));
//		panel.setPreferredSize(new Dimension(N_SQUARES_WIDTH * SQUARE_SIZE, N_SQUARES_HEIGHT * SQUARE_SIZE));
//		info.setPreferredSize(new Dimension(N_SQUARES_WIDTH * SQUARE_SIZE, LABEL_HEIGHT));
        info.setBackground(Color.BLACK);
        frame.add(panel);
        frame.add(info, BorderLayout.NORTH);
        frame.pack();
        frame.setResizable(false); // Added 27-Feb-2018
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.addWindowListener(new WindowAdapter() { // Added 25-oct-2022
            public void windowClosing(WindowEvent e) {
            	windowClosed = true;
            	notifyObservers();
            }
        });
        
        //sets the IconImage on windows, doesn't work on macOS
        ImageIcon icon = new ImageIcon("icon/Game_Icon.png");
        frame.setIconImage(icon.getImage());

        initImages();

        new KeyWatcher().start();

//		new MouseWatcher().start();

//		new Ticker().start();

        frame.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                lastKeyPressed = e.getKeyCode();
                keyPressed = true;
                releaseObserver();
            }
        });

//        panel.addMouseListener(new MouseListener() {
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                lastMouseCoordinate = new Point2D(e.getPoint());
//                mouseClicked = true;
//                releaseObserver();
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent arg0) {
//            }
//
//            @Override
//            public void mouseExited(MouseEvent arg0) {
//            }
//
//            @Override
//            public void mousePressed(MouseEvent arg0) {
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent arg0) {
//            }
//
//        });
    }

    synchronized void releaseObserver() {
        notifyAll();
    }

    synchronized void waitForKey() throws InterruptedException {
        while (!keyPressed) {
            wait();
        }
        notifyObservers();
        keyPressed = false;
    }

    synchronized void waitForClick() throws InterruptedException {
        while (!mouseClicked) {
            wait();
        }
        notifyObservers();
        mouseClicked = false;
    }

    void tick() throws InterruptedException {
        notifyObservers();
    }

    private void initImages() {
        File dir = new File(IMAGE_DIR);
        for (File f : dir.listFiles()) {
            assert (f.getName().lastIndexOf('.') != -1);
            imageDB.put(f.getName().substring(0, f.getName().lastIndexOf('.')),
                    new ImageIcon(IMAGE_DIR + "/" + f.getName()));
        }
    }

    /**
     * Make the window visible.
     */
    public void go() {
        frame.setVisible(true);
    }

    /**
     * Add a new set of images to the main window.
     *
     * @param newImages images to be added to main window
     * @throws IllegalArgumentException if no image with that name (and a suitable extension) is
     *                                  found the images folder
     */

    public void addImages(final List<ImageTile> newImages) {
        synchronized (images) { // Added 16-Mar-2016
            if (newImages == null)
                throw new IllegalArgumentException("Null list");
            if (newImages.size() == 0)
                return;
            for (ImageTile i : newImages) {
                if (i == null)
                    throw new IllegalArgumentException("Null image");
                if (!imageDB.containsKey(i.getName())) {
                    throw new IllegalArgumentException("No such image in DB " + i.getName());
                }
                addImage(i);
            }
        }
    }

    // Added 2-Mar-2016

    /**
     * Removes the image given as a parameter.
     * <p>
     * Does nothing if there is no match.
     *
     * @param image to be removed (must be the exact same Object and not a copy)
     */

    public void removeImage(final ImageTile image) {
        if (image == null)
            throw new IllegalArgumentException("Null list");
        synchronized (images) { // Added 16-Mar-2016
            images.remove(image);
        }
    }

    // Added 2-Out-2017

    /**
     * Removes the image given as a parameter.
     * <p>
     * Does nothing if there is no match.
     *
     * @param image to be removed (must be the exact same Object and not a copy)
     */

    public void removeImages(final List<ImageTile> newImages) {
        if (newImages == null)
            throw new IllegalArgumentException("Null list");
        synchronized (images) {
            images.removeAll(newImages);
        }
    }

    // Added 2-Mar-2016

    /**
     * Adds image to main window
     *
     * @param image to be added
     */
    public void addImage(final ImageTile image) {
        synchronized (images) { // Added 16-Mar-2016
            if (image == null)
                throw new IllegalArgumentException("Null image");
            if (image.getName() == null)
                throw new IllegalArgumentException("Null image name");
            if (image.getPosition() == null)
                throw new IllegalArgumentException("Null image position");
            if (image.getLayer() >= maxLevel)
                maxLevel = image.getLayer() + 1;
            if (!imageDB.containsKey(image.getName())) {
                throw new IllegalArgumentException("No such image in DB " + image.getName());
            }
            images.add(image);
        }
    }

    /**
     * Clear all images displayed in main window.
     */
    public void clearImages() {
        synchronized (images) { // Added 16-Mar-2016
            images.clear();
        }
    }

    /**
     * Add a new set of images to the status window.
     *
     * @param newImages images to be added to status bar
     * @throws IllegalArgumentException if no image with that name (and a suitable extension) is
     *                                  found the images folder
     */

    public void setStatusMessage(String message) {
    	info.setHorizontalAlignment(SwingConstants.LEFT);
    	info.setVerticalAlignment(SwingConstants.CENTER);
        info.setText(message);
    }
   
    public void setMessage(String Message) {
		JOptionPane.showMessageDialog(panel, Message);
    }

    @SuppressWarnings("serial") // Added 2-Mar-2016
    private class DisplayWindow extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            // System.out.println("Thread " + Thread.currentThread() + "
            // repainting");
            synchronized (images) { // Added 16-Mar-2016
                for (int j = 0; j != maxLevel; j++)
                    for (ImageTile i : images) {
                        if (i.getLayer() == j) {
                            g.drawImage(imageDB.get(i.getName()).getImage(), i.getPosition().getX() * SQUARE_SIZE,
                                    i.getPosition().getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, frame);
                        }
                    }
            }
        }
    }

    private class KeyWatcher extends Thread {
        public void run() {
            try {
                while (true)
                    waitForKey();
            } catch (InterruptedException e) {
            }
        }
    }

//	private class MouseWatcher extends Thread {
//		public void run() {
//			try {
//				while (true)
//					waitForClick();
//			} catch (InterruptedException e) {
//			}
//		}
//	}
//
//	private class Ticker extends Thread {
//		private static final long TICK_TIME = 1000;
//
//		public void run() {
//			try {
//				while (true) {
//					sleep(TICK_TIME);
//					tick();
//				}
//			} catch (InterruptedException e) {
//			}
//		}
//	}

    /**
     * Force scheduling of a new window paint (this may take a while, it does
     * not necessarily happen immediately after this instruction is issued)
     */
    public void update() {
        frame.repaint();
    }

    /**
     * Terminate window GUI
     */
    public void dispose() {
        images.clear();
        imageDB.clear();
        frame.dispose();
    }

    /**
     * Grid dimensions
     *
     * @return the width and height of the image grid
     */
    public Dimension getGridDimension() {
//		return new Dimension(N_SQUARES_WIDTH, N_SQUARES_HEIGHT);
        return new Dimension(n_squares_width, n_squares_height);
    }

    public void setSize(int i, int j) {
        n_squares_width = i;
        n_squares_height = j;
        if (INSTANCE != null) {
            //This is a workaround to allow dynamic resizing
            INSTANCE.panel.setPreferredSize(new Dimension(n_squares_width * INSTANCE.SQUARE_SIZE, n_squares_height * INSTANCE.SQUARE_SIZE));
            INSTANCE.info.setPreferredSize(new Dimension(n_squares_width * INSTANCE.SQUARE_SIZE, INSTANCE.LABEL_HEIGHT));
            INSTANCE.frame.setSize(INSTANCE.frame.getPreferredSize());
        }
    }

    public boolean isWithinBounds(Point2D p) {
        return p.getX() >= 0 && p.getY() >= 0 && p.getX() < n_squares_width && p.getY() < n_squares_height;
    }

    public synchronized int keyPressed() {
        return lastKeyPressed;
    }

    public synchronized boolean wasWindowClosed() { // Added 25-out-2022
        return windowClosed;
    }
    
    public String askUser(String question) { // Added 25-out-2022
    	return JOptionPane.showInputDialog(question);
    }
    
}
