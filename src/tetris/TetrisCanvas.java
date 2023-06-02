package tetris;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TetrisCanvas extends JPanel implements Runnable, KeyListener {
	protected Thread worker;
	protected Color colors[];
	protected int w = 25;
	protected TetrisData data;
	protected int margin = 20;
	protected boolean stop, makeNew;
	protected Piece current;
	protected Piece next;
	protected int interval = 2000;
	protected int level = 2;
	private JTextField 라인 = new JTextField();
	private JTextField 점수 = new JTextField();
	private JSplitPane splitpane = new JSplitPane();
	private JPanel panel = new JPanel();
	
	protected boolean value;
	
	public TetrisCanvas() {
		data = new TetrisData();
		addKeyListener(this);
		colors = new Color[9];
		colors[0] = new Color(80, 80, 80);
		colors[1] = new Color(255, 0, 0);
		colors[2] = new Color(0, 255, 0);
		colors[3] = new Color(0, 200, 255);
		colors[4] = new Color(255, 255, 0);
		colors[5] = new Color(255, 150, 0);
		colors[6] = new Color(210, 0, 240);
		colors[7] = new Color(40, 0, 240);
		colors[8] = new Color(0, 0, 0);
	}
	public void start() {
		data.clear();
		worker = new Thread(this);
		worker.start();
		makeNew = true;
		stop = false;
		value = false;
		requestFocus();
		repaint();
	}
	
	
	public void stop() {
		stop = true;
		current = null;
	}
	
	
	public void paint(Graphics g) {
	    super.paint(g);
	    this.add(panel); 
	    panel.setLayout(null); 
	    panel.setBounds(270, 270, 200, 150); 
	    splitpane.setResizeWeight(0.5);
              splitpane.setOrientation(JSplitPane.VERTICAL_SPLIT); 
	    splitpane.setBounds(12, 24, 189, 84); 
	    panel.add(splitpane); 
	    splitpane.setLeftComponent(점수); 
	    점수.setColumns(10); 
	    splitpane.setRightComponent(라인); 
	    라인.setColumns(10); 
	    점수.setText("     점수 : " + data.getScore()); 
	    라인.setText("     라인 : " + data.getLine());

		for(int i = 0; i < TetrisData.ROW; i++) {
			for(int k = 0; k < TetrisData.COL; k++) {
				if(data.getAt(i, k) == 0) {
					g.setColor(colors[data.getAt(i,k)]);
					g.draw3DRect(margin/2 + w * k, margin/2+w*i,w,w,true);
				}
				 else {
						g.setColor(colors[data.getAt(i,k)]);
						g.fill3DRect(margin/2+w*k, margin/2+w*i,w,w,true);
					        }
					}
				}
				if(current != null) {
					for(int i = 0; i < 4; i++) {
					    g.setColor(colors[current.getType()]);
		g.fill3DRect(margin/2+w*(current.getX()+current.c[i]),margin/2+w*(current.getY()+current.r[i]),w,w,true);
					    g.setColor(colors[next.getType()]);
		g.fill3DRect(220+w*(next.getX()+next.c[i]),90+w*(next.getY()+next.r[i]),w,w,true);
					}
				}
				g.setColor(colors[8]);
				g.draw3DRect(300, 50, 130, 130, true);
				g.drawString("미 리 보 기", 320, 40);
			}
			public Dimension getPreferredSize() {
				int tw = w * TetrisData.COL + margin;
				int th = w * TetrisData.ROW + margin;
				return new Dimension(tw,th);
			}
			public void run() {
				   int random, random2;
				    random2 = (int)(Math.random() * Integer.MAX_VALUE) % 7;
				    
				    while(!stop) {
				        try {
				            if(makeNew) {
				                if (value == false) {
				                    random = (int)(Math.random()*Integer.MAX_VALUE) % 7;
				                    value = true;
				                } else {
				                    random = random2;
				                    random2 = (int)(Math.random()*Integer.MAX_VALUE) % 7;
				                }
				                
				                switch(random) {
				                    case 0 :
				                        current = new Bar(data);
				                        break;
				                    case 1 :
				                        current = new Tee(data);
				                        break;
				                    case 2 :
				                        current = new El(data);
				                        break;
				                    case 3 :
				                        current = new O_type(data);
				                        break;
				                    case 4 :
				                        current = new S_type(data);
				                        break;
				                    case 5 :
				                        current = new Z_type(data);
				                        break;
				                    case 6 :
				                        current = new J_type(data);
				                        break;
				                    default :
				                        if(random % 6 == 0) {
				                            current = new Tee(data);
				                        } else {
				                            current = new El(data);
				                            current = new O_type(data);
				                            current = new S_type(data);
				                            current = new Z_type(data);
				                            current = new J_type(data);
				                        }
				                }
				                
				                random2 = (int)(Math.random() * Integer.MAX_VALUE) % 7;
				                
				                switch(random2) {
				                    case 0 :
				                        next = new Bar(data);
				                        break;
				                    case 1 :
				                        next = new Tee(data);
				                        break;
				                    case 2 :
				                        next = new El(data);
				                        break;
				                    case 3 :
				                        next = new O_type(data);
				                        break;
				                    case 4 :
				                        next = new S_type(data);
				                        break;
				                    case 5 :
				                        next = new Z_type(data);
				                        break;
				                    case 6 :
				                        next = new J_type(data);
				                        break;
				                    default :
				                        if(random % 6 == 0) {
				                            next = new Tee(data);
				                        } else {
				                            next = new El(data);
				                            next = new O_type(data);
				                            next = new S_type(data);
				                            next = new Z_type(data);
				                            next = new J_type(data);
				                        }
				                }
				                
				                makeNew = false;
				            } else {
				                if(current.moveDown()) {
				                    makeNew = true;
				                    if(current.copy()) {
				                        stop();
				                        int score = data.getLine() * 175 * level;
				                        JOptionPane.showMessageDialog(this, "게임끝\n점수 : " + data.getScore() + "\n라인 : " + data.getLine());
				                    }
				                    current = null;
				                }
				                data.removeLines();
				            }
				            
				            repaint();
				            Thread.currentThread().sleep(interval / level);
				        } catch(Exception e) { }
				    }
		}
	          public void keyPressed(KeyEvent e) {
			if(current == null) {
				return;
			}
	                    switch(e.getKeyCode()) {
			case 32 :
				current.enterDown();
				repaint();
				break;
			case 37 :
				current.moveLeft();
				repaint();
				break;
			case 39 :
				current.moveRight();
				repaint();
				break;
			   case 38 :
					current.rotate();
					repaint();
					break;
				case 40 :
					boolean temp = current.moveDown();
					if(temp) {
						makeNew = true;
						if(current.copy()) {
							stop();
							int score = data.getLine()*175*level;
							JOptionPane.showMessageDialog(this,"게임끝\n점수: "+score);
						}
					}
					data.removeLines();
					repaint();
				}
			}
			public void keyReleased(KeyEvent e) { }
			public void keyTyped(KeyEvent e) { }
		}