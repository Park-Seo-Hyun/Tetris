package tetris;

public class O_type extends Piece {
	public O_type(TetrisData data) {
		super(data);
		c[0] = -1; r[0] = 1;
        c[1] = 0; r[1] = 1;
        c[2] = -1; r[2] = 0;
        c[3] = 0; r[3] = 0;
	}
	public int getType() {
		return 4;
	}
	public int roteType() {
		return 1;
	}
}

