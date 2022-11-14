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
	private JRadioButton r1;
	private JRadioButton r2;
	private JRadioButton s1Btn;
	private JRadioButton o1Btn;
	private JRadioButton humOne;
	private JRadioButton comOne;
	private JRadioButton s2Btn;
	private JRadioButton o2Btn;
	private JRadioButton humTwo;
	private JRadioButton comTwo;
	private JTextField sizeSelect;
	private JLabel pl1Score;
	private JLabel pl2Score;
	private int p2Points = 0;
	private int p1Points = 0;
	
	public GUI(Board board) {
		this.board = board;
		setContentPane();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); 
		setTitle("SOS game");
		setVisible(true);  
	}
	
	public int getP1Score() {
		return p1Points;
	}
	
	public int updateP1Score(int points) {
		p1Points = p1Points + points;
		return p1Points;
	}
	
	public int getP2Score() {
		return p2Points;
	}
	
	public int updateP2Score(int points) {
		p2Points = p2Points + points;
		return p2Points;
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
		
		gameStatusBar = new JLabel("Select a gamemode then select the board size to start a game. ");
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
		pl1.setPreferredSize(new Dimension(200,100));
		playerPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		JPanel pl1Details = new JPanel();
		JLabel pl1Text = new JLabel("Player 1: ");
		pl1Score = new JLabel ("Score: " + p1Points);
		pl1Details.add(pl1Text);
		pl1Details.add(pl1Score);
		pl1Score.setVisible(false);
		
		ButtonGroup pl1Controls = new ButtonGroup();
		s1Btn = new JRadioButton("S");
		o1Btn = new JRadioButton("O");
		pl1Controls.add(s1Btn); //Adding the button to the button group
		pl1Controls.add(o1Btn); //Adding the button to the button group
		
		ButtonGroup player1Type = new ButtonGroup();
		humOne = new JRadioButton("Human");
		comOne = new JRadioButton("Computer");
		player1Type.add(humOne);
		player1Type.add(comOne);

		pl1.add(pl1Details);
		pl1.add(s1Btn);
		pl1.add(o1Btn);
		pl1.add(humOne);
		pl1.add(comOne);
		
//		PLAYER 2 CONTROL PANEL		
		JPanel pl2 = new JPanel();
		pl2.setPreferredSize(new Dimension(200,150));
		
		JPanel pl2Details = new JPanel();
		JLabel pl2Text = new JLabel("Player 2: ");
		pl2Score = new JLabel("Score: " + p2Points);
		pl2Details.add(pl2Text);
		pl2Details.add(pl2Score);
		pl2Score.setVisible(false);
		
		ButtonGroup pl2Controls = new ButtonGroup();
		s2Btn = new JRadioButton("S");
		o2Btn = new JRadioButton("O");
		pl2Controls.add(s2Btn); //Adding the button to the button group
		pl2Controls.add(o2Btn); //Adding the button to the button group
		
		ButtonGroup player2Type = new ButtonGroup();
		humTwo = new JRadioButton("Human");
		comTwo = new JRadioButton("Computer");
		player2Type.add(humTwo);
		player2Type.add(comTwo);
		
		pl2.add(pl2Details);
		pl2.add(s2Btn);
		pl2.add(o2Btn);
		pl2.add(humTwo);
		pl2.add(comTwo);
		
		
//		ADDING THE PLAYER 1 AND 2 CONTROL PANELS TO THE MAIN PLAYER CONTROL PANEL
		playerPanel.add(pl1, BorderLayout.NORTH);
		playerPanel.add(pl2, BorderLayout.SOUTH);
		
//		CONTENT FOR GAME MODE
		r1=new JRadioButton("Simple Game");    
		r2=new JRadioButton("General Game");    
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
				board.setMode(1);
				r1.setVisible(false);
				pl1Score.setVisible(true);
				pl2Score.setVisible(true);
				sizeSelect.setEditable(true);
				board.resetMoveCount();
				System.out.println(board.getMode()); 
			}
		});
		
		r1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				board.setMode(0);
				r2.setVisible(false);
				sizeSelect.setEditable(true);
				board.resetMoveCount();
				System.out.println(board.getMode());
			}
		});

//		CONTENT FOR BOARD SELECTION
		JLabel sizeSelectText = new JLabel("Board Size");		
		sizeSelect = new JTextField();
		sizeSelect.setEditable(false);
		sizeSelect.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer newSize = Integer.parseInt(sizeSelect.getText()); 
				board.setBoardSize(newSize);
				board.resetMoveCount();
				sizeSelect.setEditable(false);
				gameStatusBar.setText("Player 1 turn. ");
				updateBoardSize();
				repaint();
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
						 * if the moveCounter is even, Player 1 goes,
						 * if the moveCounter is odd, Player 2 goes.
						 */
						if (board.getMoveCount() % 2 == 0) {
							if (humOne.isSelected()) {
								if (s1Btn.isSelected()) {
									board.makeSMove(rowSelected, colSelected);
									if (board.getMode() == 0) {
										if (board.sgSOSCheck()) {
											gameStatusBar.setText("Player 1 wins.");
										}
										else if (!(board.checkIfFull() && board.sgSOSCheck())) {
											gameStatusBar.setText("Player 2 turn.");
										}
									
										if (board.checkIfFull() && !board.sgSOSCheck()) {
											gameStatusBar.setText("Game Draw.");
										}
									}

									if (board.getMode() == 1) {
										pl1Score.setText("Score: " + updateP1Score(board.ggSOSCheck(rowSelected, colSelected)));
										gameStatusBar.setText("Player 2 turn.");
										if (board.checkIfFull()) {
											if (p1Points > p2Points) {
												gameStatusBar.setText("Player 1 wins.");
											}
											else if (p1Points < p2Points) {
												gameStatusBar.setText("Player 2 wins.");
											}
											else if (p1Points == p2Points) {
												gameStatusBar.setText("Game draw.");
											}
										}
									}
									board.moveCountInc();
									repaint();
								}
								else if (o1Btn.isSelected()){
									board.makeOMove(rowSelected, colSelected);
									if (board.getMode() == 0) {
										if (board.sgSOSCheck()) {
											gameStatusBar.setText("Player 1 wins.");
										}
										else if (!(board.checkIfFull() && board.sgSOSCheck())) {
											gameStatusBar.setText("Player 2 turn.");
										}
									
										if (board.checkIfFull() && !board.sgSOSCheck()) {
										gameStatusBar.setText("Game Draw.");
										}
									}
	
									if (board.getMode() == 1) {
										pl1Score.setText("Score: " + updateP1Score(board.ggSOSCheck(rowSelected, colSelected)));
										gameStatusBar.setText("Player 2 turn.");
										
										if (board.checkIfFull()) {
											if (p1Points > p2Points) {
												gameStatusBar.setText("Player 1 wins.");
											}
											else if (p1Points < p2Points) {
												gameStatusBar.setText("Player 2 wins.");
											}
											else if (p1Points == p2Points) {
												gameStatusBar.setText("Game draw.");
											}
										}
									}
									board.moveCountInc();
									repaint();
								}
							}
							
// IF A COMPUTER IS SELECTED FOR PLAYER 1
							if (comOne.isSelected()) {
								board.makeAutoSMove();
								if (board.getMode() == 0) {
									if (board.sgSOSCheck()) {
										gameStatusBar.setText("Computer 1 wins.");
									}
									else if (!(board.checkIfFull() && board.sgSOSCheck())) {
										gameStatusBar.setText("Player 2 turn.");
									}
								
									if (board.checkIfFull() && !board.sgSOSCheck()) {
									gameStatusBar.setText("Game Draw.");
									}
								}
								
								if (board.getMode() == 1) {
									pl1Score.setText("Score: " + updateP1Score(board.ggSOSCheck(rowSelected, colSelected)));
									gameStatusBar.setText("Player 2 turn.");
									
									if (board.checkIfFull()) {
										if (p1Points > p2Points) {
											gameStatusBar.setText("Computer 1 wins.");
										}
										else if (p1Points < p2Points) {
											gameStatusBar.setText("Player 2 wins.");
										}
										else if (p1Points == p2Points) {
											gameStatusBar.setText("Game draw.");
										}
									}
								}
								board.moveCountInc();
								repaint();
							}	
						}
//						
						
						else {
							if (humTwo.isSelected()) {
								if (s2Btn.isSelected()) {
									board.makeSMove(rowSelected, colSelected);
									if (board.getMode() == 0) {
										if (board.sgSOSCheck()) {
											gameStatusBar.setText("Player 2 wins.");
										}
										else if (!(board.checkIfFull() && board.sgSOSCheck())) {
											gameStatusBar.setText("Player 1 turn.");
										}
									
										if (board.checkIfFull() && !board.sgSOSCheck()) {
											gameStatusBar.setText("Game Draw.");
										}
									}
				
									if (board.getMode() == 1) {
										pl2Score.setText("Score: " + updateP2Score(board.ggSOSCheck(rowSelected, colSelected)));
										gameStatusBar.setText("Player 1 turn.");
										if (board.checkIfFull()) {
											if (p1Points > p2Points) {
												gameStatusBar.setText("Player 1 wins.");
											}
											else if (p1Points < p2Points) {
												gameStatusBar.setText("Player 2 wins.");
											}
											else if (p1Points == p2Points) {
												gameStatusBar.setText("Game draw.");
											}
										}
									
										board.moveCountInc();
										repaint();
									}
									else if (o2Btn.isSelected()){
										board.makeOMove(rowSelected, colSelected);
										if (board.getMode() == 0) {
											if (board.sgSOSCheck()) {
												gameStatusBar.setText("Player 2 wins.");
											}
											else if (!(board.checkIfFull() && board.sgSOSCheck())) {
												gameStatusBar.setText("Player 1 turn.");
											}
									
											if (board.checkIfFull() && !board.sgSOSCheck()) {
												gameStatusBar.setText("Game Draw.");
											}
										}
			
										if (board.getMode() == 1) {
											pl2Score.setText("Score: " + updateP2Score(board.ggSOSCheck(rowSelected, colSelected)));
											gameStatusBar.setText("Player 1 turn.");
											if (board.checkIfFull()) {
												if (p1Points > p2Points) {
													gameStatusBar.setText("Player 1 wins.");
												}
												else if (p1Points < p2Points) {
													gameStatusBar.setText("Player 2 wins.");
												}
												else if (p1Points == p2Points) {
													gameStatusBar.setText("Game draw.");
												}
											}
										}
										board.moveCountInc();
										repaint();
									}
								}
							}
							
							if (comTwo.isSelected()) {
								board.makeAutoOMove();
								if (board.getMode() == 0) {
									if (board.sgSOSCheck()) {
										gameStatusBar.setText("Computer 2 wins.");
									}
									else if (!(board.checkIfFull() && board.sgSOSCheck())) {
										gameStatusBar.setText("Player 1 turn.");
									}
							
									if (board.checkIfFull() && !board.sgSOSCheck()) {
										gameStatusBar.setText("Game Draw.");
									}
								}
								
								if (board.getMode() == 1) {
									pl2Score.setText("Score: " + updateP2Score(board.ggSOSCheck(rowSelected, colSelected)));
									gameStatusBar.setText("Player 1 turn.");
									if (board.checkIfFull()) {
										if (p1Points > p2Points) {
											gameStatusBar.setText("Player 1 wins.");
										}
										else if (p1Points < p2Points) {
											gameStatusBar.setText("Computer 2 wins.");
										}
										else if (p1Points == p2Points) {
											gameStatusBar.setText("Game draw.");
										}
									}
								}
								
								board.moveCountInc();
								repaint();
							}
						}
				}});
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
			for (int row = 0; row < board.getRows() + 1; ++row) {
				g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDTH_HALF,
						CANVAS_WIDTH-1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
			}
			for (int col = 0; col < board.getCols() + 1; ++col) {
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