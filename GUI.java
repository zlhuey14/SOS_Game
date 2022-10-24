package application;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import application.Board.Cell;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	public static final int CELL_SIZE = 40;
	public static final int GRID_WIDTH = 5;
	public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;

	public static final int CELL_PADDING = CELL_SIZE / 6;
	public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
	public static final int SYMBOL_STROKE_WIDTH = 4;

	private int CANVAS_WIDTH; 
	private int CANVAS_HEIGHT;

	private GameBoardCanvas gameBoardCanvas;
	private JLabel gameStatusBar;  

	private Board board;
	private JRadioButton s1Btn;
	private JRadioButton o1Btn;
	private JRadioButton s2Btn;
	private JRadioButton o2Btn;
	private int turnCounter = 0; //Int used to determined which players turn it is
	

	public GUI(Board board) {
		this.board = board;
		setContentPane();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); 
		setTitle("SOS game");
		setVisible(true);  
	}
	
//	update the board size
	private void updateBoardSize() {
		CANVAS_WIDTH = CELL_SIZE * board.getRows();  
		CANVAS_HEIGHT = CELL_SIZE * board.getCols();
		repaint();
	}
	
	private void setContentPane() {
		gameBoardCanvas = new GameBoardCanvas();  
		CANVAS_WIDTH = CELL_SIZE * board.getRows();  
		CANVAS_HEIGHT = CELL_SIZE * board.getCols();

		gameBoardCanvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		
		gameStatusBar = new JLabel(" ");
		gameStatusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
		gameStatusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
		
//		PANEL FOR ALL SELECTION
		JPanel panRight = new JPanel(new BorderLayout());
		panRight.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

//		CONTENT FOR PLAYERS
		JPanel playerPanel = new JPanel(); //Panel to hold the controls for player 1 and player 2
		playerPanel.setLayout(new BorderLayout());
		
//		PLAYER 1 CONTROL PANEL			
		JPanel pl1 = new JPanel();
		playerPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		JLabel pl1Text = new JLabel("Player 1: ");
		ButtonGroup pl1Controls = new ButtonGroup();
		s1Btn = new JRadioButton("S");
		o1Btn = new JRadioButton("O");
		pl1Controls.add(s1Btn); //Adding the button to the button group
		pl1Controls.add(o1Btn); //Adding the button to the button group
		pl1.add(pl1Text);
		pl1.add(s1Btn);
		pl1.add(o1Btn);
		
//		PLAYER 2 CONTROL PANEL		
		JPanel pl2 = new JPanel();
		JLabel pl2Text = new JLabel("Player 2: ");
		ButtonGroup pl2Controls = new ButtonGroup();
		s2Btn = new JRadioButton("S");
		o2Btn = new JRadioButton("O");
		pl2Controls.add(s2Btn); //Adding the button to the button group
		pl2Controls.add(o2Btn); //Adding the button to the button group
		pl2.add(pl2Text);
		pl2.add(s2Btn);
		pl2.add(o2Btn);
		
//		ADDING THE PLAYER 1 AND 2 CONTROL PANELS TO THE MAIN PLAYER CONTROL PANEL
		playerPanel.add(pl1, BorderLayout.NORTH);
		playerPanel.add(pl2, BorderLayout.SOUTH);
		
		
		
//		CONTENT FOR GAME MODE
		JRadioButton r1=new JRadioButton("Simple Game");    
		JRadioButton r2=new JRadioButton("General Game");    
		r1.setBounds(75,50,100,30);    
		r2.setBounds(75,200,100,30);    
		ButtonGroup bg=new ButtonGroup();
		bg.add(r1);
		bg.add(r2);
		JPanel panGameMode = new JPanel();
		BoxLayout panGameModeLayout = new BoxLayout(panGameMode, BoxLayout.Y_AXIS);
		panGameMode.setLayout(panGameModeLayout);
		panGameMode.add(r1);
		panGameMode.add(r2);
		r2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				board.changeMode(1);
			}
		});
		
		r1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				board.changeMode(0);
			}
		});
		
//		CONTENT FOR BOARD SELECTION
		JLabel sizeSelectText = new JLabel("Board Size");		
		JTextField sizeSelect = new JTextField();
		sizeSelect.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer newSize = Integer.parseInt(sizeSelect.getText()); 
				board.setBoardSize(newSize);
				updateBoardSize();
			}
		});
		
//		ADD CONTENT TO Panel
		panRight.add(sizeSelect, BorderLayout.CENTER);
		panRight.add(sizeSelectText, BorderLayout.NORTH);
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(gameBoardCanvas, BorderLayout.CENTER);
		contentPane.add(gameStatusBar, BorderLayout.PAGE_END);

//		add panel to contentPane
		contentPane.add(panRight, BorderLayout.PAGE_START);
		contentPane.add(panGameMode, BorderLayout.LINE_END);
		contentPane.add(playerPanel, BorderLayout.WEST);
		
	}

	class GameBoardCanvas extends JPanel {
		GameBoardCanvas(){
			addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {  
						int rowSelected = e.getY() / CELL_SIZE;
						int colSelected = e.getX() / CELL_SIZE;
						/*
						 * player turn is determined by the turnCounter.
						 * if the turnCounter is even, Player 1 goes,
						 * if the turnCounter is odd, Player 2 goes.
						 */
						if (turnCounter % 2 == 0) {
							if (s1Btn.isSelected()) {
								board.makeSMove(rowSelected, colSelected);
								turnCounter++;
							}
							else {
								board.makeOMove(rowSelected, colSelected);
								turnCounter++;
							}
						}
						else {
							if (s2Btn.isSelected()) {
								board.makeSMove(rowSelected, colSelected);
								turnCounter++;
							}
							else {
								board.makeOMove(rowSelected, colSelected);
								turnCounter++;
							}
						}
					repaint();
				}
			});
		}

		@Override
		public void paintComponent(Graphics g) { 
			super.paintComponent(g);   
			setBackground(Color.WHITE);
			drawGridLines(g);
			drawBoard(g);
		}
		
		private void drawGridLines(Graphics g){
			g.setColor(Color.LIGHT_GRAY);
			for (int row = 1; row < board.getRows(); ++row) {
				g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDTH_HALF,
						CANVAS_WIDTH-1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
			}
			for (int col = 1; col < board.getCols(); ++col) {
				g.fillRoundRect(CELL_SIZE * col - GRID_WIDTH_HALF, 0,
						GRID_WIDTH, CANVAS_HEIGHT-1, GRID_WIDTH, GRID_WIDTH);
			}

		}

		private void drawBoard(Graphics g){
			Graphics2D g2d = (Graphics2D)g;
			g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); 
			for (int row = 0; row < board.getRows(); ++row) {
				for (int col = 0; col < board.getCols(); ++col) {
					int x1 = col * CELL_SIZE + CELL_PADDING;
					int y1 = row * CELL_SIZE + CELL_PADDING;
					if (board.getCell(row,col) == Cell.S) {
						g2d.setColor(Color.black);
						int x2 = (col + 1) * CELL_SIZE - CELL_PADDING;
						int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
						g2d.drawString("S", (x1+x2)/2, (y1+y2)/2);
					} else if (board.getCell(row,col) == Cell.O) {
						int x2 = (col + 1) * CELL_SIZE - CELL_PADDING;
						int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
						g2d.setColor(Color.black);
						g2d.drawString("O", (x1+x2)/2, (y1+y2)/2);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUI(new Board()); 
			}
		});
	}
}