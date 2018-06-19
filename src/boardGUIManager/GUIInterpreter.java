package boardGUIManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pieceRules.Bishop;
import pieceRules.Horse;
import pieceRules.King;
import pieceRules.Pawn;
import pieceRules.Piece;
import pieceRules.Queen;
import pieceRules.Rook;
import turnManagement.Player;

@SuppressWarnings("serial")
public class GUIInterpreter extends JFrame {

    private HashMap<String, ImageIcon> pieceStorageBlack;
    private HashMap<String, ImageIcon> pieceStorageWhite;

    private JLayeredPane layeredPane;
    private JPanel chessBoard;
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private JPanel tile;

    private PieceObjectManager pom;
    private int movingPieceLocation;
    private Piece lastSelectedPiece;

    private JOptionPane switchPopUp;
    private JPanel optionPanel;
    private int optionClicked = -1;
    private MouseAdapter optionMouseAdapter;

    public static void main(String[] args) {
        new GUIInterpreter(new PieceObjectManager());
    }

    public GUIInterpreter(PieceObjectManager pom) {
        this.pom = pom;
        Dimension boardSize = new Dimension(700, 700);
        // This is a Layered Pane for this application
        layeredPane = new JLayeredPane();

        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);

        // Add a chess board to the Layered Pane
        chessBoard = new JPanel();
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout(new GridLayout(8, 8));
        chessBoard.setPreferredSize(boardSize);
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        // Initialize the image storage HashMaps
        pieceStorageBlack = new HashMap<String, ImageIcon>();
        pieceStorageWhite = new HashMap<String, ImageIcon>();

        // Store the png files in a hashmap, so the ImageIcon objects are only created
        // once. Storing JLabels doesn't
        // work; we need to create a new object every time a square is updated, or it
        // will move another instance of
        // the object to the square. There would only be 12 pieces on the board at any
        // time.
        try {

            // Add the black pieces to storage
            pieceStorageBlack.put("b",
                    new ImageIcon(ImageIO.read(GUIInterpreter.class.getResource("/BlackBishop.png"))));
            pieceStorageBlack.put("p", new ImageIcon(ImageIO.read(GUIInterpreter.class.getResource("/BlackPawn.png"))));
            pieceStorageBlack.put("k", new ImageIcon(ImageIO.read(GUIInterpreter.class.getResource("/BlackKing.png"))));
            pieceStorageBlack.put("h",
                    new ImageIcon(ImageIO.read(GUIInterpreter.class.getResource("/BlackHorse.png"))));
            pieceStorageBlack.put("q",
                    new ImageIcon(ImageIO.read(GUIInterpreter.class.getResource("/BlackQueen.png"))));
            pieceStorageBlack.put("r", new ImageIcon(ImageIO.read(GUIInterpreter.class.getResource("/BlackRook.png"))));

            // Add the white pieces to storage
            pieceStorageWhite.put("b",
                    new ImageIcon(ImageIO.read(GUIInterpreter.class.getResource("/WhiteBishop.png"))));
            pieceStorageWhite.put("p", new ImageIcon(ImageIO.read(GUIInterpreter.class.getResource("/WhitePawn.png"))));
            pieceStorageWhite.put("k", new ImageIcon(ImageIO.read(GUIInterpreter.class.getResource("/WhiteKing.png"))));
            pieceStorageWhite.put("h",
                    new ImageIcon(ImageIO.read(GUIInterpreter.class.getResource("/WhiteHorse.png"))));
            pieceStorageWhite.put("q",
                    new ImageIcon(ImageIO.read(GUIInterpreter.class.getResource("/WhiteQueen.png"))));
            pieceStorageWhite.put("r", new ImageIcon(ImageIO.read(GUIInterpreter.class.getResource("/WhiteRook.png"))));

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sets up the board
        for (int i = 0; i < 64; i++) {

            Tile square = new Tile(new BorderLayout());
            chessBoard.add(square);
            tiles.add(square);

            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground(i % 2 == 0 ? Color.white : Color.gray);
            else
                square.setBackground(i % 2 == 0 ? Color.gray : Color.white);
        }

        for (int i = 0; i < 64; i++) {
            updateTile(i);
            tiles.get(i).addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent me) {
                    parseClick(me);
                }
            });
        }

        // Necessary JFrame management
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Clears a tile of JLabels, and adds new ones according to the piece stored in
     * the tile's location.
     * 
     * @param i
     *            the integer location of the tile to be updated.
     */
    public void updateTile(int i) {
        Piece piece = PieceObjectManager.getPiece(i);
        Tile panel = tiles.get(i);
        panel.removeAll();
        if (piece != null) {
            if (piece instanceof Pawn) {
                if (piece.isWhite())
                    panel.add(new JLabel(pieceStorageWhite.get("p")));
                else
                    panel.add(new JLabel(pieceStorageBlack.get("p")));
            }
            if (piece instanceof Rook) {
                if (piece.isWhite())
                    panel.add(new JLabel(pieceStorageWhite.get("r")));
                else
                    panel.add(new JLabel(pieceStorageBlack.get("r")));
            }
            if (piece instanceof Horse) {
                if (piece.isWhite())
                    panel.add(new JLabel(pieceStorageWhite.get("h")));
                else
                    panel.add(new JLabel(pieceStorageBlack.get("h")));
            }
            if (piece instanceof Bishop) {
                if (piece.isWhite())
                    panel.add(new JLabel(pieceStorageWhite.get("b")));
                else
                    panel.add(new JLabel(pieceStorageBlack.get("b")));
            }
            if (piece instanceof Queen) {
                if (piece.isWhite())
                    panel.add(new JLabel(pieceStorageWhite.get("q")));
                else
                    panel.add(new JLabel(pieceStorageBlack.get("q")));
            }
            if (piece instanceof King) {
                if (piece.isWhite())
                    panel.add(new JLabel(pieceStorageWhite.get("k")));
                else
                    panel.add(new JLabel(pieceStorageBlack.get("k")));
            }
        }
        panel.revalidate();
        panel.repaint();
    }

    protected void swapPieceInGUI(MouseEvent me, int destination) {
        // find which piece was clicked
        if (optionClicked >= 0) {
            optionClicked = -1;
            swapPieceInGUI(me, destination);
        }
        for (int i = 0; i < 4; i++) {
            if (me.getSource() == optionPanel.getComponent(i)) {
                JLabel picture = (JLabel) optionPanel.getComponent(i);

                optionClicked = i;

                for (int j = 0; j < 4; j++) {
                    if (j != optionClicked) {
                        optionPanel.getComponent(j).repaint();
                    }
                }

                Graphics2D g = (Graphics2D) picture.getGraphics();
                g.setColor(Color.DARK_GRAY);
                g.drawRect(0, 0, 59, 59);
                g.dispose();
            }
        }
    }

    private void parseClick(MouseEvent me) {
        // repaint();
        tile = (JPanel) me.getSource();
        // First, we need to check if the tile is its normal color. If it is, we know
        // the user is still in the first
        // phase of their turn (choosing a piece to move)
        Tile theTile = (Tile) me.getSource();
        if (!theTile.isEdited()) {
            resetSquareColor();
            int location = getTileLocation();
            if (PieceObjectManager.getPiece(location) == null) {
                lastSelectedPiece = null;
                repaint();
            }
            movingPieceLocation = location;

            // Some checks are necessary to determine if the player has selected an empty
            // square or a piece whose turn
            // it is not.
            if (PieceObjectManager.getPiece(location) != null
                    && Player.getTurn() == PieceObjectManager.getPiece(location).isWhite()) {
                pom.savePieceMatrix();
                ArrayList<Integer> attacks = pom.getAttacks(location);
                attacks.addAll(pom.getMoves(location));
                Iterator<Integer> it = attacks.iterator();

                // Iterate through all of the possible attacks and moves of the selected piece,
                // and remove those
                // that would lead to check or do not prevent checkmate.
                while (it.hasNext()) {
                    Integer attack = it.next();
                    pom.movePiece(movingPieceLocation, attack, true);
                    if (pom.checkAllPiecesForCheck() && PieceObjectManager.getPiece(attack).getPlayer().inCheck()) {
                        it.remove();
                    }
                    pom.undoMove(movingPieceLocation, attack);
                }

                // Repaint ONLY the tiles that can't be moved to. This block of code prevents
                // overlapping moveLists
                // (between the lastSelectedPiece and the currently selected peice) from
                // affecting the Gui.
                if (lastSelectedPiece != null && !PieceObjectManager.getPiece(location).equals(lastSelectedPiece)) {
                    // get the possible moves of the lastselectedpiece and reset those tiles.
                    ArrayList<Integer> lastAttackList = pom.getAttacks(
                            PieceObjectManager.getLocation(lastSelectedPiece.getX(), lastSelectedPiece.getY()));
                    for (int i : lastAttackList) {
                        if (!attacks.contains(new Integer(i))) {
                            tiles.get(i).repaint();
                        }
                    }

                    ArrayList<Integer> lastMoveList = pom.getMoves(
                            PieceObjectManager.getLocation(lastSelectedPiece.getX(), lastSelectedPiece.getY()));
                    for (int i : lastMoveList) {
                        if (!attacks.contains(new Integer(i))) {
                            tiles.get(i).repaint();
                        }
                    }
                }

                lastSelectedPiece = PieceObjectManager.getPiece(location);
                showPossibleMoves(attacks);
            }

            // The tile is edited. This means that the user is in the second phase of their
            // turn: choosing a destination for a moving piece. The user has just clicked
            // the destination.
        } else {
            int destination = getTileLocation();

            pom.movePiece(movingPieceLocation, destination, false);

            // This checks to see if a pawn has moved to the other side of the board.
            if ((PieceObjectManager.getPiece(destination) instanceof Pawn)
                    && ((destination >= 56) || (destination < 8))) {
                switchPieceInMatrix(destination);
            }

            // This checks to see if a King just castled.
            if (PieceObjectManager.getPiece(destination) instanceof King) {
                // Determine if the King's movement is a castle (and which direction!). We need
                // this info to
                // adjust the rook.

                int previousX = PieceObjectManager.getLocation(movingPieceLocation)[0];
                int newX = PieceObjectManager.getLocation(destination)[0];
                int y;
                if (PieceObjectManager.getPiece(destination).isWhite()) {
                    y = 7;
                } else {
                    y = 0;
                }
                if (newX - previousX > 1) {
                    // This means the King has performed a right castle. Move the rook to position.

                    pom.movePiece(7, y, 5, y, false);
                    updateTile(PieceObjectManager.getLocation(7, y));
                    updateTile(PieceObjectManager.getLocation(5, y));
                } else if (previousX - newX > 1) {
                    // This means the King has performed a left castle. Move the rook to position.
                    pom.movePiece(0, y, 3, y, false);
                    updateTile(PieceObjectManager.getLocation(0, y));
                    updateTile(PieceObjectManager.getLocation(3, y));
                }
            }

            updateTile(movingPieceLocation);
            updateTile(destination);
            resetSquareColor();
            Player.changeTurn();

            pom.checkAllPiecesForCheck();
            lastSelectedPiece = null;

            repaint();

        }
    }

    private void switchPieceInMatrix(int destination) {
        optionPanel = new JPanel();
        switchPopUp = new JOptionPane();

        optionMouseAdapter = new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                swapPieceInGUI(me, destination);
            }
        };

        setUpOptionPanel();

        int option = JOptionPane.showConfirmDialog(switchPopUp, optionPanel, "Choose a piece to replace with",
                JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (optionClicked < 0) {
                // Error: the user has not selected a piece
                JOptionPane.showMessageDialog(switchPopUp, "Select a piece.", "Error", JOptionPane.WARNING_MESSAGE);
                setUpOptionPanel();
                switchPieceInMatrix(destination);
            } else {
                if (Player.getTurn()) {
                    if (optionClicked == 0)
                        pom.replacePiece(destination,
                                new Bishop(PieceObjectManager.white, PieceObjectManager.getLocation(destination)[0],
                                        PieceObjectManager.getLocation(destination)[1]));
                    if (optionClicked == 1)
                        pom.replacePiece(destination,
                                new Horse(PieceObjectManager.white, PieceObjectManager.getLocation(destination)[0],
                                        PieceObjectManager.getLocation(destination)[1]));
                    if (optionClicked == 2)
                        pom.replacePiece(destination,
                                new Rook(PieceObjectManager.white, PieceObjectManager.getLocation(destination)[0],
                                        PieceObjectManager.getLocation(destination)[1]));
                    if (optionClicked == 3)
                        pom.replacePiece(destination,
                                new Queen(PieceObjectManager.white, PieceObjectManager.getLocation(destination)[0],
                                        PieceObjectManager.getLocation(destination)[1]));
                } else {
                    if (optionClicked == 0)
                        pom.replacePiece(destination,
                                new Bishop(PieceObjectManager.black, PieceObjectManager.getLocation(destination)[0],
                                        PieceObjectManager.getLocation(destination)[1]));
                    if (optionClicked == 1)
                        pom.replacePiece(destination,
                                new Horse(PieceObjectManager.black, PieceObjectManager.getLocation(destination)[0],
                                        PieceObjectManager.getLocation(destination)[1]));
                    if (optionClicked == 2)
                        pom.replacePiece(destination,
                                new Rook(PieceObjectManager.black, PieceObjectManager.getLocation(destination)[0],
                                        PieceObjectManager.getLocation(destination)[1]));
                    if (optionClicked == 3)
                        pom.replacePiece(destination,
                                new Queen(PieceObjectManager.black, PieceObjectManager.getLocation(destination)[0],
                                        PieceObjectManager.getLocation(destination)[1]));
                }

                updateTile(destination);
            }
        }
    }

    private void setUpOptionPanel() {
        optionPanel.removeAll();

        if (Player.getTurn()) {
            optionPanel.add(new JLabel(pieceStorageWhite.get("b")));
            optionPanel.add(new JLabel(pieceStorageWhite.get("h")));
            optionPanel.add(new JLabel(pieceStorageWhite.get("r")));
            optionPanel.add(new JLabel(pieceStorageWhite.get("q")));
        } else {
            optionPanel.add(new JLabel(pieceStorageBlack.get("b")));
            optionPanel.add(new JLabel(pieceStorageBlack.get("h")));
            optionPanel.add(new JLabel(pieceStorageBlack.get("r")));
            optionPanel.add(new JLabel(pieceStorageBlack.get("q")));
        }

        for (int i = 0; i < 4; i++) {
            optionPanel.getComponent(i).addMouseListener(optionMouseAdapter);
        }

        optionPanel.revalidate();
        optionPanel.repaint();
    }

    private void showPossibleMoves(ArrayList<Integer> possibleMoves) {
        for (Integer location : possibleMoves) {
            if (location >= 0 && location < 64) {
                Tile t = tiles.get(location);
                t.edit();

                Graphics2D g = (Graphics2D) t.getGraphics();
                if (t.getBackground() == Color.WHITE)
                    g.setColor(Color.DARK_GRAY);
                else
                    g.setColor(Color.WHITE);
                g.drawRect(8, 8, 70, 70);
            }
        }
    }

    private int getTileLocation() {
        for (int i = 0; i < 64; i++) {
            if (tile == tiles.get(i)) {
                return i;
            }
        }
        return -1;
    }

    private void resetSquareColor() {
        for (int i = 0; i < 64; i++) {
            tiles.get(i).clearEdit();
        }
    }
}
