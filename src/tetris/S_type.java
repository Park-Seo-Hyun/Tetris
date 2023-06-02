package tetris;

public class S_type extends Piece {
	public S_type(TetrisData data) {
		super(data);
		c[0] = 0; r[0] = 0;
		c[1] = 1; r[0] = 0;
		c[2] = 0; r[0] = 1;
		c[3] = -1; r[0] = 1;
	}
	public int getType() {
		return 2;
	}
	public int roteType() {
		return 4;
	}
}