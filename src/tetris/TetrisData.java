package tetris;

public class TetrisData {
	public static final int ROW = 20;
	public static final int COL = 10;
	private int data[][];
	private int line;
	private int score = 0;
	private int level = 1;
	
	public TetrisData() {
		data = new int[ROW][COL];
	}
	public int getAt(int x, int y) {
		if(x < 0 || x >= ROW || y < 0 || y >= COL) {
			return 0;
		}
		return data[x][y];
	}
	public void setAt(int x, int y, int v) {
		data[x][y] = v;
	}
	public int getLine() {
		return line;
	}
	
	public synchronized void removeLines() {
		// 수정: 한 번에 여러 줄이 사라지도록 수정
		int linesRemoved = 0; // 사라진 줄 수 초기화
		NEXT: for (int i = ROW - 1; i >= 0; i--) {
			boolean done = true;
			for (int k = 0; k < COL; k++) {
				if (data[i][k] == 0) {
					done = false;
					continue NEXT;
				}
			}
			if (done) {
				line++;
				linesRemoved++; // 사라진 줄 수 증가
				for (int x = i; x > 0; x--) {
					for (int y = 0; y < COL; y++) {
						data[x][y] = data[x - 1][y];
					}
				}
				if (i != 0) {
					for (int y = 0; y < COL; y++) {
						data[0][y] = 0;
					}
				}
			}
		}

		// 수정: 한 번에 채워진 줄 수만큼 빈 줄을 삽입
		for (int i = 0; i < linesRemoved; i++) {
			for (int j = ROW - 1; j > 0; j--) {
				for (int k = 0; k < COL; k++) {
					data[j][k] = data[j - 1][k];
				}
			}
			for (int k = 0; k < COL; k++) {
				data[0][k] = 0;
			}
		}
	}

	public void clear() {
		for(int i = 0; i < ROW; i++) {
			for(int k =0; k < COL; k++) {
				data[i][k] = 0;
			}
		}
	}
	public void dump() {
		for(int i =  0; i < ROW; i++) {
			for(int k = 0; k < COL; k++) {
				System.out.print(data[i][k] + " ");
			}
			System.out.println();
		}
	}
	public int getScore() {
		score = line * 175 * level;
		return score;
	}
	

	
}